using System;
using System.Collections.Generic;
using Newtonsoft.Json;

namespace DataLayer.HelperClasses
{

    #region CityHelper
    public class Pollution
    {
        [JsonProperty(PropertyName = "ts")]
        public DateTime TimeStamp { get; set; } //timestamp

        public int Aqius { get; set; } //AQI value based on US EPA standard

        public string Mainus { get; set; } //main pollutant for US AQI

        public int Aqicn { get; set; } //AQI value based on China MEP standard

        public string Maincn { get; set; } //main pollutant for Chinese AQI
    }

    /*"p2": "ugm3", //PM2.5 Concentration (ug/m3) fine particulate matter
    "p1": "ugm3", //PM10 coarse particulate matter
    "o3": "ppb", //ground-level ozone -Ozone O3
    "n2": "ppb", //Nitrogen dioxide NO2 
    "s2": "ppb", //Sulfur dioxide SO2 
    "co": "ppm" //Carbon monoxide CO */

    public class Weather
    {
        [JsonProperty(PropertyName = "ts")]
        public DateTime TimeStamp { get; set; } //timestamp

        [JsonProperty(PropertyName = "tp")]
        public int TempCelsius { get; set; } //temperature in Celsius

        [JsonProperty(PropertyName = "pr")]
        public int Pressure { get; set; } //atmospheric pressure in hPa

        [JsonProperty(PropertyName = "hu")]
        public int Humidity { get; set; } //humidity %

        [JsonProperty(PropertyName = "ws")]
        public float WindSpeed { get; set; } //wind speed (m/s)

        [JsonProperty(PropertyName = "wd")]
        public int WindDirection { get; set; } //wind direction, as an angle of 360° (N=0, E=90, S=180, W=270)

        [JsonProperty(PropertyName = "ic")]
        public string IconCode { get; set; } //weather icon code
    }

    public class Current
    {
        public Pollution Pollution { get; set; }

        public Weather Weather { get; set; }
    }

    public class Location
    {
        public string Type { get; set; }

        public List<double> Coordinates { get; set; }
    }

    public class CityData
    {
        public string City { get; set; }

        public string State { get; set; }

        public string Country { get; set; }

        public Location Location { get; set; }

        public Current Current { get; set; }
    }
    #endregion

    #region SupportedDestinationsHelper
    public class Country
    {
        [JsonProperty(PropertyName = "country")]
        public string Name { get; set; } 
    }

    public class State
    {
        [JsonProperty(PropertyName = "state")]
        public string Name { get; set; }
    }

    public class City
    {
        [JsonProperty(PropertyName = "city")]
        public string Name { get; set; }
    }
    #endregion
}
