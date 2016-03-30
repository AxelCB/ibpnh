package org.kairos.ibpnh.core.json;

import org.kairos.ibpnh.core.utils.I_DateUtils;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

/**
 * @author Axel Collard Bovy
 * 
 */
public class ReadableDateFormatTypeAdapterFactory implements TypeAdapterFactory {

	/**
	 * Date Utils.
	 */
	private I_DateUtils dateUtils;

	/**
	 * Constructor with fields.
	 * 
	 * @param dateUtils
	 */
	public ReadableDateFormatTypeAdapterFactory(I_DateUtils dateUtils) {
		super();
		this.dateUtils = dateUtils;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gson.TypeAdapterFactory#create(com.google.gson.Gson,
	 * com.google.gson.reflect.TypeToken)
	 */
	@Override
	public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
		// gets the original type adapter
		TypeAdapter<T> originalTypeAdapter = gson
				.getDelegateAdapter(this, type);

		// try to get the annotation
		GsonAddDateReadableFormat annotation = type.getRawType().getAnnotation(
				GsonAddDateReadableFormat.class);

		if (annotation == null) {
			return originalTypeAdapter;
		} else {
			// we return the new type adapter
			return new ReadableDateFormatTypeAdapter<T>(this.getDateUtils(),
					originalTypeAdapter, annotation.fields(),
					annotation.formats(), gson);
		}
	}

	/**
	 * @return the dateUtils
	 */
	public I_DateUtils getDateUtils() {
		return this.dateUtils;
	}

	/**
	 * @param dateUtils
	 *            the dateUtils to set
	 */
	public void setDateUtils(I_DateUtils dateUtils) {
		this.dateUtils = dateUtils;
	}

}
