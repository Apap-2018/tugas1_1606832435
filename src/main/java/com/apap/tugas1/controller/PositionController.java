package com.apap.tugas1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.PositionModel;
import com.apap.tugas1.service.PositionService;

@Controller
public class PositionController {
	
	// Temporary Id untuk Jabatan yang sedang dilihat
	private Long idCurrEmployee;
	
	@Autowired
	private PositionService jabatanService;
	
	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.GET)
	private String add(Model model) {
		PositionModel jabatanBaru = new PositionModel();
		model.addAttribute("jabatan", jabatanBaru);
		return "tambah-jabatan";
	}
	
	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.POST)
	private String addJabatanSubmit(@ModelAttribute PositionModel jabatan, Model model) {
		jabatanService.addPosition(jabatan);
		
		String notification = "Sukses! Jabatan Berhasil Ditambahkan";
		model.addAttribute("jabatan", jabatan);
		model.addAttribute("notification", notification);
		
		return "tambah-jabatan";
	}
	
	@RequestMapping(value = "/jabatan/view")
	private String viewJabatan(@RequestParam("idJabatan") Long idJabatan, Model model) {
		PositionModel jabatan = jabatanService.getPositionDetailById(idJabatan);
		idCurrEmployee = idJabatan;		
		model.addAttribute("jabatan", jabatan);
		return "lihat-jabatan";
	}
	
	@RequestMapping(value = "jabatan/ubah", method = RequestMethod.GET)
	 private String updateJabatan(@RequestParam("idJabatan") Long idJabatan, Model model) {
		 PositionModel jabatan = jabatanService.getPositionDetailById(idJabatan);
		 model.addAttribute("jabatan", jabatan);
		 return "update-jabatan";
	 }

	 @RequestMapping(value = "jabatan/ubah", method = RequestMethod.POST)
	 private String updateJabatanSubmit(@ModelAttribute PositionModel jabatan, Model model) {
		 jabatan.setId(idCurrEmployee);
		 jabatanService.updatePosition(jabatan);
		 model.addAttribute("notification", "Data Berhasil Diubah");
		 model.addAttribute("jabatan", jabatan);
		 
	     return "update-jabatan";
	 }

	 @RequestMapping(value = "jabatan/hapus", method = RequestMethod.POST) 
	 private String deleteJabatan(){
		 jabatanService.deletePosition(idCurrEmployee);
		 
		 return "hapus-berhasil";
	 }
	 
	 @RequestMapping(value = "jabatan/viewall", method = RequestMethod.GET)
	 private String viewAll(Model model) {
		 List<PositionModel> positions = jabatanService.getJabatanDb().findAll(); 
		 	
		 model.addAttribute("positions", positions);
		 return "lihat-semua-jabatan";
	 }
	
}
