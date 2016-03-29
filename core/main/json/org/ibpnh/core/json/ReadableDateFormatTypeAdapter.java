package org.ibpnh.core.json;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ibpnh.core.utils.I_DateUtils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * Readable Date Format Type Adapter for GSON.
 * 
 * @author Axel Collard Bovy
 * 
 * @param <T>
 */
public class ReadableDateFormatTypeAdapter<T> extends TypeAdapter<T> {

	/**
	 * Logger.
	 */
	private Logger logger = LoggerFactory
			.getLogger(ReadableDateFormatTypeAdapter.class);

	/**
	 * Date Utils.
	 */
	private I_DateUtils dateUtils;

	/**
	 * Original type adapter.
	 */
	private TypeAdapter<T> originalTypeAdapter;

	/**
	 * Fields to format as readable dates.
	 */
	private String[] fields;

	/**
	 * Formats to use.
	 */
	private E_DateFormat[] formats;

	/**
	 * GSON.
	 */
	private Gson gson;

	/**
	 * Constructor with fields.
	 * 
	 * @param dateUtils
	 * @param originalTypeAdapter
	 * @param fields
	 * @param formats
	 * @param gson
	 */
	public ReadableDateFormatTypeAdapter(I_DateUtils dateUtils,
			TypeAdapter<T> originalTypeAdapter, String[] fields,
			E_DateFormat[] formats, Gson gson) {
		super();
		this.dateUtils = dateUtils;
		this.originalTypeAdapter = originalTypeAdapter;
		this.fields = fields;
		this.formats = formats;
		this.gson = gson;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gson.TypeAdapter#read(com.google.gson.stream.JsonReader)
	 */
	@Override
	public T read(JsonReader jsonReader) throws IOException {
		// this doesn't do nothing
		return this.getOriginalTypeAdapter().read(jsonReader);
	}

	/**
	 * Constructs the name for the formatted field.
	 * 
	 * @param fieldName
	 *            the original field name
	 * 
	 * @return constructed name
	 */
	private String buildName(String fieldName) {
		return "readable" + fieldName.substring(0, 1).toUpperCase()
				+ fieldName.substring(1, fieldName.length());
	}

	/**
	 * Constructs the name for the original field getter method.
	 * 
	 * @param fieldName
	 *            the original field name
	 * @return constructed getter method name
	 */
	private String buildGetterName(String fieldName) {
		return "get" + fieldName.substring(0, 1).toUpperCase()
				+ fieldName.substring(1, fieldName.length());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gson.TypeAdapter#write(com.google.gson.stream.JsonWriter,
	 * java.lang.Object)
	 */
	@Override
	public void write(JsonWriter jsonWriter, T object) throws IOException {
		JsonElement jsonElement = this.getOriginalTypeAdapter().toJsonTree(
				object);

		if (jsonElement.isJsonObject()) {
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			// adds formatted fields
			this.addFormattedFields(jsonObject, object);

			// write the modified jsonObject to the jsonWriter
			this.getGson().toJson(jsonObject, jsonWriter);
		} else {
			this.logger.debug("jsonElement was not a jsonObject");

			// write the original jsonElement to the jsonWriter
			this.getGson().toJson(jsonElement, jsonWriter);
		}

	}

	/**
	 * Adds formatted fields.
	 * 
	 * @param jsonObject
	 * @param object
	 * @throws IOException
	 */
	private void addFormattedFields(JsonObject jsonObject, T object) {
		try {
			// add newly formatted fields
			for (int i = 0; i < this.getFields().length; i++) {
				Method method = object.getClass().getMethod(
						this.buildGetterName(this.getFields()[i]));
				Date date = (Date) method.invoke(object);

				if (date != null) {
					String dateString = this.getDateUtils().format(date,
							this.getFormats()[i]);

					jsonObject.addProperty(this.buildName(this.getFields()[i]),
							dateString);
				}
			}
		} catch (NoSuchMethodException | SecurityException
				| IllegalArgumentException | IllegalAccessException e) {
			this.logger.error("error trying to format date fields", e);
		} catch (Exception e) {
			this.logger.error("unkown error trying to format date fields", e);
		}
	}

	/**
	 * @return the originalTypeAdapter
	 */
	public TypeAdapter<T> getOriginalTypeAdapter() {
		return this.originalTypeAdapter;
	}

	/**
	 * @param originalTypeAdapter
	 *            the originalTypeAdapter to set
	 */
	public void setOriginalTypeAdapter(TypeAdapter<T> originalTypeAdapter) {
		this.originalTypeAdapter = originalTypeAdapter;
	}

	/**
	 * @return the fields
	 */
	public String[] getFields() {
		return this.fields;
	}

	/**
	 * @param fields
	 *            the fields to set
	 */
	public void setFields(String[] fields) {
		this.fields = fields;
	}

	/**
	 * @return the formats
	 */
	public E_DateFormat[] getFormats() {
		return this.formats;
	}

	/**
	 * @param formats
	 *            the formats to set
	 */
	public void setFormats(E_DateFormat[] formats) {
		this.formats = formats;
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

	/**
	 * @return the gson
	 */
	public Gson getGson() {
		return this.gson;
	}

	/**
	 * @param gson
	 *            the gson to set
	 */
	public void setGson(Gson gson) {
		this.gson = gson;
	}

}
