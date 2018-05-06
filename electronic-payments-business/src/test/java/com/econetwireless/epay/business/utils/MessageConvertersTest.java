package com.econetwireless.epay.business.utils;

import com.econetwireless.in.webservice.BalanceResponse;
import com.econetwireless.in.webservice.CreditRequest;
import com.econetwireless.in.webservice.CreditResponse;
import com.econetwireless.utils.pojo.INBalanceResponse;
import com.econetwireless.utils.pojo.INCreditRequest;
import com.econetwireless.utils.pojo.INCreditResponse;
import org.junit.Test;

import static org.junit.Assert.*;


public class MessageConvertersTest {

    @Test
    public void convertNullInINCreditRequestToCreditRequestShouldReturnNull() {
        CreditRequest actual = MessageConverters.convert((INCreditRequest) null);
        assertNull(actual);
    }

    @Test
    public void convertNoneNullInINCreditRequestToCreditRequestShouldReturnNoneNull() {
        INCreditRequest expected = new INCreditRequest();
        CreditRequest actual = MessageConverters.convert(expected);

        assertNotNull(actual);

        assertEquals(expected.getAmount(), actual.getAmount(), 0.001);
        assertEquals(expected.getMsisdn(), actual.getMsisdn());
        assertEquals(expected.getPartnerCode(), actual.getPartnerCode());
        assertEquals(expected.getReferenceNumber(), actual.getReferenceNumber());
    }

    @Test
    public void convertNullCreditRequestToInINCreditRequestShouldReturnNull() {
        INCreditRequest result = MessageConverters.convert((CreditRequest) null);
        assertNull(result);
    }

    @Test
    public void convertNoneNullCreditRequestToInINCreditRequestShouldReturnNoneNull() {
        CreditRequest expected = new CreditRequest();
        INCreditRequest actual = MessageConverters.convert(expected);

        assertNotNull(actual);

        assertEquals(expected.getAmount(), actual.getAmount(), 0.001);
        assertEquals(expected.getMsisdn(), actual.getMsisdn());
        assertEquals(expected.getPartnerCode(), actual.getPartnerCode());
        assertEquals(expected.getReferenceNumber(), actual.getReferenceNumber());
    }

    @Test
    public void convertNullINCreditResponseToCreditResponseShouldReturnNull() {
        INCreditResponse actual = MessageConverters.convert((CreditResponse) null);
        assertNull(actual);
    }

    @Test
    public void convertNoneINCreditResponseToCreditResponseShouldReturnNoneNull() {

        CreditResponse expected = new CreditResponse();
        INCreditResponse actual = MessageConverters.convert(expected);
        assertNotNull(actual);

        assertEquals(expected.getBalance(), actual.getBalance(), 0.001);
        assertEquals(expected.getMsisdn(), actual.getMsisdn());
        assertEquals(expected.getNarrative(), actual.getNarrative());
        assertEquals(expected.getResponseCode(), actual.getResponseCode());

    }

    @Test
    public void convertNullCreditResponseToINCreditResponseShouldReturnNull() {
        CreditResponse actual = MessageConverters.convert((INCreditResponse) null);
        assertNull(actual);
    }

    @Test
    public void convertNoneCreditResponseToINCreditResponseShouldReturnNoneNull() {

        INCreditResponse expected = new INCreditResponse();
        CreditResponse actual = MessageConverters.convert(expected);
        assertNotNull(actual);

        assertEquals(expected.getBalance(), actual.getBalance(), 0.001);
        assertEquals(expected.getMsisdn(), actual.getMsisdn());
        assertEquals(expected.getNarrative(), actual.getNarrative());
        assertEquals(expected.getResponseCode(), actual.getResponseCode());

    }

    @Test
    public void convertNullINBalanceResponseToBalanceResponseShouldReturnNull() {
        BalanceResponse actual = MessageConverters.convert((INBalanceResponse) null);
        assertNull(actual);
    }

    @Test
    public void convertNoneBalanceResponseToBalanceResponseShouldReturnNoneNull() {

        INBalanceResponse expected = new INBalanceResponse();
        BalanceResponse actual = MessageConverters.convert(expected);
        assertNotNull(actual);

        assertEquals(expected.getAmount(), actual.getAmount(), 0.001);
        assertEquals(expected.getMsisdn(), actual.getMsisdn());
        assertEquals(expected.getNarrative(), actual.getNarrative());
        assertEquals(expected.getResponseCode(), actual.getResponseCode());

    }

    @Test
    public void convertNullBalanceResponseToINBalanceResponseShouldReturnNull() {
        INBalanceResponse actual = MessageConverters.convert((BalanceResponse) null);
        assertNull(actual);
    }

    @Test
    public void convertNoneNullBalanceResponseToINBalanceResponseShouldReturnNoneNull() {

        BalanceResponse expected = new BalanceResponse();
        INBalanceResponse actual = MessageConverters.convert(expected);
        assertNotNull(actual);

        assertEquals(expected.getAmount(), actual.getAmount(), 0.001);
        assertEquals(expected.getMsisdn(), actual.getMsisdn());
        assertEquals(expected.getNarrative(), actual.getNarrative());
        assertEquals(expected.getResponseCode(), actual.getResponseCode());

    }


}