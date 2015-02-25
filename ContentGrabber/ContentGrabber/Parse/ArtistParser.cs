using HtmlAgilityPack;
using System.IO;
using System.Net;
using System.Collections.Generic;

namespace ContentGrabber.Parse
{

    /// <summary>
    /// IParseProvider implementation for parsing a collection of artists from the provided HtmlDocument
    /// </summary>
    public class ArtistParser : IParseProvider
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
            HtmlNodeCollection nodes = doc.DocumentNode.SelectNodes(Constants.XPaths.ARTIST_ENTRY);
            if (nodes == null || nodes.Count == 0)
            {
                throw new UnexpectedPageException("Could not locate any nodes using ARTIST_TABLE_ENTRY_XPATH.");
            }
            Dictionary<string, string> items = new Dictionary<string, string>();
            foreach (HtmlNode node in nodes)
            {
                string text = WebUtility.HtmlDecode(node.InnerHtml);
                if (text.Contains("??") || text.Contains(";"))
                {
                    continue;
                }
                else if (text.IndexOfAny(Path.GetInvalidPathChars()) > 0 || text.IndexOfAny(Path.GetInvalidFileNameChars()) > 0)
                {
                    continue;
                }
                try
                {
                    items.Add(text.Replace(" Lyrics", ""), node.Attributes["href"].Value);
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
