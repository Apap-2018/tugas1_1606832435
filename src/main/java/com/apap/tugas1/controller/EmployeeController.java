package com.apap.tugas1.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.PositionModel;
import com.apap.tugas1.model.ProvinceModel;
import com.apap.tugas1.model.EmployeeModel;
import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.PositionService;
import com.apap.tugas1.service.EmployeeService;
import com.apap.tugas1.service.ProvinceService;

@Controller
public class EmployeeController {
	// Temporary untuk Pegawai yang Sedang Dilihat/Diupdate/Ditambahkan
	private Long currEmployeeId;
	private EmployeeModel currentEmployeee;
		
	@Autowired
	private PositionService positionService;
	
	@Autowired
	private InstansiService instansiService;
	
	@Autowired
	private ProvinceService provinceService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@RequestMapping(value = "/pegawai")
	private String viewPegawai(@RequestParam("nip") String nip, Model model) {
		EmployeeModel pegawai = employeeService.getEmployeeDetailByNIP(nip);
		InstansiModel instansi = pegawai.getInstansi();
		ProvinceModel provinsi = instansi.getProvinsi();
		
		List<PositionModel> listJabatanPegawai = pegawai.getListJabatan(); 
		
		double gajiKotor = listJabatanPegawai.get(0).getGajiPokok();
		
		if(listJabatanPegawai.size() > 1) {
			for (int i = 1; i < listJabatanPegawai.size(); i++) {
				 if (listJabatanPegawai.get(i).getGajiPokok() > gajiKotor) {
					 gajiKotor = listJabatanPegawai.get(i).getGajiPokok();
				 }
			}
		}
			
		System.out.println(gajiKotor);
		
		gajiKotor += (gajiKotor * (provinsi.getTunjangan()/100));
		String gajiBersih = String.format ("%.0f", gajiKotor);
			
		model.addAttribute("gaji", gajiBersih);
		model.addAttribute("pegawai", pegawai);
		return "lihat-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/termuda-tertua", method = RequestMethod.GET)
	private String viewOldestYoungest(@RequestParam("idInstansi") Long idInstansi, Model model) {
		EmployeeModel tertuir = employeeService.getOldestEmployee(idInstansi);
		EmployeeModel termuda = employeeService.getYoungestEmployee(idInstansi);
		
		model.addAttribute("tertua", tertuir);
		model.addAttribute("termuda", termuda);
		return "lihat-pegawai-oldYoung";
	}
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.GET)
	private String add(Model model) {
		currentEmployeee = new EmployeeModel();
		currentEmployeee.setListJabatan(new ArrayList<PositionModel>());
		currentEmployeee.getListJabatan().add(new PositionModel());
		
		List<ProvinceModel> provinces = provinceService.getProvinceList();
		List<PositionModel> positions = positionService.getJabatanDb().findAll();
		List<InstansiModel> instansiIns = instansiService.getInstansiList();
		model.addAttribute("pegawai", currentEmployeee);
		model.addAttribute("provinces", provinces);
		model.addAttribute("positions", positions);
		model.addAttribute("instansiIns", instansiIns);
		
		return "tambah-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/tambah", params= {"addRowJabatan"})
	private String addRowJabatan(@ModelAttribute EmployeeModel pegawai, BindingResult bindingResult, Model model) {
		pegawai.setListJabatan(currentEmployeee.getListJabatan());
		pegawai.getListJabatan().add(new PositionModel());
		
		List<ProvinceModel> provinces = provinceService.getProvinceList();
		List<PositionModel> positions = positionService.getJabatanDb().findAll();
		List<InstansiModel> instansiIns = instansiService.getInstansiList();
		
		model.addAttribute("pegawai", currentEmployeee);
		model.addAttribute("provinces", provinces);
		model.addAttribute("positions", positions);
		model.addAttribute("instansiIns", instansiIns);
		
		return "tambah-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/tambah", params={"submit"}, method = RequestMethod.POST)
	private String addPegawaiSubmit(@ModelAttribute EmployeeModel pegawai, Model model) {
		employeeService.addEmployee(pegawai);
		
		model.addAttribute("pegawai", pegawai);
		return "tambah-pegawai-berhasil";
	}
	
	@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.GET)
	private String update(@RequestParam("nip") String nip, Model model) {
		EmployeeModel pegawai = employeeService.getEmployeeDetailByNIP(nip);
		currEmployeeId = pegawai.getId();
		
		List<ProvinceModel> provinces = provinceService.getProvinceList();
		List<PositionModel> positions = positionService.getJabatanDb().findAll();
		List<InstansiModel> instansiIns = instansiService.getInstansiList();
		
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("provinces", provinces);
		model.addAttribute("positions", positions);
		model.addAttribute("instansiIns", instansiIns);
		
		return "update-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/ubah", params= {"addMoreJabatanUpdate"})
	private String addMoreJabatanUpdate(@ModelAttribute EmployeeModel pegawai, BindingResult bindingResult, Model model) {
		pegawai.getListJabatan().add(new PositionModel());
		
		List<ProvinceModel> provinces = provinceService.getProvinceList();
		List<PositionModel> positions = positionService.getJabatanDb().findAll();
		List<InstansiModel> instansiIns = instansiService.getInstansiList();
		
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("provinces", provinces);
		model.addAttribute("positions", positions);
		model.addAttribute("instansiIns", instansiIns);
		
		return "update-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/ubah", params={"submit"}, method = RequestMethod.POST)
	private String updatePegawaiSubmit(@ModelAttribute EmployeeModel pegawai, Model model) {
		pegawai.setId(currEmployeeId);
		employeeService.updateEmployee(pegawai);
		pegawai.setNip(employeeService.getEmployeeDb().getOne(currEmployeeId).getNip());
		
		model.addAttribute("pegawai", pegawai);
		return "update-pegawai-berhasil";
	}
	
	@RequestMapping(value = "/pegawai/cari")
	private String searchAndFilter(Model model) {
		List<EmployeeModel> employees = employeeService.getEmployeeList();
		List<ProvinceModel> provinces = provinceService.getProvinceList();
		List<PositionModel> positions = positionService.getJabatanDb().findAll();
		List<InstansiModel> instansiIns = instansiService.getInstansiList();
		
		model.addAttribute("text", null);
		model.addAttribute("employees", employees);
		model.addAttribute("provinces", provinces);
		model.addAttribute("positions", positions);
		model.addAttribute("instansiIns", instansiIns);
		
		return "cari-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/cari", method = RequestMethod.GET)
	private String filter(@RequestParam(value = "id_Provinsi", required = false) Long id_Provinsi, @RequestParam(value = "id_Instansi", required = false) Long id_Instansi,
						  @RequestParam(value = "id_Jabatan", required = false) Long id_Jabatan, Model model) {
		
		List<EmployeeModel> listPegawai = new ArrayList<EmployeeModel>();
		
		if(id_Provinsi != null && id_Instansi != null && id_Jabatan != null) {
			listPegawai = employeeService.getEmployeeDb().findByProvinsiAndInstansiAndJabatan(id_Provinsi, id_Instansi, id_Jabatan);
			model.addAttribute("text", "Hasil pencarian pegawai berdasarkan provinsi, instansi, dan jabatan");
		} else if (id_Provinsi != null && id_Instansi != null && id_Jabatan == null) {
			listPegawai = employeeService.getEmployeeDb().findByProvinsiAndInstansi(id_Provinsi, id_Instansi);
			model.addAttribute("text", "Hasil pencarian pegawai berdasarkan provinsi dan instansi");
		} else if (id_Provinsi != null && id_Instansi == null && id_Jabatan != null) {
			listPegawai = employeeService.getEmployeeDb().findByProvinsiAndJabatan(id_Provinsi, id_Jabatan);
			model.addAttribute("text", "Hasil pencarian pegawai berdasarkan provinsi dan jabatan");
		} else if (id_Provinsi == null && id_Instansi != null && id_Jabatan != null) {
			listPegawai = employeeService.getEmployeeDb().findByInstansiAndJabatan(id_Instansi, id_Jabatan);
			model.addAttribute("text", "Hasil pencarian pegawai berdasarkan instansi dan jabatan");		
		} else if (id_Provinsi != null && id_Instansi == null && id_Jabatan == null) {
			listPegawai = employeeService.getEmployeeDb().findByProvinsi(id_Provinsi);
			model.addAttribute("text", "Hasil pencarian pegawai berdasarkan provinsi");
		} else if (id_Provinsi == null && id_Instansi != null && id_Jabatan == null) {
			listPegawai = employeeService.getEmployeeDb().findByInstansi(instansiService.getInstansiDb().getOne(id_Instansi));
			model.addAttribute("text", "Hasil pencarian pegawai berdasarkan instansi");
		} else if (id_Provinsi == null && id_Instansi == null && id_Jabatan != null) {
			listPegawai = employeeService.getEmployeeDb().findByJabatan(id_Jabatan);
			model.addAttribute("text", "Hasil pencarian pegawai berdasarkan jabatan");
		}
		
		List<ProvinceModel> provinces = provinceService.getProvinceList();
		List<PositionModel> positions = positionService.getJabatanDb().findAll();
		List<InstansiModel> instansiIns = instansiService.getInstansiList();
		
		model.addAttribute("employees", listPegawai);
		model.addAttribute("provinces", provinces);
		model.addAttribute("positions", positions);
		model.addAttribute("instansiIns", instansiIns);
		model.addAttribute("id_Provinsi", id_Provinsi);
		model.addAttribute("id_Instansi", id_Instansi);
		model.addAttribute("id_Jabatan", id_Jabatan);
		
		return "cari-pegawai";
	}
}
