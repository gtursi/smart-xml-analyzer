package com.agileengine;

public class XmlAnalyzerApp {

	public static void main(String[] args) {
		if (args.length < 3) {
			throw new IllegalArgumentException("Please provide the following arguments: original file, new file and element id");
		}
		String originalFilePath = args[0];
		String newFilePath = args[1];
		String elementId = args[2];
		// In the examples, a.btn is the only common attribute.
		// We add the chance to pass the cssSelector to make the solution more generic.
		String cssSelector = null;
		if (args.length == 4) {
			cssSelector = args[4];
		}
		SmartXMLAnalyzer smartXMLAnalyzer = new SmartXMLAnalyzer(originalFilePath, newFilePath, elementId, cssSelector);
		smartXMLAnalyzer.findBestMatch();
	}

}