package com.upi.upts.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.upi.upts.model.Trade;

@Transactional
@Repository
public interface TradeRepository extends JpaRepository<Trade, String> {

	List<Trade> findTradesByFlag(String flag);
	@Query(value="select * from upts_trade_info t where (t.stime like CONCAT(?1,'%')  or ?1 is null) and t.flag=?2",nativeQuery = true)
	List<Trade> findDateData(String date,String flag);
//	@Query("select * from upts_trade_info t where (t.stime like :date%  or :date is null) and t.flag=:flag")
//	List<Trade> findDateData(@Param("date") String date,@Param("flag") String flag);
	/**
	 * 传入date不为null时，获取对应日期的盈利，为null时获取当前盈利
	 * @param date
	 * @return
	 */
	@Query(value="select sum(profit) from upts_trade_info t where (t.stime like CONCAT(?1,'%')  or ?1 is null)",nativeQuery = true)
	String getProfitDate(String date);
}
