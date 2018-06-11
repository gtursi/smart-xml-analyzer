package com.agileengine;

import javafx.util.Pair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class SmartXMLAnalyzer {

	private static final String DEFAULT_CSS_SELECTOR = "a.btn";
	private static Logger LOGGER = LoggerFactory.getLogger(SmartXMLAnalyzer.class);

	private static String CHARSET_NAME = "utf8";
	private String originalFilePath;
	private String newFilePath;
	private String elementId;
	private String cssSelector;

	public SmartXMLAnalyzer(String originalFilePath, String newFilePath, String elementId, String cssSelector) {
		this.originalFilePath = originalFilePath;
		this.newFilePath = newFilePath;
		this.elementId = elementId;
		this.cssSelector = cssSelector != null? cssSelector : DEFAULT_CSS_SELECTOR;
	}

	private static Document getDocument(String resourcePath) {
		File htmlFile = new File(resourcePath);
		try {
			Document doc = Jsoup.parse(
					htmlFile,
					CHARSET_NAME,
					htmlFile.getAbsolutePath());
			return doc;
		} catch (IOException ioException) {
			String msg = String.format("Error reading [{}] file", resourcePath);
			throw new RuntimeException(msg, ioException);
		}
	}

	public void findBestMatch() {
		Document originalFile = getDocument(originalFilePath);
		AttributesFinder attDetector = new AttributesFinder();
		Optional<List<Pair<String, String>>> originalAttributes = attDetector.findAttributesById(originalFile, elementId);
		Document newFile = getDocument(newFilePath);
		Optional<Elements> candidates = attDetector.findElementsByCssQuery(newFile, cssSelector);
		if (!candidates.isPresent()) {
			String msg = "Could not find any element with cssQuery [{}] in file [{}]";
			throw new IllegalArgumentException(String.format(msg, cssSelector, newFilePath));
		}
		Optional<CandidateElement> bestElement = candidates.get().stream().
				map(element -> new CandidateElement(element, originalAttributes)).
				max(Comparator.comparingInt(CandidateElement::getScore));
		if (bestElement.isPresent()) {
			LOGGER.info("Selected element: " + bestElement.get().toString());
		} else {
			LOGGER.info("Similar element could not be found");
		}
	}
}
