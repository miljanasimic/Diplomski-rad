using System;
using System.Net;
using System.Net.Http;
using System.Threading.Tasks;
using DataLayer.DTOs;
using DataLayer.Models;
using ServiceLogic.Interfaces;
using Newtonsoft.Json;

namespace ServiceLogic.Services
{
    public class CityCurrentDataService : ICityCurrentDataService
    {
        private readonly IHttpClientFactory _HttpClientFactory;

        public CityCurrentDataService(IHttpClientFactory httpClientFactory)
        {
            _HttpClientFactory = httpClientFactory;
        }

        public async Task<ApiResponse<CityCurrentDataDTO>> GetNearestCityData(double latitude, double longitude)
        {
            try
            {
                CityCurrentDataDTO cityData = null;
                string latitudeDot = latitude.ToString().Replace(',','.');
                string longitudeDot = longitude.ToString().Replace(',', '.');
                using (var response = await Common.SendApiRequest(_HttpClientFactory, Common.ConfigureApiKey($"/nearest_city?lat={latitudeDot}&lon={longitudeDot}&")))
                {
                    if (response.StatusCode == HttpStatusCode.OK)
                    {
                        var responseContent = await response.Content.ReadAsStringAsync();
                        cityData = JsonConvert.DeserializeObject<CityCurrentDataDTO>(responseContent);                       
                    }
                }

                if (cityData==null)
                    return new ApiResponse<CityCurrentDataDTO>
                    {
                        IsSuccess = false,
                        ErrorMessage = ""
                    };

                return new ApiResponse<CityCurrentDataDTO>
                {
                    ResponseContent = cityData,
                    IsSuccess = true
                };

            }
            catch (Exception e)
            {
                return new ApiResponse<CityCurrentDataDTO>
                {
                    IsSuccess = false,
                    ErrorMessage = e.Message
                };
            }
        }

        public async Task<ApiResponse<CityCurrentDataDTO>> GetCityData(string city, string state, string country)
        {
            try
            {
                CityCurrentDataDTO cityData = null;
                using (var response = await Common.SendApiRequest(_HttpClientFactory, Common.ConfigureApiKey($"/city?city={city}&state={state}&country={country}&")))
                {
                    if (response.StatusCode == HttpStatusCode.OK)
                    {
                        var responseContent = await response.Content.ReadAsStringAsync();
                        cityData = JsonConvert.DeserializeObject<CityCurrentDataDTO>(responseContent);
                    }
                }

                if (cityData == null)
                    return new ApiResponse<CityCurrentDataDTO>
                    {
                        IsSuccess = false,
                        ErrorMessage = ""
                    };

                return new ApiResponse<CityCurrentDataDTO>
                {
                    ResponseContent = cityData,
                    IsSuccess = true
                };

            }
            catch (Exception e)
            {
                return new ApiResponse<CityCurrentDataDTO>
                {
                    IsSuccess = false,
                    ErrorMessage = e.Message
                };
            }
        }
    }
}
