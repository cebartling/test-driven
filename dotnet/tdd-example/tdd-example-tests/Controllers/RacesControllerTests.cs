using System;
using System.Collections.Generic;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
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

    private Mock<ILogger<RacesController>>? _loggerMock;
    private Mock<IRaceService>? _raceServiceMock;
    private RacesController? _controller;

    private readonly IEnumerable<Race> _expectedRaces = new List<Race>
    {
        new()
        {
            Id = "0bfa5ac6-61c6-4210-8e2e-aff86732f5a1",
            Name = "Fat Race 1",
            RaceDate = new DateTime(2002, 1, 12)
        },
        new()
        {
            Id = "d2803a83-71fb-466a-8a57-4676638e71cc",
            Name = "Fat Race 2",
            RaceDate = new DateTime(2002, 1, 19)
        },
        new()
        {
            Id = "8186731f-f684-4eb2-9a73-11431ad8f1dd",
            Name = "Fat Race 3",
            RaceDate = new DateTime(2002, 1, 26)
        }
    };

    private readonly Race _expectedRace = new()
    {
        Id = "0bfa5ac6-61c6-4210-8e2e-aff86732f5a1",
        Name = "Fat Race 1",
        RaceDate = new DateTime(2002, 1, 12)
    };

    [TestInitialize]
    public void DoBeforeEachTest()
    {
        _loggerMock = new Mock<ILogger<RacesController>>();
        _raceServiceMock = new Mock<IRaceService>();
        _controller = new RacesController(_loggerMock.Object, _raceServiceMock.Object);
    }

    #endregion

    #region Retrieve all races tests

    private void SetupMocksRetrieveAll()
    {
        _raceServiceMock!
            .Setup(x => x.RetrieveAll())
            .Returns(_expectedRaces);
    }

    [TestMethod]
    public void RetrieveAll_Success_ContractTest()
    {
        SetupMocksRetrieveAll();

        var actionResult = _controller!.RetrieveAll();

        var okObjectResult = actionResult.Result as OkObjectResult;
        Assert.AreEqual(StatusCodes.Status200OK, okObjectResult!.StatusCode);
        Assert.AreEqual(_expectedRaces, okObjectResult!.Value);
    }

    [TestMethod]
    public void RetrieveAll_Success_CollaborationTest()
    {
        SetupMocksRetrieveAll();

        _controller!.RetrieveAll();

        _raceServiceMock!.Verify(x => x.RetrieveAll());
    }

    #endregion

    #region Retrieve by primary key identifier tests

    private void SetupMocksRetrieveById()
    {
        _raceServiceMock!
            .Setup(x => x.RetrieveById(_expectedRace.Id))
            .Returns(_expectedRace);
    }

    [TestMethod]
    public void RetrieveById_Success_ContractTest()
    {
        SetupMocksRetrieveById();

        var actionResult = _controller!.RetrieveById(_expectedRace.Id);

        var okOjbectResult = actionResult.Result as OkObjectResult;
        Assert.AreEqual(StatusCodes.Status200OK, okOjbectResult!.StatusCode);
        Assert.AreEqual(_expectedRace, okOjbectResult.Value);
    }

    [TestMethod]
    public void RetrieveById_Success_CollaborationTest()
    {
        SetupMocksRetrieveById();

        _controller!.RetrieveById(_expectedRace.Id);

        _raceServiceMock!.Verify(x => x.RetrieveById(_expectedRace.Id));
    }

    #endregion

    #region Create a new race tests

    private void SetupMocksCreate()
    {
        _raceServiceMock!
            .Setup(x => x.Create(It.Is<Race>(i => i.Equals(_expectedRace))))
            .Returns(_expectedRace);
    }

    [TestMethod]
    public void Create_ContractTest()
    {
        SetupMocksCreate();

        var actionResult = _controller!.Create(_expectedRace);
        
        var createdResult = actionResult.Result as CreatedResult;
        Assert.AreEqual($"races/{_expectedRace.Id}", createdResult!.Location);
        Assert.AreEqual(StatusCodes.Status201Created, createdResult.StatusCode);
    }

    [TestMethod]
    public void Create_CollaborationTest()
    {
        SetupMocksCreate();

        _controller!.Create(_expectedRace);

        _raceServiceMock!.Verify(x =>
            x.Create(It.Is<Race>(i => i.Equals(_expectedRace)))
        );
    }

    #endregion

    #region Update an existing race tests

    private void SetupMocksUpdate()
    {
        _raceServiceMock!
            .Setup(x => x.Update(It.Is<Race>(i => i.Equals(_expectedRace))))
            .Returns(_expectedRace);
    }

    [TestMethod]
    public void Update_ContractTest()
    {
        SetupMocksUpdate();

        var actionResult = _controller!.Update(_expectedRace.Id, _expectedRace);

        var noContentResult = actionResult.Result as NoContentResult;
        Assert.AreEqual(StatusCodes.Status204NoContent, noContentResult!.StatusCode);
    }

    [TestMethod]
    public void Update_CollaborationTest()
    {
        SetupMocksUpdate();

        _controller!.Update(_expectedRace.Id, _expectedRace);

        _raceServiceMock!.Verify(x => x.Update(It.Is<Race>(i => i.Equals(_expectedRace))));
    }

    #endregion

    #region Delete an existing race tests

    private void SetupMocksDelete()
    {
        _raceServiceMock!
            .Setup(x => x.Delete(It.Is<string>(i => i.Equals(_expectedRace.Id))));
    }

    [TestMethod]
    public void Delete_ContractTest()
    {
        SetupMocksDelete();

        var actionResult = _controller!.Delete(_expectedRace.Id);

        var noContentResult = actionResult.Result as NoContentResult;
        Assert.AreEqual(StatusCodes.Status204NoContent, noContentResult!.StatusCode);
    }

    [TestMethod]
    public void Delete_CollaborationTest()
    {
        SetupMocksDelete();

        _controller!.Delete(_expectedRace.Id);

        _raceServiceMock!.Verify(x => x.Delete(It.Is<string>(i => i.Equals(_expectedRace.Id))));
    }

    #endregion
}