package com.econetwireless.epay.business.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

/**
 * Created by tnyamakura on 17/3/2017.
 */
@Configuration
@Import({EpayBusinessConfig.class, IntegrationsConfig.class})
public class RootConfig {

}
