using System;
using ContentGrabber;
using ContentGrabber.Parse;
using NUnit.Framework;
using Rhino.Mocks;

namespace TestSuite
{
    [TestFixture]
    public class HtmlPageTests
    {

        private string UNCAUGHT_EXCEPTION_MESSAGE = "An uncaught error occured during provider.DoParse";

        public static object[] PARSE_ARTIST_PARAMS = {
                                                       new object[]{"http://www.metrolyrics.com/artists-s-3.html", 40},
                                                       new object[]{"http://www.metrolyrics.com/artists-a-1.html", 39},
                                                       new object[]{"http://www.metrolyrics.com/artists-f-2.html", 40}
                                                   };
        public static object[] PARSE_SONG_PARAMS = {
                                                       new object[]{"http://www.metrolyrics.com/aaron-freeman-lyrics.html", 24},
                                                       new object[]{"http://www.metrolyrics.com/ed-sheeran-lyrics.html", 57},
                                                       new object[]{"http://www.metrolyrics.com/obie-trice-lyrics.html", 73}
                                                   };
        public static object[] PARSE_LYRIC_PARAMS = {
                                                        new object[]{"http://www.metrolyrics.com/covert-discretion-lyrics-aaron-freeman.html"},
                                                        new object[]{"http://www.metrolyrics.com/got-some-teeth-lyrics-obie-trice.html"}
                                                    };

        [Test]
        [TestCaseSource("PARSE_SONG_PARAMS")]
        public void ParseSongList(string url, int count)
        {
            HtmlPage page = new HtmlPage(url, new SongParser());
            page.DoGet();
            Assert.NotNull(page.Items);
            Assert.AreEqual(count, page.Items.Count);
        }

        [Test]
        [TestCaseSource("PARSE_ARTIST_PARAMS")]
        public void ParseArtistList(string url, int count)
        {
            HtmlPage page = new HtmlPage(url, new ArtistParser());
            page.DoGet();
            Assert.NotNull(page.Items);
            Assert.AreEqual(count, page.Items.Count);
        }

        [Test]
        [TestCaseSource("PARSE_LYRIC_PARAMS")]
        public void ParseSongLyrics(string url)
        {
            HtmlPage page = new HtmlPage(url, new LyricParser());
            page.DoGet();
            Assert.NotNull(page.Items);
            Assert.IsTrue(page.Items.ContainsKey("lyrics"));
        }

        [Test]
        public void ConfirmNextPage()
        {
            HtmlPage page = new HtmlPage("http://www.metrolyrics.com/artists-a-1.html", new ArtistParser());
            page.DoGet();
            Assert.NotNull(page.Items);
            Assert.IsTrue(page.HasNextPage);
        }

        [Test]
        public void ParseNotCalled()
        {
            IParseProvider parser = MockRepository.GenerateMock<IParseProvider>();
            parser.Expect((x) => x.DoParse(null)).Repeat.Never();
            HtmlPage page = new HtmlPage("test", parser);
            try
            {
                page.DoGet();
            }
            catch { }
            parser.VerifyAllExpectations();
        }

        [Test]
        [ExpectedException(typeof(UnexpectedPageException))]
        public void HandleParseException()
        {
            IParseProvider parser = MockRepository.GenerateMock<IParseProvider>();
            parser.Expect((x) => x.DoParse(null)).IgnoreArguments().Throw(new UnexpectedPageException("Test Exception"));
            HtmlPage page = new HtmlPage("https://portfolio-andrewsstuff.rhcloud.com/", parser);
            page.DoGet();
        }

        [Test]
        public void HandleUncaughtParseException()
        {
            IParseProvider parser = MockRepository.GenerateMock<IParseProvider>();
            parser.Expect((x) => x.DoParse(null)).IgnoreArguments().Throw(new Exception());
            HtmlPage page = new HtmlPage("https://portfolio-andrewsstuff.rhcloud.com/", parser);
            try
            {
                page.DoGet();
            }
            catch (UnexpectedPageException e)
            {
                Assert.AreEqual(UNCAUGHT_EXCEPTION_MESSAGE, e.Message);
                Assert.IsNotNull(e.InnerException);
                Assert.AreEqual(typeof(Exception), e.InnerException.GetType());
            }
        }
    }
}
