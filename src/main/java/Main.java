import java.net.URI;
 import java.net.http.HttpClient;
 import java.net.http.HttpRequest;
 import java.net.http.HttpResponse;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");

        HttpClient client = HttpClient.newHttpClient();

        URI uri = URI.create("https://api.openweathermap.org/data/2.5/weather?lat=42&lon=-80&appid=f36a33e216c196d0dd51e1bf4a8197d3");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Status code: " + response.statusCode());
            System.out.println("Response body: " + response.body());

            String body = response.body();
            Weather weather = new OpenWeatherMapWeather(body);
            System.out.println("Temperature: " + weather.getTemperature(TemperatureScale.KELVIN));
            System.out.println("Condition: " + weather.getCondition());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
