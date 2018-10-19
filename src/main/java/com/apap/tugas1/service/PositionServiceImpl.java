package com.apap.tugas1.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.PositionModel;
import com.apap.tugas1.repository.PositionDb;

@Service
@Transactional
public class PositionServiceImpl implements PositionService {
	@Autowired
	private PositionDb positionDb;
	
	@Override
	public PositionModel getPositionDetailById(long id) {
		return positionDb.getOne(id);
	}
	
	@Override
	public void addPosition(PositionModel employee) {
		positionDb.save(employee);
	}

	@Override
	public void updatePosition(PositionModel employee) {
		PositionModel updatedPosition = positionDb.getOne(employee.getId());
		
		updatedPosition.setNama(employee.getNama());
		updatedPosition.setDeskripsi(employee.getDeskripsi());
		updatedPosition.setGajiPokok(employee.getGajiPokok());
		
        positionDb.save(employee);
	}

	@Override
	public void deletePosition(Long id) {
		positionDb.deleteById(id);	
	}
	
	@Override
	public PositionDb getJabatanDb() {
		return positionDb;
	}
}
