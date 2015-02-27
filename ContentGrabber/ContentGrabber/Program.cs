using System;
using System.IO;

namespace ContentGrabber
{

    /// <summary>
    /// Entry Point
    /// </summary>
    public class Program
    {

        static void Main(string[] args)
        {
            LaunchParams param = new LaunchParams()
            {
                Tasks = 1,
                Clean = true
            };
            if (args.Length == 0)
            {
                Console.WriteLine("Starting with defaults -tasks:1 -clean:true");
            }
            else
            {
                try
                {
                    param = LaunchParams.Parse(args);
                }
                catch(NoSuchParamException e)
                {
                    Console.WriteLine(string.Format("Invalid param: {0}\n\t{1}", e.Param, e.Message));
                    return;
                }
                catch (InvalidParamFormatException e)
                {
                    Console.WriteLine(string.Format("Invalid format for param: {0}\n\t{1}", e.Param, e.Message));
                    return;
                }
            }

            if (param.Clean && Directory.Exists("grabs"))
            {
                Console.WriteLine("Clearing grabs directory...");
                Directory.Delete("grabs", true);
            }
            Console.WriteLine("Starting TaskManager instance with {0} tasks...", param.Tasks);
            TaskManager manager = new TaskManager(param.Tasks);
            manager.Start();
        }
    }
}
