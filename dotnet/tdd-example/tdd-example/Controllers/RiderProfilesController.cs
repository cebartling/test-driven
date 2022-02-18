using System.Net.Mime;
using Microsoft.AspNetCore.Mvc;
using tdd_example.Models;
using tdd_example.Services;

namespace tdd_example.Controllers;

[ApiController]
[Route("api/[controller]")]
public class RiderProfilesController : ControllerBase
{
    private readonly IRiderProfileService _riderProfileService;

    public RiderProfilesController(IRiderProfileService riderProfileService)
    {
        _riderProfileService = riderProfileService;
    }

    [HttpPost]
    [Consumes(MediaTypeNames.Application.Json)]
    [ProducesResponseType(StatusCodes.Status201Created)]
    [ProducesResponseType(StatusCodes.Status400BadRequest)]
    public ActionResult<RiderProfile> Create(RiderProfile riderProfile)
    {
        var savedRiderProfile = _riderProfileService.Create(riderProfile);
        var locationUrl = $"/riderProfiles/{savedRiderProfile.Id}";
        return Created(locationUrl, savedRiderProfile);
    }

    [HttpGet("{id}")]
    [Produces(MediaTypeNames.Application.Json)]
    [ProducesResponseType(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status404NotFound)]
    public ActionResult<Race> GetById(string id)
    {
        var riderProfile = _riderProfileService.GetById(id);
        return Ok(riderProfile);
    }

    [HttpGet]
    [Produces(MediaTypeNames.Application.Json)]
    [ProducesResponseType(StatusCodes.Status200OK)]
    public ActionResult<IEnumerable<RiderProfile>> RetrieveAll()
    {
        var riderProfiles =  _riderProfileService.RetrieveAll();
        return Ok(riderProfiles);
    }
}