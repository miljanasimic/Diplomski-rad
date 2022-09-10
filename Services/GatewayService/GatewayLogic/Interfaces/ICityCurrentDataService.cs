using DataLayer.DTOs;
using DataLayer.Models;
using System.Threading.Tasks;

namespace GatewayLogic.Interfaces
{
    public interface ICityCurrentDataService
    {
        public Task<GatewayApiResponse<CityCurrentDataDTO>> GetNearestCityData(double latitude,double longitude);
        public Task<GatewayApiResponse<CityCurrentDataDTO>> GetCityData(string city, string state, string country);
    }
}
