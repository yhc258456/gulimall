package com.rachel.common.utils;

import java.util.List;

public class CollectionUtils {

    private CollectionUtils() {

    }

    public static boolean isEmpty(List collections) {
        return collections == null || collections.isEmpty();
    }

    public static boolean isNotEmpty(List collections) {
        return !isEmpty(collections);
    }


}
