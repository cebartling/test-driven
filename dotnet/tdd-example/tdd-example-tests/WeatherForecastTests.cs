using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using tdd_example;

namespace tdd_example_tests;

[TestClass]
public class WeatherForecastTests
{
    private WeatherForecast _weatherForecast;

    [TestInitialize]
    public void DoBeforeEachTest()
    {
        _weatherForecast = new WeatherForecast();
    }

    [TestMethod]
    public void TemperatureFTest()
    {
        _weatherForecast.Date = DateTime.Now;
        _weatherForecast.TemperatureC = 0;
        Assert.AreEqual(32, _weatherForecast.TemperatureF);
    }
}