using Microsoft.AspNetCore.Mvc;
using tdd_example.Models;
using tdd_example.Services;

namespace tdd_example.Controllers;

[ApiController]
[Route("[controller]")]
public class RaceController
{
    private readonly ILogger<WeatherForecastController> _logger;
    private readonly IRaceService _raceService;

    public RaceController(ILogger<WeatherForecastController> logger, IRaceService raceService)
    {
        _logger = logger ?? throw new ArgumentNullException(nameof(logger));
        _raceService = raceService ?? throw new ArgumentNullException(nameof(raceService));
    }
    
    [HttpGet(Name = "races")]
    public IEnumerable<Race> GetAllRaces()
    {
        return _raceService.RetrieveAll();
    }

    [HttpGet(Name = "races/{id}")]
    public Race GetRaceById(string id)
    {
        return _raceService.RetrieveById(id);
    }
}