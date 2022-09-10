using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using GatewayLogic.Interfaces;
using Microsoft.AspNetCore.Http;
using System;

namespace GatewayAPI.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class CityCurrentDataController : ControllerBase
    {
        private readonly ICityCurrentDataService _citiesService;

        public CityCurrentDataController(ICityCurrentDataService citiesService)
        {
            _citiesService = citiesService;
        }

        [HttpGet]
        [Route("{latitude}/{longitude}")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        [ProducesResponseType(StatusCodes.Status500InternalServerError)]
        public async Task<IActionResult> GetNearestCityData([FromRoute] double latitude, [FromRoute] double longitude)
        {
            try
            {
                var result = await _citiesService.GetNearestCityData(latitude, longitude);

                if (result == null)
                    return StatusCode(500, "An error occured.");

                if (result.ErrorMessage != null)
                    return NotFound(result.ErrorMessage);

                return Ok(result.ResponseContent);
            }
            catch (Exception e)
            {
                return StatusCode(500, "An error occured.");
            }
        }

        [HttpGet]
        [Route("{city}/{state}/{country}")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        [ProducesResponseType(StatusCodes.Status500InternalServerError)]
        public async Task<IActionResult> GetCityData([FromRoute] string city, [FromRoute] string state, [FromRoute] string country)
        {
            try
            {
                var result = await _citiesService.GetCityData(city, state, country);

                if (result == null)
                    return StatusCode(500, "An error occured.");

                if (result.ErrorMessage != null)
                    return NotFound(result.ErrorMessage);

                return Ok(result.ResponseContent);
            }
            catch (Exception e)
            {
                return StatusCode(500, "An error occured.");
            }
        }
    }
}
