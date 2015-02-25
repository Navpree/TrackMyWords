using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ContentGrabber.Write
{
    public enum WriteMode
    {
        Overrite,
        Append,
        DeleteBeforeWrite,
        Skip
    }

    public interface IWriteProvider
    {
        void DoWrite(Dictionary<string, string> items, StreamWriter writer);
        WriteMode PreferredWriteMode { get; }
    }
}
