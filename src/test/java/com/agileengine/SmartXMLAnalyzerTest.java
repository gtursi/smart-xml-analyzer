package com.agileengine;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class SmartXMLAnalyzerTest {

	private static final String BASE_DIR = "src/test/resources/samples/";

	@Test
	public void findBestMatch_givenBothOriginalFiles_elementFound(){
		String originalFile = BASE_DIR + "sample-0-origin.html";
		String elementId = "make-everything-ok-button";
		SmartXMLAnalyzer smartXMLAnalyzer = new SmartXMLAnalyzer(originalFile, originalFile, elementId, null);
		smartXMLAnalyzer.findBestMatch();
	}
}
