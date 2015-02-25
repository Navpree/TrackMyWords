using HtmlAgilityPack;
using System;
using System.Collections.Generic;
using ContentGrabber.Parse;

namespace ContentGrabber.Parse
{
    /// <summary>
    /// The base class for making get requests and using the parse service to create a collection of nodes/values
    /// from the raw HtmlDocument.
    /// </summary>
    public class HtmlPage
    {
        private readonly string REQUEST_URL;
        private HtmlDocument doc;
        private IParseProvider parseProvider;
        private Dictionary<string, string> items;

        /// <summary>
        /// Returns false if the element for the XPath NEXT_BUTTON has a 'disable' class attribute,
        /// the HtmlDocument is null, or if the XPath returns a null node.
        /// Otherwise returns true.
        /// </summary>
        public bool HasNextPage
        {
            get
            {
                if (doc == null)
                {
                    return false;
                }
                HtmlNode node = doc.DocumentNode.SelectSingleNode(Constants.XPaths.NEXT_BUTTON);
                if (node == null)
                {
                    return false;
                }
                if (node.Attributes["class"].Value.Contains("disable"))
                {
                    return false;
                }
                return true;
            }
        }

        /// <summary>
        /// The dictionary containing the items parsed from the HtmlDocument by the parse provider.
        /// Will be null until the DoGet method is called.
        /// </summary>
        public Dictionary<string, string> Items
        {
            get
            {
                return items;
            }
        }

        /// <summary>
        /// The HtmlDocument received from the web get request.
        /// Will be null until the DoGet method is called.
        /// </summary>
        public HtmlDocument Doc
        {
            get
            {
                return doc;
            }
        }

        /// <summary>
        /// Main constructor for HtmlPage class requiring a get request url and a parse provider instance.
        /// </summary>
        /// <param name="url">The url will be used in a get request to load the HtmlDocument.</param>
        /// <param name="provider">The parse provider will be used to parse the loaded HtmlDocument and fill the Items dictionary.</param>
        /// <exception cref="System.ArgumentException">Thrown either when the url is blank or whitespace or if the parse
        /// provider instance is a null value.</exception>
        public HtmlPage(string url, IParseProvider provider)
        {
            if (url.Trim() == "")
            {
                throw new ArgumentException("url", "Url string cannot be blank or whitespace.");
            }
            if (provider == null)
            {
                throw new ArgumentNullException("provider", "IParseProvider instance cannot be a null value.");
            }
            REQUEST_URL = url.Trim();
            this.parseProvider = provider;
            items = new Dictionary<string, string>();
        }

        /// <summary>
        /// Loads an HtmlDocument using a web get request for the provided request URL.
        /// After which the items dictionary will be filled by the provided IParseProvider instance.
        /// </summary>
        /// <exception cref="ContentGrabber.Parse.UnexpectedPageException">An expection either rethrown from the parse provider's DoParse method call
        /// or when an unexpected exception occurs during that call. If thrown from an unexpected exception the main cause will be thrown as the
        /// inner exception.</exception>
        public void DoGet()
        {
            HtmlWeb get = new HtmlWeb();
            try
            {
                doc = get.Load(REQUEST_URL);
                if (doc == null || doc.DocumentNode == null)
                {
                    throw new Exception("Loaded document from request url, or its document node, was a null value.");
                }
                items = parseProvider.DoParse(doc);
            }
            catch (UnexpectedPageException e)
            {
                throw e;
            }
            catch (Exception e)
            {
                throw new UnexpectedPageException("An uncaught error occured during provider.DoParse", e);
            }
            finally
            {
                get = null;
            }
        }
    }
}
