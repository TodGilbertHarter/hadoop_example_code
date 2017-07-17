/**
 * 
 */
package com.boeing.hadoop.security.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author tharter
 *
 */
public class TestGenerator {
	private final TestFactory testFactory;
	private XPath xPath;
	
	public TestGenerator(TestFactory tf) {
		this.testFactory = tf;
		xPath = XPathFactory.newInstance().newXPath();
	}

	public Document parseTestDescription(File testDescription) throws TestGeneratorException {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document rv = builder.parse(testDescription);
			return rv;
		} catch (ParserConfigurationException | SAXException | IOException e) {
			throw new TestGeneratorException("Failed to parse test description "+testDescription.getPath(),e);
		}
	}
	
	private Map<String,Subject> getSubjects(Document testDescription) throws TestGeneratorException {
		try {
			Map<String,Subject> result = new HashMap<String,Subject>();
			String xpr = "/securitytest/subjects/groups/*";
			NodeList nl = (NodeList) xPath.compile(xpr).evaluate(testDescription, XPathConstants.NODESET);
			for(int i = 0; i < nl.getLength(); i++) {
				Node n = nl.item(i);
				NamedNodeMap nAttrs = n.getAttributes();
				String id = nAttrs.getNamedItem("id").getNodeValue();
				String name = nAttrs.getNamedItem("name").getNodeValue();
				Subject s = new Group(id,name);
				result.put(id, s);
			}
			Map<String,Subject> groups = new HashMap<String,Subject>(result);
			
			xpr = "/securitytest/subjects/users/*";
			nl = (NodeList) xPath.compile(xpr).evaluate(testDescription, XPathConstants.NODESET);
			for(int i = 0; i < nl.getLength(); i++) {
				Node n = nl.item(i);
				NamedNodeMap nAttrs = n.getAttributes();
				String id = nAttrs.getNamedItem("id").getNodeValue();
				String name = nAttrs.getNamedItem("name").getNodeValue();
				String credential1 = nAttrs.getNamedItem("credential1").getNodeValue();
				String credential2 = nAttrs.getNamedItem("credential2").getNodeValue();
				Map<String,Group> sGroups = new HashMap<String,Group>();
				String gxpr = "/subjects/users/groups";
				NodeList gnl = (NodeList) xPath.compile(gxpr).evaluate(testDescription, XPathConstants.NODESET);
				for(int j = 0; j < gnl.getLength(); j++) {
					Node gNode = gnl.item(j);
					String ref = gNode.getNodeValue();
					Group g = (Group) groups.get(ref);
					if(g == null) {
						throw new TestGeneratorException("Unknown group reference "+ref+" in user "+id);
					}
					sGroups.put(ref, g);
				}
				Subject s = new User(id,name,credential1,credential2,sGroups);
				result.put(id, s);
			}
			
			return result;
		} catch (XPathExpressionException | DOMException e) {
			e.printStackTrace();
			throw new TestGeneratorException("Failed to parse description "+testDescription,e);
		}
	}
	
	private Map<String,Resource> getResources(Document testDescription) throws TestGeneratorException {
		try {
			Map<String,Resource> result = new HashMap<String,Resource>();
			String xpr = "/securitytest/resources/*";
			NodeList nl = (NodeList) xPath.compile(xpr).evaluate(testDescription, XPathConstants.NODESET);
			for(int i = 0; i < nl.getLength(); i++) {
				Node n = nl.item(i);
				NamedNodeMap nAttrs = n.getAttributes();
				String id = nAttrs.getNamedItem("id").getNodeValue();
				String name = nAttrs.getNamedItem("name").getNodeValue();
				String rClass = nAttrs.getNamedItem("class").getNodeValue();
				Resource o = new Resource(id,name,rClass);
				result.put(id, o);
			}
			
			return result;
		} catch (XPathExpressionException | DOMException e) {
			e.printStackTrace();
			throw new TestGeneratorException("Failed to parse description "+testDescription,e);
		}
	}
	
	private Map<String,Action> getActions(Document testDescription) throws TestGeneratorException {
		try {
			Map<String,Action> result = new HashMap<String,Action>();
			String xpr = "/securitytest/actions/*";
			NodeList nl = (NodeList) xPath.compile(xpr).evaluate(testDescription, XPathConstants.NODESET);
			for(int i = 0; i < nl.getLength(); i++) {
				Node n = nl.item(i);
				NamedNodeMap nAttrs = n.getAttributes();
				String aClass = nAttrs.getNamedItem("class").getNodeValue();
				String name = nAttrs.getNamedItem("name").getNodeValue();
				Action a = new Action(aClass,name);
				result.put(aClass+"_"+name, a);
			}
			
			return result;
		} catch (XPathExpressionException | DOMException e) {
			e.printStackTrace();
			throw new TestGeneratorException("Failed to parse description "+testDescription,e);
		}
	}
	
	private Map<String,Rule> getRules(Document testDescription, TestMatrix testMatrix) throws TestGeneratorException {
		try {
			Map<String,Rule> result = new HashMap<String,Rule>();
			String xpr = "/securitytest/rules/*";
			NodeList nl = (NodeList) xPath.compile(xpr).evaluate(testDescription, XPathConstants.NODESET);
			for(int i = 0; i < nl.getLength(); i++) {
				Node n = nl.item(i);
				NamedNodeMap nAttrs = n.getAttributes();
				String id = nAttrs.getNamedItem("id").getNodeValue();
				String resourceid = nAttrs.getNamedItem("resourceid").getNodeValue();
				NodeList foo = n.getChildNodes();
				Set<Action> as = new HashSet<Action>();
				Set<Subject> ss = new HashSet<Subject>();
				for(int q = 0; q < foo.getLength(); q++) {
					Node bar = foo.item(q);
					NodeList cnl = bar.getChildNodes();
					if(bar.getNodeType() == Node.ELEMENT_NODE) {
						if(bar.getNodeName().equals("subjects")) {
							for(int j = 0; j < cnl.getLength(); j++) {
								Node sNode = cnl.item(j);
								if(sNode.getNodeType() == Node.ELEMENT_NODE) {
									String sRef = sNode.getAttributes().getNamedItem("subjectid").getNodeValue();
									Subject s = testMatrix.getSubject(sRef);
									ss.add(s);
								}
							}
						} else {
							for(int j = 0; j < cnl.getLength(); j++) {
								Node aNode = cnl.item(j);
								if(aNode.getNodeType() == Node.ELEMENT_NODE) {
									String aRef = aNode.getAttributes().getNamedItem("ref").getNodeValue();
									Action a = testMatrix.getAction(aRef);
									as.add(a);
								}
							}
						}
					}
				}
				Resource resource = testMatrix.getResource(resourceid);
				Rule r = new Rule(id,resource,as,ss,testMatrix);
				result.put(id, r);
			}
			
			return result;
		} catch (XPathExpressionException | DOMException e) {
			e.printStackTrace();
			throw new TestGeneratorException("Failed to parse description "+testDescription,e);
		}
	}
	
	private Test generateTest(Rule r) {
		return testFactory.generateTest(r);
	}
	
	public void runTests(final String fileName, final String endPoint) throws TestGeneratorException {
		File file = new File(fileName);
		Document testDescription = parseTestDescription(file);
		TestMatrix testMatrix = generateTestMatrix(testDescription);
		List<Test> tests = generateTests(testMatrix,testDescription);
		tests.stream().forEach((on) -> {
			try {
				on.runTest(endPoint);
			} catch (TestGeneratorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	
	public List<Test> generateTests(TestMatrix matrix, Document testDescription) throws TestGeneratorException {
		Map<String,Rule> rules = getRules(testDescription,matrix);
		return rules.values().stream().collect(() -> new ArrayList<Test>(), (c,r) -> c.add(generateTest(r)), (c1,c2) -> c1.addAll(c2));
	}
	
	public TestMatrix generateTestMatrix(Document testDescription) throws TestGeneratorException {
		Map<String,Subject> subjects = getSubjects(testDescription);
		Map<String,Resource> resources = getResources(testDescription);
		Map<String,Action> actions = getActions(testDescription);
		TestMatrix testMatrix =  new TestMatrix(subjects,resources,actions);
//		return new TestMatrix(subjects,resources,actions,rules);
		return testMatrix;
	}
}
