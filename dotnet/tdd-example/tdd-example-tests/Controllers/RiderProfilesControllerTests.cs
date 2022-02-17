using System;
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

    [TestInitialize]
    public void DoBeforeEachTest()
    {
        _riderProfileServiceMock = new Mock<IRiderProfileService>();
        _controller = new RiderProfilesController(_riderProfileServiceMock.Object);
    }

    #endregion

    #region Create a new rider profile

    private void ConfigureMocksCreate()
    {
        _riderProfileServiceMock!.Setup(x => x.Create(It.Is<RiderProfile>(y => y.Equals(_expectedRiderProfile))))
            .Returns(_expectedRiderProfile);
    }

    [TestMethod]
    public void CreateNewRiderProfile_ContractTest()
    {
        ConfigureMocksCreate();
        
        var actionResult = _controller!.Create(_expectedRiderProfile);
        
        var createdResult = actionResult.Result as CreatedResult;
        Assert.AreEqual(StatusCodes.Status201Created, createdResult!.StatusCode);
        Assert.AreEqual($"/riderProfiles/{_expectedRiderProfile.Id}", createdResult!.Location);
    }

    [TestMethod]
    public void CreateNewRiderProfile_CollaborationTest()
    {
        ConfigureMocksCreate();
        
        _controller!.Create(_expectedRiderProfile);
        
        _riderProfileServiceMock!.Verify(x => x.Create(It.Is<RiderProfile>(y => y == _expectedRiderProfile)));
    }

    #endregion
}