package com.technical.test.meli.challenge.Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.technical.test.meli.challenge.Application.Dto.ItemsResponse.ItemsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class Utils {

    private static final Logger logger = LoggerFactory.getLogger(Utils.class);

    public static <T> String convertToJson(T object) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(object);
    }

    public static boolean isValidIds(String ids) {
        return ids.matches("^(MLA\\d+)(,MLA\\d+)*$");
    }

    public static List<ItemsResponse> getLargestNonOverlappingSet(List<ItemsResponse> items) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Map<ItemsResponse, Date[]> dateMap = new HashMap<>();

        try {
            for (ItemsResponse item : items) {
                Date start = sdf.parse(item.getBody().getDateCreated());
                Date end = sdf.parse(item.getBody().getLastUpdated());
                dateMap.put(item, new Date[]{start, end});
            }

            items.sort(Comparator.comparing(o -> dateMap.get(o)[1]));

            List<ItemsResponse> currentCombination = new ArrayList<>();

            for (ItemsResponse item : items) {
                boolean overlaps = false;
                Date[] itemDates = dateMap.get(item);

                for (ItemsResponse selected : currentCombination) {
                    Date[] selectedDates = dateMap.get(selected);

                    if (!(itemDates[1].before(selectedDates[0]) || itemDates[0].after(selectedDates[1]))) {
                        overlaps = true;
                        break;
                    }
                }

                if (!overlaps) {
                    currentCombination.add(item);
                }
            }

            return currentCombination;
        } catch (Exception e) {
            logger.error("Error in getLargestNonOverlappingSet", e);
            return null;
        }

    }

}