package pofExr;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class PofVariables {
	
	@FindBy (how = How.ID, using = "searchTxt") WebElement searchField;
	@FindBy (how = How.ID, using = "Button1")  WebElement searchButton;
	@FindBy (how = How.ID, using = "quantity")  WebElement quantityField;
	@FindBy (how = How.XPATH, using = "//*[@id=\"food_search_wrapper\"]/div[3]/table/tbody/tr/td[6]/input")  WebElement addButton;	
	
}
