using Microsoft.EntityFrameworkCore;
using tdd_example.Models;

namespace tdd_example;

public class AppDatabaseContext : DbContext
{
    private string _host = "localhost";
    private string _database = "racing_db";
    private string _username = "racing_user";
    private string _password = "P4ssword";

    public virtual DbSet<Race> Races { get; set; } = null!;
    public virtual DbSet<RiderProfile> RiderProfiles { get; set; }

    protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
    {
        var connectionString = $"Host={_host};Database={_database};Username={_username};Password={_password}";
        optionsBuilder.UseNpgsql(connectionString);
    }
}