package com.upi.upts.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.upi.upts.model.Candle;
import com.upi.upts.model.Dcandle;

@Transactional
@Repository
public interface DcandleRepository extends JpaRepository<Dcandle, String> {
	
}