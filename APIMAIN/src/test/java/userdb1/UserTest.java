package userdb1;

import testcase1.UserDetails;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;

public class UserTest {


    String createToken;
    RequestSpecification requestSpec;
    ResponseSpecification responseSpec;

    @BeforeClass
    public void setUp()
    {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri("https://api-nodejs-todolist.herokuapp.com").
                addHeader("Content-Type","application/json");

        requestSpec =RestAssured.with().spec(requestSpecBuilder.build());

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectContentType(ContentType.JSON);
        responseSpec=responseSpecBuilder.build();
    }

    @Test(priority = 1)
    public void registerUserTest() throws Exception {
        HashMap hashmap;
        UserDetails user =new UserDetails();
        hashmap = user.registerUser();
        System.out.println(hashmap);
        Response response = requestSpec.
                body(hashmap).
                when().
                post("/user/register").
                then().
                spec(responseSpec).extract().response();
        Assert.assertEquals(response.statusCode(),201);
        System.out.println(createToken);
        // user.storeOwnerInDB(createToken);
    }

    @Test(priority = 2)
    public void loginUserTest() throws Exception
    {
        UserDetails user =new UserDetails();
        HashMap hashmap;
        hashmap=user.registerUser();
        HashMap Hash = new HashMap<>();
        Hash.put("email",hashmap.get("email"));
        Hash.put("password",hashmap.get("password"));
        System.out.println(Hash);
        Response response = requestSpec.
                body(Hash).
                when().
                post("/user/login").
                then().spec(responseSpec).extract().response();
        //Assert.assertEquals(response.statusCode(),200);
        JSONObject object=new JSONObject(response.asString());
        createToken=object.getString("token");
        user.registerToken(createToken);
        System.out.println(createToken);
    }

    @Test(priority = 3)
    public void validUsercredentials() throws Exception
    {
        UserDetails user =new UserDetails();
        HashMap hashMap;
        hashMap = user.registerUser();
        String token = user.token;
        Response response = requestSpec.auth().oauth2(token).
                when().
                get("/user/me").
                then().
                spec(responseSpec).extract().response();
        JSONObject object = new JSONObject(response.asString());
        String checkName = object.getString("name");
        String checkEmail = object.getString("email");
        String checkAge = String.valueOf(object.getInt("age"));
        Assert.assertEquals("pavan","pavan");
        Assert.assertEquals(checkEmail,hashMap.get("email"));
        Assert.assertEquals(checkAge,hashMap.get("age"));
    }

}
