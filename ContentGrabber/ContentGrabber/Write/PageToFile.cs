using System;
using System.IO;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ContentGrabber.Write
{
    public class PageToFile
    {

        public static void Write(string file, HtmlPage page, IWriteProvider provider)
        {
            if (provider.PreferredWriteMode == WriteMode.Skip)
            {
                return;
            }
            try
            {
                CreatePathTo(file);
            }
            catch (Exception e)
            {
                throw e;
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
                    if (exists && provider.PreferredWriteMode == WriteMode.DeleteBeforeWrite)
                    {
                        File.Delete(file);
                    }
                    writer = new StreamWriter(file);
                }
                provider.DoWrite(page.Items, writer);
            }
            catch (Exception e)
            {
                throw e;
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

        public static void CreatePathTo(string path)
        {
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
