package com.econetwireless.epay.business.services.impl;

import com.econetwireless.epay.business.integrations.api.ChargingPlatform;
import com.econetwireless.epay.business.services.api.EnquiriesService;
import com.econetwireless.epay.business.utils.TestUtils;
import com.econetwireless.epay.dao.subscriberrequest.api.SubscriberRequestDao;
import com.econetwireless.epay.domain.SubscriberRequest;
import com.econetwireless.utils.messages.AirtimeBalanceResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class EnquiriesServiceImplTest {
    @Mock
    private SubscriberRequestDao requestDao;
    @Mock
    private ChargingPlatform platform;

    private EnquiriesService enquiriesService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(requestDao.save(any(SubscriberRequest.class))).then(TestUtils.SUBSCRIBER_REQUEST_ANSWER);
        when(platform.enquireBalance(anyString(), anyString())).then(TestUtils.SUCCESSFUL_BALANCE_ENQUIRY);
        this.enquiriesService = new EnquiriesServiceImpl(platform, requestDao);
    }

    @Test
    public void testNullPartnerCode() {
        AirtimeBalanceResponse actual = enquiriesService.enquire(null, null);
        assertNotNull(actual);
        assertEquals("Invalid request, missing partner code", actual.getNarrative());
        assertEquals("400", actual.getResponseCode());
        assertNull(actual.getMsisdn());
    }

    @Test
    public void testNoneNullPartnerCode() {
        AirtimeBalanceResponse actual = enquiriesService.enquire("Exa", null);
        assertNotNull(actual);
        assertEquals("Invalid request, missing mobile number", actual.getNarrative());
        assertEquals("400", actual.getResponseCode());
        assertNull(actual.getMsisdn());
    }

    @Test
    public void testAllParamsNotNull() {
        String constant = "constant";
        AirtimeBalanceResponse actual = enquiriesService.enquire(constant, constant);
        assertNotNull(actual);
        assertEquals("Successful balance enquiry", actual.getNarrative());
        assertEquals("200", actual.getResponseCode());
        assertEquals(constant, actual.getMsisdn());
    }
}