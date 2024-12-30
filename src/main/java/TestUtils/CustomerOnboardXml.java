package TestUtils;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.Map;

public class CustomerOnboardXml {

	private static final String NAMESPACE = "http://www.lotus.com/dxl";
	private static CustomerOnboardXml instance;

	// Private constructor to prevent instantiation
	private CustomerOnboardXml() {
	}

	// Public method to get the single instance of the class
	public static CustomerOnboardXml getInstance() {
		if (instance == null) {
			instance = new CustomerOnboardXml(); // Create instance only once
		}
		return instance;
	}

	public static void generateXmlFromTemplate(Map<String, String> customerData, String templateXmlPath,
			String outputFolderPath) throws Exception {
		// Generate the file name with timestamp
	    String timestamp = String.valueOf(System.currentTimeMillis());
	    String outputFileName = "cusOnboard_" + timestamp + ".xml";

	    // Combine the folder path and file name to create the full output path
	    String outputXmlPath = outputFolderPath + File.separator + outputFileName;
	    
	    

	    // Ensure the output folder exists
	    File outputFolder = new File(outputFolderPath);
	    if (!outputFolder.exists()) {
	        outputFolder.mkdirs(); // Create the folder if it doesn't exist
	    }
	    
		// Load the template XML
		File inputFile = new File(templateXmlPath);
		DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
		documentFactory.setNamespaceAware(true); // Important for namespace support
		DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(inputFile);

		// Update the root tags with customerData
		updateRootTags(document, customerData);

		// Update LEGAL_REP tags under LEGAL_REPS
		updateLegalRepTags(document, customerData);

//        convertEmptyTags(document);

		// Write to output file
		writeDocumentToFile(document, outputXmlPath);

		System.out.println("XML generated successfully at " + outputXmlPath);
	}

	private static void updateRootTags(Document document, Map<String, String> customerData) {
		customerData.forEach((key, value) -> {
			if (key.startsWith("custinfo.")) {
				String tagName = key.substring("custinfo.".length());
				NodeList nodeList = document.getElementsByTagNameNS(NAMESPACE, tagName);
				if (nodeList.getLength() > 0) {
					nodeList.item(0).setTextContent(value);
				}
			}
		});
	}

	private static void updateLegalRepTags(Document document, Map<String, String> customerData) {
		String namespace = "http://www.lotus.com/dxl";
		NodeList legalReps = document.getElementsByTagNameNS(namespace, "LEGAL_REP");

		// Check and add a second LEGAL_REP if missing
		if (legalReps.getLength() < 2) {
			Node legalRepsParent = legalReps.item(0).getParentNode();
			Node clonedLegalRep = legalReps.item(0).cloneNode(true);
			legalRepsParent.appendChild(clonedLegalRep);
		}

		// Update the first LEGAL_REP with "legalone." data
		if (legalReps.getLength() > 0) {
			updateSingleLegalRep(legalReps.item(0), customerData, "legalone.");
		}

		// Update the second LEGAL_REP with "legaltwo." data
		if (legalReps.getLength() > 1) {
			updateSingleLegalRep(legalReps.item(1), customerData, "legaltwo.");
		}
	}

	private static void updateSingleLegalRep(Node legalRepNode, Map<String, String> customerData, String prefix) {
		Element legalRepElement = (Element) legalRepNode;

		// Iterate over the map and update nodes that match the prefix
		customerData.forEach((key, value) -> {
			if (key.startsWith(prefix)) {
				String tagName = key.substring(prefix.length()); // Extract tag name after prefix

				// Retrieve only direct child elements
				NodeList children = legalRepElement.getChildNodes();
				for (int i = 0; i < children.getLength(); i++) {
					Node child = children.item(i);
					if (child.getNodeType() == Node.ELEMENT_NODE && child.getLocalName().equals(tagName)) {
						child.setTextContent(value); // Update the node value
						break;
					}
				}
			}
		});
	}

	private static void writeDocumentToFile(Document document, String filePath) {
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();

			// Ensure formatting to produce <TAG></TAG> for empty tags
			transformer.setOutputProperty(OutputKeys.INDENT, "no");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");

			// Prevent self-closing tags
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			transformer.setOutputProperty(OutputKeys.CDATA_SECTION_ELEMENTS, "no");

			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(new File(filePath));

			transformer.transform(source, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
