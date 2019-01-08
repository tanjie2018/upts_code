package com.upi.upts.service.impl;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.upi.upts.common.CommonVO;
import com.upi.upts.common.UiisConstant;
import com.upi.upts.model.Candle;
import com.upi.upts.model.Param;
import com.upi.upts.model.Trade;
import com.upi.upts.repository.TradeRepository;
import com.upi.upts.service.TradeService;
import com.upi.upts.util.StringUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Scope("prototype")
@Transactional
public class TradeServiceImpl implements TradeService {

	@Autowired
	private TradeRepository tradeRepository;
	@Autowired
	private ParamServiceImpl paramServiceImpl;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public void insert(Trade trade) {
		tradeRepository.save(trade);
	}

	@Override
	public void update(Trade trade) {
		try {
			entityManager.merge(trade);
		} catch (Exception e) {
			log.error("交易信息更新失败",e);
		}
	}

	@Override
	public List<Trade> getUndoTrades(String flag) {
		return tradeRepository.findTradesByFlag(flag);
	}
	
	@Override
	public List<Trade> getDateData(String date, String flag) {
		return tradeRepository.findDateData(date, flag);
	}
	/**
	 * 建仓
	 * @param candle
	 * @param level
	 */
	public void openOrder(Candle candle,String level) {
		Trade trade = new Trade();
		trade.setOrderId(level+StringUtil.getNowFormatDate(UiisConstant.UPI_MILLISECOND_FORMAT));
//		trade.setBtime(StringUtil.getNowFormatDate(UiisConstant.UPI_TIME_FORMAT));
		trade.setBtime(candle.getTime());
		trade.setBprice(candle.getClose());
		
		trade.setStrategy(level);
		trade.setSize((int) (UiisConstant.ORDER_AMOUNT/trade.getBprice()));
		trade.setBsum(trade.getBprice()*trade.getSize());
		trade.setFlag("0");
		if(UiisConstant.UP.equals(candle.getProp())) {
			trade.setSider(UiisConstant.BUY);
			BlockingQueue<Trade> tQueue = CommonVO.bTradeMap.get(level);
			if(StringUtil.isEmpty(tQueue)) {
				tQueue = new LinkedBlockingQueue<>();
			}
			log.info("买多处理，队列值为："+tQueue.size()+",candle:"+candle);
			if(tQueue.size()!=0) {
				log.error("队列中已存在订单");
				return;
			}else {
				try {
					tQueue.put(trade);
				} catch (InterruptedException e) {
					log.error("订单生成失败",e);
				}
			}
			CommonVO.bTradeMap.put(level, tQueue);
		}else {
			trade.setSider(UiisConstant.SELL);
			BlockingQueue<Trade> tQueue = CommonVO.sTradeMap.get(level);
			if(StringUtil.isEmpty(tQueue)) {
				tQueue = new LinkedBlockingQueue<>();
			}
			log.info("买空处理，队列值为："+tQueue.size()+",candle:"+candle);
			if(tQueue.size()!=0) {
				log.error("队列中已存在订单");
				return;
			}else {
				try {
					tQueue.put(trade);
				} catch (InterruptedException e) {
					log.error("订单生成失败",e);
				}
			}
			CommonVO.sTradeMap.put(level, tQueue);
		}
		update(trade);
		log.info("交易信息入库："+trade);
	}
	
	public void closeOrder(Candle candle,String level) throws CloneNotSupportedException {
		Trade trade = null;
		if(UiisConstant.UP.equals(candle.getProp())) {
			BlockingQueue<Trade> tQueue = CommonVO.sTradeMap.get(level);
			if(!StringUtil.isEmpty(tQueue)&&tQueue.size()>0) {
				log.info("卖空处理，队列值为："+tQueue.size());
				try {
					trade = tQueue.take();
				} catch (InterruptedException e) {
					log.error("订单获取失败",e);;
				}
			}
		}else {
			BlockingQueue<Trade> tQueue = CommonVO.bTradeMap.get(level);
			if(!StringUtil.isEmpty(tQueue)&&tQueue.size()>0) {
				log.info("卖多处理，队列值为："+tQueue.size());
				try {
					trade = tQueue.take();
				} catch (InterruptedException e) {
					log.error("订单获取失败",e);
				}
			}
		}
		if(!StringUtil.isEmpty(trade)) {
			trade.setSprice(candle.getClose());
			trade.setSsum(candle.getClose()*trade.getSize());
			trade.setStime(candle.getTime());
			if(candle.getProp().equals(UiisConstant.UP)) {
				trade.setProfit(BigDecimal.valueOf(trade.getBsum()-trade.getSsum()).setScale(3, BigDecimal.ROUND_HALF_UP));
				trade.setRang(BigDecimal.valueOf((trade.getBprice()-trade.getSprice())/trade.getBprice()*100).setScale(3, BigDecimal.ROUND_HALF_UP));
			}else {
				trade.setProfit(BigDecimal.valueOf(trade.getSsum()-trade.getBsum()).setScale(3, BigDecimal.ROUND_HALF_UP));
				trade.setRang(BigDecimal.valueOf((trade.getSprice()-trade.getBprice())/trade.getBprice()*100).setScale(3, BigDecimal.ROUND_HALF_UP));
			}
			trade.setFlag("1");
			update(trade);
		}
	}

	/**
	 * 不止损策略开仓（前后%0.6不得出现同向单）
	 * @param candle
	 * @param level
	 */
	public void openNlOrder(Candle candle,String level) {
		Trade trade = new Trade();
		trade.setOrderId(level+StringUtil.getNowFormatDate(UiisConstant.UPI_MILLISECOND_FORMAT));
		trade.setBtime(candle.getTime());
		trade.setBprice(candle.getClose());
		
		trade.setStrategy(level);
		trade.setSize((int) (UiisConstant.ORDER_AMOUNT/trade.getBprice()));
		trade.setBsum(trade.getBprice()*trade.getSize());
		trade.setFlag("0");
		if(UiisConstant.UP.equals(candle.getProp())) {
			trade.setSider(UiisConstant.BUY);
			LinkedList<Trade> tLIst = CommonVO.nbTradeMap.get(level);
			if(StringUtil.isEmpty(tLIst)) {
				tLIst = new LinkedList<>();
			}
			log.info("N买多处理，队列值为："+tLIst.size()+",candle:"+candle);
			if(tLIst.size()>0) {
				Trade last = tLIst.getLast();
				if((last.getBprice()-trade.getBprice())/last.getBprice()<UiisConstant.DEEP) {//last.getBprice()<trade.getBprice()||
					log.info("存在更低价格的买多单，last:"+last);
					return;
				}else {
					tLIst.add(trade);
				}
			}else {
				tLIst.add(trade);
			}
			CommonVO.nbTradeMap.put(level, tLIst);
		}else {
			trade.setSider(UiisConstant.SELL);
			LinkedList<Trade> tList = CommonVO.nsTradeMap.get(level);
			if(StringUtil.isEmpty(tList)) {
				tList = new LinkedList<>();
			}
			log.info("买空处理，队列值为："+tList.size()+",candle:"+candle);
			if(tList.size()>0) {
				Trade last = tList.getLast();
				if((trade.getBprice()-last.getBprice())/last.getBprice()<UiisConstant.DEEP) {//last.getBprice()>trade.getBprice()||
					log.info("存在更低价格的买多单，last:"+last);
					return;
				}else {
					tList.add(trade);
				}
			}else {
				tList.add(trade);
			}
			CommonVO.nsTradeMap.put(level, tList);
		}
		update(trade);
		log.info("交易信息入库："+trade);
	}
	
	/**
	 * 不止损策略平仓
	 * @param candle
	 * @param level
	 * @throws CloneNotSupportedException
	 */
	public void closeNlOrder(Candle candle,String level) throws CloneNotSupportedException {
		Trade trade = null;
		LinkedList<Trade> tList = null;
		if(UiisConstant.UP.equals(candle.getProp())) {
			tList = CommonVO.nsTradeMap.get(level);
			if(!StringUtil.isEmpty(tList)&&tList.size()>0) {
				log.info("N卖空处理，队列深度为："+tList.size());
				trade = tList.getLast();
				if("2".equals(trade.getFlag())) {
					return;
				}
				if(trade.getBprice()<candle.getClose()) {
					trade.setFlag("2");
					update(trade);
					return;
				}
			}
		}else {
			tList = CommonVO.nbTradeMap.get(level);
			if(!StringUtil.isEmpty(tList)&&tList.size()>0) {
				log.info("N卖多处理，队列深度为："+tList.size());
				trade = tList.getLast();
				if("2".equals(trade.getFlag())) {
					return;
				}
				if(trade.getBprice()>candle.getClose()) {
					trade.setFlag("2");
					update(trade);
					return;
				}
			}
		}
		if(!StringUtil.isEmpty(trade)) {
			trade.setSprice(candle.getClose());
			trade.setSsum(candle.getClose()*trade.getSize());
			trade.setStime(candle.getTime());
			if(candle.getProp().equals(UiisConstant.UP)) {
				trade.setProfit(BigDecimal.valueOf(trade.getBsum()-trade.getSsum()).setScale(3, BigDecimal.ROUND_HALF_UP));
				trade.setRang(BigDecimal.valueOf((trade.getBprice()-trade.getSprice())/trade.getBprice()*100).setScale(3, BigDecimal.ROUND_HALF_UP));
			}else {
				trade.setProfit(BigDecimal.valueOf(trade.getSsum()-trade.getBsum()).setScale(3, BigDecimal.ROUND_HALF_UP));
				trade.setRang(BigDecimal.valueOf((trade.getSprice()-trade.getBprice())/trade.getBprice()*100).setScale(3, BigDecimal.ROUND_HALF_UP));
			}
			trade.setFlag("1");
			update(trade);
			tList.removeLast();
		}
	}
	
	/**
	 * 未盈利单平仓
	 * @param candle
	 * @param level
	 */
	public void closeProfitOrder(Candle candle,String level) {
		Trade iTrade = null;
		if(UiisConstant.UP.equals(candle.getProp())) {
			LinkedList<Trade> tList = CommonVO.nsTradeMap.get(level);
			if(!StringUtil.isEmpty(tList)&&tList.size()>0) {
				//记录队列深度
				queueSizeWrite(level);
				log.info("N卖空处理，队列深度为："+tList.size());
				for(int i=tList.size()-1;i>=0;i--) {
					iTrade = tList.get(i);
					if("2".equals(iTrade.getFlag())) {
						if((candle.getHigh()-iTrade.getBprice())/iTrade.getBprice()>=UiisConstant.FORCE_PERCENT) {
							dealForceOrder(iTrade,candle);
							log.info("爆仓单平仓："+iTrade);
							tList.remove(i);
						}else if(iTrade.getBprice()>candle.getClose()) {
							dealSellOrder(iTrade,candle);
							log.info("完成未盈利单平仓："+iTrade);
							tList.remove(i);
						}
					}
				}
			}
		}else {
			LinkedList<Trade> tList = CommonVO.nbTradeMap.get(level);
			if(!StringUtil.isEmpty(tList)&&tList.size()>0) {
				//记录队列深度
				queueSizeWrite(level);
				log.info("N卖多处理，队列深度为："+tList.size());
				for(int i=tList.size()-1;i>=0;i--) {
					iTrade = tList.get(i);
					if("2".equals(iTrade.getFlag())) {
						if((candle.getLow()-iTrade.getBprice())/iTrade.getBprice()<-UiisConstant.FORCE_PERCENT) {
							dealForceOrder(iTrade,candle);
							log.info("爆仓单平仓："+iTrade);
							tList.remove(iTrade);
						}else if(iTrade.getBprice()<candle.getClose()) {
							dealSellOrder(iTrade,candle);
							log.info("完成未盈利单平仓："+iTrade);
							tList.remove(iTrade);
						}
					}
				}
			}
		}
	}
	
	/**
	 * 强制平仓入库
	 * @param trade
	 * @param candle
	 */
	private void dealSellOrder(Trade trade,Candle candle) {
		trade.setSprice(candle.getClose());
		trade.setSsum(candle.getClose()*trade.getSize());
		trade.setStime(candle.getTime());
		if(candle.getProp().equals(UiisConstant.UP)) {
			trade.setProfit(BigDecimal.valueOf(trade.getBsum()-trade.getSsum()).setScale(3, BigDecimal.ROUND_HALF_UP));
			trade.setRang(BigDecimal.valueOf((trade.getBprice()-trade.getSprice())/trade.getBprice()*100).setScale(3, BigDecimal.ROUND_HALF_UP));
		}else {
			trade.setProfit(BigDecimal.valueOf(trade.getSsum()-trade.getBsum()).setScale(3, BigDecimal.ROUND_HALF_UP));
			trade.setRang(BigDecimal.valueOf((trade.getSprice()-trade.getBprice())/trade.getBprice()*100).setScale(3, BigDecimal.ROUND_HALF_UP));
		}
		trade.setFlag("3");
		try {
			update(trade.clone());
		} catch (CloneNotSupportedException e) {
			log.error("对象克隆失败",e);
		}
		log.info("强平完成库表更新："+trade);
	}
	
	/**
	 * 扫描爆仓单实现爆仓处理和深度扫描
	 * @param trade
	 * @param candle
	 */
	public void dealForceOrder(Candle candle,String level) {
		Trade iTrade = null;
		
		if(UiisConstant.UP.equals(candle.getProp())) {
			LinkedList<Trade> tList = CommonVO.nsTradeMap.get(level);
			if(!StringUtil.isEmpty(tList)&&tList.size()>0) {
				for(int i=tList.size()-1;i>=0;i--) {
					iTrade = tList.get(i);
					if("2".equals(iTrade.getFlag())) {
						if((candle.getHigh()-iTrade.getBprice())/iTrade.getBprice()>=UiisConstant.FORCE_PERCENT) {
							dealForceOrder(iTrade,candle);
							log.info("爆仓单平仓："+iTrade);
							tList.remove(i);
						}
					}
				}
			}
		}else {
			LinkedList<Trade> tList = CommonVO.nbTradeMap.get(level);
			if(!StringUtil.isEmpty(tList)&&tList.size()>0) {
				log.info("N卖多处理，队列深度为："+tList.size());
				for(int i=tList.size()-1;i>=0;i--) {
					iTrade = tList.get(i);
					if("2".equals(iTrade.getFlag())) {
						if((candle.getLow()-iTrade.getBprice())/iTrade.getBprice()<-UiisConstant.FORCE_PERCENT) {
							dealForceOrder(iTrade,candle);
							log.info("爆仓单平仓："+iTrade);
							tList.remove(iTrade);
						}
					}
				}
			}
		}
	}
	
	/**
	 * 强制平仓入库
	 * @param trade
	 * @param candle
	 */
	private void dealForceOrder(Trade trade,Candle candle) {
		trade.setStime(candle.getTime());
		if(candle.getProp().equals(UiisConstant.UP)) {
			trade.setSprice(trade.getBprice()*(1+UiisConstant.FORCE_PERCENT));
			trade.setSsum(trade.getBsum()*(1+UiisConstant.FORCE_PERCENT));
			trade.setProfit(BigDecimal.valueOf(trade.getBsum()-trade.getSsum()).setScale(3, BigDecimal.ROUND_HALF_UP));
			trade.setRang(BigDecimal.valueOf(-UiisConstant.FORCE_PERCENT*100).setScale(3, BigDecimal.ROUND_HALF_UP));
		}else {
			trade.setSprice(trade.getBprice()*(1-UiisConstant.FORCE_PERCENT));
			trade.setSsum(trade.getBsum()*(1-UiisConstant.FORCE_PERCENT));
			trade.setProfit(BigDecimal.valueOf(trade.getSsum()-trade.getBsum()).setScale(3, BigDecimal.ROUND_HALF_UP));
			trade.setRang(BigDecimal.valueOf(-UiisConstant.FORCE_PERCENT*100).setScale(3, BigDecimal.ROUND_HALF_UP));
		}
		trade.setFlag("4");
		try {
			update(trade.clone());
			addForceNum();
		} catch (CloneNotSupportedException e) {
			log.error("对象克隆失败",e);
		}
		log.info("爆仓单更新："+trade);
	}
	
	/**
	 * 记录队列最大深度值
	 * @param level
	 */
	private void queueSizeWrite(String level) {
		//记录队列深度，供资金分批数量参考
		LinkedList<Trade> btList = CommonVO.nbTradeMap.get(level);
		LinkedList<Trade> stList = CommonVO.nsTradeMap.get(level);
		Param bq = CommonVO.paramMap.get(UiisConstant.BUY_QUEURE_SIZE);
		Param sq = CommonVO.paramMap.get(UiisConstant.SELL_QUEURE_SIZE);
		Param bsq = CommonVO.paramMap.get(UiisConstant.QUEURE_SIZE_MAX);
		if(StringUtil.isEmpty(btList,stList,bq,sq,bsq)) {
			log.info("存在空对象，暂时无法记录队列深度");
			return;
		}
		if(Integer.valueOf(bq.getParamValue())<btList.size()) {
			bq.setParamValue(String.valueOf(btList.size()));
			paramServiceImpl.update(bq);
		}
		if(Integer.valueOf(sq.getParamValue())<stList.size()) {
			sq.setParamValue(String.valueOf(stList.size()));
			paramServiceImpl.update(sq);
		}
		if(Integer.valueOf(bsq.getParamValue())<(btList.size()+stList.size())) {
			bsq.setParamValue(String.valueOf(btList.size()+stList.size()));
			paramServiceImpl.update(bsq);
		}
	}
	
	/**
	 * 新增爆仓单数量
	 */
	private void addForceNum() {
		Param param = CommonVO.paramMap.get(UiisConstant.FORCE_ORDER_NO);
		if(!StringUtil.isEmpty(param)) {
			param.setParamValue(String.valueOf(Integer.valueOf(param.getParamValue())+1));
			paramServiceImpl.update(param);
			log.info("强制平仓单更新完毕");
		}
	}

	
}
