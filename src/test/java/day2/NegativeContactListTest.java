package day2;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import org.testng.annotations.Test;

public class NegativeContactListTest {
 
	  @Test(enabled=false)
	  public void recordNotFound() {
		  given()
		  .when()
		  .get("http://3.13.86.142:3000/contacts/5")
		  .then()
		  .log()
		  .body()
		  .statusCode(404);
	  }
	  
	  @Test(enabled=true, description="adding contact with missing details")
	  public void addingContactMissing() {
		  JSONObject details = new JSONObject();
		  JSONObject location = new JSONObject();
		  JSONObject emp = new JSONObject();
		  
		  location.put("city", "Mumbai");
		  location.put("country", "India");
		  
		  emp.put("jobTitle", "QA");
		  emp.put("company", "LTI");
		  
		  details.put("firstName", null);
		  details.put("lastName", "Smith");
		  details.put("email", "john@email.com");
		  details.put("location", location);
		  details.put("employer", emp);
		  
		  
		  String error = given()
		  .header("Content-Type", "application/json")
		  .body(details.toJSONString())
		  .when()
		  .post("http://3.13.86.142:3000/contacts")
		  .then()
		  .log()
		  .body()
		  .statusCode(400)
		  .extract()
		  .path("err");
		  
		  Assert.assertTrue(error.contains("firstName: First Name is required"));
	  }
	  
	  @Test(enabled=true, description="adding contact with too many character")
	  public void addingContactBigSize() {
		  JSONObject details = new JSONObject();
		  JSONObject location = new JSONObject();
		  JSONObject emp = new JSONObject();
		  
		  location.put("city", "MumbaiMumbaiMumbaiMumbaiMumbaiMumbaiMumbaiMumbaiMumbaiMumbaiMumbaiMumbaiMumbai");
		  location.put("country", "India");
		  
		  emp.put("jobTitle", "QA");
		  emp.put("company", "LTI");
		  
		  details.put("firstName", "joe");
		  details.put("lastName", "Smith");
		  details.put("email", "john@email.com");
		  details.put("location", location);
		  details.put("employer", emp);
		  
		  
		  String error = given()
		  .header("Content-Type", "application/json")
		  .body(details.toJSONString())
		  .when()
		  .post("http://3.13.86.142:3000/contacts")
		  .then()
		  .log()
		  .body()
		  .statusCode(400)
		  .extract()
		  .path("err");

		  Assert.assertTrue(error.contains("is longer than the maximum allowed length (30)"));
	  }
	  
	  
	  
	  @Test(enabled = true,description="Adding Contact with Numbers in First Name")
	  public void invalidFirstName() {
		  JSONObject details = new JSONObject();
		  JSONObject loc = new JSONObject();
		  JSONObject emp = new JSONObject();
		  
		  loc.put("city", "Bengaluru");
		  loc.put("country", "India");
		  emp.put("jobTitle", "SE");
		  emp.put("company", "LTI");
		  details.put("firstName", "Neha4");
		  details.put("lastName", "Singh");
		  details.put("email", "neha@lnt.com");
		  details.put("location", loc);
		  details.put("employer", emp);

		 String error = given()
		  			.header("Content-Type","application/json")
		  			.body(details.toJSONString())
		  		.when()
		  			.post("http://3.13.86.142:3000/contacts")
		  		.then()
		  			.log()
		  			.body()
		  			.statusCode(400)
		  			.extract()
		  			.path("err");
		  		
		  Assert.assertTrue(error.contains("Contacts validation failed: firstName: Validator failed for path `firstName` with value "));
	  }
	  

	  @Test(enabled = true,description="Adding Contact with missing @ in email")
	  public void invalidEmail() {
		  JSONObject details = new JSONObject();
		  JSONObject loc = new JSONObject();
		  JSONObject emp = new JSONObject();
		  
		  loc.put("city", "Bengaluru");
		  loc.put("country", "India");
		  emp.put("jobTitle", "SE");
		  emp.put("company", "LTI");
		  details.put("firstName", "Neha");
		  details.put("lastName", "Singh");
		  details.put("email", "nehalnt.com");
		  details.put("location", loc);
		  details.put("employer", emp);

		 String error = given()
		  			.header("Content-Type","application/json")
		  			.body(details.toJSONString())
		  		.when()
		  			.post("http://3.13.86.142:3000/contacts")
		  		.then()
		  			.log()
		  			.body()
		  			.statusCode(400)
		  			.extract()
		  			.path("err");
		  		
		  Assert.assertTrue(error.contains("Contacts validation failed: email: Validator failed for path `email` "));
	  }
}
