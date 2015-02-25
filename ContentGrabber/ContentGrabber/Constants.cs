using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ContentGrabber
{
    public class Constants
    {

        public static readonly string BASE_URL = "http://www.metrolyrics.com/artists-", BASE_URL_SUFFIX = ".html";

        public static readonly string ARTIST_TABLE_ENTRY = "//*[@id=\"main-content\"]/div/div[3]/div/table/tbody/tr/td/a";
        public static readonly string NEXT_BUTTON_XPATH = "//*[@id=\"main-content\"]/div/p/a[2]";
        public static readonly string SONG_ENTRY = "//*[@id=\"main-content\"]/div/div[3]/div/table/tbody/tr/td/a", SONG_POP_ENTRY = "//*[@id=\"popular\"]/div/table/tbody/tr/td/a";
        public static readonly string SONG_ALBUM_XPATH = "//*[@id=\"album-name-link\"]", SONG_LYRIC_BODY = "//*[@id=\"lyrics-body-text\"]";

        public static string BuildArtistLink(string artistUrl, int page)
        {
            return artistUrl.Replace("-lyrics", "-alpage-" + page);
        }
        
        public class Alphabet : IEnumerable<char>
        {
            char[] letters = "abcdefghijklmnopqrstuvwxyz1".ToCharArray();

            IEnumerator IEnumerable.GetEnumerator()
            {
                return GetEnumerator();
            }

            public IEnumerator<char> GetEnumerator()
            {
                foreach (char c in letters)
                {
                    yield return c;
                }
            }
        }
    }
}
