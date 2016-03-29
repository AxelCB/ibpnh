package org.ibpnh.core.json;

import java.lang.reflect.Type;

import org.quartz.JobDetail;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * Job Detail Serializer.
 * 
 * @author Axel Collard Bovy
 * 
 */
public class JobDetailSerializer implements JsonSerializer<JobDetail> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gson.JsonSerializer#serialize(java.lang.Object,
	 * java.lang.reflect.Type, com.google.gson.JsonSerializationContext)
	 */
	@Override
	public JsonElement serialize(JobDetail jobDetail, Type type,
			JsonSerializationContext context) {
		// the object
		JsonObject jsonObject = new JsonObject();

		jsonObject.addProperty("jobClass", jobDetail.getJobClass().getCanonicalName());
		jsonObject.add("jobDataMap", context.serialize(jobDetail.getJobDataMap()));
		jsonObject.addProperty("description", jobDetail.getDescription());
		
		return jsonObject;
	}

}
