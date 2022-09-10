using GatewayLogic.Interfaces;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace GatewayAPI.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class SupportedDestinationsController : ControllerBase
    {

        private readonly ISupportedDestinationsService _destinationsService;

        public SupportedDestinationsController(ISupportedDestinationsService destinationsService)
        {
            _destinationsService = destinationsService;
        }

        [HttpGet]
        [Route("Countries")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        [ProducesResponseType(StatusCodes.Status500InternalServerError)]
        public async Task<IActionResult> GetSupportedCountries()
        {
            try
            {
                var result = await _destinationsService.GetSupportedCountries();

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
        [Route("States/{countryName}")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        [ProducesResponseType(StatusCodes.Status500InternalServerError)]
        public async Task<IActionResult> GetSupportedStates(string countryName)
        {
            try
            {
                var result = await _destinationsService.GetSupportedStates(countryName);

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
        [Route("Cities/{countryName}/{stateName}")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        [ProducesResponseType(StatusCodes.Status500InternalServerError)]
        public async Task<IActionResult> GetSupportedStates(string countryName, string stateName)
        {
            try
            {
                var result = await _destinationsService.GetSupportedCities(countryName, stateName);

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
