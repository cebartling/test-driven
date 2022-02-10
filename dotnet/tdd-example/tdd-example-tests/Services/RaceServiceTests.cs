using Microsoft.Extensions.Logging;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Moq;
using tdd_example;
using tdd_example.Services;

namespace tdd_example_tests.Services;

[TestClass]
public class RaceServiceTests
{
    #region Test setup
    
    private Mock<ILogger<RaceService>>? _loggerMock;
    private Mock<AppDatabaseContext>? _appDatabaseContextMock;
    private RaceService? _service;
    
    

    [TestInitialize]
    public void DoBeforeEachTest()
    {
        _loggerMock = new Mock<ILogger<RaceService>>();
        _appDatabaseContextMock = new Mock<AppDatabaseContext>();
        _service = new RaceService(_loggerMock.Object, _appDatabaseContextMock.Object);
    }

    #endregion

    #region RetrieveAll tests

    #endregion

    #region RetrieveById tests

    #endregion

    #region Create tests

    #endregion

    #region Update tests

    #endregion

    #region Delete tests

    #endregion
}