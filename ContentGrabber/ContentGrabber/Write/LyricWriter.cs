using System;
using System.IO;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ContentGrabber.Write
{
    public class LyricWriter : IWriteProvider
    {

        public WriteMode PreferredWriteMode
        {
            get
            {
                return WriteMode.Overrite;
            }
        }

        public void DoWrite(Dictionary<string, string> items, StreamWriter writer)
        {
            writer.Write(items["lyrics"]);
        }
    }
}
