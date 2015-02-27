using System.Collections;
using System.Collections.Generic;

namespace ContentGrabber
{
    /// <summary>
    /// A series of non-changing values such as the base request url and various vital element XPaths.
    /// </summary>
    public class Constants
    {

        /// <summary>
        /// The base request url for requesting artist information from metrolyrics.
        /// Actual Value: http://www.metrolyrics.com/artists-
        /// </summary>
        public static readonly string BASE_URL = "http://www.metrolyrics.com/artists-";

        /// <summary>
        /// The url suffix for all requests.
        /// Actual Value: .html
        /// </summary>
        public static readonly string BASE_URL_SUFFIX = ".html";

        /// <summary>
        /// A struct containing all the XPaths for each of the valid Html elements.
        /// </summary>
        public struct XPaths
        {
            public static readonly string ARTIST_ENTRY = "//*[@id=\"main-content\"]/div/div[3]/div/table/tbody/tr/td/a";
            public static readonly string NEXT_BUTTON = "//*[@id=\"main-content\"]/div/p/a[2]";
            public static readonly string SONG_ENTRY = "//*[@id=\"main-content\"]/div/div[3]/div/table/tbody/tr/td/a", SONG_POP_ENTRY = "//*[@id=\"popular\"]/div/table/tbody/tr/td/a";
            public static readonly string SONG_ALBUM = "//*[@id=\"album-name-link\"]", SONG_LYRIC_BODY = "//*[@id=\"lyrics-body-text\"]";
        }

        /// <summary>
        /// Takes in a base url for a specific artist and the current lyric page number and returns a new formatted url for accessing
        /// the specified artist page.
        /// </summary>
        /// <param name="artistUrl">The base url for viewing the songs a specific artist.</param>
        /// <param name="page">The current artist song list page.</param>
        /// <returns>Returns the newly formatted url for accessing the specified artist song list page.</returns>
        public static string BuildArtistLink(string artistUrl, int page)
        {
            return artistUrl.Replace("-lyrics", "-alpage-" + page);
        }
        
        /// <summary>
        /// A class for iterating over all the lower case letters in the alphabet plus the number 1.
        /// </summary>
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
