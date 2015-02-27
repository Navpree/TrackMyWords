using System;
using System.IO;
using System.Linq;
using ContentGrabber.Parse;

namespace ContentGrabber.Write
{

    /// <summary>
    /// A utility class for writing an HtmlPage instance to file.
    /// </summary>
    public class PageToFile
    {

        private IWriteProvider provider;

        /// <summary>
        /// Used to write an HtmlDocument instance to file using an IWriteProvider instance.
        /// </summary>
        /// <param name="provider">The IWriteProvider instance needed to properly write the HtmlDocument to a specific file.</param>
        /// <exception cref="System.ArgumentNullException">Thrown if the IWriteProvider instance is a  null value.</exception>
        public PageToFile(IWriteProvider provider)
        {
            if (provider == null)
            {
                throw new ArgumentNullException("provider", "IWriteProvider instance cannot be a null value.");
            }
            this.provider = provider;
        }

        /// <summary>
        /// Writes an HtmlPage to file using the IWriteProvider instance.
        /// Requires the absolute or relative path to the file location and a valid instance of the HtmlPage.
        /// </summary>
        /// <param name="file">A string representation of the absolute or relative path to the file location.</param>
        /// <param name="page">The HtmlPage instance to write to file.</param>
        /// <exception cref="System.ArgumentNullException">Thrown if the HtmlPage instance is a null value or if the file name is blank or whitespace.</exception>
        /// <exception cref="ContentGrabber.Write.WriteException">An expection either rethrown from the parse provider's DoWrite method call
        /// or when an unexpected exception occurs during that call. If thrown from an unexpected exception the main cause will be thrown as the
        /// inner exception of the WriteException instance.</exception>
        public void Write(string file, HtmlPage page)
        {
            if (file.Trim() == "")
            {
                throw new ArgumentException("file", "The file name cannot be empty or whitespace.");
            }
            if (file.IndexOfAny(Path.GetInvalidPathChars()) > 0)
            {
                throw new ArgumentException("file", "The specified file path contains invalid characters for the file system.");
            }
            if (page == null)
            {
                throw new ArgumentNullException("page", "The provided HtmlPage instance was a null value.");
            }
            StreamWriter writer = null;
            try
            {
                bool exists = File.Exists(file);
                if (exists && provider.PreferredWriteMode == WriteMode.Append)
                {
                    writer = File.AppendText(file);
                }
                else
                {
                    writer = new StreamWriter(file);
                }
                provider.DoWrite(page.Items, writer);
            }
            catch (WriteException e)
            {
                throw e;
            }
            catch (Exception e)
            {
                throw new WriteException("An uncaught error occured during provider.DoWrite", e);
            }
            finally
            {
                if (writer != null)
                {
                    writer.Flush();
                    writer.Close();
                    writer.Dispose();
                    writer = null;
                }
            }
        }

        /// <summary>
        /// Recursively creates a path in the filesystem leading the specified file in the provided path string.
        /// </summary>
        /// <param name="path">The absolute or relative path to a specific file.</param>
        /// <exception cref="System.ArgumentException">Thrown if either the path string is blank or whitespace, or if the path does not contain
        /// any / delimeters.</exception>
        public static void CreatePathTo(string path)
        {
            if (path.Trim() == "")
            {
                throw new ArgumentException("path", "path string cannot be empty or whitespace.");
            }
            else
            {
                path = path.Trim();
                if (!path.Contains("/"))
                {
                    throw new ArgumentException("path", "path string must contain / delimeters");
                }
            }
            string[] temp = path.Split('/');
            string[] parts = temp.Where((x) => x != temp[temp.Length - 1]).ToArray();
            string full = "";
            foreach (string part in parts)
            {
                full += part + "/";
                if (!Directory.Exists(full))
                {
                    try
                    {
                        Directory.CreateDirectory(full);
                    }
                    catch (Exception e)
                    {
                        throw e;
                    }
                }
            }
        }
    }
}
