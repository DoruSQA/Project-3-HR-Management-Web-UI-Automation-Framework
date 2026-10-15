package project.utils;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/*prezent in DOM > visibil > enabled > in viewport > not covered > (weblement) reference still valid > connection */

public class ElementWaits {

	public static WebElement waitToBeInteractable2(WebDriver driver, By locator) {
		Duration TIMEOUT = Duration.ofSeconds(Integer.parseInt(ConfigEnvReader.get("timeout")));
		WebDriverWait wait = new WebDriverWait(driver, TIMEOUT);
		wait.ignoring(StaleElementReferenceException.class);

		try {
			// Check existence,visibility,entabled
			WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));

			// Scroll to element
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", element);

			// Covered or not
			if (!isNotCovered(driver, element))
				throw new RuntimeException("Element is covered by another element. " + locator);

			return element;

		} catch (TimeoutException e) {

			throw new RuntimeException("Element not interactable: " + locator, e);
		}

	}

	public static WebElement waitToBeInteractable(WebDriver driver, By locator) {

		Duration timeout = Duration.ofSeconds(Integer.parseInt(ConfigEnvReader.get("timeout")));

		WebDriverWait wait = new WebDriverWait(driver, timeout);

		try {

			return wait.until(driver1 -> {

				try {

					// 1. Element exists in DOM
					WebElement element = driver1.findElement(locator);

					// 2. Element is visible
					if (!element.isDisplayed()) {
						return null;
					}

					// 3. Element is enabled
					if (!element.isEnabled()) {
						return null;
					}

					// 4. Scroll element into viewport
					((JavascriptExecutor) driver1).executeScript("arguments[0].scrollIntoView({block:'center'});",
							element);

					// 5. Element is not covered by another element
					if (!isNotCovered(driver1, element)) {
						return null;
					}

					// 6. Element reference still valid
					// If stale happens, catch block below retries everything
					element.isDisplayed();

					// Everything OK
					return element;

				} catch (StaleElementReferenceException e) {

					// DOM changed -> retry entire flow
					return null;

				} catch (org.openqa.selenium.NoSuchElementException e) {

					return null;
				}

			});

		} catch (TimeoutException e) {

			throw new RuntimeException("Element is not interactable after timeout: " + locator, e);
			//return new FakeWebElement();
		}
	}

	private static boolean isNotCovered(WebDriver driver, WebElement element) {

		JavascriptExecutor js = (JavascriptExecutor) driver;

		return (Boolean) js.executeScript(
				"var rect = arguments[0].getBoundingClientRect();" + "var x = rect.left + rect.width / 2;"
						+ "var y = rect.top + rect.height / 2;" + "var topElement = document.elementFromPoint(x, y);"
						+ "return topElement !== null && arguments[0].contains(topElement);",
				element);
	}

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
