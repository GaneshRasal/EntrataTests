package EntrataTests.EntrataTests;

import java.time.Duration;
import java.util.Iterator;
import java.util.Set;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class EntrataTests {

	private WebDriver driver;
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

	@BeforeClass
	public void setup() {
		// Set up the ChromeDriver and open the Entrata website
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.entrata.com/");
		// Close the cookie consent dialog
		driver.findElement(By.id("cookie-close")).click();

	}

	// Method to switch to the next opened window
	public void switchToNextWindow() {
		Set<String> windows = driver.getWindowHandles();
		Iterator<String> it = windows.iterator();
		while (it.hasNext()) {
			driver.switchTo().window(it.next());
			System.out.println(driver.getTitle());
		}
	}

	@Test(priority = 1)
	public void validateHomePageTitle() {
		// Validate that the homepage title is correct
		String title = driver.getTitle();
		Assert.assertEquals(title, "Property Management Software | Entrata");
	}

	@Test(priority = 2)
	public void validateProductMouseHover() {
		// Validate the mouse hover action over the "Products" section
		WebElement products = driver.findElement(By.xpath("//div[text()='Products']"));
		Actions a = new Actions(driver);
		a.moveToElement(products).build().perform();
	}

	@Test(priority = 3)
	public void validateFooterSection() {

		// open link in new tab
		String clickonlinkTag = Keys.chord(Keys.CONTROL, Keys.ENTER);
		driver.findElement(By.xpath("//div[@class='footer-nav']/div/a[text()='ResidentPay']")).sendKeys(clickonlinkTag);

		// switch to the new open window
		switchToNextWindow();

	}

	@Test(priority = 4)
	public void validateInputForm() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		driver.findElement(By.xpath("//nav[@role='navigation']/a[text()='Summit']")).click();
		switchToNextWindow();
		WebElement summitWindowDriver = driver.findElement(By.cssSelector("div.landing-cap"));
		summitWindowDriver.findElement(By.xpath("//a[text()='Register Now']")).click();
		switchToNextWindow();
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("react-mount")));
		WebElement personalInfoDriver = driver.findElement(By.id("react-mount"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='First Name']")));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(
				By.xpath("//span[@class='Dialog_placeholder_iV0a4_Dialog_coverScreen__a48vk']")));

		personalInfoDriver.findElement(By.xpath("//input[@name='56aeaca6-a0ad-4548-8afc-94d8d4361ba1']"))
				.sendKeys("Ganesh Rasal");
	}

	@AfterSuite
	public void tearDown() {
		driver.quit();
	}

}