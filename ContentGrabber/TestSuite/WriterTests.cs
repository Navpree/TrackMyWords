using System;
using ContentGrabber;
using ContentGrabber.Write;
using ContentGrabber.Parse;
using NUnit.Framework;
using Rhino.Mocks;

namespace TestSuite
{
    [TestFixture]
    public class WriterTests
    {

        private string UNCAUGHT_EXCEPTION_MESSAGE = "An uncaught error occured during provider.DoWrite";

        [Test]
        [ExpectedException(typeof(ArgumentException))]
        public void BadPathTest()
        {
            PageToFile.CreatePathTo("badpathformat");
        }

        [Test]
        [ExpectedException(typeof(WriteException))]
        public void WriterExceptionTest()
        {
            IWriteProvider write = MockRepository.GenerateMock<IWriteProvider>();
            write.Expect((x) => x.DoWrite(null, null)).IgnoreArguments().Throw(new WriteException(""));
            PageToFile pf = new PageToFile(write);
            pf.Write("test.txt", new HtmlPage("http://www.metrolyrics.com/aaron-freeman-lyrics.html", new SongParser()));
        }

        [Test]
        public void WriteNotCalledTest()
        {
            IWriteProvider write = MockRepository.GenerateMock<IWriteProvider>();
            write.Expect((x) => x.DoWrite(null, null)).Repeat.Never();
            PageToFile pf = new PageToFile(write);
            try
            {
                pf.Write("", new HtmlPage("http://www.metrolyrics.com/aaron-freeman-lyrics.html", new SongParser()));
            }
            catch { }
            write.VerifyAllExpectations();
        }

        [Test]
        public void UncaughtWriteExceptionTest()
        {
            IWriteProvider write = MockRepository.GenerateMock<IWriteProvider>();
            write.Expect((x) => x.DoWrite(null, null)).IgnoreArguments().Throw(new Exception());
            PageToFile pf = new PageToFile(write);
            try
            {
                pf.Write("test.txt", new HtmlPage("http://www.metrolyrics.com/aaron-freeman-lyrics.html", new SongParser()));
            }
            catch (WriteException e)
            {
                Assert.AreEqual(UNCAUGHT_EXCEPTION_MESSAGE, e.Message);
                Assert.IsNotNull(e.InnerException);
                Assert.AreEqual(typeof(Exception), e.InnerException.GetType());
            }
        }
    }
}
