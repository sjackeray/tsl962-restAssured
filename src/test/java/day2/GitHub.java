package day2;

import org.hamcrest.Matchers;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;

import static io.restassured.RestAssured.*;

import java.util.concurrent.TimeUnit;

public class GitHub {
	@BeforeTest
	public void beforeTest() {
		RestAssured.baseURI="https://api.github.com/user/repos";
		RestAssured.authentication=RestAssured.oauth2("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");
	}
  @Test(enabled=false)
  public void gettingAllRepositories() {
	  given()
	  .auth()
	  .oauth2("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")
	  .when()
	  .get("https://api.github.com/user/repos")
	  .then()
	  .log()
	  .body()
	  .statusCode(200);
	  
  }
  
  @Test(enabled=true)
  public void createRepositories() {
	  JSONObject data = new JSONObject();
	  data.put("name", "RestAssuredCreations2");
	  data.put("description", "I am created by RestAssured tools");
	  data.put("hoempage", "https://github.com/sjackeray");
	  given()
	 
	  .header("Content-Type","application/json")
	  .body(data.toJSONString())
	  .when()
	  .post("https://api.github.com/user/repos")
	  .then()
	  .log()
	  .headers()
	  .statusCode(201)
	  .time(Matchers.lessThan(6000L),TimeUnit.MILLISECONDS);
  }
}
