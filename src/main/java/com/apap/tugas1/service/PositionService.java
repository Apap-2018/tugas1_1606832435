package com.apap.tugas1.service;

import com.apap.tugas1.model.PositionModel;
import com.apap.tugas1.repository.PositionDb;

public interface PositionService {
	PositionModel getPositionDetailById(long id);
	void addPosition(PositionModel jabatan);
	void updatePosition(PositionModel jabatan);
	void deletePosition(Long id);
	PositionDb getJabatanDb();
}
