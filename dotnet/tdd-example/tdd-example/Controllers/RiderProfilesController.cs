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
}