package com.econetwireless.epay.business.services.impl;

import com.econetwireless.epay.business.services.api.ReportingService;
import com.econetwireless.epay.business.utils.TestUtils;
import com.econetwireless.epay.dao.subscriberrequest.api.SubscriberRequestDao;
import com.econetwireless.epay.domain.SubscriberRequest;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;


public class ReportingServiceImplTest {
    private ReportingService reportingService;


    @Mock
    private SubscriberRequestDao subscriberRequestDao;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(subscriberRequestDao.findByPartnerCode(anyString())).then(new Answer<List<SubscriberRequest>>() {
            @Override
            public List<SubscriberRequest> answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();
                if (arguments != null && arguments.length > 0) {
                    String partnerCode = (String) arguments[0];
                    return TestUtils.SUBSCRIBER_REQUESTS.stream()
                            .filter(
                                    request -> request.getPartnerCode() != null &&
                                            !StringUtils.isEmpty(partnerCode) &&
                                            request.getPartnerCode().equalsIgnoreCase(partnerCode))
                            .collect(Collectors.toList());
                }
                return null;
            }
        });
        reportingService = new ReportingServiceImpl(subscriberRequestDao);
    }

    @Test
    public void testNullPartnerCode() {
        List<SubscriberRequest> subscriberRequests = reportingService.findSubscriberRequestsByPartnerCode(null);
        assertNotNull(subscriberRequests);
        assertEquals(0, subscriberRequests.size());
    }

    @Test
    public void testNullPartnerCodeA() {
        List<SubscriberRequest> subscriberRequests = reportingService.findSubscriberRequestsByPartnerCode("A");
        assertNotNull(subscriberRequests);
        assertEquals(1, subscriberRequests.size());
    }

    @Test
    public void testNullPartnerCodeC() {
        List<SubscriberRequest> subscriberRequests = reportingService.findSubscriberRequestsByPartnerCode("C");
        assertNotNull(subscriberRequests);
        assertEquals(2, subscriberRequests.size());
    }
}