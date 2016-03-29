package org.ibpnh.core.utils;

import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Utils method for XML generation and handlig.
 * 
 * @author Axel Collard Bovy
 *
 */
public class XmlUtils {

	public static final String WS_NAMESPACE = "http://tempuri.org/";
	public static final String WS_PREFIX = "ws";
	
	/**
	 * Gets a newly created document.
	 * 
	 * @return a Document
	 * 
	 * @throws ParserConfigurationException
	 */
	public static Document getNewDocument() throws ParserConfigurationException {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory
				.newDocumentBuilder();

		return documentBuilder.newDocument();
	}
	
	/**
	 * Transforms a Document to it's string representation.
	 * 
	 * @param document
	 *            a Document
	 * 
	 * @return an XML string
	 * @throws TransformerConfigurationException
	 *             , TransformerException
	 */
	public static String documentToString(Document document)
			throws TransformerConfigurationException, TransformerException {
		TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		StringWriter writer = new StringWriter();
		transformer
				.transform(new DOMSource(document), new StreamResult(writer));

		return writer.getBuffer().toString();
	}
	
	/**
	 * Transforms an Element to it's string representation.
	 * 
	 * @param element
	 *            an Element
	 * 
	 * @return an XML string
	 * @throws TransformerConfigurationException
	 *             , TransformerException
	 */
	public static String elementToString(Element element)
			throws TransformerConfigurationException, TransformerException {
		TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		StringWriter writer = new StringWriter();
		transformer
				.transform(new DOMSource(element), new StreamResult(writer));

		return writer.getBuffer().toString();
	}
	
}
