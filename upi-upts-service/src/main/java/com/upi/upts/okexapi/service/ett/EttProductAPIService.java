package com.upi.upts.okexapi.service.ett;

import java.util.List;

import com.upi.upts.okexapi.bean.ett.result.EttConstituentsResult;
import com.upi.upts.okexapi.bean.ett.result.EttSettlementDefinePrice;

/**
 * @author chuping.cui
 * @date 2018/7/4
 */
public interface EttProductAPIService {

    /**
     * Get ett constituents
     *
     * @param ett ett name
     * @return constituents
     */
    EttConstituentsResult getConstituents(String ett);

    /**
     * Get ett settlement plan define price
     *
     * @param ett ett name
     * @return settlement plan define price list
     */
    List<EttSettlementDefinePrice> getDefinePrice(String ett);

}
