using tdd_example.Models;

namespace tdd_example.Services;

public class RiderProfileService : IRiderProfileService
{
    private readonly AppDatabaseContext _appDatabaseContext;

    public RiderProfileService(AppDatabaseContext appDatabaseContext)
    {
        _appDatabaseContext = appDatabaseContext;
    }

    public RiderProfile Create(RiderProfile newRiderProfile)
    {
        var guid = Guid.NewGuid().ToString();
        newRiderProfile.Id = guid;
        _appDatabaseContext.RiderProfiles.Add(newRiderProfile);
        _appDatabaseContext.SaveChanges();
        return _appDatabaseContext.RiderProfiles.Find(guid)!;
    }

    public RiderProfile Update(RiderProfile riderProfile)
    {
        throw new NotImplementedException();
    }

    public RiderProfile RetrieveById(string id)
    {
        throw new NotImplementedException();
    }

    public IEnumerable<RiderProfile> RetrieveAll()
    {
        throw new NotImplementedException();
    }

    public void Delete(RiderProfile riderProfile)
    {
        throw new NotImplementedException();
    }
}