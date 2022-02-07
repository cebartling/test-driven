using System.Collections.Generic;
using Microsoft.Extensions.Logging;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Moq;
using tdd_example.Controllers;
using tdd_example.Models;
using tdd_example.Services;

namespace tdd_example_tests.Controllers;

[TestClass]
public class RaceControllerTests
{
    #region Test suite setup

    private Mock<ILogger<WeatherForecastController>> _loggerMock;
    private Mock<IRaceService> _raceServiceMock;
    private RaceController _controller;

    private IEnumerable<Race> _expectedRaces = new List<Race>
    {
        new("0bfa5ac6-61c6-4210-8e2e-aff86732f5a1"), 
        new("d2803a83-71fb-466a-8a57-4676638e71cc"), 
        new("8186731f-f684-4eb2-9a73-11431ad8f1dd")
    };
    private Race _expectedRace = new("0bfa5ac6-61c6-4210-8e2e-aff86732f5a1");

    [TestInitialize]
    public void DoBeforeEachTest()
    {
        _loggerMock = new Mock<ILogger<WeatherForecastController>>();
        _raceServiceMock = new Mock<IRaceService>();
        _controller = new RaceController(_loggerMock.Object, _raceServiceMock.Object);
    }

    #endregion

    #region Get all races tests
    
    [TestMethod]
    public void GetAllRaces_Success_ContractTest()
    {
        _raceServiceMock.Setup(x => x.RetrieveAll()).Returns(_expectedRaces);
        
        IEnumerable<Race> races = _controller.GetAllRaces();
        
        Assert.AreEqual(_expectedRaces, races);
    }

    [TestMethod]
    public void GetAllRaces_Success_CollaborationTest()
    {
        _raceServiceMock.Setup(x => x.RetrieveAll()).Returns(_expectedRaces);
     
        _controller.GetAllRaces();
        
        _raceServiceMock.Verify(x => x.RetrieveAll());
    }
    
    #endregion

    #region Get race by primary key identifier tests

    [TestMethod]
    public void GetRaceById_Success_ContractTest()
    {
        _raceServiceMock.Setup(x => x.RetrieveById(_expectedRace.Id)).Returns(_expectedRace);
        
        Race race = _controller.GetRaceById(_expectedRace.Id);
        
        Assert.AreEqual(_expectedRace, race);
    }

    [TestMethod]
    public void GetRaceById_Success_CollaborationTest()
    {
        _raceServiceMock.Setup(x => x.RetrieveById(_expectedRace.Id)).Returns(_expectedRace);
     
        _controller.GetRaceById(_expectedRace.Id);
        
        _raceServiceMock.Verify(x => x.RetrieveById(_expectedRace.Id));
    }

    #endregion
}