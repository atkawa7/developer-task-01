package com.econetwireless.epay.business.utils;

import com.econetwireless.in.webservice.BalanceResponse;
import com.econetwireless.in.webservice.CreditRequest;
import com.econetwireless.in.webservice.CreditResponse;
import com.econetwireless.in.webservice.IntelligentNetworkService;
import com.econetwireless.utils.enums.ResponseCode;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.econetwireless.epay.business.utils.TestUtils.BALANCE;
import static com.econetwireless.epay.business.utils.TestUtils.shouldBeSame;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class IntelligentNetworkServiceTest {


    @Mock
    private IntelligentNetworkService intelligentNetworkService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(intelligentNetworkService.enquireBalance(anyString(), anyString())).then(TestUtils.BALANCE_RESPONSE_ANSWER);
        when(intelligentNetworkService.creditSubscriberAccount(any(CreditRequest.class))).then(TestUtils.CREDIT_RESPONSE_ANSWER);
    }

    @Test
    public void testMissingPartnerCode() {
        BalanceResponse expected = new BalanceResponse();
        expected.setResponseCode(ResponseCode.INVALID_REQUEST.getCode());
        expected.setNarrative("Invalid request, missing partner code");
        BalanceResponse actual = intelligentNetworkService.enquireBalance(null, null);
        shouldBeSame(expected, actual);

    }

    @Test
    public void testMissingMobileNumber() {
        BalanceResponse expected = new BalanceResponse();
        expected.setResponseCode(ResponseCode.INVALID_REQUEST.getCode());
        expected.setNarrative("Invalid request, missing mobile number");
        BalanceResponse actual = intelligentNetworkService.enquireBalance("hello", null);
        shouldBeSame(expected, actual);
    }


    @Test
    public void testWithAllParams() {
        String msisdn = "msisdn";
        BalanceResponse expected = new BalanceResponse();
        expected.setMsisdn(msisdn);
        expected.setAmount(BALANCE);
        expected.setNarrative("Successful balance enquiry");
        expected.setResponseCode(ResponseCode.SUCCESS.getCode());
        BalanceResponse actual = intelligentNetworkService.enquireBalance("hello", msisdn);
        shouldBeSame(expected, actual);
    }

    @Test
    public void testNullCreditRequest() {
        CreditResponse expected = new CreditResponse();
        expected.setResponseCode(ResponseCode.FAILED.getCode());
        expected.setNarrative("Invalid request, empty credit request");
        CreditRequest creditRequest = null;
        CreditResponse actual = intelligentNetworkService.creditSubscriberAccount(creditRequest);
        shouldBeSame(expected, actual);
    }

    @Test
    public void testNoneNullCreditRequest() {
        double amount = 100;
        CreditResponse expected = new CreditResponse();
        expected.setMsisdn("msisdn");
        expected.setBalance(amount + BALANCE);
        expected.setNarrative("Successful credit request");
        expected.setResponseCode(ResponseCode.SUCCESS.getCode());

        CreditRequest creditRequest = new CreditRequest();
        creditRequest.setMsisdn("msisdn");
        creditRequest.setAmount(amount);
        CreditResponse actual = intelligentNetworkService.creditSubscriberAccount(creditRequest);
        shouldBeSame(expected, actual);
    }
}
