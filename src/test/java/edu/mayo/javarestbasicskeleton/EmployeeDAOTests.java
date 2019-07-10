package edu.mayo.javarestbasicskeleton;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.mayo.javarestbasicskeleton.dao.EmployeeDAO;
import edu.mayo.javarestbasicskeleton.model.Employee;

public class EmployeeDAOTests {
	
	private EmployeeDAO employeeDao;
	
	@Before()
	public void setup () {
		this.employeeDao = new EmployeeDAO();
	}
	
	private void assertEmployeeSame (Employee employee1, Employee employee2) {
		Assert.assertTrue(employee1 != null && employee2 !=null);
		Assert.assertTrue(employee1.getFirstName().contentEquals(employee2.getFirstName()));
		Assert.assertTrue(employee1.getLastName().contentEquals(employee2.getLastName()));
		Assert.assertTrue(employee1.getEmail().contentEquals(employee2.getEmail()));
	}	
	
	@Test()
	public void findEmployeeTest () {
		Employee employee = new Employee(1, "Jim", "Henson", "jhenson@@gmail.com");
		Optional<Employee> foundEmployee = employeeDao.findEmployee(1);
		Assert.assertTrue(foundEmployee.isPresent());
		assertEmployeeSame(foundEmployee.get(),employee);
	}
	
	@Test()
	public void addEmployeeTest () {
		Employee employee = new Employee(0, "Thomas", "Fielding", "tfielding@@gmail.com");
		int initialEmployeeCount = employeeDao.getAllEmployees().size();
		employeeDao.addEmployee(employee);
		int finalEmployeeCount = employeeDao.getAllEmployees().size();
		Assert.assertTrue(finalEmployeeCount == initialEmployeeCount + 1);
		Optional<Employee> foundEmployee = employeeDao.findEmployee(employee.getId());
		Assert.assertTrue(foundEmployee.isPresent());
		assertEmployeeSame(foundEmployee.get(),employee);	
	}
	
	@Test()
	public void deleteEmployeeTest () {
		int employeeId = 1;
		int initialEmployeeCount = employeeDao.getAllEmployees().size();
		employeeDao.deleteEmployee(employeeId);
		int finalEmployeeCount = employeeDao.getAllEmployees().size();
		Assert.assertTrue(finalEmployeeCount == initialEmployeeCount - 1);
		Optional<Employee> foundEmployee = employeeDao.findEmployee(employeeId);
		Assert.assertTrue(!foundEmployee.isPresent());
	}	
}
