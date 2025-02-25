package io.github.egor78945.weatherSDK.model.dto;

/**
 * Representation of JSON of weather information in a city with concrete fields.
 */
public class CityWeatherDTO {
    private Weather weather;
    private Temperature temperature;
    private int visibility;
    private Wind wind;
    private long datetime;
    private Sys sys;
    private int timezone;
    private String name;

    public CityWeatherDTO(Builder builder) {
        this.weather = builder.weather;
        this.temperature = builder.temperature;
        this.visibility = builder.visibility;
        this.wind = builder.wind;
        this.datetime = builder.datetime;
        this.sys = builder.sys;
        this.timezone = builder.timezone;
        this.name = builder.name;
    }

    public static class Builder {
        private Weather weather;
        private Temperature temperature;
        private int visibility;
        private Wind wind;
        private long datetime;
        private Sys sys;
        private int timezone;
        private String name;

        public Builder setWeather(Weather weather){
            this.weather = weather;
            return this;
        }

        public Builder setTemperature(Temperature temperature){
            this.temperature = temperature;
            return this;
        }

        public Builder setVisibility(int visibility){
            this.visibility = visibility;
            return this;
        }

        public Builder setWind(Wind wind){
            this.wind = wind;
            return this;
        }

        public Builder setDatetime(long datetime){
            this.datetime = datetime;
            return this;
        }

        public Builder setSys(Sys sys){
            this.sys = sys;
            return this;
        }

        public Builder setTimezone(int timezone){
            this.timezone = timezone;
            return this;
        }

        public Builder setName(String name){
            this.name = name;
            return this;
        }

        public CityWeatherDTO build(){
            return new CityWeatherDTO(this);
        }
    }

    /**
     * Information about weather.
     */
    public class Weather {
        /**
         * Current weather in a city.
         */
        private String main;
        /**
         * Detailed information about clouds.
         */
        private String description;

        public Weather(String main, String description) {
            this.main = main;
            this.description = description;
        }

        public String getMain() {
            return main;
        }

        public String getDescription() {
            return description;
        }
    }

    /**
     * Information about current temperature in a city.
     */
    public class Temperature {
        /**
         * Current temperature.
         */
        private double temp;
        /**
         * Temperature of temperature feels like.
         */
        private double feels_like;

        public Temperature(double temp, double feels_like) {
            this.temp = temp;
            this.feels_like = feels_like;
        }

        public double getTemp() {
            return temp;
        }

        public double getFeels_like() {
            return feels_like;
        }
    }

    /**
     * Information about wind.
     */
    public class Wind {
        /**
         * Speed of wind.
         */
        private double speed;

        public Wind(double speed) {
            this.speed = speed;
        }

        public double getSpeed() {
            return speed;
        }
    }

    /**
     * Information about sun.
     */
    public static class Sys {
        /**
         * Time of sunrise.
         */
        private long sunrise;
        /**
         * Time of sunset.
         */
        private long sunset;

        public Sys(long sunrise, long sunset) {
            this.sunrise = sunrise;
            this.sunset = sunset;
        }

        public long getSunrise() {
            return sunrise;
        }

        public long getSunset() {
            return sunset;
        }
    }

    public static Builder builder(){
        return new Builder();
    }

    public Weather getWeather() {
        return weather;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public int getVisibility() {
        return visibility;
    }

    public Wind getWind() {
        return wind;
    }

    public long getDatetime() {
        return datetime;
    }

    public Sys getSys() {
        return sys;
    }

    public int getTimezone() {
        return timezone;
    }

    public String getName() {
        return name;
    }
}
