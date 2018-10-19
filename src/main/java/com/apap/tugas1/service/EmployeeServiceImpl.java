package com.apap.tugas1.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.EmployeeModel;
import com.apap.tugas1.repository.InstansiDb;
import com.apap.tugas1.repository.EmployeeDb;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService{
	@Autowired
	private EmployeeDb employeeDb;
	
	@Autowired
	private InstansiDb instansiDb;
	
	@Override
	public EmployeeModel getEmployeeDetailByNIP(String nip) {
		return employeeDb.findBynip(nip);
	}

	@Override
	public EmployeeModel getOldestEmployee (long id) {
		List<EmployeeModel> employees = instansiDb.getOne(id).getEmployeesList();
		
		EmployeeModel oldest = employees.get(0);
		
		for (int i=1; i<employees.size(); i++) {
			if (employees.get(i).getTanggalLahir().before(oldest.getTanggalLahir())) {
				oldest = employees.get(i);
			}
		}
		
		return oldest;
	}

	@Override
	public EmployeeModel getYoungestEmployee(long id) {
		List<EmployeeModel> pegawaiInInstansi = instansiDb.getOne(id).getEmployeesList();
		
		EmployeeModel youngest = pegawaiInInstansi.get(0);
		
		for (int i=1; i<pegawaiInInstansi.size(); i++) {
			if (pegawaiInInstansi.get(i).getTanggalLahir().after(youngest.getTanggalLahir())) {
				youngest = pegawaiInInstansi.get(i);
			}
		}
		
		return youngest;
	}

	@Override
	public EmployeeDb getEmployeeDb() {
		return employeeDb;
	}

	@Override
	public void deleteEmployee(EmployeeModel employee) {
		employeeDb.delete(employee);
	}

	@Override
	public List<EmployeeModel> getEmployeeList() {
		return employeeDb.findAll();
	}
}