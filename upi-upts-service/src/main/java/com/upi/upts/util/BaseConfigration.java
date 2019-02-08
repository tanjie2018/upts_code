package com.upi.upts.util;

import com.upi.upts.okexapi.config.APIConfiguration;
import com.upi.upts.okexapi.enums.I18nEnum;

public class BaseConfigration {

	public static APIConfiguration config() {
        final APIConfiguration config = new APIConfiguration();
        config.setEndpoint("https://www.okex.com/");
        config.setApiKey("97902cb4-162a-46bd-a1c7-9cdb9727701f");
        config.setSecretKey("FA836B15DCDD97F76A8597DDF3CF594B");

        config.setPassphrase("ActionForTingTing");
        config.setI18n(I18nEnum.SIMPLIFIED_CHINESE);
        config.setPrint(true);
        //失败重连设置为false
        config.setRetryOnConnectionFailure(false);

        return config;
    }
	
}
