package com.apap.tugas1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.tugas1.model.EmpPosModel;

@Repository
public interface EmpPosDB extends JpaRepository<EmpPosModel, Long> {
	EmpPosModel findById(long id);
}

