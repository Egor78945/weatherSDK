package org.example.weather.service.validator;

/**
 * Class, provides validation of city name.
 */
public class CityValidator {
    /**
     * Checks if is valid city name.
     * @param city City name.
     * @return Is valid or not.
     */
    public static boolean isValidCity(String city) {
        return city != null && !city.isEmpty() && !city.isBlank() && city.length() <= 50 && isContainsOnlyLetters(city);
    }

    /**
     * Checks if city contains only letters and spaces.
     * @param city City name.
     * @return Contains only letters and spaces or not.
     */
    private static boolean isContainsOnlyLetters(String city) {
        for (char c : city.toCharArray()) {
            if (!(c >= 97 && c <= 122 || c == 32 || c >= 65 && c <= 90)) {
                return false;
            }
        }
        return true;
    }
}
