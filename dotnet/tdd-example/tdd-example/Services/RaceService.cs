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
        var guid = Guid.NewGuid().ToString();
        newRace.Id = guid;
        _appDatabaseContext.Races.Add(newRace);
        _appDatabaseContext.SaveChanges();
        return _appDatabaseContext.Races.Find(guid)!;
    }

    public Race Update(Race existingRace)
    {
        _appDatabaseContext.Races.Update(existingRace);
        _appDatabaseContext.SaveChanges();
        return _appDatabaseContext.Races.Find(existingRace.Id)!;
    }

    public void Delete(string? id)
    {
        var match = _appDatabaseContext.Races.Find(id);
        if (match == null) return;
        _appDatabaseContext.Races.Remove(match);
        _appDatabaseContext.SaveChanges();
    }
}