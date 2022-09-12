using DataLayer.DTOs;
using DataLayer.Models;
using System.Threading.Tasks;

namespace ServiceLogic.Interfaces
{
    public interface ISupportedDestinationsService
    {
        public Task<ApiResponse<SupportedCountriesDTO>> GetSupportedCountries();

        public Task<ApiResponse<SupportedStatesDTO>> GetSupportedStates(string countryName);

        public Task<ApiResponse<SupportedCitiesDTO>> GetSupportedCities(string countryName, string stateName);
    }
}
