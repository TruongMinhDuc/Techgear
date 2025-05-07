package com.techgear.techgear_be.services.waybill;

import java.util.Map;

public interface WaybillCallbackConstants {
    int WAITING = 1;
    int SHIPPING = 2;
    int SUCCESS = 3;
    int FAILED = 4;
    int RETURN = 5;

    Map<String, Integer> WAYBILL_STATUS_CODE = Map.ofEntries(
            Map.entry("ready_to_pick", WAITING),
            Map.entry("picking", WAITING),
            Map.entry("cancel", FAILED),
            Map.entry("money_collect_picking", SHIPPING),
            Map.entry("picked", SHIPPING),
            Map.entry("transporting", SHIPPING),
            Map.entry("sorting", SHIPPING),
            Map.entry("delivering", SHIPPING),
            Map.entry("money_collect_delivering", SHIPPING),
            Map.entry("delivered", SUCCESS),
            Map.entry("delivery_fail", FAILED),
            Map.entry("waiting_to_return", RETURN),
            Map.entry("return", RETURN),
            Map.entry("return_transporting", RETURN),
            Map.entry("return_sorting", RETURN),
            Map.entry("returning", RETURN),
            Map.entry("return_fail", RETURN),
            Map.entry("returned", SUCCESS),
            Map.entry("exception", FAILED),
            Map.entry("damage", FAILED),
            Map.entry("lost", FAILED)
    );
}
