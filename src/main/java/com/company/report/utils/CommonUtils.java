package com.company.report.utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * The class defines the common utility methods for the daily trade reporting
 * engine feature.
 *
 * @author prejith.devarajan
 *
 */
public class CommonUtils {

	/**
	 * Sort the map based on its value. The method accepts a map which needs to
	 * be sorted. However the method does not modify the input map as it creates
	 * a new map based on the sorted entries.
	 *
	 * @param unsortMap
	 *            the map which needs to be sorted by value
	 * @return sortedMap the resulting sorted map
	 */
	public static <K, V extends Comparable<? super V>> Map<K, V> sortMapByValue(Map<K, V> unsortMap) {

	    List<Map.Entry<K, V>> list = new LinkedList<>(unsortMap.entrySet());
	    // sort the map entries
	    Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
	        public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
	            return (o2.getValue()).compareTo(o1.getValue());
	        }
	    });

		/*
		 * Constructs a new map based on the already sorted map entries. This is
		 * to make the input unmodified.
		 */
		Map<K, V> result = new LinkedHashMap<>();
		for (Map.Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}
	    return result;
	}
}
