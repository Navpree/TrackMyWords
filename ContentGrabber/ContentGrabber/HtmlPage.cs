using HtmlAgilityPack;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ContentGrabber.Parse;

namespace ContentGrabber
{
    public class HtmlPage
    {
        private readonly string REQUEST_URL;
        private HtmlDocument doc;
        private IParseProvider parseProvider;
        private Dictionary<string, string> items;

        public bool HasNextPage
        {
            get
            {
                if (doc != null)
                {
                    HtmlNode node = doc.DocumentNode.SelectSingleNode(Constants.NEXT_BUTTON_XPATH);
                    if (node == null)
                    {
                        return false;
                    }
                    if (node.Attributes["class"].Value.Contains("disable"))
                    {
                        return false;
                    }
                }
                return true;
            }
        }

        public Dictionary<string, string> Items
        {
            get
            {
                return items;
            }
        }

        public HtmlDocument Doc
        {
            get
            {
                return doc;
            }
        }

        public IParseProvider ParseProvider
        {
            set
            {
                if (value != null)
                {
                    parseProvider = value;
                }
            }
        }

        public HtmlPage(string url)
        {
            REQUEST_URL = url;
            items = new Dictionary<string, string>();
        }

        public void DoGet()
        {
            if (parseProvider == null)
            {
                return;
            }
            HtmlWeb get = new HtmlWeb();
            try
            {
                doc = get.Load(REQUEST_URL);
                if (parseProvider != null)
                {
                    items = parseProvider.DoParse(doc);
                }
            }
            catch (Exception e)
            {
                throw e;
            }
            finally
            {
                get = null;
            }
        }
    }
}
