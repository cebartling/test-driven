using tdd_example.Models;

namespace tdd_example.Services;

public interface IRaceService
{
    /// <summary>
    /// Retrieves all the races from the persistent store.
    /// </summary>
    /// <returns>
    /// An IEnumerable of Race models.
    /// </returns>
    IEnumerable<Race> RetrieveAll();

    /// <summary>
    /// Retrieves a race by its primary key identifer.
    /// </summary>
    /// <param name="id">Represents the primary key identifier for the race in persistent storage.</param>
    /// /// <returns>A Race model instance.</returns>
    Race RetrieveById(string id);
}