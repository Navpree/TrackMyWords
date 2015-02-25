using HtmlAgilityPack;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ContentGrabber.Parse
{
    public interface IParseProvider
    {
        Dictionary<string, string> DoParse(HtmlDocument doc);
    }
}
