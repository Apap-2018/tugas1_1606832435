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
	
	@Override
	public void addEmployee(EmployeeModel employee) {		
		String kodeInstansi = Long.toString(employee.getInstansi().getId());
		String kodeTanggalLahir = employee.getTanggalLahir().toString();
		String hari = kodeTanggalLahir.substring(8);
		String bulan = kodeTanggalLahir.substring(5, 7);
		String tahun = kodeTanggalLahir.substring(2, 4);
		kodeTanggalLahir = hari + bulan + tahun;
		String kodeTahunMasuk = employee.getTahunMasuk();
		
		String lahirMasukSama = "";
		
		List<EmployeeModel> employeeSama = employeeDb.findByTahunMasukAndTanggalLahir(employee.getTahunMasuk(), employee.getTanggalLahir());
		employeeSama.add(employee);
		lahirMasukSama = Integer.toString(employeeSama.size());
		
		if (Integer.parseInt(lahirMasukSama) < 10) {
			lahirMasukSama = "0" + lahirMasukSama;
		}
		
		employee.setNip(kodeInstansi + kodeTanggalLahir + kodeTahunMasuk + lahirMasukSama);
		
		employeeDb.save(employee);
	}
	
	@Override
	public void updateEmployee(EmployeeModel employee) {
		EmployeeModel updatedEmployee = employeeDb.getOne(employee.getId());
		
		updatedEmployee.setNama(employee.getNama());
		updatedEmployee.setTempatLahir(employee.getTempatLahir());
		updatedEmployee.setTanggalLahir(employee.getTanggalLahir());
		updatedEmployee.setTahunMasuk(employee.getTahunMasuk());
		updatedEmployee.setInstansi(employee.getInstansi());
		updatedEmployee.setListJabatan(employee.getListJabatan());
		
		
		String kodeInstansi = Long.toString(employee.getInstansi().getId());
		String kodeTanggalLahir = employee.getTanggalLahir().toString();
		String hari = kodeTanggalLahir.substring(8);
		String bulan = kodeTanggalLahir.substring(5, 7);
		String tahun = kodeTanggalLahir.substring(2, 4);
		kodeTanggalLahir = hari + bulan + tahun;
		String kodeTahunMasuk = employee.getTahunMasuk();
		String lahirMasukSama = "";
		List<EmployeeModel> employees = employeeDb.findByTahunMasukAndTanggalLahir(employee.getTahunMasuk(), employee.getTanggalLahir());
		employees.add(employee);
		
		lahirMasukSama = Integer.toString(employees.size());
		
		if (Integer.parseInt(lahirMasukSama) < 10) {
			lahirMasukSama = "0" + lahirMasukSama;
		}
			
		updatedEmployee.setNip(kodeInstansi + kodeTanggalLahir + kodeTahunMasuk + lahirMasukSama);
        
        employeeDb.save(updatedEmployee);
	}
}