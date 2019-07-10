package edu.mayo.javarestbasicskeleton;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.mayo.javarestbasicskeleton.model.Employee;
import edu.mayo.javarestbasicskeleton.pojo.rest.EmployeePayload;

@RunWith(SpringRunner.class)
@SpringBootTest()
@WebAppConfiguration
public class EmployeeManagementTests {
	private MockMvc mockMvc;
	private Gson gson = new GsonBuilder().create();

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void getEmployees() throws Exception {
		MvcResult result = mockMvc
				.perform(get("/api/employees/").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andReturn();
		Employee[] employeeList = new Employee[0];
		employeeList = this.gson.fromJson(result.getResponse().getContentAsString(), employeeList.getClass());
		Assert.assertTrue(employeeList != null && employeeList.length > 0);
	}
	
	@Test
	public void getEmployee() throws Exception {
		int employeeId = 2;
		MvcResult result = mockMvc
				.perform(get("/api/employees/" + employeeId).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andReturn();
		Employee employee = this.gson.fromJson(result.getResponse().getContentAsString(), Employee.class);
		Assert.assertTrue(employee != null && employee.getId() == employeeId);
	}
	
	@Test
	public void getNonexistentEmployee() throws Exception {
		int employeeId = 0;
		mockMvc.perform(get("/api/employees/" + employeeId).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andReturn();
	}	
	
	@Test
	public void addEmployee() throws Exception {
		String employeeFirstName = "Don";
		String employeeLastName = "Jacobs";
		String employeeEmail = "djacobs@gmail.com";
		
		EmployeePayload employeePayload = new EmployeePayload(employeeFirstName,employeeLastName,employeeEmail);
		
		MvcResult result = mockMvc
				.perform(post("/api/employees/").content(this.gson.toJson(employeePayload)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andReturn();
		
		String returnedLocationPath = result.getResponse().getHeader("location");
		Pattern pattern = Pattern.compile(".*(/api/employees/.*)");
        Matcher matcher = pattern.matcher(returnedLocationPath);
        if(matcher.matches()) {
            returnedLocationPath = matcher.group(1);
        }	
				
		result = mockMvc
				.perform(get(returnedLocationPath).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andReturn();
		Employee newEmployee = this.gson.fromJson(result.getResponse().getContentAsString(), Employee.class);
		Assert.assertTrue(newEmployee != null && newEmployee.getFirstName().contentEquals(employeeFirstName) && newEmployee.getLastName().contentEquals(employeeLastName) && newEmployee.getEmail().contentEquals(employeeEmail));
	}
	
	@Test
	public void updateEmployee() throws Exception {
		int employeeId = 2;
		String upEmployeeFirstName = "Stephanie";
		String upEmployeeLastName = "Johnson";
		String upEmployeeEmail = "sjohnson@gmail.com";
		
		EmployeePayload employeePayload = new EmployeePayload(upEmployeeFirstName,upEmployeeLastName,upEmployeeEmail);
		
		MvcResult result = mockMvc
				.perform(put("/api/employees/" + employeeId).content(this.gson.toJson(employeePayload)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		
		result = mockMvc
				.perform(get("/api/employees/" + employeeId).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andReturn();
		Employee upEmployee = this.gson.fromJson(result.getResponse().getContentAsString(), Employee.class);
		Assert.assertTrue(upEmployee!=null && upEmployee.getId() == employeeId && upEmployee.getFirstName().contentEquals(upEmployeeFirstName));
	}
	
	@Test
	public void updateNonexistentEmployee() throws Exception {
		int employeeId = 0;
		String upEmployeeFirstName = "Stephanie";
		String upEmployeeLastName = "Johnson";
		String upEmployeeEmail = "sjohnson@gmail.com";

		EmployeePayload employeePayload = new EmployeePayload(upEmployeeFirstName, upEmployeeLastName, upEmployeeEmail);

		mockMvc.perform(put("/api/employees/" + employeeId).content(this.gson.toJson(employeePayload))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound()).andReturn();
	}	
	
	@Test
	public void removeEmployee() throws Exception {
		int employeeId = 3;
		
		mockMvc.perform(delete("/api/employees/" + employeeId).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		mockMvc.perform(get("/api/employees/" + employeeId).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andReturn();
	}
	
	@Test
	public void removeNonexistentEmployee() throws Exception {
		int employeeId = 0;

		mockMvc.perform(delete("/api/employees/" + employeeId).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andReturn();
	}	
}
