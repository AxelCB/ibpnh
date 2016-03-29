package org.ibpnh.core.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.persistence.EntityManager;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.io.IOUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.ibpnh.core.dao.configuration.parameter.I_ParameterDao;
import org.ibpnh.core.utils.CollectionUtils.CollectionToMapConverter;
import org.ibpnh.core.vo.configuration.parameter.ParameterVo;

/**
 * String Utils.
 * 
 * @author Axel Collard Bovy
 * 
 */
public class StringUtils {

	/**
	 * Static email validator instance.
	 */
	private static EmailValidator emailValidator = EmailValidator.getInstance();

	/**
	 * Returns a list of the referenced parameters included in the specified
	 * template. (parameters are enclosed in {{}} brackets)
	 * 
	 * @param template
	 *            the template to search parameters for
	 * @return list of Strings
	 */
	public static List<String> referencedParametersList(String template) {
		if (org.apache.commons.lang.StringUtils.isBlank(template)) {
			return new ArrayList<>();
		} else {
			List<String> parameters = new ArrayList<>();
			Pattern pattern = Pattern.compile("(\\{\\{.+?\\}\\})");
			Matcher matcher = pattern.matcher(template);

			// iterates the template searching groups that match the {{}}
			// pattern for parameter
			while (matcher.find()) {
				// adds the parameter itself (without the {{}} characters)
				parameters.add(matcher.group().substring(2,
						matcher.group().length() - 2));
			}

			return parameters;
		}
	}

	/**
	 * Returns a list with the referenced parameters that are checked at
	 * compile-time.
	 * 
	 * @param parameters
	 *            the parameters to filter
	 * 
	 * @return
	 */
	public static List<String> compileTimeParametersList(List<String> parameters) {
		if (parameters != null) {
			List<String> parametersAux = new ArrayList<>();
			parametersAux.addAll(parameters);
			org.apache.commons.collections4.CollectionUtils.filter(
					parametersAux, new Predicate<String>() {
						@Override
						public boolean evaluate(String parameter) {
							return !parameter
									.startsWith(ParameterVo.TEMPLATE_PREFFIX)
									&& !parameter
											.startsWith(ParameterVo.REFERENCE_PREFFIX);
						}
					});
			return parametersAux;
		} else {
			return new ArrayList<>();
		}
	}

	/**
	 * Returns a list with the referenced parameters that are checked at
	 * creation time.
	 * 
	 * @param parameters
	 *            the parameters to filter
	 * 
	 * @return
	 */
	public static List<String> creationTimeParametersList(
			List<String> parameters) {
		if (parameters != null) {
			List<String> parametersAux = new ArrayList<>();
			parametersAux.addAll(parameters);
			org.apache.commons.collections4.CollectionUtils.filter(
					parametersAux, new Predicate<String>() {
						@Override
						public boolean evaluate(String parameter) {
							return parameter
									.startsWith(ParameterVo.TEMPLATE_PREFFIX)
									|| parameter
											.startsWith(ParameterVo.REFERENCE_PREFFIX);
						}
					});
			return parametersAux;
		} else {
			return new ArrayList<>();
		}
	}

	/**
	 * Compiles a text using creation and compile parameters.
	 * 
	 * @param em
	 *            entity manager (to query for parameters)
	 * @param parameterDao
	 *            parameter DAO (to query for parameters)
	 * @param text
	 *            the text to compile
	 * @param compileTimeParameters
	 *            compile time parameters
	 * @param referencedCreationTimeParameters
	 *            creation time parameters
	 * @param referencedCompileTimeParameters
	 *            compile time parameters
	 * 
	 * @return compiled text
	 */
	public static String compileText(EntityManager em,
			I_ParameterDao parameterDao, String text,
			Map<String, String> compileTimeParameters,
			List<String> referencedCreationTimeParameters,
			List<String> referencedCompileTimeParameters) {
		List<ParameterVo> parameters = null;

		if (referencedCreationTimeParameters.size() > 0) {
			parameters = parameterDao.getByName(em,
					org.apache.commons.collections4.CollectionUtils.collect(
							referencedCreationTimeParameters,
							new Transformer<String, String>() {
								@Override
								public String transform(String line) {
									return line.replaceFirst("^ref\\.", "");
								}
							}, new ArrayList<String>()));
		} else {
			parameters = new ArrayList<>();
		}

		Map<String, String> creationTimeParameters = CollectionUtils
				.collectionToMap(
						parameters,
						new CollectionToMapConverter<String, String, ParameterVo>() {

							@Override
							public String getKey(ParameterVo object) {
								return object.getName();
							}

							@Override
							public String getValue(ParameterVo object) {
								return object.getValue();
							}

						});

		// replace all the creation time parameters
		for (String parameter : referencedCreationTimeParameters) {
			text = text.replace("{{" + parameter + "}}", creationTimeParameters
					.get(parameter.replaceFirst("^ref\\.", "")));
		}
		// replace all the compile time parameters
		for (String parameter : referencedCompileTimeParameters) {
			text = text.replace("{{" + parameter + "}}",
					compileTimeParameters.get(parameter));
		}

		return text;
	}

	/**
	 * Compiles a text using creation and compile parameters.
	 * 
	 * @param em
	 *            entity manager (to query for parameters)
	 * @param parameterDao
	 *            parameter DAO (to query for parameters)
	 * @param text
	 *            the text to compile
	 * @param compileTimeParameters
	 *            compile time parameters
	 * 
	 * @return compiled text
	 */
	public static String compileText(EntityManager em,
			I_ParameterDao parameterDao, String text,
			Map<String, String> compileTimeParameters) {
		List<String> referenced = referencedParametersList(text);
		List<String> creation = creationTimeParametersList(referenced);
		List<String> compile = compileTimeParametersList(referenced);

		return compileText(em, parameterDao, text, compileTimeParameters,
				creation, compile);
	}

	/**
	 * Convenience method that redirects the call to the Apache Commons
	 * StringUtils class.
	 * 
	 * @param text
	 *            the text
	 * @return true iif text is null and != ""
	 */
	public static Boolean isBlank(String text) {
		return org.apache.commons.lang.StringUtils.isBlank(text);
	}

	/**
	 * Convenience method that redirects the call to the Apache Commons
	 * StringUtils class.
	 * 
	 * @param text
	 *            the text
	 * @return true iif text is not null or == ""
	 */
	public static Boolean isNotBlank(String text) {
		return org.apache.commons.lang.StringUtils.isNotBlank(text);
	}

	/**
	 * Validates a mail.
	 * 
	 * @param mail
	 *            mail
	 * 
	 * @return true iif the mail is valid
	 */
	public static Boolean validMail(String mail) {
		return emailValidator.isValid(mail);
	}

	/**
	 * Validates a list of mails. Must be a list of valid mails, separated by
	 * ","
	 * 
	 * @param mailList
	 *            the mail list to validate
	 * 
	 * @return true iif all mails in the list are valid
	 */
	public static Boolean validMailList(String mailList) {
		String[] mails = mailList.split(",");

		// for every mail
		for (String mail : mails) {
			if (!validMail(mail)) {
				// if one fails, the whole list fails
				return Boolean.FALSE;
			}
		}

		// no mail failed, everything is nice
		return Boolean.TRUE;
	}

	/**
	 * Compresses a string for HTTP transport.
	 * 
	 * @param text
	 *            the text to compress
	 * 
	 * @return compressed text
	 * 
	 * @throws IOException
	 */
	public static String compressString(String text) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		GZIPOutputStream gzos = new GZIPOutputStream(baos);
		gzos.write(text.getBytes());
		IOUtils.closeQuietly(gzos);

		byte[] bytes = baos.toByteArray();

		return Base64.encodeBase64String(bytes);
	}

	/**
	 * Uncompresses a string compressed for HTTP transport.
	 * 
	 * @param zippedBase64Text
	 *            compressed text
	 * 
	 * @return original string
	 * 
	 * @throws IOException
	 */
	public static String uncompressString(String zippedBase64Text)
			throws IOException {
		String result = null;

		byte[] bytes = Base64.decodeBase64(zippedBase64Text);
		GZIPInputStream gzis = null;
		try {
			gzis = new GZIPInputStream(new ByteArrayInputStream(bytes));
			result = IOUtils.toString(gzis);
		} finally {
			IOUtils.closeQuietly(gzis);
		}
		return result;
	}
}
