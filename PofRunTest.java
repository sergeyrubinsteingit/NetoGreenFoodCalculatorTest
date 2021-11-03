// Testing a nutrition calculator on site "http://www.netogreen.co.il". Using POF.


package pofExr;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class PofRunTest {
	
	public static WebDriver webDriver;
	public static int countActions = 1;
	public static List<WebElement> dropdownOptions = new ArrayList<>();
	public static String dialogBox;
	public static String dropMenu;
	public static String menuString [];
	public static Object keywordsObject []  = {"ìçí","12345","ìçí","àáâãä","900000000",};
	
	public static PofVariables pofVars;
	
	
	public static void startDriver() {
// Starting Firefox
	FirefoxOptions firefoxOptions = new FirefoxOptions();
    firefoxOptions.setCapability("marionette", true);
	webDriver = new FirefoxDriver(firefoxOptions);
		
		webDriver.navigate().to("https://google.com");
		Robot robot; // opens new tabs
		try {
			robot = new Robot();
			for (int i = 0; i < 5; i++) {
				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_T);
				robot.keyRelease(KeyEvent.VK_CONTROL);
				robot.keyRelease(KeyEvent.VK_T);
			}
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// gets pof variables:
		pofVars = new PofVariables();
		pofVars = PageFactory.initElements(webDriver, PofVariables.class);
		
		webDriver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		
		try {
			RunTest();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static String dialogBox() { // here testing options selection goes (words, digits, anything):
		dialogBox = (String) JOptionPane.showInputDialog(
				null,
				"Choose an option:",
				"Test options",
				JOptionPane.PLAIN_MESSAGE,
				null, keywordsObject,
				keywordsObject[0]
		);
		if (dialogBox == null) {
			System.exit(0);
		}
		return dialogBox;
	}
	
	
	public static String dropMenu() {
		
		pofVars.searchField.sendKeys("ìçí");
		
		webDriver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		pofVars.searchButton.click();
		webDriver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		
		dropdownOptions = new WebDriverWait(webDriver, 20).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@id=\"serachResults\"]")));		
		webDriver.manage().timeouts().implicitlyWait(35,TimeUnit.SECONDS);
		
// Gets from the Select tag a list of results of the search by keyword
		Select select = new Select(webDriver.findElement(By.xpath("//select[@id='serachResults']")));
		webDriver.manage().timeouts().implicitlyWait(35,TimeUnit.SECONDS);
// Writes them into ListArray		
		dropdownOptions = select.getOptions();
		
		System.out.println("dropdownOptions.size = " + dropdownOptions.size());
		String menuString [] = new String[dropdownOptions.size()];
		int i = 0;
		
		for (WebElement checkString : dropdownOptions) {
			System.out.println(checkString.getAttribute("innerHTML"));
			try {
				menuString[i] = checkString.getAttribute("innerHTML");
			} catch (org.openqa.selenium.StaleElementReferenceException e) {
				webDriver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
				menuString[i] = checkString.getAttribute("innerHTML");
			}
			i++;
		}
		
		dropMenu = (String) JOptionPane.showInputDialog(
				null,
				"Dropmenu Contents:",
				"Options",
				JOptionPane.PLAIN_MESSAGE,
				null, menuString,
				menuString[0]
		);
		if (dropMenu == null) {
			System.exit(0);
		}
		return dropMenu;
	}
	
	public static  void RunTest() throws IOException {
		
		webDriver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		ArrayList<String> allTabs = new ArrayList<String> (webDriver.getWindowHandles());
		webDriver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		webDriver.switchTo().window(allTabs.get(countActions));

		
		webDriver.get("http://www.netogreen.co.il/Calculators/Nutrition-Calories.aspx");

		webDriver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
				
		webDriver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
				
			if (countActions == 1 || countActions == 2) {
				
				webDriver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
				dialogBox();
				
				webDriver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
						
				pofVars.searchField.sendKeys(dialogBox.toString());		
				webDriver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
				pofVars.searchButton.click();
						
			} else if (countActions == 3) {
				
				webDriver.manage().timeouts().implicitlyWait(60,TimeUnit.SECONDS);
				dropMenu();

				webDriver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
				webDriver.findElement(By.xpath( "//*[text()=\'" + dropMenu + "\']" )).click();
				
				webDriver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
				webDriver.findElement(By.xpath( "//*[@id=\"mesComvo\"]/option[1]" ) ).click();
				webDriver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
				webDriver.findElement(By.xpath( "//*[@id=\"food_search_wrapper\"]/div[3]/table/tbody/tr/td[6]/input" ) ).click();
				
			} else if (countActions == 4) {
				
				dialogBox();
				webDriver.manage().timeouts().implicitlyWait(60,TimeUnit.SECONDS);
				dropMenu();
				webDriver.findElement(By.xpath( "//*[text()=\'" + dropMenu + "\']" )).click();
				webDriver.manage().timeouts().implicitlyWait(120,TimeUnit.SECONDS);
				
				pofVars.quantityField.sendKeys("9000000000");
				webDriver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
				pofVars.addButton.click();
				
			} else if (countActions == 5) {
					
				dialogBox();
				webDriver.manage().timeouts().implicitlyWait(60,TimeUnit.SECONDS);
				dropMenu();
				webDriver.findElement(By.xpath( "//*[text()=\'" + dropMenu + "\']" )).click();
				webDriver.manage().timeouts().implicitlyWait(120,TimeUnit.SECONDS);
					
				pofVars.quantityField.sendKeys("2");
				webDriver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
				pofVars.addButton.click();
					
				webDriver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
				WebElement clearButt = webDriver.findElement(By.xpath("//a[@href='javascript:delRowNo(0)']"));
				webDriver.manage().timeouts().implicitlyWait(15,TimeUnit.SECONDS);
				clearButt.click();
					
			}
				
		countActions++;	
		
		webDriver.manage().timeouts().implicitlyWait(2000,TimeUnit.SECONDS);
		for (int i = 0; i < countActions + 1; i++) {

			if (countActions < 6) {
				webDriver.manage().timeouts().implicitlyWait(300,TimeUnit.SECONDS);
				RunTest();
			} 
		}
	}
	
//	
//
//	public static void main(String[] args) throws IOException {
//		startDriver();
//	}

}
