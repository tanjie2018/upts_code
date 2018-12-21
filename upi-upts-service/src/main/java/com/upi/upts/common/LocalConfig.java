package com.upi.upts.common;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.upi.upts.util.StringUtil;


/**
 * 本地参数缓存
 * @author tanjie
 *
 */
@Component
public class LocalConfig {
	
	private static final Logger log = LoggerFactory.getLogger(LocalConfig.class);

	public static LocalConfig localConfig;

	@Value("${proxy.ip:null}")
	private String proxyHost;
	@Value("${proxy.port:0}")
	private int proxyPort;
	@Value("${uselocaltime:true}")
	private boolean uselocaltime;
	
	@Value("${poll.maxSize:200}")
	private int maxPoolSize;
	@Value("${poll.coreSize:30}")
	private int corePoolSize;
	@Value("${poll.aliveTime:600}")
	private long keepAliveTime;
	@Value("${poll.capacity:1000}")
	private int capacity;
	
	@PostConstruct
	private void init() {
		localConfig = this;
	}
	
	public static String getProxyHost() {
//		log.info("localConfig.proxyHost:"+localConfig.proxyHost);
		if(StringUtil.isEmpty(localConfig)) {
			return "172.19.66.204";
		}
		return localConfig.proxyHost;
	}
	
	public static int getProxyPort() {
//		log.info("localConfig.proxyPort:"+localConfig.proxyPort);
		if(StringUtil.isEmpty(localConfig)) {
			return 9080;
		}
		return localConfig.proxyPort;
	}
	
	public static boolean getIsUseLocalTime() {
//		log.info("localConfig.proxyPort:"+localConfig.proxyPort);
		if(StringUtil.isEmpty(localConfig)) {
			return false;
		}
		return localConfig.uselocaltime;
	}
	
	public int getMaxPoolSize() {
		return maxPoolSize;
	}

	public int getCorePoolSize() {
		return corePoolSize;
	}

	public long getKeepAliveTime() {
		return keepAliveTime;
	}

	public int getCapacity() {
		return capacity;
	}
}