package com.agileengine;

import javafx.util.Pair;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AttributesFinder {

	private static Logger LOGGER = LoggerFactory.getLogger(AttributesFinder.class);

	public Optional<List<Pair<String, String>>> findAttributesById(Document doc, String elementId) {
		Optional<Element> elementOpt = Optional.of(doc.getElementById(elementId));
		if (!elementOpt.isPresent()) {
			LOGGER.info("Element id " + elementId + " not found in original file");
		}
		Optional<List<Pair<String, String>>> attributes = elementOpt.map(element ->
				element.attributes().asList().stream()
						.map(attr -> new Pair<String,String>(attr.getKey(), attr.getValue()))
						.collect(Collectors.toList()));
		if (attributes.isPresent() && !attributes.get().isEmpty()) {
			LOGGER.info("Attributes detected in original file:");
			for (Pair<String, String> att : attributes.get()) {
				LOGGER.info(att.getKey() + "=" + att.getValue());
			}
		}
		return attributes;
	}

	public Optional<Elements> findElementsByCssQuery(Document doc, String cssQuery) {
		Optional<Elements> elementsOpt = Optional.of(doc.select(cssQuery));
		if (elementsOpt.isPresent()) {
			LOGGER.info("Candidate elements: ");
			for (Element element : elementsOpt.get()) {
				LOGGER.info(element.toString());
			}
		}
		return elementsOpt;
	}
}
