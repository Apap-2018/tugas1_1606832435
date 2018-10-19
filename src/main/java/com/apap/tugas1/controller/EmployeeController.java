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
}
