package com.econetwireless.epay.business.services.impl;

import com.econetwireless.epay.business.integrations.api.ChargingPlatform;
import com.econetwireless.epay.business.services.api.CreditsService;
import com.econetwireless.epay.business.utils.TestUtils;
import com.econetwireless.epay.dao.subscriberrequest.api.SubscriberRequestDao;
import com.econetwireless.epay.domain.SubscriberRequest;
import com.econetwireless.utils.messages.AirtimeTopupRequest;
import com.econetwireless.utils.messages.AirtimeTopupResponse;
import com.econetwireless.utils.pojo.INCreditRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class CreditsServiceImplTest {
    @Mock
    private SubscriberRequestDao requestDao;
    @Mock
    private ChargingPlatform platform;

    private CreditsService creditsService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(requestDao.save(any(SubscriberRequest.class))).then(TestUtils.SUBSCRIBER_REQUEST_ANSWER);
        when(platform.enquireBalance(anyString(), anyString())).then(TestUtils.SUCCESSFUL_BALANCE_ENQUIRY);
        when(platform.creditSubscriberAccount(any(INCreditRequest.class))).then(TestUtils.SUCCESSFUL_CREDIT_REQUEST);
        this.creditsService = new CreditsServiceImpl(platform, requestDao);
    }

    @Test
    public void testNullTopUpRequest() {
        AirtimeTopupResponse topupResponse = creditsService.credit(null);
        System.out.println(topupResponse.toString());
    }

    @Test
    public void testNoneNullTopUpRequest() {
        AirtimeTopupResponse topupResponse = creditsService.credit(new AirtimeTopupRequest());
    }
    @Test
    public void testNoneNullTopUpRequestWithMsiSdnNull() {
        AirtimeTopupRequest request = new AirtimeTopupRequest();
        request.setMsisdn("SHOULD_SEND_NULL_STATUS");
        AirtimeTopupResponse topupResponse = creditsService.credit(request);
    }

}