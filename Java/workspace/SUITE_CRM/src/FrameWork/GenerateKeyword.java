package FrameWork;


import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.reflections.Reflections;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.thoughtworks.paranamer.AnnotationParanamer;
import com.thoughtworks.paranamer.BytecodeReadingParanamer;
import com.thoughtworks.paranamer.CachingParanamer;
import com.thoughtworks.paranamer.Paranamer;

public class GenerateKeyword {
	
	public static void main(String[] args) throws Exception {
		//mvn clean compile exec:java -Dexec.mainClass="FrameWork.GenerateKeyword" -Dexec.args="D:/key.xml"
		String sTestSuiteXmlPath = "";
		if(args.length >0)
		{
			sTestSuiteXmlPath = args[0];
		}else
		{
			sTestSuiteXmlPath = Paths.get(".").toAbsolutePath().normalize().toString() + "/../../../ProjectData/Keywords/Keywords.xml";
		}
			
		
	   	File f = new File(sTestSuiteXmlPath);
	   	if(f.exists() && !f.isDirectory()) { 
	   	    f.delete();
	   	}
   	 
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.newDocument();
        //Document doc =  dBuilder.parse(sTestSuiteXmlPath);
        // root element
        Element rootElement = doc.createElement("KeywordRepository");         
        doc.appendChild(rootElement);
        Attr attrRoot = doc.createAttribute("Name");
        attrRoot.setValue("");
        rootElement.setAttributeNode(attrRoot);       
        
        
		List<Class<?>> classes = ClassFinder.find("Libs");
		for (Class<?> classItem : classes) {
			String sPackageName = classItem.getPackage().getName();
			String sClassName = classItem.getName();
			String[] arrasClassName = sClassName.split("\\.");
			//System.out.printf("%n%n%nPackage: " + sPackageName + ", Class: " + sClassName);
			sClassName = arrasClassName[arrasClassName.length-1];
			
			
			for (Method method : classItem.getDeclaredMethods()) {				
				Annotation annotation = method.getAnnotation(keyword.class);
				keyword keywordInfo = (keyword) annotation;				
				
				if (method.isAnnotationPresent(keyword.class)) {
					String sKeywordName = method.getName();
					String sAuthor = keywordInfo.Author();
					String sDescription = keywordInfo.Description();
					String[] sParams = keywordInfo.Params();
					//System.out.printf("%n____________________");
					//System.out.printf("%nKeyword :" + sKeywordName);
					//System.out.printf("%nAuthor :%s", sAuthor);
					//System.out.printf("%nDescription :%s", sDescription);
					
					XPath xPath =   XPathFactory.newInstance().newXPath();
					String sProductpath = "//Product[@Name='"+sPackageName+"']";
					NodeList lstProducNode = (NodeList) (xPath).compile(sProductpath).evaluate(doc, XPathConstants.NODESET);
					if(lstProducNode.getLength() == 0)
					{
						Element productElement = doc.createElement("Product");         
						rootElement.appendChild(productElement);
				        Attr attrProductName = doc.createAttribute("Name");
				        attrProductName.setValue(sPackageName);	
				        productElement.setAttributeNode(attrProductName);
					}
					Element ProductElement = (Element) ((NodeList)(xPath).compile(sProductpath).evaluate(doc, XPathConstants.NODESET)).item(0);
					String sFeaturepath = "//Product[@Name='"+sPackageName+"']/Feature[@Name='"+sClassName+"']";
					NodeList lstFeatureNode = (NodeList) (xPath).compile(sFeaturepath).evaluate(doc, XPathConstants.NODESET);
					if(lstFeatureNode.getLength() == 0)
					{
						Element featureElement = doc.createElement("Feature");         
						ProductElement.appendChild(featureElement);
				        Attr attrFeatureName = doc.createAttribute("Name");
				        attrFeatureName.setValue(sClassName);		
				        featureElement.setAttributeNode(attrFeatureName);
					}
					Element FeatureElement = (Element) ((NodeList)(xPath).compile(sFeaturepath).evaluate(doc, XPathConstants.NODESET)).item(0);
					
					Element keywordElement = doc.createElement("Keyword");         
					FeatureElement.appendChild(keywordElement);
			       
			        Attr attrKeywordAuthor = doc.createAttribute("Author");
			        attrKeywordAuthor.setValue(sAuthor);		
			        keywordElement.setAttributeNode(attrKeywordAuthor);
			        
			        Attr attrKeywordDescription = doc.createAttribute("Description");
			        attrKeywordDescription.setValue(sDescription);		
			        keywordElement.setAttributeNode(attrKeywordDescription);
			        
			        Attr attrKeywordName = doc.createAttribute("Name");
			        attrKeywordName.setValue(sKeywordName);		
			        keywordElement.setAttributeNode(attrKeywordName);
			        Paranamer info = new CachingParanamer(new AnnotationParanamer(new BytecodeReadingParanamer()));

			        String[] parameterNames = info.lookupParameterNames(method);
			        int k=0;
			        for(String sItem : parameterNames)
			        {
			        	String sParamName = sItem;
						String sParamDescription = "Default description";
						String sDefaultData = "";
						for(String sItemDescript: sParams)
						{
							if(sItemDescript.split(":")[0].trim().equals(sItem))
							{
								sParamDescription = sItemDescript.substring(sItemDescript.indexOf(":") + 1).trim();
							}
						}
						//System.out.printf("%nParameter name:%s", sParamName);
						Element paramElement = doc.createElement("Param");         
						keywordElement.appendChild(paramElement);
						
						Attr attrParamdName = doc.createAttribute("Name");
						attrParamdName.setValue(sParamName);		
						paramElement.setAttributeNode(attrParamdName);
						
						
						Attr attrParamdDecs = doc.createAttribute("Description");
						attrParamdDecs.setValue(sParamDescription);		
						paramElement.setAttributeNode(attrParamdDecs);
						
						paramElement.setTextContent(sDefaultData);
			        }
					/*for(Parameter paramItem: method.getParameters())
					{
				        
						String sParamName = paramItem.getName();
						String sParamDescription = "Default description";
						String sDefaultData = "Default data";
						System.out.printf("%nParameter name:%s", sParamName);
						//System.out.printf("%nParameter class:%s", paramItem.getType().toString());
						//System.out.printf("%nModifiers:%s", paramItem.getModifiers());
						//System.out.printf("%nIs implicit?:%s", paramItem.isImplicit());
						//System.out.printf("%nIs name present?:%s", paramItem.isNamePresent());
						//System.out.printf("%nIs synthetic?:%s", paramItem.isSynthetic());
						Element paramElement = doc.createElement("Param");         
						keywordElement.appendChild(paramElement);
						
						Attr attrParamdName = doc.createAttribute("Name");
						attrParamdName.setValue(sParamName);		
						paramElement.setAttributeNode(attrParamdName);
						
						
						Attr attrParamdDecs = doc.createAttribute("Description");
						attrParamdDecs.setValue(sParamDescription);		
						paramElement.setAttributeNode(attrParamdDecs);
						
						paramElement.setTextContent(sDefaultData);
						
					}*/
				}				
			}
		}	
		
		// write the content into xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
		System.out.printf("%n%n%nKeyword file at " + sTestSuiteXmlPath);
        StreamResult result =  new StreamResult(new File(sTestSuiteXmlPath).getPath());
        transformer.transform(source, result);
        // Output to console for testing
        StreamResult consoleResult = new StreamResult(System.out);
        transformer.transform(source, consoleResult);
	}	
}
