using HtmlAgilityPack;
using System;
using System.IO;
using System.Net;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ContentGrabber.Parse
{
    public class ArtistParser : IParseProvider
    {
        public Dictionary<string, string> DoParse(HtmlDocument doc)
        {
            Dictionary<string, string> items = new Dictionary<string, string>();
            foreach (HtmlNode node in doc.DocumentNode.SelectNodes(Constants.ARTIST_TABLE_ENTRY))
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
