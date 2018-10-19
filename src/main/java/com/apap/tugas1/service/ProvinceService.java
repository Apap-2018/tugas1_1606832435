package com.apap.tugas1.service;

import java.util.List;

import com.apap.tugas1.model.ProvinceModel;
import com.apap.tugas1.repository.ProvinceDb;

public interface ProvinceService {
	void addProvince(ProvinceModel province);
	List<ProvinceModel> getProvinceList();
	ProvinceDb getProvinsiDb();
}
