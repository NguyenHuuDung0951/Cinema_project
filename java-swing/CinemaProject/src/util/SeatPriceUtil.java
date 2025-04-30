
package util;

import java.util.Map;

public class SeatPriceUtil {
    private static final Map<String, Double> PRICE_MAP = Map.of(
        "ST01", 60000.0,
        "ST02", 90000.0,
        "ST03", 100000.0
    );

    public static double getPriceByType(String seatTypeID) {
        return PRICE_MAP.getOrDefault(seatTypeID, 60000.0);
    }
}
