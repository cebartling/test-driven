using tdd_example;
using tdd_example.Services;

var builder = WebApplication.CreateBuilder(args);

// Add database contexts.
builder.Services.AddDbContext<AppDatabaseContext>();

// Add services to the container.
builder.Services.AddScoped<IRaceService, RaceService>();

builder.Services.AddControllers();
// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();


var app = builder.Build();

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseHttpsRedirection();

app.UseAuthorization();

app.MapControllers();

app.Run();