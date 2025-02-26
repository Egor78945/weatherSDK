package io.github.egor78945.weatherSDK.service.validator;

/**
 * Class, provides validation of integers.
 */
public class IntegerValidator {
    /**
     * Return if the {@code val} is between {@code left} and {@code right}.
     * @param val Checking value.
     * @param left Left border.
     * @param right Right border.
     * @return Is between or not.
     */
    public static boolean isBetween(int val, int left, int right){
        return val >= left && val <= right || val <= left && val >= right;
    }
}
