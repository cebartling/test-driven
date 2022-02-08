namespace tdd_example.Models;

public class Race
{
    public string Id { get; }
    public DateOnly RaceDate { get; }
    public string Name { get; }

    public Race(string id, string name, DateOnly raceDate)
    {
        Id = id;
        Name = name;
        RaceDate = raceDate;
    }
}