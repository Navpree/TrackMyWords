using System;
using System.IO;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ContentGrabber.Write
{
    public class ArtistWriter : IWriteProvider
    {
        public WriteMode PreferredWriteMode
        {
            get
            {
                return WriteMode.Append;
            }
        }

        public void DoWrite(Dictionary<string, string> items, StreamWriter writer)
        {
            foreach (KeyValuePair<string, string> item in items)
            {
                writer.WriteLine(item.Key);
                writer.WriteLine(item.Value);
                writer.WriteLine("");
                writer.Flush();
            }
        }
    }
}
