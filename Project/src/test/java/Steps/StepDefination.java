package Steps;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefination {
	static WebDriver driver;
	static String priceofPhone;
	static String text;
	static String Assertion;
	static ExtentReports extent;
	ExtentTest test;

	@Given("User is on Amazon page")
	public void user_is_on_amazon_page() {
		driver = new ChromeDriver();
		driver.get("https://www.amazon.in");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		driver.manage().deleteAllCookies();
		
		ExtentSparkReporter sparkReporter = new ExtentSparkReporter("extentReport.html");
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);

	}

	@When("User Enter the phone as {string} in search box")
	public void user_enter_the_phone_as_in_search_box(String phone) {
		WebElement wb = driver.findElement(By.xpath("//input[@id='twotabsearchtextbox']"));
		wb.sendKeys(phone);
		driver.findElement(By.xpath("//input[@id='nav-search-submit-button']")).click();

	}

	@When("User Select the Phone")
	public void user_select_the_phone() {
		driver.findElement(By.xpath("//span[text()='Apple iPhone 13 (128GB) - Midnight']")).click();

	}

	@Then("User Select Specifcation as {string}")
	public void user_select_specifcation_as(String Specs) {

		List<String> tabs = new ArrayList<>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(tabs.size() - 1));

		List<WebElement> size = driver.findElements(By.xpath("//p[@class='a-text-left a-size-base']"));

		for (WebElement a : size) {
			String sizeofstring = a.getText();
			if (Specs.equalsIgnoreCase(sizeofstring)) {
				a.click();
				 text = a.getText();
				System.out.println(text);
				break;
			}
		}

	}

	@Then("User Select color color as {string}")
	public void user_select_color_color_as(String colorofPhone) {

		WebElement colcourName = driver
				.findElement(By.cssSelector("div[id='variation_color_name'] span[class='selection']"));
		String color = colcourName.getText();

		List<WebElement> harshal = driver.findElements(By.xpath(
				"//ul[@class='a-unordered-list a-nostyle a-button-list a-declarative a-button-toggle-group a-horizontal "
						+ "a-spacing-top-micro swatches swatchesSquare imageSwatches']/child::li"));

		if (!colorofPhone.equalsIgnoreCase(color)) {

			for (WebElement xyz : harshal) {

				xyz.click();
				WebElement currnetColor = driver
						.findElement(By.cssSelector("div[id='variation_color_name'] span[class='selection']"));
				String c = currnetColor.getText();
				if (c.equalsIgnoreCase(colorofPhone)) {
					xyz.click();
					WebElement AssertionColor  = driver
							.findElement(By.cssSelector("div[id='variation_color_name'] span[class='selection']"));
					
					Assertion =AssertionColor.getText();
					
					break;

				} else {
					c = "null";
				}

			}

		}

	}

	@Then("User get the Price")
	public void user_get_the_price() {
		WebElement price = driver.findElement(
				By.xpath("//span[@class='a-price aok-align-center " + "reinventPricePriceToPayMargin priceToPay']"));
		priceofPhone = price.getText();
		System.out.println(priceofPhone);
	}

	@Then("User click on add cart to cart button")
	public void user_click_on_add_cart_to_cart_button() throws InterruptedException {
		int retries = 3;
		for (int i = 0; i < retries; i++) {
			try {
				// Wait for the button to become clickable
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
				WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
						"//div[@class='a-section a-spacing-none a-padding-none']//input[@id='add-to-cart-button']")));

				// Click the button
				addToCartButton.click();
				break; // Exit the loop if successful
			} catch (StaleElementReferenceException e) {
				System.out.println("StaleElementReferenceException caught. Retrying...");
			}
		}
		Thread.sleep(4000);
		// driver.findElement(By.xpath("(//input[@type='submit'])[21]")).click();
		driver.findElement(By.xpath("//input[@aria-labelledby='attach-sidesheet-view-cart-button-announce']")).click();
	}

	@Then("Validate the Phone Price")
	public void validate_the_phone_price() throws InterruptedException {
		Thread.sleep(3000);
		WebElement CurrentPrice = driver.findElement(By.xpath(
				"//span[@class='a-size-medium a-color-base sc-price sc-white-space-nowrap sc-product-price a-text-bold']"));
		String CPrice = CurrentPrice.getText();
		System.out.println(CPrice);
		
		long BeforePrice = Long.parseLong(priceofPhone.replaceAll("[^\\d]", ""));
		String BeforePrice1= Long.toString(BeforePrice);
		System.out.println(BeforePrice1);
		
		
        //long AfterPrice = Long.parseLong(CPrice.replaceAll("[^\\d]", "").split("\\.")[0]);
		double price = Double.parseDouble(CPrice.replace(",", ""));
		 long roundedPrice = (long) Math.ceil(price);
		 
		 
		String AfterPrice1= Long.toString(roundedPrice);
		System.out.println(AfterPrice1);
		Assert.assertTrue(BeforePrice1.contains(AfterPrice1));

	}

	@Then("Validate the Phone Specification")
	public void validate_the_phone_specification() {
		
		
		WebElement CurrentSpecs=driver.findElement(By.xpath("(//li[@class='sc-product-variation']/child::span[@class='a-list-item']/child::span[@class='a-size-small'])[2]"));
		String mm=CurrentSpecs.getText();
			Assert.assertEquals(mm,text );
	}

	@Then("Validate the Color Name")
	public void validate_the_color_name() {
		WebElement CurrentSpecs=driver.findElement(By.xpath("(//li[@class='sc-product-variation']/child::span[@class='a-list-item']/child::span[@class='a-size-small'])[1]"));
		String ss=CurrentSpecs.getText();
		Assert.assertEquals(Assertion,ss);
			
	}

	@Then("Quit the session")
	public void quit_the_session() {
		driver.quit();

	}

}
