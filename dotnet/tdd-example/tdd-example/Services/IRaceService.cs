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

    /// <summary>
    /// Creates a new race.
    /// </summary>
    /// <param name="newRace">A transient Race instance without an identifier.</param>
    /// <returns>A persistent Race instance with an identifier.</returns>
    Race Create(Race newRace);

    /// <summary>
    /// Updates an existing race.
    /// </summary>
    /// <param name="existingRace">A persistent Race instance containing changes.</param>
    /// <returns>A persistent Race instance that reflects the current state of the persistent storage.</returns>
    Race Update(Race existingRace);

    /// <summary>
    /// Deletes an existing race from persistent storage.
    /// </summary>
    /// <param name="id">A string identifier for a persistent Race instance.</param>
    void Delete(string id);
}