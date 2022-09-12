using DataLayer.HelperClasses;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataLayer.DTOs
{
    public class SupportedCitiesDTO
    {
        public string Status { get; set; }

        [JsonProperty(PropertyName = "data")]
        public List<City> cities { get; set; }
    }
}
