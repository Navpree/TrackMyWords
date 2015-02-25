using System;
using System.IO;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ContentGrabber.Parse;
using ContentGrabber.Write;

namespace ContentGrabber
{
    class Program
    {

        private struct LaunchParams
        {
            public int Tasks;
            public bool Clean;

            public static LaunchParams Parse(string[] args)
            {
                LaunchParams param = new LaunchParams()
                {
                    Tasks = 1,
                    Clean = true
                };
                foreach (string s in args)
                {
                    if (!s.Contains(':'))
                    {
                        throw new Exception("Invalid parameter, : expected as name/value delimeter.");
                    }
                    string[] parts = s.Split(':');
                    switch (parts[0])
                    {
                        case "-tasks":
                            try
                            {
                                param.Tasks = Convert.ToInt32(parts[1]);
                            }
                            catch
                            {
                                throw new Exception(string.Format("Invalid value for parameter named -tasks, '{0}' must be of type Int32.", parts[1]));
                            }
                            break;
                        case "-clean":
                            try
                            {
                                param.Clean = Convert.ToBoolean(parts[1]);
                            }
                            catch
                            {
                                throw new Exception(string.Format("Invalid value for parameter named -clear, '{0}' must be of type bool."));
                            }
                            break;
                        default:
                            throw new Exception(string.Format("Invalid parameter name '{0}', either -tasks or -clean expected.", parts[0]));
                    }
                }
                return param;
            }
        }

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
                catch(Exception e)
                {
                    Console.WriteLine(e.Message);
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
