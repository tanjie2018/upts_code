package com.upi.upts.okexapi.service.ett.impl;

import com.upi.upts.okexapi.bean.ett.result.EttConstituentsResult;
import com.upi.upts.okexapi.bean.ett.result.EttSettlementDefinePrice;
import com.upi.upts.okexapi.client.APIClient;
import com.upi.upts.okexapi.config.APIConfiguration;
import com.upi.upts.okexapi.service.ett.EttProductAPIService;

import java.util.List;

/**
 * @author chuping.cui
 * @date 2018/7/5
 */
public class EttProductAPIServiceImpl implements EttProductAPIService {

    private final APIClient client;
    private final EttProductAPI api;

    public EttProductAPIServiceImpl(APIConfiguration config) {
        this.client = new APIClient(config);
        this.api = this.client.createService(EttProductAPI.class);
    }

    @Override
    public EttConstituentsResult getConstituents(String ett) {
        return this.client.executeSync(this.api.getConstituents(ett));
    }

    @Override
    public List<EttSettlementDefinePrice> getDefinePrice(String ett) {
        return this.client.executeSync(this.api.getDefinePrice(ett));
    }
}