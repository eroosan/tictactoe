package es.codeurjc.ais.tictactoe;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import cucumber.api.java.en.And;
import cucumber.api.java.en.But;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import io.github.bonigarcia.wdm.ChromeDriverManager;

public class SystemTestWithCucumberRunSteps {

	private static WebDriver driverChrome1 = new ChromeDriver(); 
	private static WebDriver driverChrome2 = new ChromeDriver(); 
	private String alertBrowser1, alertBrowser2;
	
	@BeforeClass
	public static void setupClass() {
		ChromeDriverManager.getInstance().setup();
		WebApp.start();	
//        driverChrome1 = new ChromeDriver();
//        driverChrome2 = new ChromeDriver();  	
    }
    
    @Given("^both players are connected to the browsers$")
    public void both_players_are_connected_to_the_browsers() throws Throwable {

		driverChrome1.get("http://localhost:8080/");
		
		driverChrome1.findElement(By.className("form-control")).sendKeys("Player1");
		driverChrome1.findElement(By.id("startBtn")).click();	
		
		driverChrome2.get("http://localhost:8080/");
		
		driverChrome2.findElement(By.className("form-control")).sendKeys("Player2");
		driverChrome2.findElement(By.id("startBtn")).click();	
					
    }

    @Then("^the first player wins$")
    public void the_first_player_wins() throws Throwable {
		alertBrowser1 = driverChrome1.switchTo().alert().getText();
		alertBrowser2 = driverChrome2.switchTo().alert().getText();	
		
		assertThat(alertBrowser1, containsString("Player1 wins"));
		assertThat(alertBrowser2, containsString("Player1 wins"));		
    }

    @Then("^the first player loses$")
    public void the_first_player_loses() throws Throwable {
		alertBrowser1 = driverChrome1.switchTo().alert().getText();
		alertBrowser2 = driverChrome2.switchTo().alert().getText();	
		
		assertThat(alertBrowser1, containsString("Player1 looses"));
		assertThat(alertBrowser2, containsString("Player1 looses"));		
    }

    @Then("^the game ends with a draw$")
    public void the_game_ends_with_a_draw() throws Throwable {
		alertBrowser1 = driverChrome1.switchTo().alert().getText();
		alertBrowser2 = driverChrome2.switchTo().alert().getText();	
		
		assertThat(alertBrowser1, containsString("Draw"));
		assertThat(alertBrowser2, containsString("Draw"));	
    }

    @And("^the second player marks cell-(\\d+)$")
    public void the_second_player_marks_cell(String cellId) throws Throwable {
		driverChrome2.findElement(By.id(cellId)).click();	
    }

    @And("^the first player marks cell-(\\d+)$")
    public void the_first_player_marks_cell(String cellId) throws Throwable {
		driverChrome1.findElement(By.id(cellId)).click();		
    }

    @But("^the second player loses$")
    public void the_second_player_loses() throws Throwable {
		String alertBrowser1 = driverChrome1.switchTo().alert().getText();
		String alertBrowser2 = driverChrome2.switchTo().alert().getText();	
		
		assertThat(alertBrowser1, containsString("Player2 looses"));
		assertThat(alertBrowser2, containsString("Player2 looses"));	
    }

    @But("^the second player wins$")
    public void the_second_player_wins() throws Throwable {
		String alertBrowser1 = driverChrome1.switchTo().alert().getText();
		String alertBrowser2 = driverChrome2.switchTo().alert().getText();	
		
		assertThat(alertBrowser1, containsString("Player2 wins"));
		assertThat(alertBrowser2, containsString("Player2 wins"));	
    }
}
