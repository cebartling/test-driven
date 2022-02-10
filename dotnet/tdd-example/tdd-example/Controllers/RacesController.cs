using System.Net.Mime;
using Microsoft.AspNetCore.Mvc;
using tdd_example.Models;
using tdd_example.Services;

namespace tdd_example.Controllers;

[ApiController]
[Route("api/[controller]")]
public class RacesController : ControllerBase
{
    private readonly ILogger<RacesController> _logger;
    private readonly IRaceService _raceService;

    public RacesController(ILogger<RacesController> logger, IRaceService raceService)
    {
        _logger = logger ?? throw new ArgumentNullException(nameof(logger));
        _raceService = raceService ?? throw new ArgumentNullException(nameof(raceService));
    }

    [HttpGet]
    [ProducesResponseType(StatusCodes.Status200OK)]
    public IEnumerable<Race> GetAllRaces()
    {
        return _raceService.RetrieveAll();
    }

    [HttpGet("{id}")]
    [ProducesResponseType(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status404NotFound)]
    public ActionResult<Race> GetRaceById(string? id)
    {
        var race = _raceService.RetrieveById(id);
        // if (race == null)
        // {
            // return NotFound();
        // }
        return Ok(race);
    }

    [HttpPost]
    [Consumes(MediaTypeNames.Application.Json)]
    [ProducesResponseType(StatusCodes.Status201Created)]
    [ProducesResponseType(StatusCodes.Status400BadRequest)]
    public ActionResult<Race> Create(Race race)
    {
        var newlyCreatedRace = _raceService.Create(race);
        var uriString = $"races/{newlyCreatedRace.Id}";
        return Created(uriString, newlyCreatedRace);
    }

    [HttpPut("{id}")]
    [Consumes(MediaTypeNames.Application.Json)]
    [ProducesResponseType(StatusCodes.Status204NoContent)]
    [ProducesResponseType(StatusCodes.Status400BadRequest)]
    public ActionResult<Race> Update(string? id, Race race)
    {
        _raceService.Update(race);
        return NoContent();
    }

    [HttpDelete("{id}")]
    [ProducesResponseType(StatusCodes.Status204NoContent)]
    [ProducesResponseType(StatusCodes.Status404NotFound)]
    public ActionResult<Race> Delete(string? id)
    {
        _raceService.Delete(id);
        return NoContent();
    }
}