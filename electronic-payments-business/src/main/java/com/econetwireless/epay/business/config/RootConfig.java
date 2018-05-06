package com.econetwireless.epay.business.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by tnyamakura on 17/3/2017.
 */
@Configuration
@Import({EpayBusinessConfig.class, IntegrationsConfig.class})
public class RootConfig {

}
