package com.androidapps.buyusedcars.utilities;

import java.math.BigDecimal;

public class Converters {
    //  instead of this line, we can use method to convert
    //  android:text="@{String.valueOf(model.mileage/1000).concat(@string/k).concat(@string/space).concat(@string/mi)}"
    public static String convertToKMi(Float miles) {

        String milesInK = miles / 1000 + "k mi";
        return milesInK;
    }

    //BigDecimal(16777217) is converted to a float, the result is 1.6777216E7.

    public static float decimalToFloat(BigDecimal decimal) {

        return decimal.floatValue();
    }

    public static String addStringWithValue(String name, String value) {

        String resultString = name + "  :  " + value;
        return resultString;
    }
}
