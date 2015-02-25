using System;
using ContentGrabber;
using NUnit.Framework;

namespace TestSuite
{
    [TestFixture]
    public class LaunchParamsTests
    {

        private string[] VALID_PARAMS = "-tasks:3 -clean:true".Split(' ');
        private string[] INVALID_PARAMS_EXTRA = "-tasks:3 -clean:true -test:3".Split(' ');
        private string[] INVALID_PARAMS_FORMAT = "-tasks:3 -cleantrue".Split(' ');
        private string[] INVALID_PARAMS_TYPE = "-tasks:false -clean:true".Split(' ');

        [Test]
        public void ValidParamTest()
        {
            LaunchParams lp = LaunchParams.Parse(VALID_PARAMS);
            Assert.AreEqual(3, lp.Tasks);
            Assert.IsTrue(lp.Clean);
        }

        [Test]
        [ExpectedException(typeof(NoSuchParamException))]
        public void InvalidExtraParamTest()
        {
            LaunchParams lp = LaunchParams.Parse(INVALID_PARAMS_EXTRA);
        }

        [Test]
        [ExpectedException(typeof(InvalidParamFormatException))]
        public void InvalidParamFormatTest()
        {
            LaunchParams lp = LaunchParams.Parse(INVALID_PARAMS_FORMAT);
        }

        [Test]
        [ExpectedException(typeof(Exception))]
        public void InvalidParamTypeTest()
        {
            LaunchParams lp = LaunchParams.Parse(INVALID_PARAMS_TYPE);
        }

    }
}
