package testcase1;

import userdb1.TasksDB;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class AddTaskTest {

    RequestSpecification requestSpec;
    ResponseSpecification responseSpec;


    @BeforeClass
    public void setUp()
    {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri("https://api-nodejs-todolist.herokuapp.com").
                addHeader("Content-Type","application/json");

        requestSpec = RestAssured.with().spec(requestSpecBuilder.build());

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectContentType(ContentType.JSON);
        responseSpec=responseSpecBuilder.build();
    }

    @Test
    public void addTaskTest() throws Exception {
        UserDetails user= new UserDetails();
        TasksDB tasksDB = new TasksDB();
        ArrayList list;
        HashMap hashMap;
        hashMap=user.registerUser();
        String checkToken = (String) hashMap.get("token");
        list =tasksDB.addTasks();
        for(int i=0;i< list.size();i++)
        {
            HashMap hash = new HashMap<>();
            hash.put("description",list.get(i));
            Response response = requestSpec.body(hash).auth().oauth2(checkToken).request(Method.POST,"/task");
            String check = response.getBody().asString();
            System.out.println(response.getStatusCode());
            JSONObject obj = new JSONObject(response.asString());
            System.out.println(obj);
            JSONArray array = (JSONArray)obj.get("data");
            System.out.println(array);
            JSONObject ownerObj = array.getJSONObject(i);
            String ownerStr = ownerObj.getString("owner");
            System.out.println(ownerStr);
        }


        System.out.println(checkToken);
        System.out.println(list);
    }
    /*JSONObject RequestObject = new JSONObject();
        for(int i=0;i<list.size();i++) {
        RequestObject.put("description", list.get(i));
    }*/
    @Test
    public void getOwnerIDTest() throws Exception {
        UserDetails user =new UserDetails();
        TasksDB tasksDB = new TasksDB();
        HashMap hashmap;
        hashmap= user.registerUser();
        String token = (String) hashmap.get("token");
        Response response =requestSpec.auth().oauth2(token).request(Method.GET,"/task");
        JSONObject object = new JSONObject(response.asString());
        JSONArray array = (JSONArray)object.get("data");
        JSONObject object1 = array.getJSONObject(0);
        /*for(int i =0;i<array.length();i++)
        {
            JSONObject ownerObj =array.getJSONObject(i);
            JSONObject idOBj = array.getJSONObject(i);
            String ownerStr = ownerObj.getString("owner");
            tasksDB.storeOwnerInDB(ownerStr);
            System.out.println("Owner is"+ownerStr);
            System.out.println("Id is "+ idOBj.get("_id"));
        }*/

        System.out.println(array);
        System.out.println(object1.get("description"));
        System.out.println(token);
    }
}
