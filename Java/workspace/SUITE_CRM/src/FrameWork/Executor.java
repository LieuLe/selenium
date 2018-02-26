package FrameWork;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Executor {
	public String[] getReadySuiteList()
	{
		List<String> listSuites = new ArrayList<String>();		
		try
		{
			String sTestSuiteXmlPath="D:/Working/MeU/Projects/MeU Internal/One2Automate/SourceCode/One2Automate/One2Automate/bin/TestsWorkspace/TestScriptsRepository/TestLab/WaitingRunTestSuites/WaitingRunTestSuites.xml";
			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc =  docBuilder.parse(sTestSuiteXmlPath);
			
			//Find the collection of KWs has name equals KW name
			XPath xPath =   XPathFactory.newInstance().newXPath();
			String sKWXpath = "//TestSet[@Status='3']";
			NodeList nodeList = (NodeList) (xPath).compile(sKWXpath).evaluate(doc, XPathConstants.NODESET);
			if(nodeList.getLength() > 0)
			{
				for(int i=0;i<nodeList.getLength();i++)
				{
					String sTestPath = nodeList.item(i).getTextContent();	
					listSuites.add(sTestPath);
				}				
			}
		}catch(Exception ex)
		{
			System.out.print(ex.getMessage());
		}
		return (String[]) listSuites.toArray();
	}
	
	public void getReadyCaseList(String sTestCaseXmlPath)
	{
		List<String> listReturn = new ArrayList<String>();		
		try
		{		
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc =  docBuilder.parse(sTestCaseXmlPath);
			
			//Find the collection of KWs has name equals KW name
			XPath xPath =   XPathFactory.newInstance().newXPath();
			String sKWXpath = "//TestCase[@Status='3']";
			NodeList nodeList = (NodeList) (xPath).compile(sKWXpath).evaluate(doc, XPathConstants.NODESET);
			if(nodeList.getLength() > 0)
			{
				for(int i=0;i<nodeList.getLength();i++)
				{
					Node nTestCase = nodeList.item(i);
					
				}				
			}
		}catch(Exception ex)
		{
			System.out.print(ex.getMessage());
		}		
	}
}
