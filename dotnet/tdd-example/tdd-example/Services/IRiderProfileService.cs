using tdd_example.Models;

namespace tdd_example.Services;

public interface IRiderProfileService
{
    RiderProfile Create(RiderProfile newRiderProfile);
    RiderProfile GetById(string id);
}