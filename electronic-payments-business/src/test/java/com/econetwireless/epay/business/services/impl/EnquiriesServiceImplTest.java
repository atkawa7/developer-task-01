package com.econetwireless.epay.business.services.impl;

import com.econetwireless.epay.business.integrations.api.ChargingPlatform;
import com.econetwireless.epay.business.services.api.EnquiriesService;
import com.econetwireless.epay.business.utils.TestUtils;
import com.econetwireless.epay.dao.subscriberrequest.api.SubscriberRequestDao;
import com.econetwireless.epay.domain.SubscriberRequest;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class EnquiriesServiceImplTest {
    @Mock
    private EnquiriesService enquiriesService;
    @Mock
    private SubscriberRequestDao requestDao;


    private ChargingPlatform platform;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(requestDao.save(any(SubscriberRequest.class))).then(TestUtils.SUBSCRIBER_REQUEST_ANSWER);
        when(platform.enquireBalance(anyString(), anyString())).then(TestUtils.SUCCESSFUL_BALANCE_ENQUIRY);
        this.enquiriesService = new EnquiriesServiceImpl(platform, requestDao);
    }
}