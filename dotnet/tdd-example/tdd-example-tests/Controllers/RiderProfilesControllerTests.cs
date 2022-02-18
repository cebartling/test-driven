using System;
using System.Collections.Generic;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Moq;
using tdd_example.Controllers;
using tdd_example.Models;
using tdd_example.Services;

namespace tdd_example_tests.Controllers;

[TestClass]
public class RiderProfilesControllerTests
{
    #region Test suite setup

    private RiderProfilesController? _controller;
    private Mock<IRiderProfileService>? _riderProfileServiceMock;

    private readonly RiderProfile _expectedRiderProfile = new()
    {
        Id = "1577bed8-3228-4924-afd4-952395ec475a",
        BirthDate = new DateTime(1974, 1, 24),
        GivenName = "John",
        Surname = "Doe"
    };

    private readonly IEnumerable<RiderProfile> _expectedRiderProfiles = new[]
    {
        new RiderProfile()
        {
            Id = "1577bed8-3228-4924-afd4-952395ec475a",
            BirthDate = new DateTime(1974, 1, 24),
            GivenName = "John",
            Surname = "Doe"
        },
        new RiderProfile()
        {
            Id = "1577bed8-3228-4924-afd4-952395ec475b",
            BirthDate = new DateTime(1972, 7, 5),
            GivenName = "Jane",
            Surname = "Hackney"
        },
        new RiderProfile()
        {
            Id = "1577bed8-3228-4924-afd4-952395ec475c",
            BirthDate = new DateTime(1973, 6, 30),
            GivenName = "Tom",
            Surname = "Carney"
        },
    };

    [TestInitialize]
    public void DoBeforeEachTest()
    {
        _riderProfileServiceMock = new Mock<IRiderProfileService>();
        _controller = new RiderProfilesController(_riderProfileServiceMock.Object);
    }

    #endregion

    #region Get all rider profiles

    private void SetupMocksRetrieveAll()
    {
        _riderProfileServiceMock!.Setup(x => x.RetrieveAll())
            .Returns(_expectedRiderProfiles);
    }

    [TestMethod]
    public void RetrieveAll_CollaborationTest()
    {
        SetupMocksRetrieveAll();

        _controller!.RetrieveAll();

        _riderProfileServiceMock!.Verify(x => x.RetrieveAll());
    }

    [TestMethod]
    public void RetrieveAll_ContractTest()
    {
        SetupMocksRetrieveAll();

        var actionResult = _controller!.RetrieveAll();
        
        var okObjectResult = actionResult.Result as OkObjectResult;
        Assert.AreEqual(StatusCodes.Status200OK, okObjectResult!.StatusCode);
        Assert.AreEqual(_expectedRiderProfiles, okObjectResult!.Value);
    }

    #endregion

    #region Get rider profile by ID

    private void SetupMocksRetrieveById()
    {
        _riderProfileServiceMock!.Setup(x => x.RetrieveById(It.Is<string>(y => y.Equals(_expectedRiderProfile.Id))))
            .Returns(_expectedRiderProfile);
    }

    [TestMethod]
    public void RetrieveById_CollaborationTest()
    {
        SetupMocksRetrieveById();

        _controller!.RetrieveById(_expectedRiderProfile.Id!);

        _riderProfileServiceMock!.Verify(x => x.RetrieveById(It.Is<string>(y => y.Equals(_expectedRiderProfile.Id))));
    }

    [TestMethod]
    public void RetrieveById_ContractTest()
    {
        SetupMocksRetrieveById();

        var actionResult = _controller!.RetrieveById(_expectedRiderProfile.Id!);

        var okObjectResult = actionResult.Result as OkObjectResult;
        Assert.AreEqual(StatusCodes.Status200OK, okObjectResult!.StatusCode);
        Assert.AreEqual(_expectedRiderProfile, okObjectResult.Value);
    }

    #endregion

    #region Create a new rider profile

    private void SetupMocksCreate()
    {
        _riderProfileServiceMock!.Setup(x => x.Create(It.Is<RiderProfile>(y => y.Equals(_expectedRiderProfile))))
            .Returns(_expectedRiderProfile);
    }

    [TestMethod]
    public void CreateNewRiderProfile_ContractTest()
    {
        SetupMocksCreate();

        var actionResult = _controller!.Create(_expectedRiderProfile);

        var createdResult = actionResult.Result as CreatedResult;
        Assert.AreEqual(StatusCodes.Status201Created, createdResult!.StatusCode);
        Assert.AreEqual($"/riderProfiles/{_expectedRiderProfile.Id}", createdResult!.Location);
    }

    [TestMethod]
    public void CreateNewRiderProfile_CollaborationTest()
    {
        SetupMocksCreate();

        _controller!.Create(_expectedRiderProfile);

        _riderProfileServiceMock!.Verify(x => x.Create(It.Is<RiderProfile>(y => y == _expectedRiderProfile)));
    }

    #endregion

    #region Update an existing rider profile

    private void SetupMocksUpdate()
    {
        _riderProfileServiceMock!.Setup(x => x.Update(It.Is<RiderProfile>(y => y.Equals(_expectedRiderProfile))))
            .Returns(_expectedRiderProfile);
    }

    [TestMethod]
    public void Update_CollaborationTest()
    {
        SetupMocksUpdate();

        _controller!.Update(_expectedRiderProfile.Id!, _expectedRiderProfile);

        _riderProfileServiceMock!.Verify(x => x.Update(It.Is<RiderProfile>(y => y.Equals(_expectedRiderProfile))));
    }

    [TestMethod]
    public void Update_ContractTest()
    {
        SetupMocksUpdate();

        var actionResult = _controller!.Update(_expectedRiderProfile.Id!, _expectedRiderProfile);

        var noContentResult = actionResult.Result as NoContentResult;
        Assert.AreEqual(StatusCodes.Status204NoContent, noContentResult!.StatusCode);
    }

    #endregion

    #region Delete an existing rider profile

    private void SetupMocksDelete()
    {
        _riderProfileServiceMock!.Setup(x => x.RetrieveById(It.Is<string>(y => y.Equals(_expectedRiderProfile.Id))))
            .Returns(_expectedRiderProfile);
        _riderProfileServiceMock!.Setup(x => x.Delete(It.Is<RiderProfile>(y => y.Equals(_expectedRiderProfile))));
    }

    [TestMethod]
    public void Delete_CollaborationTest()
    {
        SetupMocksDelete();

        _controller!.Delete(_expectedRiderProfile.Id!);

        _riderProfileServiceMock!.Verify(x => x.RetrieveById(It.Is<string>(y => y.Equals(_expectedRiderProfile.Id))));
        _riderProfileServiceMock!.Verify(x => x.Delete(It.Is<RiderProfile>(y => y.Equals(_expectedRiderProfile))));
    }

    [TestMethod]
    public void Delete_ContractTest()
    {
        SetupMocksDelete();

        var actionResult = _controller!.Delete(_expectedRiderProfile.Id!);

        var noContentResult = actionResult.Result as NoContentResult;
        Assert.AreEqual(StatusCodes.Status204NoContent, noContentResult!.StatusCode);
    }

    #endregion
}