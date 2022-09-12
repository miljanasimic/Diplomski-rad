namespace DataLayer.Models
{
    public class ApiResponse<T>
    {
        public bool IsSuccess { get; set; }

        public T ResponseContent { get; set; }

        public string ErrorMessage { get; set; }
    }
}
