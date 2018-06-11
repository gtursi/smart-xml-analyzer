package com.agileengine;


import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import static org.hamcrest.CoreMatchers.containsString;

import java.util.List;

import static java.util.Arrays.asList;

@RunWith(DataProviderRunner.class)
public class SmartXMLAnalyzerTest {

	private static final String BASE_DIR = "src/test/resources/samples/";

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	@UseDataProvider("inputSampleFiles")
	public void findBestMatch_givenBothFiles_elementFound(String newFile){
		String originalFile = BASE_DIR + "sample-0-origin.html";
		String elementId = "make-everything-ok-button";

		SmartXMLAnalyzer smartXMLAnalyzer = new SmartXMLAnalyzer(originalFile, BASE_DIR + newFile, elementId, null);
		smartXMLAnalyzer.findBestMatch();

		// TODO: return the element and assert it's the correct one
	}

	@Test
	public void findBestMatch_givenNonexistentElementId_exceptionThronw(){
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage(containsString("Element id id-not-exist not found in original file"));

		String originalFile = BASE_DIR + "sample-0-origin.html";
		String elementId = "id-not-exist";
		SmartXMLAnalyzer smartXMLAnalyzer = new SmartXMLAnalyzer(originalFile, originalFile, elementId, null);
		smartXMLAnalyzer.findBestMatch();

		// TODO: return the element and assert it's the correct one
	}

	@Test
	public void findBestMatch_givenNonexistentFile_exceptionThronw(){
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage(containsString("Error reading ["));

		String originalFile = BASE_DIR + "sample-file-non-exist.html";
		String elementId = "id-not-exist";
		SmartXMLAnalyzer smartXMLAnalyzer = new SmartXMLAnalyzer(originalFile, originalFile, elementId, null);
		smartXMLAnalyzer.findBestMatch();

		// TODO: return the element and assert it's the correct one
	}

	@DataProvider(format = "(%p[0]) %m")
	public static List<List<String>> inputSampleFiles() {
		return asList(
				//   Description						Input File
				asList("Sample 0 original file", "sample-0-origin.html"),
				asList("Sample 1 evil gemini", "sample-1-evil-gemini.html"),
				asList("Sample 2 container and clone", "sample-2-container-and-clone.html" ),
				asList("Sample 3 the escape", "sample-3-the-escape.html"),
				asList("Sample 4 the mash", "sample-4-the-mash.html")
				);
	}

}
