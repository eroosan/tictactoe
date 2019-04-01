package es.codeurjc.ais.tictactoe;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

import java.net.MalformedURLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.ChromeDriverManager;

public class SystemTest {

	
	/* ATENCIÓN: A veces el test es inestable.  
	 * Ejecutar de nuevo si falla  */
	
	private WebDriver driverChrome1, driverChrome2;
	private WebDriverWait wait1,wait2;
	
	@BeforeClass
	public static void setupClass() {
		ChromeDriverManager.getInstance().setup();
		WebApp.start();		
	}

	@AfterClass
	public static void teardownClass() {
		WebApp.stop();
	}
	
	@Before
	public void setupTest() throws MalformedURLException {
        driverChrome1 = new ChromeDriver();
        driverChrome2 = new ChromeDriver();  
        
		driverChrome1.get("http://localhost:8080/");
		
		driverChrome1.findElement(By.className("form-control")).sendKeys("Player1");
		driverChrome1.findElement(By.id("startBtn")).click();	
		
		driverChrome2.get("http://localhost:8080/");
		
		driverChrome2.findElement(By.className("form-control")).sendKeys("Player2");
		driverChrome2.findElement(By.id("startBtn")).click();	
		
		wait1 = new WebDriverWait(driverChrome1, 30);
		wait2 = new WebDriverWait(driverChrome1, 30);		
		
	}
	
	@After
	public void teardown() {
		if (driverChrome1 != null) {
			driverChrome1.quit();
		}
		if (driverChrome2 != null) {
			driverChrome2.quit();
		}		
	}
	
	@Test
	public void testFirstPlayerWins() {
		
		driverChrome1.findElement(By.id("cell-0")).click();		
		driverChrome2.findElement(By.id("cell-1")).click();	
		driverChrome1.findElement(By.id("cell-2")).click();		
		driverChrome2.findElement(By.id("cell-3")).click();		
		driverChrome1.findElement(By.id("cell-4")).click();		
		driverChrome2.findElement(By.id("cell-5")).click();		
		driverChrome1.findElement(By.id("cell-6")).click();	
		
		wait1.until(ExpectedConditions.alertIsPresent());
		wait2.until(ExpectedConditions.alertIsPresent());
		
		String alertBrowser1 = driverChrome1.switchTo().alert().getText();
		String alertBrowser2 = driverChrome2.switchTo().alert().getText();	
		
		assertThat(alertBrowser1, containsString("Player1 wins"));
		assertThat(alertBrowser2, containsString("Player1 wins"));		
	}

	@Test
	public void testFirstPlayerLoses() {
		
		driverChrome1.findElement(By.id("cell-0")).click();		
		driverChrome2.findElement(By.id("cell-3")).click();	
		driverChrome1.findElement(By.id("cell-1")).click();		
		driverChrome2.findElement(By.id("cell-4")).click();		
		driverChrome1.findElement(By.id("cell-8")).click();		
		driverChrome2.findElement(By.id("cell-5")).click();
		
		wait1.until(ExpectedConditions.alertIsPresent());
		wait2.until(ExpectedConditions.alertIsPresent());
		
		String alertBrowser1 = driverChrome1.switchTo().alert().getText();
		String alertBrowser2 = driverChrome2.switchTo().alert().getText();	
		
		assertThat(alertBrowser1, containsString("Player1 looses"));
		assertThat(alertBrowser2, containsString("Player1 looses"));
	}
	
	@Test
	public void testDraw() {			
		
		driverChrome1.findElement(By.id("cell-0")).click();		
		driverChrome2.findElement(By.id("cell-1")).click();	
		driverChrome1.findElement(By.id("cell-2")).click();		
		driverChrome2.findElement(By.id("cell-3")).click();		
		driverChrome1.findElement(By.id("cell-4")).click();		
		driverChrome2.findElement(By.id("cell-8")).click();
		driverChrome1.findElement(By.id("cell-5")).click();		
		driverChrome2.findElement(By.id("cell-6")).click();
		driverChrome1.findElement(By.id("cell-7")).click();		
			
		wait1.until(ExpectedConditions.alertIsPresent());
		wait2.until(ExpectedConditions.alertIsPresent());
		
		String alertBrowser1 = driverChrome1.switchTo().alert().getText();
		String alertBrowser2 = driverChrome2.switchTo().alert().getText();	
		
		assertThat(alertBrowser1, containsString("Draw"));
		assertThat(alertBrowser2, containsString("Draw"));		
	}
}
