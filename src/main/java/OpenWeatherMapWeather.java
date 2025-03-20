import org.json.JSONArray;
import org.json.JSONObject;

public class OpenWeatherMapWeather implements Weather {

    private double temperature; //Temperature in Kelvin by default
    private String condition;
    private TemperatureScale originalScale = TemperatureScale.KELVIN;

    //Constructor that takes JSON response to parse temperature and condition

    public OpenWeatherMapWeather(String json) {
        this.fromJson(json);
    }

    public void fromJson(String json) {
        JSONObject jsonObject = new JSONObject(json);
        JSONObject main = jsonObject.getJSONObject("main");
        this.temperature = main.getFloat("temp");
        JSONArray weather = jsonObject.getJSONArray("weather");
        JSONObject weatherObj = weather.getJSONObject(0);
        this.condition = weatherObj.getString("main");
    }


    @Override
    public double getTemperature(TemperatureScale scale) {
        switch (scale) {
            case CELSIUS:
                return kelvinToCelsius(this.temperature);// Convert Kelvin to Celsius
            case FAHRENHEIT:
                return kelvinToFahrenheit(this.temperature);// Convert Kelvin to Fahrenheit
            case KELVIN:
            default:
                return this.temperature;// Return temperature in Kelvin by default
        }
    }

    // Method to get weather condition
    @Override
    public String getCondition() {
        return this.condition;
    }

    // Method to convert Kelvin to Celsius
    private double kelvinToCelsius(double kelvin) {
        return kelvin - 273.15;// Kelvin to Celsius formula
    }

    // Method to convert Kelvin to Fahrenheit
    private double kelvinToFahrenheit(double kelvin) {
        return (kelvin - 273.15) * 9/5 + 32;// Kelvin to Fahrenheit formula
    }
}

