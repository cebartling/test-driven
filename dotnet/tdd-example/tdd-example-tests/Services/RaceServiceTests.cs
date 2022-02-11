using System.Collections.Generic;
using System.Linq;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Moq;
using Moq.EntityFrameworkCore;
using tdd_example;
using tdd_example.Models;
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

    [TestMethod]
    public void RetrieveAll_ContractTest()
    {
        var expected = new List<Race> { new Race(), new Race(), new Race() };
        _appDatabaseContextMock!.Setup(x => x.Races).ReturnsDbSet(expected);
        
        var result = _service!.RetrieveAll();
     
        var actualRaces = result.ToArray();
        Assert.AreEqual(3, actualRaces.Length);
        Assert.AreEqual(expected[0], actualRaces[0]);
        Assert.AreEqual(expected[1], actualRaces[1]);
        Assert.AreEqual(expected[2], actualRaces[2]);
    }

    [TestMethod]
    public void RetrieveAll_CollaborationTest()
    {
        var expected = new List<Race> { new Race(), new Race(), new Race() };
        _appDatabaseContextMock!.Setup(x => x.Races).ReturnsDbSet(expected);
        
        _service!.RetrieveAll();
        
        _appDatabaseContextMock!.Verify(x => x.Races);
    }

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