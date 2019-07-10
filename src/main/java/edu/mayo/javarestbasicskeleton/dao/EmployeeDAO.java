package edu.mayo.javarestbasicskeleton.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import edu.mayo.javarestbasicskeleton.model.Employee;
import edu.mayo.javarestbasicskeleton.model.Employees;
 
@Repository
public class EmployeeDAO {
	private static Employees list = new Employees();
	private static int nextId = 4;

	static {
		list.getEmployeeList().add(new Employee(1, "Jim", "Henson", "jhenson@@gmail.com"));
		list.getEmployeeList().add(new Employee(2, "Jill", "Fremstad", "jfremstad@gmail.com"));
		list.getEmployeeList().add(new Employee(3, "Anton", "Masloski", "amasloski@gmail.com"));
	}

	public List<Employee> getAllEmployees() {
		return list.getEmployeeList();
	}

	public void addEmployee(Employee employee) {
		employee.setId(nextId++);
		list.getEmployeeList().add(employee);
	}

	public boolean deleteEmployee(int id) {
		List<Employee> employeeList = list.getEmployeeList();
		for (int i = 0; i < employeeList.size(); i++) {
			if (employeeList.get(i).getId() == id) {
				employeeList.remove(i);
				return true;
			}
		}
		return false;
	}

	public Optional<Employee> findEmployee(int id) {
		List<Employee> employeeList = list.getEmployeeList();
		Employee employee;
		for (int i = 0; i < employeeList.size(); i++) {
			employee = employeeList.get(i);
			if (employee.getId() == id) {
				return Optional.of(employee);
			}
		}
		return Optional.empty();
	}
}
