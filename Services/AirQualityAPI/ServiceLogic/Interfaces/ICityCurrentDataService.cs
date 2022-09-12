using DataLayer.DTOs;
using DataLayer.Models;
using System.Threading.Tasks;

namespace ServiceLogic.Interfaces
{
    public interface ICityCurrentDataService
    {
        public Task<ApiResponse<CityCurrentDataDTO>> GetNearestCityData(double latitude,double longitude);
        public Task<ApiResponse<CityCurrentDataDTO>> GetCityData(string city, string state, string country);
    }
}
