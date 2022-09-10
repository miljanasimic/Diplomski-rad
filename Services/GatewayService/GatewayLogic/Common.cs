using System;
using System.Net.Http;
using System.Threading.Tasks;

namespace GatewayLogic
{
    public static class Common
    {
        public static async Task<HttpResponseMessage> SendApiRequest(IHttpClientFactory httpClientFactory, string requestUri)
        {
            var httpClient = httpClientFactory.CreateClient("ApiHttpClient");

            var request = new HttpRequestMessage
            {
                Method = HttpMethod.Get,
                RequestUri = new Uri(httpClient.BaseAddress.ToString() + requestUri)
            };

            return await httpClient.SendAsync(request);
        }

        public static string ConfigureApiKey(string query)
        {
            return $"{query}key=64c6eb5f-1394-4051-98bb-b74b3c886a93";
        }
    }
}
