using HtmlAgilityPack;
using System.Net;
using System.Collections.Generic;
using System.Text.RegularExpressions;

namespace ContentGrabber.Parse
{

    /// <summary>
    /// An IParseProvider implementation for parsing the lyrics and album of a song from the provided HtmlDocument.
    /// </summary>
    public class LyricParser : IParseProvider
    {

        /// <summary>
        /// The DoParse method is called by the HtmlPage during the DoGet method.
        /// It works to parse the retrieved HtmlDocument and return the relevant Html nodes
        /// and their respective values.
        /// </summary>
        /// <param name="doc">The HtmlDocument instance obtained from the AgilityPack get request.</param>
        /// <returns>A dictionary containing all the node/values parsed from the HtmlDocument instance.</returns>
        public Dictionary<string, string> DoParse(HtmlDocument doc)
        {
            Dictionary<string, string> items = new Dictionary<string, string>();
            HtmlNode node = doc.DocumentNode.SelectSingleNode(Constants.XPaths.SONG_ALBUM);
            string al = "";
            if (node == null)
            {
                al = "unknown";
            }
            else
            {
                al = Regex.Replace(WebUtility.HtmlDecode(node.InnerHtml), @"\t|\n|\r", "");
            }
            items.Add("album", al);
            items.Add("lyrics", WebUtility.HtmlDecode(doc.DocumentNode.SelectSingleNode(Constants.XPaths.SONG_LYRIC_BODY).InnerText));
            return items;
        }
    }
}
