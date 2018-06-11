package com.agileengine;

import javafx.util.Pair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class DocumentUtils {

	private static Logger LOGGER = LoggerFactory.getLogger(AttributesFinder.class);

	private static String CHARSET_NAME = "utf8";

	private DocumentUtils() {
	}

	public static Document getDocument(String resourcePath) {
		File htmlFile = new File(resourcePath);
		try {
			Document doc = Jsoup.parse(
					htmlFile,
					CHARSET_NAME,
					htmlFile.getAbsolutePath());
			return doc;
		} catch (IOException ioException) {
			String msg = String.format("Error reading [{}] file", resourcePath);
			throw new IllegalArgumentException(msg, ioException);
		}
	}

	public static Optional<List<Pair<String, String>>> findAttributesById(Document doc, String elementId) {
		Optional<Element> elementOpt = Optional.ofNullable(doc.getElementById(elementId));
		if (!elementOpt.isPresent()) {
			throw new IllegalArgumentException("Element id " + elementId + " not found in original file");
		}
		Optional<List<Pair<String, String>>> attributes = elementOpt.map(element ->
				element.attributes().asList().stream()
						.map(attr -> new Pair<String, String>(attr.getKey(), attr.getValue()))
						.collect(Collectors.toList()));
		if (attributes.isPresent() && !attributes.get().isEmpty()) {
			LOGGER.info("Attributes detected in original file:");
			for (Pair<String, String> att : attributes.get()) {
				LOGGER.info("\t" + att.getKey() + "=" + att.getValue());
			}
		}
		return attributes;
	}

	public static Optional<Elements> findElementsByCssQuery(Document doc, String cssQuery) {
		Optional<Elements> elementsOpt = Optional.of(doc.select(cssQuery));
		if (elementsOpt.isPresent()) {
			LOGGER.info("Candidate elements: ");
			for (Element element : elementsOpt.get()) {
				LOGGER.info("\t" + element.toString());
			}
		}
		return elementsOpt;
	}

}
