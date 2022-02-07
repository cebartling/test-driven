using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using Microsoft.Extensions.Logging;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Moq;
using tdd_example;
using tdd_example.Controllers;

namespace tdd_example_tests.Controllers;

[TestClass]
public class WeatherForecastControllerTests
{
    private Mock<ILogger<WeatherForecastController>> loggerMock;
    private WeatherForecastController controller;

    [TestInitialize]
    public void DoBeforeEachTest()
    {
        loggerMock = new Mock<ILogger<WeatherForecastController>>();
        controller = new WeatherForecastController(loggerMock.Object);
    }

    [TestMethod]
    public void GetTest()
    {
        loggerMock.Setup(x =>
                x.Log(
                    It.IsAny<LogLevel>(),
                    It.IsAny<EventId>(),
                    It.IsAny<It.IsAnyType>(),
                    It.IsAny<Exception>(),
                    (Func<It.IsAnyType, Exception, string>)It.IsAny<object>()
                )
            )
            .Callback(new InvocationAction(invocation =>
            {
                // The first two will always be whatever is specified in the setup above
                // so I'm not sure you would ever want to actually use them
                var logLevel = (LogLevel)invocation.Arguments[0];
                var eventId = (EventId)invocation.Arguments[1];
                var state = invocation.Arguments[2];
                var exception = (Exception)invocation.Arguments[3];
                var formatter = invocation.Arguments[4];
                var invokeMethod = formatter.GetType().GetMethod("Invoke");
                var logMessage = (string)invokeMethod?.Invoke(formatter, new[] { state, exception });

                Trace.WriteLine($"{logLevel} - {logMessage}");
            }));
        IEnumerable<WeatherForecast> weatherForecasts = controller.Get();
        Assert.IsNotNull(weatherForecasts);
        Assert.AreEqual(5, weatherForecasts.Count());
        loggerMock.Verify(x => x.Log(
            It.IsAny<LogLevel>(),
            It.IsAny<EventId>(),
            It.IsAny<It.IsAnyType>(),
            It.IsAny<Exception>(),
            (Func<It.IsAnyType, Exception, string>)It.IsAny<object>()));
    }
}