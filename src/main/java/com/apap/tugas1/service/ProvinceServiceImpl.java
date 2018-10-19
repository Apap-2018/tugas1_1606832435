package com.apap.tugas1.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.ProvinceModel;
import com.apap.tugas1.repository.ProvinceDb;

@Service
@Transactional
public class ProvinceServiceImpl implements ProvinceService {
	@Autowired
	private ProvinceDb provinsiDb;
	
	@Override
	public void addProvince(ProvinceModel province) {
		provinsiDb.save(province);
	}

	@Override
	public List<ProvinceModel> getProvinceList() {
		return provinsiDb.findAll();
	}
	
	@Override
	public ProvinceDb getProvinsiDb() {
		return provinsiDb;
	}
}
