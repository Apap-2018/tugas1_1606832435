package com.apap.tugas1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PositionModel;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.PositionService;

@Controller
public class HomeController {
	@Autowired
	private PositionService positionService;
	
	@Autowired
	private InstansiService instansiService;
	
	@RequestMapping("/")
	private String home (Model model) {
		List<PositionModel> positions = positionService.getJabatanDb().findAll();
		List<InstansiModel> instansiIns = instansiService.getInstansiDb().findAll();
		
		model.addAttribute("positions", positions);
		model.addAttribute("allInstansi", instansiIns);
		
		return "home";
	}
}
