package com.econetwireless.epay.business.utils;

import com.econetwireless.in.webservice.BalanceResponse;
import com.econetwireless.in.webservice.CreditRequest;
import com.econetwireless.in.webservice.CreditResponse;
import com.econetwireless.utils.enums.ResponseCode;
import com.econetwireless.utils.pojo.INBalanceResponse;
import com.econetwireless.utils.pojo.INCreditResponse;
import org.apache.commons.lang3.StringUtils;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestUtils {
    public static final Double BALANCE = 100.0;
    public final static Answer<BalanceResponse> BALANCE_RESPONSE_ANSWER = new Answer<BalanceResponse>() {
        @Override
        public BalanceResponse answer(InvocationOnMock invocation) throws Throwable {
            Object[] arguments = invocation.getArguments();
            if (arguments != null && arguments.length > 1) {
                String partnerCode = (String) arguments[0];
                String msisdn = (String) arguments[1];
                final BalanceResponse balanceResponse = new BalanceResponse();
                if (StringUtils.isEmpty(partnerCode)) {
                    balanceResponse.setResponseCode(ResponseCode.INVALID_REQUEST.getCode());
                    balanceResponse.setNarrative("Invalid request, missing partner code");
                    return balanceResponse;
                }
                if (StringUtils.isEmpty(msisdn)) {
                    balanceResponse.setResponseCode(ResponseCode.INVALID_REQUEST.getCode());
                    balanceResponse.setNarrative("Invalid request, missing mobile number");
                    return balanceResponse;
                }
                balanceResponse.setMsisdn(msisdn);
                balanceResponse.setAmount(BALANCE);
                balanceResponse.setNarrative("Successful balance enquiry");
                balanceResponse.setResponseCode(ResponseCode.SUCCESS.getCode());
                return balanceResponse;
            }
            return null;
        }
    };
    public static final Answer<CreditResponse> CREDIT_RESPONSE_ANSWER = new Answer<CreditResponse>() {
        @Override
        public CreditResponse answer(InvocationOnMock invocation) throws Throwable {
            Object[] arguments = invocation.getArguments();
            if (arguments != null && arguments.length > 0) {
                CreditRequest creditRequest = (CreditRequest) arguments[0];
                final CreditResponse creditResponse = new CreditResponse();
                if (creditRequest == null) {
                    creditResponse.setResponseCode(ResponseCode.FAILED.getCode());
                    creditResponse.setNarrative("Invalid request, empty credit request");
                    return creditResponse;
                }
                creditResponse.setMsisdn(creditRequest.getMsisdn());
                creditResponse.setBalance(creditRequest.getAmount() + BALANCE);
                creditResponse.setNarrative("Successful credit request");
                creditResponse.setResponseCode(ResponseCode.SUCCESS.getCode());
                return creditResponse;
            }
            return null;
        }
    };

    public static void shouldBeSame(BalanceResponse expected, BalanceResponse actual) {
        assertNotNull(expected);
        assertNotNull(actual);
        assertEquals(expected.getResponseCode(), actual.getResponseCode());
        assertEquals(expected.getAmount(), actual.getAmount(), 0.001);
        assertEquals(expected.getNarrative(), actual.getNarrative());
        assertEquals(expected.getMsisdn(), actual.getMsisdn());
    }

    public static void shouldBeSame(INBalanceResponse expected, INBalanceResponse actual) {
        assertNotNull(expected);
        assertNotNull(actual);
        assertEquals(expected.getResponseCode(), actual.getResponseCode());
        assertEquals(expected.getAmount(), actual.getAmount(), 0.001);
        assertEquals(expected.getNarrative(), actual.getNarrative());
        assertEquals(expected.getMsisdn(), actual.getMsisdn());
    }

    public static void shouldBeSame(CreditResponse expected, CreditResponse actual) {
        assertNotNull(expected);
        assertNotNull(actual);
        assertEquals(expected.getResponseCode(), actual.getResponseCode());
        assertEquals(expected.getBalance(), actual.getBalance(), 0.001);
        assertEquals(expected.getNarrative(), actual.getNarrative());
        assertEquals(expected.getMsisdn(), actual.getMsisdn());
    }

    public static void shouldBeSame(INCreditResponse expected, INCreditResponse actual) {
        assertNotNull(expected);
        assertNotNull(actual);
        assertEquals(expected.getResponseCode(), actual.getResponseCode());
        assertEquals(expected.getBalance(), actual.getBalance(), 0.001);
        assertEquals(expected.getNarrative(), actual.getNarrative());
        assertEquals(expected.getMsisdn(), actual.getMsisdn());
    }
}
