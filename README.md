# Weather SDK
## Introduction
This is a SDK, used for access to weather in any city. It allows you to use the built-in interface to get weather information in a city by city name. You can also create your own interface implementation to suit your requirements.
## Contents
[Configuration](https://github.com/Egor78945/weatherSDK?tab=readme-ov-file#Configuration)  
[Usage example](https://github.com/Egor78945/weatherSDK?tab=readme-ov-file#Usage%20example)
## Configuration
SDK configured using application.properties file. It has 5 properties, 4 optionally and 1 is mandatory.  
### Mandatory
weather.secret - It receive a string, which represents a secret access key to OpenWeatherMapAPI.
### Optionally
weather.mode - It is a work mode of the sdk. It has two modes - on_demand, polling.
- On_demand: SDK will caching OpenWeatherMapAPI responses for optimization. And if you will have getting cached city one more time, it will return cached value, instead one more request to API. But if the cached data is not actual, SDK will update cached data and return it.
- Polling: SDK will automatically tracking cached data and update it.
  
weather.cache.expiration - Time in **MINUTES**, after which cached data will be considered expired.  
weather.cache.update - Time in **MINUTES** as a pause, after which cached data will be updating.  
weather.cache.limit - The maximum count of caching objects.  
All of these optionally properties using only for default realisation of interface. If you will using your custom solutions, it not necessary.
## Usage example
To start using the SDK, you just need to write default realisation code like this:  
```java
JsonMapper<CityWeatherDTO> cityWeatherDTOJsonMapper = new CityWeatherJsonMapper();  
WeatherProperties weatherProperties = new WeatherProperties();  
WebClientService<CityWeatherDTO> webClientService = new WeatherClientService(new WebClientConfiguration(), cityWeatherDTOJsonMapper);  
CacheService<CityWeatherDTO> cacheService = new WeatherCacheService(new ReentrantLock(), weatherProperties.getCACHE_LIMIT());  
WeatherService<CityWeatherDTO> weatherService = new DefaultWeatherService(cityWeatherDTOJsonMapper, webClientService, weatherProperties, cacheService);  
try {  
   System.out.println(weatherService.getCityWeather("moscow"));  
} catch (IOException e) {  
   throw new RuntimeException(e);  
}
```
The result of this code will be like this:  
```json
{
"weather": {
   "main": "Clear",
   "description": "clear sky"
},
"temperature":{
   "temp": 271.46,
   "feels_like": 266.12
},
"visibility": 10000,
"wind":{
   "speed": 5.0
},
"datetime": 1740587581,
"sys":{
   "sunrise": 1740541780,
   "sunset": 1740579401
},
"timezone": 10800,
"name": "Moscow"
}
```
Temperature is in Kelvins.
