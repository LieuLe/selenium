package FrameWork;

import java.io.File;

public class Constant {
	public static String sProjectPath = System.getProperty("user.dir"); 
	public static String sTestLabFolder = "TestLab";
	public static String sXmlTestLabFolder = (new File(sProjectPath + "\\..\\..\\..\\TestScriptsRepository\\TestLab")).getAbsolutePath();
}
