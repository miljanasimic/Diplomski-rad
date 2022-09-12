FROM mcr.microsoft.com/dotnet/sdk:5.0 as build-env
WORKDIR /app

COPY *.sln .
COPY AirQualityAPI/*.csproj ./AirQualityAPI/
COPY ServiceLogic/*.csproj ./ServiceLogic/
COPY DataLayer/*.csproj ./DataLayer/

RUN dotnet restore

COPY AirQualityAPI/. ./AirQualityAPI/
COPY ServiceLogic/. ./ServiceLogic/
COPY DataLayer/. ./DataLayer/

WORKDIR /app/AirQualityAPI
RUN dotnet publish -c Release -o out

FROM mcr.microsoft.com/dotnet/aspnet:5.0
WORKDIR /app
COPY --from=build-env /app/AirQualityAPI/out ./

EXPOSE 80
ENTRYPOINT ["dotnet", "AirQualityAPI.dll"]