using System.Collections.Generic;
using System.IO;

namespace ContentGrabber.Write
{

    /// <summary>
    /// Defines the possible write modes usable by the PageToFile instance when creating a StreamWriter for the IWriteProvider.
    /// </summary>
    public enum WriteMode
    {
        /// <summary>
        /// Overrite tells PageToFile to create a StreamWriter instance that will overrite an existing.
        /// </summary>
        Overrite,

        /// <summary>
        /// Append tells PageToFile to create a StreamWriter instance that will append to an existing file.
        /// </summary>
        Append
    }

    /// <summary>
    /// The interface used to provide a write service for the PageToFile's Write method.
    /// </summary>
    public interface IWriteProvider
    {

        /// <summary>
        /// Method used to write a dictionary of nodes/values parsed from an HtmlDocument to a particular file.
        /// </summary>
        /// <param name="items">A Dictionary containing the nodes/values parsed from the HtmlDocument.</param>
        /// <param name="writer">The StreamWriter either will be used to overrite an existing file, create a new file, or append to an existing file.</param>
        void DoWrite(Dictionary<string, string> items, StreamWriter writer);

        /// <summary>
        /// The WriteMode that the current IWriteProvider instance prefers to use for writing the Node Dictionary to file.
        /// Affects the manner in which the PageToFile instance will create a StreamWriter for the DoWrite method.
        /// </summary>
        WriteMode PreferredWriteMode { get; }
    }
}
