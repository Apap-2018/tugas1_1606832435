package com.apap.tugas1.service;

import java.util.List;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.repository.InstansiDb;

public interface InstansiService {
	InstansiModel getInstansiDetailById(long id);
	void addInstansi(InstansiModel instansi);
	List<InstansiModel> getInstansiList();
	InstansiDb getInstansiDb();
}
