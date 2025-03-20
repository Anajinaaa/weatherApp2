public interface Weather {
    static void fromJson(String json) {

    }

    double getTemperature(TemperatureScale scale);
    String getCondition();
}

