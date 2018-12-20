package com.upi.upts.util;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.upi.upts.common.LocalConfig;


/**
 * 线程处理工具类
 * @author tanjie
 *
 */
@Component
public class ThreadUtil {
	private static Logger logger = LoggerFactory.getLogger(ThreadUtil.class);
	@Autowired
	private LocalConfig localConfig;
	
	public static ThreadUtil threadUtil;
	
	@PostConstruct
	private void init() {
		this.threadUtil = this;
	}
	
	private static ThreadPoolExecutor threadPoolExecutor = null;
	
	public static ThreadPoolExecutor getThreadPoolExecutor() {
		if(StringUtil.isEmpty(threadPoolExecutor)) {
			threadPoolExecutor = getThreadPoolExecutorInstence();
		}
		return threadPoolExecutor;
	}
	
	private static synchronized ThreadPoolExecutor getThreadPoolExecutorInstence() {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(threadUtil.localConfig.getCorePoolSize(), 
				threadUtil.localConfig.getMaxPoolSize(), threadUtil.localConfig.getKeepAliveTime(),TimeUnit.SECONDS,
				new LinkedBlockingDeque<Runnable>(threadUtil.localConfig.getCapacity()));
		logger.info("threadUtil.localConfig.CorePoolSize:"+threadUtil.localConfig.getCorePoolSize());
		return threadPoolExecutor;
	}
}
