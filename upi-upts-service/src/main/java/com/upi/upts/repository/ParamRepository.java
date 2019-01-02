package com.upi.upts.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.upi.upts.model.Param;

@Transactional
@Repository
public interface ParamRepository extends JpaRepository<Param, String> {

}
