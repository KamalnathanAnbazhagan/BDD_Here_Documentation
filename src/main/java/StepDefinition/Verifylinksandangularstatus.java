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

public class Verifylinksandangularstatus {
	private static WebDriver driver;
	private static WebDriverWait jsWait;
	private static JavascriptExecutor jsExec;

	public static void main(String args[]) {

		// Get the driver
	
		System.setProperty("webdriver.chrome.driver", "C:/Apps/drivers/chromedriver.exe");
		driver = new ChromeDriver();
		jsWait = new WebDriverWait(driver, 10);
		jsExec = (JavascriptExecutor) driver;
		driver.manage().window().maximize();
		driver.get("https://developer.here.com/documentation");
		List<WebElement> links = driver.findElements(By.tagName("a"));
		System.out.println("Total links are " + links.size());
		for (int i = 0; i < links.size(); i++) {
			WebElement element = links.get(i);
			// By using "href" attribute, we could get the url of the requried link
			String url = element.getAttribute("href");
			// calling verifyLink() method here. Passing the parameter as url which we
			// collected in the above link
			// See the detailed functionality of the verifyLink(url) method below
			verifyLink(url);
		}
		driver.quit();
	}

	public static void verifyLink(String urlLink) {

		// Sometimes we may face exception "java.net.MalformedURLException". Keep the
		// code in try catch block to continue the broken link analysis
		try {

			// Use URL Class - Create object of the URL Class and pass the urlLink as
			// parameter
			URL link = new URL(urlLink);

			// Create a connection using URL object (i.e., link)
			HttpURLConnection httpConn = (HttpURLConnection) link.openConnection();

			
			// Set the timeout for 2 seconds
			httpConn.setConnectTimeout(2000);

			// connect using connect method
			httpConn.connect();

			// use getResponseCode() to get the response code.
			if (httpConn.getResponseCode() == 200) {
				System.out.println(urlLink + " - " + httpConn.getResponseMessage());

				// verify weather angular is loaded or not
				waitForAngularLoad();
			}
				
				if (httpConn.getResponseCode() == 404) {
				System.out.println(urlLink + " - " + httpConn.getResponseMessage());
			}
			
		}
		
		// getResponseCode method returns = IOException - if an error occurred
		// connecting to the server.
		catch (Exception e) {
			// e.printStackTrace();
		}
	}

	// Wait for Angular Load
	public static void waitForAngularLoad() {
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

}