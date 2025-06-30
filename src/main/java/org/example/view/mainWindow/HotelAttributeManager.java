package org.example.view.mainWindow;

import java.util.ArrayList;
import java.util.List;

public class HotelAttributeManager {
    private static final List<String> attributeList = new ArrayList<>();
//attribute für senioruser
    static {
        attributeList.add("family friendly");
        attributeList.add("dog friendly");
        attributeList.add("spa");
        attributeList.add("fitness");
    }

    public static List<String> getAttributes() {
        return new ArrayList<>(attributeList);
    }

    public static void addAttribute(String attr) {
        if (!attributeList.contains(attr)) {
            attributeList.add(attr);
        }
    }
}
