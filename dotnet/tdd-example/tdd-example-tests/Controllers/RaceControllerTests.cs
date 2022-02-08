using System;
using System.Collections.Generic;
using Microsoft.AspNetCore.Http;
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
        new("0bfa5ac6-61c6-4210-8e2e-aff86732f5a1", "Fat Race 1", new DateOnly(2002, 1, 12)),
        new("d2803a83-71fb-466a-8a57-4676638e71cc", "Fat Race 2", new DateOnly(2002, 1, 19)),
        new("8186731f-f684-4eb2-9a73-11431ad8f1dd", "Fat Race 3", new DateOnly(2002, 1, 26))
    };

    private Race _expectedRace = new("0bfa5ac6-61c6-4210-8e2e-aff86732f5a1", "Fat Race 1", new DateOnly(2002, 1, 12));

    [TestInitialize]
    public void DoBeforeEachTest()
    {
        _loggerMock = new Mock<ILogger<WeatherForecastController>>();
        _raceServiceMock = new Mock<IRaceService>();
        _controller = new RaceController(_loggerMock.Object, _raceServiceMock.Object);
    }

    #endregion

    #region Get all races tests

    private void ConfigureMockForGetAllRaces()
    {
        _raceServiceMock.Setup(x => x.RetrieveAll()).Returns(_expectedRaces);
    }

    [TestMethod]
    public void GetAllRaces_Success_ContractTest()
    {
        ConfigureMockForGetAllRaces();

        IEnumerable<Race> races = _controller.GetAllRaces();

        Assert.AreEqual(_expectedRaces, races);
    }

    [TestMethod]
    public void GetAllRaces_Success_CollaborationTest()
    {
        ConfigureMockForGetAllRaces();

        _controller.GetAllRaces();

        _raceServiceMock.Verify(x => x.RetrieveAll());
    }

    #endregion

    #region Get race by primary key identifier tests

    private void ConfigureMockForGetRaceById()
    {
        _raceServiceMock.Setup(x => x.RetrieveById(_expectedRace.Id)).Returns(_expectedRace);
    }

    [TestMethod]
    public void GetRaceById_Success_ContractTest()
    {
        ConfigureMockForGetRaceById();

        Race race = _controller.GetRaceById(_expectedRace.Id);

        Assert.AreEqual(_expectedRace, race);
    }

    [TestMethod]
    public void GetRaceById_Success_CollaborationTest()
    {
        ConfigureMockForGetRaceById();

        _controller.GetRaceById(_expectedRace.Id);

        _raceServiceMock.Verify(x => x.RetrieveById(_expectedRace.Id));
    }

    #endregion

    #region Create a new race tests

    private void ConfigureMocksForCreate()
    {
        _raceServiceMock.Setup(x =>
                x.Create(It.Is<Race>(i => i.Equals(_expectedRace)))
            )
            .Returns(_expectedRace);
    }

    [TestMethod]
    public void CreateNewRace_ContractTest()
    {
        ConfigureMocksForCreate();

        var createdResult = _controller.Create(_expectedRace);

        Assert.AreEqual($"races/{_expectedRace.Id}", createdResult.Location);
        Assert.AreEqual(StatusCodes.Status201Created, createdResult.StatusCode);
    }

    [TestMethod]
    public void CreateNewRace_CollaborationTest()
    {
        ConfigureMocksForCreate();

        _controller.Create(_expectedRace);

        _raceServiceMock.Verify(x =>
            x.Create(It.Is<Race>(i => i.Equals(_expectedRace)))
        );
    }

    #endregion

    #region Update an existing race tests

    private void ConfigureMocksForUpdate()
    {
        _raceServiceMock.Setup(x =>
                x.Update(It.Is<Race>(i => i.Equals(_expectedRace)))
            )
            .Returns(_expectedRace);
    }

    [TestMethod]
    public void Update_ContractTest()
    {
        ConfigureMocksForUpdate();

        var actionResult = _controller.Update(_expectedRace);

        Assert.AreEqual(StatusCodes.Status204NoContent, actionResult.StatusCode);
    }

    [TestMethod]
    public void Update_CollaborationTest()
    {
        ConfigureMocksForUpdate();

        _controller.Update(_expectedRace);

        _raceServiceMock.Verify(x => x.Update(It.Is<Race>(i => i.Equals(_expectedRace))));
    }

    #endregion

    #region Delete an existing race tests

    private void ConfigureMocksForDelete()
    {
        _raceServiceMock.Setup(x =>
            x.Delete(It.Is<string>(i => i.Equals(_expectedRace.Id)))
        );
    }

    [TestMethod]
    public void Delete_ContractTest()
    {
        ConfigureMocksForDelete();

        var actionResult = _controller.Delete(_expectedRace.Id);

        Assert.AreEqual(StatusCodes.Status204NoContent, actionResult.StatusCode);
    }

    [TestMethod]
    public void Delete_CollaborationTest()
    {
        ConfigureMocksForDelete();

        _controller.Delete(_expectedRace.Id);

        _raceServiceMock.Verify(x => x.Delete(It.Is<string>(i => i.Equals(_expectedRace.Id))));
    }

    #endregion
}