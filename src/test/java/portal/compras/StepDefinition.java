package portal.compras;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


public class StepDefinition extends Functions {

	public static WebDriver driver;
	Functions obj = new Functions();  


	//PlaceHolders and variables
	String Homeurl = "https://www.magazineluiza.com.br"; // e-commerce homepage
	String campobusca = "//input[@id='inpHeaderSearch']";
	String searchTerm = "O monge e o executivo";
	String searchbtn = "//*[@id='btnHeaderSearch']";
	String resultsPage = "//a[@itemprop='url' and contains(@data-product, '0886059')]";
	String title = "Livro - O monge e o executivo";
	String produto;
	String id="0886059";
	String book = "//*[contains(@id, 'product_0886059')]";
	String ResultsUrl="https://www.magazineluiza.com.br/busca/O%20monge%20e%20o%20executivo/";
	String CurrentUrl;
	String ExpectedUrl = "https://www.magazineluiza.com.br/livro-o-monge-e-o-executivo/p/088605900/li/adml/";
	String buttonBuy = "//button[@class='button__buy button__buy-product-detail js-add-cart-button js-main-add-cart-button js-add-box-prime']";
	WebElement bookElement;
	String itemSacola = "//a[@class='BasketItemProduct-info-title']/p";
	String cartElement;
	String TituloFinal = "Livro - O monge e o executivo -";
	String WrongsearchTerm = "O m0nge e o execuivo";


	//Initialize Webdriver and access Magazine Luiza HomePage

	@Given("I set up my Chrome Driver")
	public void i_am_on_Magalu_HomePage() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		System.setProperty("webdriver.chrome.driver", "C:/Users/gocruz/eclipse-workspace/portal.compras/chromedriver.exe");
		driver = new ChromeDriver(options);

	}

	//I access Magazine Luiza HomePage

	@Given("I am on Magalu HomePage")
	public void i_am_on_the_homepage() {
		driver.get(Homeurl);
	}

	//I type my search term in the search box
	@And("I enter the book name in the search field")
	public void i_enter_the_book_name_in_the_search_field() {
		driver.findElement(By.xpath(campobusca)).sendKeys(searchTerm);
	}

	//Search for the wrong book name
	@Given("I enter the wrong book name in the search field")
	public void i_enter_the_wrong_book_name_in_the_search_field() {
		driver.findElement(By.xpath(campobusca)).sendKeys(WrongsearchTerm);
	}

	//I click the search button
	@When("I click on the search button")
	public void i_click_on_search_button() {
		driver.findElement(By.xpath(searchbtn)).click();
	}

	//Search results are correct
	@Then("search results are displayed")
	public void search_results_are_displayed() throws Exception {
		//Waits for the result page to load
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		//Saves all results that match our book ID
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(resultsPage)));
		List<WebElement> results = driver.findElements(By.xpath(resultsPage));

		//Verifies that my book id is really among the results

		if(results.size() > 1) {
			for(int i = 0; i < results.size(); ++i) {
				produto = results.get(i).getAttribute("data-product");
				if (produto.contains(id)) {
					System.out.println("\nA busca encontrou o meu livro!");
					System.out.println(id);
					System.out.println("------------------------------------------------");
					break;
				}else {
						System.out.println("Livro não encontrado!");
					}
				}
			}else {
				System.out.println("\nNenhum resultado encontrado!\n");
			}

			// Take snapshot as evidence
			Functions.takeSnapShot(driver, null);

		}

		//I received no search result for an invalid search
		@Then("no search results are displayed")
		public void no_search_results_are_displayed() throws Exception {
			//Waits for the result page to load
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

			//Saves all results that match our book ID
			List<WebElement> results = driver.findElements(By.xpath(resultsPage));

			if(results.size() > 1) {

				//Verifies that my book id is really among the results
				for(int i = 0; i < results.size(); ++i) {
					produto = results.get(i).getAttribute("data-product");
					if (produto.contains(id)) {
						System.out.println("\nA busca encontrou o meu livro!");
						System.out.println(id);
						System.out.println("------------------------------------------------");
						break;
					}else {
						System.out.println("Livro não encontrado!");
					}
				}
			}else {
				System.out.println("\nNenhum resultado encontrado!\n");
			}

			// Take snapshot as evidence
			Functions.takeSnapShot(driver, null);

		}

		//Access search results page
		@Given("I am on the search results page")
		public void i_am_on_the_search_results_page() {
			//I am on the results page
			driver.get(ResultsUrl);

		}

		//I click on a product in the search results page
		@When("I click on the product I want to buy")
		public void i_click_on_the_product_I_want_to_buy() {
			WebDriverWait wait = new WebDriverWait(driver, 20);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(book)));
			bookElement = driver.findElement(By.xpath(book));
			//scroll down the page to my product
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", bookElement);
			driver.findElement(By.xpath(book)).click();
		}

		//I am on the product page, where I can see more details about the product
		@Then("I am redirected to the product page")
		public void i_am_redirected_to_the_product_page() throws Exception {
			CurrentUrl = driver.getCurrentUrl();
			Assert.assertEquals(CurrentUrl, ExpectedUrl);

			// Take snapshot as evidence
			Functions.takeSnapShot(driver, null);

		}

		//I access the product page to see more details
		@Given("I am on the product page")
		public void i_am_on_the_product_page() {
			driver.get(ExpectedUrl);
		}

		//I click on the buy button to add the product to the shopping cart
		@When("I click on the buy button")
		public void i_click_on_the_buy_button() {
			driver.findElement(By.xpath(buttonBuy)).click();
		}
		
		//The product is added to my shopping cart and I want to verify it's the correct product
		@Then("the product is added to my cart")
		public void the_product_is_added_to_my_cart() throws Exception {

			//Waits for the cart page to load
			driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
			cartElement = driver.findElement(By.xpath(itemSacola)).getText();
			
			//Verify that the product on the shopping cart is the correct one
			Assert.assertEquals(cartElement, TituloFinal);

			// Take snapshot as evidence
			Functions.takeSnapShot(driver, null);
		}


		//Quits Webdriver after all steps are completed
		@After
		public void af() {
			driver.quit();
		}
	}
