package com.econetwireless.epay.business.utils;

import com.econetwireless.epay.business.config.EpayBusinessConfig;
import com.econetwireless.epay.business.config.IntegrationsConfig;
import com.econetwireless.epay.business.config.RootConfig;
import org.junit.After;
import org.junit.Test;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import static org.junit.Assert.assertNotNull;

public class RootConfigBeanCreationTest {

    private AnnotationConfigWebApplicationContext context;

    @After
    public void close() {
        if (this.context != null) {
            this.context.close();
        }
    }

    @Test
    public void testEpayBusinessConfigCreationIsNotNull() {
        this.context = new AnnotationConfigWebApplicationContext();
        this.context.setServletContext(new MockServletContext());
        this.context.register(RootConfig.class);
        this.context.refresh();
        EpayBusinessConfig creditsService = this.context.getBean(EpayBusinessConfig.class);
        assertNotNull(creditsService);

    }

    @Test
    public void testIntegrationsConfigCreationIsNotNull() {
        this.context = new AnnotationConfigWebApplicationContext();
        this.context.setServletContext(new MockServletContext());
        this.context.register(RootConfig.class);
        this.context.refresh();
        IntegrationsConfig integrationsConfig = this.context.getBean(IntegrationsConfig.class);
        assertNotNull(integrationsConfig);

    }

}
