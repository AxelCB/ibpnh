/*
 * Copyright (C) 2011 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kairos.ibpnh.core.json;

import java.io.IOException;
import java.math.BigDecimal;

import org.kairos.ibpnh.core.utils.I_BigDecimalUtils;

import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * Adapts a BigDecimal type to and from its JSON representation.
 * 
 * @author Joel Leitch
 */
public final class BigDecimalTypeAdapter extends TypeAdapter<BigDecimal> {

	/**
	 * Big decimal utils
	 */
	I_BigDecimalUtils bigDecimalUtils;
	
	/**
	 * Constructor with fields.
	 * 
	 * @param bigDecimalUtils
	 */
	public BigDecimalTypeAdapter(I_BigDecimalUtils bigDecimalUtils) {
		super();
		this.bigDecimalUtils = bigDecimalUtils;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gson.TypeAdapter#read(com.google.gson.stream.JsonReader)
	 */
	@Override
	public BigDecimal read(JsonReader reader) throws IOException {
		if (reader.peek() == JsonToken.NULL) {
			reader.nextNull();
			return null;
		}
		try {
			return this.getBigDecimalUtils().fromPlainStringFormat(reader.nextString());
		} catch (NumberFormatException e) {
			throw new JsonSyntaxException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gson.TypeAdapter#write(com.google.gson.stream.JsonWriter,
	 * java.lang.Object)
	 */
	@Override
	public void write(JsonWriter writer, BigDecimal value) throws IOException {
		if (value == null) {
			writer.value(value);
		} else {
			writer.value(this.getBigDecimalUtils().toPlainStringFormat(value));
		}
	}

	/**
	 * @return the bigDecimalUtils
	 */
	public I_BigDecimalUtils getBigDecimalUtils() {
		return this.bigDecimalUtils;
	}

	/**
	 * @param bigDecimalUtils the bigDecimalUtils to set
	 */
	public void setBigDecimalUtils(I_BigDecimalUtils bigDecimalUtils) {
		this.bigDecimalUtils = bigDecimalUtils;
	}
	
	
}
