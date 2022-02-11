using System;
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
    private readonly List<Race> _expectedRaces = new List<Race>
    {
        new()
        {
            Id = "0bfa5ac6-61c6-4210-8e2e-aff86732f5a1",
            Name = "Fat Race 1",
            RaceDate = new DateOnly(2002, 1, 12)
        },
        new()
        {
            Id = "d2803a83-71fb-466a-8a57-4676638e71cc",
            Name = "Fat Race 2",
            RaceDate = new DateOnly(2002, 1, 19)
        },
        new()
        {
            Id = "8186731f-f684-4eb2-9a73-11431ad8f1dd",
            Name = "Fat Race 3",
            RaceDate = new DateOnly(2002, 1, 26)
        }
    };


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
        _appDatabaseContextMock!.Setup(x => x.Races).ReturnsDbSet(_expectedRaces);
        
        var result = _service!.RetrieveAll();
     
        var actualRaces = result.ToArray();
        Assert.AreEqual(3, actualRaces.Length);
        Assert.AreEqual(_expectedRaces[0], actualRaces[0]);
        Assert.AreEqual(_expectedRaces[1], actualRaces[1]);
        Assert.AreEqual(_expectedRaces[2], actualRaces[2]);
    }

    [TestMethod]
    public void RetrieveAll_CollaborationTest()
    {
        _appDatabaseContextMock!.Setup(x => x.Races).ReturnsDbSet(_expectedRaces);
        
        _service!.RetrieveAll();
        
        _appDatabaseContextMock!.Verify(x => x.Races);
    }

    #endregion

    #region RetrieveById tests

    [TestMethod]
    public void RetrieveById_ContractTest()
    {
        var racesDbSetMock = new Mock<DbSet<Race>>();
        racesDbSetMock.Setup(x => x.Find(It.IsAny<string>())).Returns(_expectedRaces[0]);
        _appDatabaseContextMock!.Setup(x => x.Races).Returns(racesDbSetMock.Object);
        
        var result = _service!.RetrieveById(_expectedRaces[0].Id);
     
        Assert.AreEqual(_expectedRaces[0], result);
    }

    [TestMethod]
    public void RetrieveById_CollaborationTest()
    {
        var racesDbSetMock = new Mock<DbSet<Race>>();
        racesDbSetMock.Setup(x => x.Find(It.IsAny<string>())).Returns(_expectedRaces[0]);
        _appDatabaseContextMock!.Setup(x => x.Races).Returns(racesDbSetMock.Object);
        
        var result = _service!.RetrieveById(_expectedRaces[0].Id);

        _appDatabaseContextMock!.Verify(x => x.Races);
        racesDbSetMock.Verify(x => x.Find(It.IsAny<string>()));
    }

    #endregion

    #region Create tests

    #endregion

    #region Update tests

    #endregion

    #region Delete tests

    #endregion
}