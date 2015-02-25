using HtmlAgilityPack;
using System.Collections.Generic;

namespace ContentGrabber.Parse
{

    /// <summary>
    /// The interface exposing the DoParse method required by the HtmlPage to parse a raw HtmlDocument for valid nodes
    /// and their values.
    /// </summary>
    public interface IParseProvider
    {
        /// <summary>
        /// The DoParse method is called by the HtmlPage during the DoGet method.
        /// It works to parse the retrieved HtmlDocument and return the relevant Html nodes
        /// and their respective values.
        /// </summary>
        /// <param name="doc">The HtmlDocument instance obtained from the AgilityPack get request.</param>
        /// <returns>A dictionary containing all the node/values parsed from the HtmlDocument instance.</returns>
        Dictionary<string, string> DoParse(HtmlDocument doc);
    }
}
