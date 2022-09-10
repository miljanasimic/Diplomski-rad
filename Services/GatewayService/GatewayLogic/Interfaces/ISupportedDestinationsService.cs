using DataLayer.DTOs;
using DataLayer.Models;
using System.Threading.Tasks;

namespace GatewayLogic.Interfaces
{
    public interface ISupportedDestinationsService
    {
        public Task<GatewayApiResponse<SupportedCountriesDTO>> GetSupportedCountries();

        public Task<GatewayApiResponse<SupportedStatesDTO>> GetSupportedStates(string countryName);

        public Task<GatewayApiResponse<SupportedCitiesDTO>> GetSupportedCities(string countryName, string stateName);
    }
}
