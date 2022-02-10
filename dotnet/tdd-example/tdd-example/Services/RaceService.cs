using tdd_example.Models;

namespace tdd_example.Services;

public class RaceService : IRaceService
{
    private readonly ILogger<RaceService> _logger;
    private readonly AppDatabaseContext _appDatabaseContext;

    public RaceService(ILogger<RaceService> logger, AppDatabaseContext appDatabaseContext)
    {
        _logger = logger ?? throw new ArgumentNullException(nameof(logger));
        _appDatabaseContext = appDatabaseContext ?? throw new ArgumentNullException(nameof(appDatabaseContext));
    }

    public IEnumerable<Race> RetrieveAll()
    {
        return _appDatabaseContext.Races.AsEnumerable();
    }

    public Race? RetrieveById(string? id)
    {
        return _appDatabaseContext.Races.Find(id);
    }

    public Race Create(Race newRace)
    {
        throw new NotImplementedException();
    }

    public Race Update(Race existingRace)
    {
        throw new NotImplementedException();
    }

    public void Delete(string? id)
    {
        throw new NotImplementedException();
    }
}