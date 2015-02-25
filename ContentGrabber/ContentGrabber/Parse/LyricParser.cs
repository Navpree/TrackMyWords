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
    public class LyricParser : IParseProvider
    {
        public Dictionary<string, string> DoParse(HtmlDocument doc)
        {
            Dictionary<string, string> items = new Dictionary<string, string>();
            HtmlNode node = doc.DocumentNode.SelectSingleNode(Constants.SONG_ALBUM_XPATH);
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
            items.Add("lyrics", WebUtility.HtmlDecode(doc.DocumentNode.SelectSingleNode(Constants.SONG_LYRIC_BODY).InnerText));
            return items;
        }
    }
}
