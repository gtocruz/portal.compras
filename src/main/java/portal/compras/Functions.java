package portal.compras;


import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.openqa.selenium.OutputType; 
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Functions {
	
	// ===========================================================================================
	// Placeholders
	// ===========================================================================================

	public WebDriverWait wait;
	public WebDriver driver;
	public String url = "https://www.magazineluiza.com.br"; // e-commerce homepage
	public String campobusca = "//input[@id='inpHeaderSearch']";
	public String searchbtn = "//*[@id='btnHeaderSearch']";
	
	// ===========================================================================================
	
		
	public static void takeSnapShot(WebDriver driver, String filepath) throws Exception{
		
		DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy h-m-s");
		Date date = new Date();
		
		//Convert web driver object to TakeScreenshot
		
		TakesScreenshot scrShot =((TakesScreenshot)driver);
				
		//Call getScreenshotAs method to create image file
		
		File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
		
		
		//Move image file to new destination
				
		if(filepath == null) {
		filepath=("C:/Users/gocruz/eclipse-workspace/AcessaPortal/Screenshots/"+dateFormat.format(date)+".png");
		}
		
		File DestFile=new File(filepath);
		
		//Copy file at destination
		
		FileUtils.copyFile(SrcFile, DestFile);
		
				
	}
	
	// Function to sleep in seconds
	public void sleepFunction(int sleepTime) {
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return;
	}

}
