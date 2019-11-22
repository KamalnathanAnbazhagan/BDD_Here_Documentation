package StepDefinition;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class DocumentionLinks {
	private static WebDriver driver;
	private static WebDriverWait jsWait;
	private static JavascriptExecutor jsExec;

	public static List<WebElement> links;
	public static String url;
	
	@Given("^Launch browser and navigate to documention page$")
	public void launch_browser_and_navigate_to_documention_page() throws Throwable {
		System.setProperty("webdriver.chrome.driver", "C:/Apps/drivers/chromedriver.exe");
		driver = new ChromeDriver();
		jsWait = new WebDriverWait(driver, 10);
		jsExec = (JavascriptExecutor) driver;
		driver.manage().window().maximize();
		driver.get("https://developer.here.com/documentation");
	}

	@When("^Getting number of links in Documention page$")
	public void getting_number_of_links_in_Documention_page() throws Throwable {
		 links = driver.findElements(By.tagName("a"));
		System.out.println("Total links are " + links.size());
		for (int i = 0; i < links.size(); i++) {
			WebElement element = links.get(i);
			// By using "href" attribute, we could get the url of the requried link
			url = element.getAttribute("href");
			// calling verifyLink() method here. Passing the parameter as url which we
			// collected in the above link
			// See the detailed functionality of the verifyLink(url) method below
			
	}
	}

	@Then("^Validate the all the links are working and angular is loading$")
	public void validate_the_all_the_links_are_working_and_angular_is_loading() throws Throwable {

		// Use URL Class - Create object of the URL Class and pass the urlLink as parameter
		URL link = new URL(url);

		// Create a connection using URL object (i.e., link)
		HttpURLConnection httpConn = (HttpURLConnection) link.openConnection();

		// Set the timeout for 2 seconds
		httpConn.setConnectTimeout(2000);

		// connect using connect method
		httpConn.connect();
		// use getResponseCode() to get the response code.
					if (httpConn.getResponseCode() == 200) {
						System.out.println(url + " - " + httpConn.getResponseMessage());

						// verify weather angular is loaded or not
						//waitForAngularLoad();
						
						WebDriverWait wait = new WebDriverWait(driver, 15);
						JavascriptExecutor jsExec = (JavascriptExecutor) driver;

						String angularReadyScript = "return angular.element(document).injector().get('$http').pendingRequests.length === 0";

						// Wait for ANGULAR to load
						ExpectedCondition<Boolean> angularLoad = driver -> Boolean
								.valueOf(((JavascriptExecutor) driver).executeScript(angularReadyScript).toString());

						// Get Angular is Ready
						boolean angularReady = Boolean.valueOf(jsExec.executeScript(angularReadyScript).toString());

						// Wait ANGULAR until it is Ready!
						if (!angularReady) {
							System.out.println("ANGULAR is NOT Loaing!");
							// Wait for Angular to load
							wait.until(angularLoad);
						} else {
							System.out.println("ANGULAR is Loading!");
						}
						
					}
						
						if (httpConn.getResponseCode() == 404) {
						System.out.println(url + " - " + httpConn.getResponseMessage());
					}
	}

	@Then("^Close the browser$")
	public void close_the_browser() throws Throwable {
	    driver.close();
	    driver.quit();
	}

	
	
}
