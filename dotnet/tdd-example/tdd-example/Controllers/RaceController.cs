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
    public CreatedResult Create(Race race)
    {
        var newlyCreatedRace = _raceService.Create(race);
        var uriString = $"races/{newlyCreatedRace.Id}";
        return Created(uriString, newlyCreatedRace);
    }

    [HttpPut(Name = "races/{id}")]
    [ProducesResponseType(StatusCodes.Status204NoContent)]
    [ProducesResponseType(StatusCodes.Status400BadRequest)]
    public NoContentResult Update(Race race)
    {
        _raceService.Update(race);
        return NoContent();
    }

    [HttpDelete(Name = "races/{id}")]
    [ProducesResponseType(StatusCodes.Status204NoContent)]
    [ProducesResponseType(StatusCodes.Status404NotFound)]
    public NoContentResult Delete(string id)
    {
        _raceService.Delete(id);
        return NoContent();
    }
}