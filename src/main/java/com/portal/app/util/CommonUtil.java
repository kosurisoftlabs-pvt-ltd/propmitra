package com.portal.app.util;

import com.portal.app.exception.BadRequestException;

import java.text.DecimalFormat;
import java.util.Random;

public class CommonUtil {

    public static Integer roundToTwoDecimal(Double val) {
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.valueOf(df.format(val)).intValue();
    }

    public static int getRandomNumber() {
        return 100000 + new Random().nextInt(900000);
    }

    public static void validatePageNumberAndSize(int page, int size) {
        if (page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if (size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }
}
