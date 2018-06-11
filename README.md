# Smart XML Analyzer

## Intro


Imagine that you are writing a simple web crawler that locates a user-selected element on a web site with frequently changing information. You regularly face an issue that the crawler fails to find the element after minor page updates. After some analysis you decided to make your analyzer tolerant to minor website changes so that you don’t have to update the code every time.

It would be best to view the attached HTML page, imagining that you need to find the “Everything OK” button on every page.

## Run test cases

A fat jar is provided on the root of the project (smart-xml-analyzer-all-0.0.1.jar)

To run the app exexute:

```sh
java -jar <input_origin_file_path> <input_other_sample_file_path> <element_id> <css_selector>
```

cssSelector accepts null input (default to a.btn)

### Sample

Samples are in the src/test/resources/samples. For example, to run test case number 4 use the following command:
```sh
java -jar smart-xml-analyzer-all-0.0.1.jar src/test/resources/samples/sample-0-origin.html src/test/resources/samples/sample-4-the-mash.html make-everything-ok-button
```

The app will print out the candidates, the score for each element, and the selected element.