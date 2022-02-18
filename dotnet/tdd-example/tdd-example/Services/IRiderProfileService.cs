using tdd_example.Models;

namespace tdd_example.Services;

public interface IRiderProfileService
{
    /// <summary>
    /// Creates a new rider profile.
    /// </summary>
    /// <param name="newRiderProfile">A transient RiderProfile. It won't have an ID.</param>
    /// <returns>A persistent RiderProfile object instance with an ID.</returns>
    RiderProfile Create(RiderProfile newRiderProfile);
    
    /// <summary>
    /// Creates a new rider profile.
    /// </summary>
    /// <param name="riderProfile">A new version of RiderProfile state with an ID.</param>
    /// <returns>A persistent RiderProfile object instance with an ID representing this new version of state.</returns>
    RiderProfile Update(RiderProfile riderProfile);
    
    /// <summary>
    /// Obtain a RiderProfile by its ID.
    /// </summary>
    /// <param name="id">A string representing the identifier for a RiderProfile.</param>
    /// <returns>A persistent RiderProfile object instance representing the current version of state.</returns>
    RiderProfile RetrieveById(string id);
    
    /// <summary>
    /// Obtain all RiderProfile instances in the system.
    /// </summary>
    /// <returns>An IEnumerable of RiderProfile object instances representing the current version of state.</returns>
    IEnumerable<RiderProfile> RetrieveAll();
}