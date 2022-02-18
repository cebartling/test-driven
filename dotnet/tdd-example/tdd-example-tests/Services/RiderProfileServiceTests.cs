using System;
using Microsoft.EntityFrameworkCore;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Moq;
using tdd_example;
using tdd_example.Models;
using tdd_example.Services;

namespace tdd_example_tests.Services;

[TestClass]
public class RiderProfileServiceTests
{
    #region Test suite setup

    private IRiderProfileService? _service;

    private Mock<AppDatabaseContext>? _appDatabaseContextMock;
    private Mock<DbSet<RiderProfile>>? _riderProfilesDbSetMock;
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
        _appDatabaseContextMock = new Mock<AppDatabaseContext>();
        _service = new RiderProfileService(_appDatabaseContextMock.Object);
    }

    #endregion

    #region Create new rider profile

    private void SetupMocksCreate()
    {
        _riderProfilesDbSetMock = new Mock<DbSet<RiderProfile>>();
        _appDatabaseContextMock!.Setup(x => x.RiderProfiles).Returns(_riderProfilesDbSetMock.Object);
        _riderProfilesDbSetMock!.Setup(x => x.Add(It.IsAny<RiderProfile>()));
        _riderProfilesDbSetMock!.Setup(x => x.Find(It.IsAny<string>()))
            .Returns(_expectedRiderProfile);
        _appDatabaseContextMock!.Setup(x => x.SaveChanges());
    }

    [TestMethod]
    public void Create_Success_CollaborationTest()
    {
        SetupMocksCreate();

        _expectedRiderProfile.Id = null;
        _service!.Create(_expectedRiderProfile);

        _appDatabaseContextMock!.Verify(x => x.RiderProfiles);
        _riderProfilesDbSetMock!
            .Verify(x => x.Add(It.Is<RiderProfile>(y => y.Id != null)));
        _appDatabaseContextMock.Verify(x => x.SaveChanges());
        _riderProfilesDbSetMock.Verify(x => x.Find(It.IsAny<string>()));
    }

    [TestMethod]
    public void Create_Success_ContractTest()
    {
        SetupMocksCreate();
        
        _expectedRiderProfile.Id = null;
        var actual = _service!.Create(_expectedRiderProfile);
        
        Assert.IsNotNull(actual.Id);
    }

    #endregion
}