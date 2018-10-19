package com.apap.tugas1.service;


import java.util.List;

import com.apap.tugas1.model.EmployeeModel;
import com.apap.tugas1.repository.EmployeeDb;

public interface EmployeeService {
	EmployeeModel getEmployeeDetailByNIP(String nip);
	void deleteEmployee(EmployeeModel employee);
	EmployeeModel getOldestEmployee(long id);
	EmployeeModel getYoungestEmployee(long id);
	List<EmployeeModel> getEmployeeList();
	EmployeeDb getEmployeeDb();
	void addEmployee(EmployeeModel employee);
	void updateEmployee(EmployeeModel employee);
}
