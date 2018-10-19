package com.apap.tugas1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.tugas1.model.PositionModel;

@Repository
public interface PositionDb extends JpaRepository<PositionModel, Long> {
	PositionModel findById(long id);
}
