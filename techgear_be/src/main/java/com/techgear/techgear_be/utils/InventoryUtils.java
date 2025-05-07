package com.techgear.techgear_be.utils;

import com.techgear.techgear_be.models.inventory.DocketVariant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryUtils {

    public static Map<String, Integer> calculateInventoryIndices(List<DocketVariant> transactions) {
        int inventory = 0;
        int waitingForDelivery = 0;
        int canBeSold;
        int areComing = 0;

        for (DocketVariant transaction : transactions) {
            if (transaction.getDocket().getType().equals(1) && transaction.getDocket().getStatus().equals(3)) {
                inventory += transaction.getQuantity();
            }

            if (transaction.getDocket().getType().equals(2) && transaction.getDocket().getStatus().equals(3)) {
                inventory -= transaction.getQuantity();
            }

            if (transaction.getDocket().getType().equals(2) && List.of(1, 2).contains(transaction.getDocket().getStatus())) {
                waitingForDelivery += transaction.getQuantity();
            }

            if (transaction.getDocket().getType().equals(1) && List.of(1, 2).contains(transaction.getDocket().getStatus())) {
                areComing += transaction.getQuantity();
            }
        }

        canBeSold = inventory - waitingForDelivery;

        Map<String, Integer> indices = new HashMap<>();

        indices.put("inventory", inventory);
        indices.put("waitingForDelivery", waitingForDelivery);
        indices.put("canBeSold", canBeSold);
        indices.put("areComing", areComing);

        return indices;
    }

}
