package com.econetwireless.epay.business.utils;

import com.econetwireless.epay.business.config.EpayBusinessConfig;
import com.econetwireless.epay.business.services.api.CreditsService;
import com.econetwireless.epay.business.services.api.EnquiriesService;
import com.econetwireless.epay.business.services.api.PartnerCodeValidator;
import com.econetwireless.epay.business.services.api.ReportingService;
import org.junit.After;
import org.junit.Test;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import static org.junit.Assert.assertNotNull;

public class EpayBusinessConfigBeanCreationTest {

    private AnnotationConfigWebApplicationContext context;

    @After
    public void close() {
        if (this.context != null) {
            this.context.close();
        }
    }

    @Test
    public void testCreditsServiceBeanCreationIsNotNull() {
        this.context = new AnnotationConfigWebApplicationContext();
        this.context.setServletContext(new MockServletContext());
        this.context.register(EpayBusinessConfig.class);
        this.context.refresh();
        CreditsService creditsService = this.context.getBean(CreditsService.class);
        assertNotNull(creditsService);

    }

    @Test
    public void testReportingServiceBeanCreationIsNotNull() {
        this.context = new AnnotationConfigWebApplicationContext();
        this.context.setServletContext(new MockServletContext());
        this.context.register(EpayBusinessConfig.class);
        this.context.refresh();
        ReportingService reportingService = this.context.getBean(ReportingService.class);
        assertNotNull(reportingService);

    }

    @Test
    public void testPartnerCodeValidatorBeanCreationIsNotNull() {
        this.context = new AnnotationConfigWebApplicationContext();
        this.context.setServletContext(new MockServletContext());
        this.context.register(EpayBusinessConfig.class);
        this.context.refresh();
        PartnerCodeValidator partnerCodeValidator = this.context.getBean(PartnerCodeValidator.class);
        assertNotNull(partnerCodeValidator);

    }

    @Test
    public void testEnquiriesServiceBeanCreationIsNotNull() {
        this.context = new AnnotationConfigWebApplicationContext();
        this.context.setServletContext(new MockServletContext());
        this.context.register(EpayBusinessConfig.class);
        this.context.refresh();
        EnquiriesService enquiriesService = this.context.getBean(EnquiriesService.class);
        assertNotNull(enquiriesService);

    }

}
