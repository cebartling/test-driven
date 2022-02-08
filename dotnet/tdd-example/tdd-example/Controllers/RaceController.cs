using System.Net;
using Microsoft.AspNetCore.Mvc;
using tdd_example.Models;
using tdd_example.Services;

namespace tdd_example.Controllers;

[ApiController]
[Route("[controller]")]
public class RaceController : ControllerBase
{
    private readonly ILogger<WeatherForecastController> _logger;
    private readonly IRaceService _raceService;

    public RaceController(ILogger<WeatherForecastController> logger, IRaceService raceService)
    {
        _logger = logger ?? throw new ArgumentNullException(nameof(logger));
        _raceService = raceService ?? throw new ArgumentNullException(nameof(raceService));
    }
    
    [HttpGet(Name = "races")]
    [ProducesResponseType(StatusCodes.Status200OK)]
    public IEnumerable<Race> GetAllRaces()
    {
        return _raceService.RetrieveAll();
    }

    [HttpGet(Name = "races/{id}")]
    [ProducesResponseType(StatusCodes.Status200OK)]
    public Race GetRaceById(string id)
    {
        return _raceService.RetrieveById(id);
    }

    [HttpPost(Name = "races")]
    [ProducesResponseType(StatusCodes.Status201Created)]
    [ProducesResponseType(StatusCodes.Status400BadRequest)]
    public ActionResult<Race> Create(string name, DateOnly raceDate)
    {
        var newlyCreatedRace = _raceService.Create(name, raceDate);
        return CreatedAtAction(nameof(GetRaceById), new {id = newlyCreatedRace.Id}, newlyCreatedRace);
    }
}