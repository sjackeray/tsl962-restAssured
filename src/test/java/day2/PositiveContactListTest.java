package day2;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class PositiveContactListTest {
	String id ;

	@Test(enabled = false, description = "Getting all Contact List")
	public void getContactListInfo() {
		given()
		.get("http://3.13.86.142:3000/contacts")
		.then().log().body().statusCode(200);

	}

	@Test(enabled = false, dependsOnMethods = "updateContact", description = "Getting specific Contact")
	public void getSpecificContact() {
		given()
			.get("http://3.13.86.142:3000/contacts/615fd60bf2967f0ec893af32")
		.then().log().body()
				.statusCode(200);
	}

	
	@Test(enabled=true,dependsOnMethods = "deleteContact", description="Getting specific Contact")
	public void getSpecificContact2() {
	Response res = given()
	.get("http://3.13.86.142:3000/contacts/"+id);
	
	System.out.println(res.getTime());
	
	res.then()
	.log()
	.body()
	//.statusCode(200);
	.statusCode(404);
	}
	
	

	@Test(enabled=true,description="Post specific Contact")
	public void AddContact() {
		
		JSONObject details = new JSONObject();
		JSONObject loc = new JSONObject();
		JSONObject emp = new JSONObject();
		
		loc.put("city", "Mumbai");
		loc.put("country", "India");
		emp.put("jobTitle", "GET");
		emp.put("company", "LTI");
		details.put("firstName", "Saloni");
		details.put("lastName", "Singh");
		details.put("email", "saloni@xyz.com");
		details.put("location", loc);
		details.put("employer", emp);
		
		ExtractableResponse<Response> eres = given()
				.header("Content-Type","application/json")
		.body(details.toJSONString())
		.post("http://3.13.86.142:3000/contacts")
		.then()
		.log()
		.body()
		.statusCode(200)
		.extract();
		//.path("_id");
		id = eres.path("_id");
		String fn = eres.path("firstName");
		String ln = eres.path("lastName");
		String email = eres.path("email");
		String country = eres.path("location.country");
		String city = eres.path("location.city");
		
		System.out.println("The Id For Above Added Contact is "+id);
		System.out.println("The First Name For Above Added Contact is "+fn);
		System.out.println("The Last Name For Above Added Contact is "+ln);
		System.out.println("The Email For Above Added Contact is "+email);
		System.out.println("The Country For Above Added Contact is "+country);
		System.out.println("The City For Above Added Contact is "+city);
		
		
		
	}
	@Test(enabled=true,dependsOnMethods = "AddContact",description = "Updating specific Contact and getting It to display")
	public void updateContact() {
		
		JSONObject details = new JSONObject();
		JSONObject loc = new JSONObject();
		JSONObject emp = new JSONObject();
		
		loc.put("city", "Mumbai");
		loc.put("country", "India");
		emp.put("jobTitle", "GET");
		emp.put("company", "LTI");
		details.put("firstName", "Saloni");
		details.put("lastName", "Jackeray");
		details.put("email", "saloni@xyz.com");
		details.put("location", loc);
		details.put("employer", emp);
		
	
		
		given().header("Content-Type","application/json")
				.body(details.toJSONString())
				.put("http://3.13.86.142:3000/contacts/"+id)
				.then()
				.log()
				.body()
				.statusCode(204);
		

	}
	@Test(enabled=true,dependsOnMethods = "updateContact", description="Delete specific Contact")
	public void deleteContact() {
		
		given()
		.delete("http://3.13.86.142:3000/contacts/"+id)
		.then()
		.log()
		.body()
		.statusCode(204);
		
		System.out.println("Contact Deleted\n");
		
		
	}
	
	
	
}