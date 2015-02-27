using System.IO;
using System.Collections.Generic;

namespace ContentGrabber.Write
{

    /// <summary>
    /// IWriteProvider implementation for writing a collection of artists/artist urls to file.
    /// </summary>
    public class ArtistWriter : IWriteProvider
    {

        /// <summary>
        /// The WriteMode that the current IWriteProvider instance prefers to use for writing the Node Dictionary to file.
        /// Affects the manner in which the PageToFile instance will create a StreamWriter for the DoWrite method.
        /// </summary>
        public WriteMode PreferredWriteMode
        {
            get
            {
                return WriteMode.Append;
            }
        }

        /// <summary>
        /// Method used to write a dictionary of nodes/values parsed from an HtmlDocument to a particular file.
        /// </summary>
        /// <param name="items">A Dictionary containing the nodes/values parsed from the HtmlDocument.</param>
        /// <param name="writer">The StreamWriter either will be used to overrite an existing file, create a new file, or append to an existing file.</param>
        public void DoWrite(Dictionary<string, string> items, StreamWriter writer)
        {
            if (items.Count == 0)
            {
                throw new WriteException("Dictionary does not contain any items.");
            }
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
