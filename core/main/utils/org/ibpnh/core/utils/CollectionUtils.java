package org.ibpnh.core.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Utils Class with collection helper methods
 * 
 * @author Axel Collard Bovy
 *
 */
public class CollectionUtils {

	/**
	 * Converts a collection to a map using the convenience converter interface.
	 * 
	 * @param collection the collection to convert 
	 * @param converter the converter to use
	 * 
	 * @return the resulte map
	 */
	public static <T,U,E> Map<T,U> collectionToMap(Collection<E> collection, CollectionToMapConverter<T,U,E> converter) {
		Map<T,U> map = new HashMap<>();
		
		for (E object : collection) {
			map.put(converter.getKey(object), converter.getValue(object));
		}
		
		return map;
	}	
	
	/**
	 * Inner Interface used in the listToMap method
	 * 
	 * @author Axel Collard Bovy
	 *
	 * @param <T> the key class for the map
	 * @param <U> the value class for the map
	 * @param <E> the collection item's class
	 */
	public interface CollectionToMapConverter<T,U,E> {
		
		/**
		 * Get's the value to use as key for this object
		 * 
		 * @param object the object
		 * 
		 * @return key
		 */
		public T getKey(E object);
		
		/**
		 * Get's the value to use as value for this object
		 * 
		 * @param object the object
		 * 
		 * @return value
		 */
		public U getValue(E object);
	}
	
}
