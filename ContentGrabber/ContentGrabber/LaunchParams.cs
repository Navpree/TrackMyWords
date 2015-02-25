using System;
using System.Linq;

namespace ContentGrabber
{

    /// <summary>
    /// Base abstract class for launch parameter exception specifications.
    /// </summary>
    public abstract class ParamException : Exception
    {
        private string param;

        /// <summary>
        /// A string representation of the invalid paramter.
        /// </summary>
        public string Param
        {
            get
            {
                return param;
            }
        }

        /// <summary>
        /// The base constructor of the ParamException class.
        /// </summary>
        /// <param name="message">The string exception message.</param>
        /// <param name="param">The string representing the launch parameter causing a ParamException.</param>
        public ParamException(string message, string param)
            : base(message)
        {
            this.param = param;
        }
    }

    /// <summary>
    /// An exception used by the LaunchParams struct to specify when a named parameter is invalid.
    /// Inherits from the ParamException abstract class.
    /// </summary>
    public class NoSuchParamException : ParamException
    {
        /// <summary>
        /// Used when a parameter name is invalid.
        /// </summary>
        /// <param name="message">The main exception message.</param>
        /// <param name="param">A string representing the invalid parameter name.</param>
        public NoSuchParamException(string message, string param) : base(message, param) { }
    }

    /// <summary>
    /// An exception used by the LaunchParams struct to specify when a parameter is specified without proper name/value delimeter.
    /// </summary>
    public class InvalidParamFormatException : ParamException
    {
        /// <summary>
        /// Used when a provided parameter cannot be parsed due to invalid parameter string format.
        /// </summary>
        /// <param name="message">The main exception message.</param>
        /// <param name="param">A string representing the parameter.</param>
        public InvalidParamFormatException(string message, string param) : base(message, param) { }
    }

    /// <summary>
    /// Structure of parsed parameters for altering the runtime functionality of the program.
    /// </summary>
    public struct LaunchParams
    {
        /// <summary>
        /// Represents the number of threads to be used when starting up the program.
        /// </summary>
        public int Tasks;

        /// <summary>
        /// If true and the grabs directory exists, the directory will be recursively deleted before continuing.
        /// </summary>
        public bool Clean;

        /// <summary>
        /// Parses an array of string parameters and returns a new instance of the LaunchParams struct.
        /// </summary>
        /// <param name="args">A string array of the raw launch parameters.</param>
        /// <returns>A new instance of the LaunchParams struct.</returns>
        /// <exception cref="ContentGrabber.NoSuchParamException">Thrown when a parameter with an invalid name is provided.</exception>
        /// <exception cref="ContentGrabber.InvalidParamFormatException">Thrown when a paramter with an invalid format is provided.</exception>
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
                    throw new InvalidParamFormatException("Invalid parameter, : expected as name/value delimeter.", s);
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
                            throw new Exception(string.Format("Invalid value for parameter named -clear, '{0}' must be of type bool.", parts[1]));
                        }
                        break;
                    default:
                        throw new NoSuchParamException(string.Format("Invalid parameter name '{0}', either -tasks or -clean expected.", parts[0]), parts[0]);
                }
            }
            return param;
        }
    }
}
