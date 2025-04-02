import org.json.JSONArray;
import org.json.JSONObject;

public class OpenWeatherMapWeather implements Weather {

    private double temperature;
    private String condition;
    private TemperatureScale originalScale = TemperatureScale.KELVIN;

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
        return this.temperature;
    }

    @Override
    public String getCondition() {
        return this.condition;
    }
}