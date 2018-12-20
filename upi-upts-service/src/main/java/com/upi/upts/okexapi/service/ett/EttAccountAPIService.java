package com.upi.upts.okexapi.service.ett;

import java.util.List;

import com.upi.upts.okexapi.bean.ett.result.CursorPager;
import com.upi.upts.okexapi.bean.ett.result.EttAccount;
import com.upi.upts.okexapi.bean.ett.result.EttLedger;

/**
 * @author chuping.cui
 * @date 2018/7/4
 */
public interface EttAccountAPIService {

    /**
     * Get all ett account list
     *
     * @return account info list
     */
    List<EttAccount> getAccount();

    /**
     * Get ett currency account
     *
     * @param currency currency code
     * @return account info
     */
    EttAccount getAccount(String currency);

    /**
     * Get ett account ledger list
     *
     * @param currency currency code
     * @param before   before and after cursors are available via response headers OK-BEFORE and OK-AFTER. Your requests should use these cursor values when making requests for pages after the initial
     *                 request. {@link CursorPager}
     * @param after    before and after cursors are available via response headers OK-BEFORE and OK-AFTER. Your requests should use these cursor values when making requests for pages after the initial
     *                 request. {@link CursorPager}
     * @param limit    number of results per request.
     * @return
     */
    CursorPager<EttLedger> getLedger(String currency, String before, String after, int limit);

}
