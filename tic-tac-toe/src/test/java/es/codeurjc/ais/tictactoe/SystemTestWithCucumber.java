package es.codeurjc.ais.tictactoe;

import org.junit.runner.RunWith;

import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@cucumber.api.CucumberOptions(
  plugin = {"pretty"}, 
  features = { "classpath:es/codeurjc/ais/tictactoe" },
  glue = {"es.codeurjc.ais.tictactoe" })
public class SystemTestWithCucumber {


}