using HtmlAgilityPack;
using System.Net;
using System.Collections.Generic;
using System.Text.RegularExpressions;

namespace ContentGrabber.Parse
{

    /// <summary>
    /// IParseProvider implementation for parsing a collection of songs from the provided HtmlDocument.
    /// </summary>
    public class SongParser : IParseProvider
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
            HtmlNodeCollection nodes = doc.DocumentNode.SelectNodes(Constants.XPaths.SONG_POP_ENTRY);
            if (nodes == null || nodes.Count == 0)
            {
                throw new UnexpectedPageException("Could not locate any nodes using the SONG_POP_ENTRY_XPATH.");
            }
            foreach (HtmlNode node in nodes)
            {
                string text = WebUtility.HtmlDecode(node.InnerHtml);
                if (text.Contains("??") && text.Contains(";"))
                {
                    continue;
                }
                try
                {
                    items.Add(Regex.Replace(text.Replace(" Lyrics", ""), @"\t|\n|\r", ""), node.Attributes["href"].Value);
                }
                catch
                {
                    continue;
                }
            }
            return items;
        }
    }
}
