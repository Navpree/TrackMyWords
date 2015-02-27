using System;

namespace ContentGrabber.Write
{
    /// <summary>
    /// The exception that should be throw by the IParseProvider implementation whenever an issue occurs during the DoWrite method call.
    /// </summary>
    public class WriteException: Exception
    {
        /// <summary>
        /// A WriteException instance with the basic string message exception.
        /// </summary>
        /// <param name="message">The string representation of the exception message.</param>
        public WriteException(string message) : base(message) { }

        /// <summary>
        /// A WriteException instance with the basic string message exception and the nested inner exception.
        /// This exception constructor will be used when an uncaught exception occurs during the DoWrite method of the IWriteProvider instance.
        /// </summary>
        /// <param name="message">The string representation of the exception message.</param>
        /// <param name="inner">The nested inner exception is the uncaught exception from the DoWrite method call.</param>
        public WriteException(string message, Exception inner) : base(message, inner) { }
    }
}
