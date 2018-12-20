package com.upi.upts.okexapi.service.futures.impl;

import com.upi.upts.okexapi.bean.futures.result.ExchangeRate;
import com.upi.upts.okexapi.bean.futures.result.ServerTime;
import com.upi.upts.okexapi.client.APIClient;
import com.upi.upts.okexapi.config.APIConfiguration;
import com.upi.upts.okexapi.service.GeneralAPIService;

/**
 * General api
 *
 * @author Tony Tian
 * @version 1.0.0
 * @date 10/03/2018 19:28
 */
public class GeneralAPIServiceImpl implements GeneralAPIService {

    private APIClient client;
    private FuturesMarketAPI api;

    public GeneralAPIServiceImpl(APIConfiguration config) {
        this.client = new APIClient(config);
        this.api = client.createService(FuturesMarketAPI.class);
    }

    @Override
    public ServerTime getServerTime() {
        return this.client.executeSync(this.api.getServerTime());
    }

    @Override
    public ExchangeRate getExchangeRate() {
        return this.client.executeSync(this.api.getExchangeRate());
    }
}
