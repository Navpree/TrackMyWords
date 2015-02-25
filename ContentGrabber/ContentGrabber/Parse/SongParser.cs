using HtmlAgilityPack;
using System;
using System.Net;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;

namespace ContentGrabber.Parse
{
    public class SongParser : IParseProvider
    {
        public Dictionary<string, string> DoParse(HtmlDocument doc)
        {
            Dictionary<string, string> items = new Dictionary<string, string>();
            try
            {
                foreach (HtmlNode node in doc.DocumentNode.SelectNodes(Constants.SONG_POP_ENTRY))
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
            catch
            {
                return items;
            }
        }
    }
}
