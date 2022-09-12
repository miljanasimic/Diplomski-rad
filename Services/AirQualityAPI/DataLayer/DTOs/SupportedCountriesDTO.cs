using DataLayer.HelperClasses;
using Newtonsoft.Json;
using System.Collections.Generic;

namespace DataLayer.DTOs
{
    public class SupportedCountriesDTO
    {
        public string Status { get; set; }

        [JsonProperty(PropertyName = "data")]
        public List<Country> Countries { get; set; }
    }
}
