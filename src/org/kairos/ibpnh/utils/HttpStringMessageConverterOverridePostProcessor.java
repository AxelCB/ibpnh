package org.kairos.ibpnh.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;

import java.nio.charset.Charset;

/**
 * Overrides the StringHttpConverter to use UTF-8 as the default encoding.
 *
 * @author AxelCollardBovy ,created on 24/02/2015.
 * 
 */
@SuppressWarnings("deprecation")
public class HttpStringMessageConverterOverridePostProcessor implements
		BeanPostProcessor {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.beans.factory.config.BeanPostProcessor#
	 * postProcessAfterInitialization(java.lang.Object, java.lang.String)
	 */
	@Override
	public Object postProcessAfterInitialization(Object bean, String name)
			throws BeansException {
		if (bean instanceof AnnotationMethodHandlerAdapter) {
			// the configured bean is the AnnotationMethodHandlerAdapter
			HttpMessageConverter<?>[] converters = ((AnnotationMethodHandlerAdapter) bean)
					.getMessageConverters();
			for (int i = 0; i < converters.length; i++) {
				if (converters[i] instanceof StringHttpMessageConverter) {
					// we override the StringHttpMessageConverter with a new one
					// with the default encoding as UTF-8
					converters[i] = new StringHttpMessageConverter(
							Charset.forName("UTF-8"));
				}
			}
		}
		return bean;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.beans.factory.config.BeanPostProcessor#
	 * postProcessBeforeInitialization(java.lang.Object, java.lang.String)
	 */
	@Override
	public Object postProcessBeforeInitialization(Object bean, String name)
			throws BeansException {
		// does nothing
		return bean;
	}

}
