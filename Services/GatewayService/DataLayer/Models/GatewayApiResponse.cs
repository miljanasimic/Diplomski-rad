namespace DataLayer.Models
{
    public class GatewayApiResponse<T>
    {
        public bool IsSuccess { get; set; }

        public T ResponseContent { get; set; }

        public string ErrorMessage { get; set; }
    }
}
