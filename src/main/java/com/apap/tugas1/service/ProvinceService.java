package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import com.apap.tugas1.model.ProvinceModel;
import com.apap.tugas1.repository.ProvinceDb;

public interface ProvinceService {
	void addProvince(ProvinceModel province);
	List<ProvinceModel> getProvinceList();
	ProvinceDb getProvinsiDb();
	Optional<ProvinceModel> getProvinsiById(Long id);
}
