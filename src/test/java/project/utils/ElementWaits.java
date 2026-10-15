package project.utils;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import orangeHRM.utils.CustomWaits;


public class ElementWaits extends CustomWaits{


	public static WebElement waitForVisibility(WebDriver driver, By locator) {
		
		Duration TIMEOUT = Duration.ofSeconds(Integer.parseInt(ConfigEnvReader.get("timeout")));
		WebDriverWait wait = new WebDriverWait(driver, TIMEOUT);

		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	public static void waitForValuePresent(WebDriver driver, By locator) {

	    Duration timeout = Duration.ofSeconds(Integer.parseInt(ConfigEnvReader.get("timeout")));

	    WebDriverWait wait = new WebDriverWait(driver, timeout);

	    try {

	        wait.until(driver1 -> {

	            try {

	                WebElement element = driver1.findElement(locator);

	                String value = element.getAttribute("value");

	                return value != null && !value.isBlank();

	            } catch (StaleElementReferenceException e) {

	                return false;

	            } catch (org.openqa.selenium.NoSuchElementException e) {

	                return false;
	            }

	        });

	    } catch (TimeoutException e) {

	        throw new RuntimeException("Element value not present after timeout: " + locator, e);
	    }
	}
}
