package com.econetwireless.epay.business.utils;

import com.econetwireless.epay.business.integrations.api.ChargingPlatform;
import com.econetwireless.epay.business.integrations.impl.ChargingPlatformImpl;
import com.econetwireless.in.webservice.CreditRequest;
import com.econetwireless.in.webservice.IntelligentNetworkService;
import com.econetwireless.utils.enums.ResponseCode;
import com.econetwireless.utils.pojo.INBalanceResponse;
import com.econetwireless.utils.pojo.INCreditRequest;
import com.econetwireless.utils.pojo.INCreditResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.econetwireless.epay.business.utils.TestUtils.BALANCE;
import static com.econetwireless.epay.business.utils.TestUtils.shouldBeSame;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class ChargingPlatformTest {

    private ChargingPlatform platform;

    @Mock
    private IntelligentNetworkService intelligentNetworkService;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(intelligentNetworkService.enquireBalance(anyString(), anyString())).then(TestUtils.BALANCE_RESPONSE_ANSWER);
        when(intelligentNetworkService.creditSubscriberAccount(any(CreditRequest.class))).then(TestUtils.CREDIT_RESPONSE_ANSWER);
        platform = new ChargingPlatformImpl(intelligentNetworkService);
    }

    @Test
    public void testMissingPartnerCode() {
        INBalanceResponse expected = new INBalanceResponse();
        expected.setResponseCode(ResponseCode.INVALID_REQUEST.getCode());
        expected.setNarrative("Invalid request, missing partner code");
        INBalanceResponse actual = platform.enquireBalance(null, null);
        shouldBeSame(expected, actual);

    }

    @Test
    public void testMissingMobileNumber() {
        INBalanceResponse expected = new INBalanceResponse();
        expected.setResponseCode(ResponseCode.INVALID_REQUEST.getCode());
        expected.setNarrative("Invalid request, missing mobile number");
        INBalanceResponse actual = platform.enquireBalance("hello", null);
        shouldBeSame(expected, actual);
    }


    @Test
    public void testWithAllParams() {
        String msisdn = "msisdn";
        INBalanceResponse expected = new INBalanceResponse();
        expected.setMsisdn(msisdn);
        expected.setAmount(BALANCE);
        expected.setNarrative("Successful balance enquiry");
        expected.setResponseCode(ResponseCode.SUCCESS.getCode());
        INBalanceResponse actual = platform.enquireBalance("hello", msisdn);
        shouldBeSame(expected, actual);
    }

    @Test
    public void testNullCreditRequest() {
        INCreditResponse expected = new INCreditResponse();
        expected.setResponseCode(ResponseCode.FAILED.getCode());
        expected.setNarrative("Invalid request, empty credit request");
        INCreditRequest creditRequest = null;
        INCreditResponse actual = platform.creditSubscriberAccount(creditRequest);
        shouldBeSame(expected, actual);
    }

    @Test
    public void testNoneNullCreditRequest() {
        double amount = 100;
        INCreditResponse expected = new INCreditResponse();
        expected.setMsisdn("msisdn");
        expected.setBalance(amount + BALANCE);
        expected.setNarrative("Successful credit request");
        expected.setResponseCode(ResponseCode.SUCCESS.getCode());

        INCreditRequest creditRequest = new INCreditRequest();
        creditRequest.setMsisdn("msisdn");
        creditRequest.setAmount(amount);
        INCreditResponse actual = platform.creditSubscriberAccount(creditRequest);
        shouldBeSame(expected, actual);
    }

}
