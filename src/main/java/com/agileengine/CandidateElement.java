package com.agileengine;

import javafx.util.Pair;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class CandidateElement {

	private static Logger LOGGER = LoggerFactory.getLogger(SmartXMLAnalyzer.class);

	private final Element element;

	private final Optional<List<Pair<String, String>>> originalAttributes;

	public CandidateElement(Element element, Optional<List<Pair<String, String>>> originalAttributes) {
		this.element = element;
		this.originalAttributes = originalAttributes;
	}

	public int getScore() {
		int score = 0;
		for (Pair<String, String> att : this.originalAttributes.get()) {
			String value = Optional.ofNullable(element.attributes().get(att.getKey())).orElse("");
			if (value.equalsIgnoreCase(att.getValue())) {
				score++;
			}
		}
		LOGGER.info("Score for element [" + element.toString() + "] is " + score);
		return score;
	}

	public String toString(){
		return element.toString();
	}
}
