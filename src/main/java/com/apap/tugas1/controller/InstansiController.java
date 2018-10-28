package com.apap.tugas1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.ProvinceModel;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.ProvinceService;

@Controller
public class InstansiController {
	@Autowired
	private ProvinceService provinceService;
	
	@RequestMapping(value = "/instansi/getInstansiFromProvinsi", method = RequestMethod.GET)
	public @ResponseBody List<InstansiModel> getInstansi(@RequestParam (value = "provinsiId", required = true) Long provinsiId) {
	    ProvinceModel provinsi = provinceService.getProvinsiById(provinsiId).get();
	    return provinsi.getInstansiList();
	}
}
