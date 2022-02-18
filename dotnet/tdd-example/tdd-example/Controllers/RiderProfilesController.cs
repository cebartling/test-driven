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
    public ActionResult<Race> RetrieveById(string id)
    {
        var riderProfile = _riderProfileService.RetrieveById(id);
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

    [HttpPut("{id}")]
    [Consumes(MediaTypeNames.Application.Json)]
    [ProducesResponseType(StatusCodes.Status204NoContent)]
    [ProducesResponseType(StatusCodes.Status404NotFound)]
    public ActionResult<RiderProfile> Update(string id, RiderProfile riderProfile)
    {
        _riderProfileService.Update(riderProfile);
        return NoContent();
    }

    [HttpDelete("{id}")]
    [ProducesResponseType(StatusCodes.Status204NoContent)]
    [ProducesResponseType(StatusCodes.Status404NotFound)]
    public ActionResult<RiderProfile> Delete(string id)
    {
        var riderProfile = _riderProfileService.RetrieveById(id);
        _riderProfileService.Delete(riderProfile);
        return NoContent();
    }
}