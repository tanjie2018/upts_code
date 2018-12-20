package com.upi.upts.okexapi.service.ett.impl;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

import com.upi.upts.okexapi.bean.ett.result.EttConstituentsResult;
import com.upi.upts.okexapi.bean.ett.result.EttSettlementDefinePrice;

/**
 * @author chuping.cui
 * @date 2018/7/5
 */
interface EttProductAPI {

    @GET("/api/ett/v3/constituents/{ett}")
    Call<EttConstituentsResult> getConstituents(@Path("ett") String ett);

    @GET("/api/ett/v3/define-price/{ett}")
    Call<List<EttSettlementDefinePrice>> getDefinePrice(@Path("ett") String ett);

}
