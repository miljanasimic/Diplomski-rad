using DataLayer.DTOs;
using DataLayer.Models;
using GatewayLogic.Interfaces;
using Newtonsoft.Json;
using System;
using System.Net;
using System.Net.Http;
using System.Threading.Tasks;

namespace GatewayLogic.Services
{
    public class SupportedDestinationsService : ISupportedDestinationsService
    {
        private readonly IHttpClientFactory _HttpClientFactory;

        public SupportedDestinationsService(IHttpClientFactory httpClientFactory)
        {
            _HttpClientFactory = httpClientFactory;
        }

        public async Task<GatewayApiResponse<SupportedCountriesDTO>> GetSupportedCountries()
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
                    return new GatewayApiResponse<SupportedCountriesDTO>
                    {
                        IsSuccess = false,
                        ErrorMessage = "There was an error fetching countries."
                    };

                return new GatewayApiResponse<SupportedCountriesDTO>
                {
                    ResponseContent = supportedCountries,
                    IsSuccess = true
                };

            } catch (Exception ex)
            {
                return new GatewayApiResponse<SupportedCountriesDTO>
                {
                    IsSuccess = false,
                    ErrorMessage = ex.Message
                };
            }
        }

        public async Task<GatewayApiResponse<SupportedStatesDTO>> GetSupportedStates(string countryName)
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
                    return new GatewayApiResponse<SupportedStatesDTO>
                    {
                        IsSuccess = false,
                        ErrorMessage = "There was an error fetching states."
                    };

                return new GatewayApiResponse<SupportedStatesDTO>
                {
                    ResponseContent = supportedStates,
                    IsSuccess = true
                };

            }
            catch (Exception ex)
            {
                return new GatewayApiResponse<SupportedStatesDTO>
                {
                    IsSuccess = false,
                    ErrorMessage = ex.Message
                };
            }
        }

        public async Task<GatewayApiResponse<SupportedCitiesDTO>> GetSupportedCities(string countryName, string stateName)
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
                    return new GatewayApiResponse<SupportedCitiesDTO>
                    {
                        IsSuccess = false,
                        ErrorMessage = "There was an error fetching cities."
                    };

                return new GatewayApiResponse<SupportedCitiesDTO>
                {
                    ResponseContent = supportedCities,
                    IsSuccess = true
                };

            }
            catch (Exception ex)
            {
                return new GatewayApiResponse<SupportedCitiesDTO>
                {
                    IsSuccess = false,
                    ErrorMessage = ex.Message
                };
            }
        }
    }
}
