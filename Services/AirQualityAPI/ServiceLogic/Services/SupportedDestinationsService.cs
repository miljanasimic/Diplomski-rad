using DataLayer.DTOs;
using DataLayer.Models;
using ServiceLogic.Interfaces;
using Newtonsoft.Json;
using System;
using System.Net;
using System.Net.Http;
using System.Threading.Tasks;

namespace ServiceLogic.Services
{
    public class SupportedDestinationsService : ISupportedDestinationsService
    {
        private readonly IHttpClientFactory _HttpClientFactory;

        public SupportedDestinationsService(IHttpClientFactory httpClientFactory)
        {
            _HttpClientFactory = httpClientFactory;
        }

        public async Task<ApiResponse<SupportedCountriesDTO>> GetSupportedCountries()
        {
            try
            {
                SupportedCountriesDTO supportedCountries = null;
                using (var response = await Common.SendApiRequest(_HttpClientFactory, Common.ConfigureApiKey("/countries?")))
                {
                    if (response.StatusCode == HttpStatusCode.OK)
                    {
                        var responseContent = await response.Content.ReadAsStringAsync();
                        supportedCountries = JsonConvert.DeserializeObject<SupportedCountriesDTO>(responseContent);
                    }
                }
                if (supportedCountries==null)
                    return new ApiResponse<SupportedCountriesDTO>
                    {
                        IsSuccess = false,
                        ErrorMessage = "There was an error fetching countries."
                    };

                return new ApiResponse<SupportedCountriesDTO>
                {
                    ResponseContent = supportedCountries,
                    IsSuccess = true
                };

            } catch (Exception ex)
            {
                return new ApiResponse<SupportedCountriesDTO>
                {
                    IsSuccess = false,
                    ErrorMessage = ex.Message
                };
            }
        }

        public async Task<ApiResponse<SupportedStatesDTO>> GetSupportedStates(string countryName)
        {
            try
            {
                SupportedStatesDTO supportedStates = null;
                using (var response = await Common.SendApiRequest(_HttpClientFactory, Common.ConfigureApiKey($"/states?country={countryName}&")))
                {
                    if (response.StatusCode == HttpStatusCode.OK)
                    {
                        var responseContent = await response.Content.ReadAsStringAsync();
                        supportedStates = JsonConvert.DeserializeObject<SupportedStatesDTO>(responseContent);
                    }
                }
                if (supportedStates == null)
                    return new ApiResponse<SupportedStatesDTO>
                    {
                        IsSuccess = false,
                        ErrorMessage = "There was an error fetching states."
                    };

                return new ApiResponse<SupportedStatesDTO>
                {
                    ResponseContent = supportedStates,
                    IsSuccess = true
                };

            }
            catch (Exception ex)
            {
                return new ApiResponse<SupportedStatesDTO>
                {
                    IsSuccess = false,
                    ErrorMessage = ex.Message
                };
            }
        }

        public async Task<ApiResponse<SupportedCitiesDTO>> GetSupportedCities(string countryName, string stateName)
        {
            try
            {
                SupportedCitiesDTO supportedCities = null;
                using (var response = await Common.SendApiRequest(_HttpClientFactory, Common.ConfigureApiKey($"/cities?country={countryName}&state={stateName}&")))
                {
                    if (response.StatusCode == HttpStatusCode.OK)
                    {
                        var responseContent = await response.Content.ReadAsStringAsync();
                        supportedCities = JsonConvert.DeserializeObject<SupportedCitiesDTO>(responseContent);
                    }
                }
                if (supportedCities == null)
                    return new ApiResponse<SupportedCitiesDTO>
                    {
                        IsSuccess = false,
                        ErrorMessage = "There was an error fetching cities."
                    };

                return new ApiResponse<SupportedCitiesDTO>
                {
                    ResponseContent = supportedCities,
                    IsSuccess = true
                };

            }
            catch (Exception ex)
            {
                return new ApiResponse<SupportedCitiesDTO>
                {
                    IsSuccess = false,
                    ErrorMessage = ex.Message
                };
            }
        }
    }
}
