package FrameWork;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class FactoryDriver {
	private static FactoryDriver instance;
	    
    private FactoryDriver(){}
    
    //static block initialization for exception handling
    static{
        try{
            instance = new FactoryDriver();
        }catch(Exception e){
            throw new RuntimeException("Exception occured in creating singleton instance");
        }
    }
    
    public static FactoryDriver getInstance(){
        return instance;
    }
    public WebDriver getChromeDriver()
    {
    	ChromeOptions options = new ChromeOptions();
		  options.addArguments("--start-maximized");
		  options.addArguments("--disable-web-security");
		  options.addArguments("--no-proxy-server");
		  options.addArguments("enable-automation");
		  options.addArguments("--disable-infobars");
		  //options.addArguments("--disable-extensions-file-access-check");
		  options.addArguments("--disable-notifications");
		  options.addArguments("--disable-extensionsr");
		  // options.addArguments("--disable-ios-password-suggestions");

		  HashMap<String, Object> prefs = new HashMap<String, Object>();
		  prefs.put("credentials_enable_service", false);
		  prefs.put("profile.password_manager_enabled", false);
		  options.setExperimentalOption("prefs", prefs);
    	return new ChromeDriver(options);
    }
    
    public WebDriver getFirefoxDriver()
    {
    	System.setProperty("webdriver.gecko.driver", "geckodriver.exe");
    	/*DesiredCapabilities capabilities = new DesiredCapabilities(); 
    	capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true); 
    	capabilities.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true); 
    	capabilities.setCapability("acceptInsecureCerts",true);*/
    	
    	FirefoxProfile profile = new FirefoxProfile();
    	profile.setPreference("security.insecure_field_warning.contextual.enabled","false");
    	
    	return new FirefoxDriver(profile);
    }
    
    public WebDriver getIEDriver()
    {
    	return new InternetExplorerDriver();
    }
}
