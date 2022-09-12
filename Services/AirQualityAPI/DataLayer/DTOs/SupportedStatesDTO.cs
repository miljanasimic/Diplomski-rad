using DataLayer.HelperClasses;
using Newtonsoft.Json;
using System.Collections.Generic;

namespace DataLayer.DTOs
{
    public class SupportedStatesDTO
    {
        public string Status { get; set; }

        [JsonProperty(PropertyName = "data")]
        public List<State> states { get; set; }
    }
}
