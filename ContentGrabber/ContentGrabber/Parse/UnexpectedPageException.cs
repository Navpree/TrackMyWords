using System;

namespace ContentGrabber.Parse
{

    /// <summary>
    /// The exception that should be throw by the IWriteProvider implementation whenever an issue occurs during the DoParse method call.
    /// </summary>
    public class UnexpectedPageException:Exception
    {

        /// <summary>
        /// An UnexpectedPageException instance with the basic string message exception.
        /// </summary>
        /// <param name="message">The string representation of the exception message.</param>
        public UnexpectedPageException(string message) : base(message) { }

        /// <summary>
        /// An UnexpectedPageException isntance with the basic string message exception and the nested inner exception.
        /// This exception constructor will be used when an uncaught exception occurs during the DoWrite method of the IWriteProvider instance.
        /// </summary>
        /// <param name="message">The string representation of the exception message.</param>
        /// <param name="inner">The nested inner exception is the uncaught exception from the DoWrite method call.</param>
        public UnexpectedPageException(string message, Exception inner) : base(message, inner) { }
    }
}
