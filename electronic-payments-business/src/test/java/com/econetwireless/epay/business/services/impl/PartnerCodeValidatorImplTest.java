package com.econetwireless.epay.business.services.impl;

import com.econetwireless.epay.business.services.api.PartnerCodeValidator;
import com.econetwireless.epay.business.utils.TestUtils;
import com.econetwireless.epay.dao.requestpartner.api.RequestPartnerDao;
import com.econetwireless.epay.domain.RequestPartner;
import com.econetwireless.utils.execeptions.EpayException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Random;
import java.util.UUID;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class PartnerCodeValidatorImplTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();
    @Mock
    private RequestPartnerDao requestPartnerDao;

    private PartnerCodeValidator partnerCodeValidator;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(requestPartnerDao.findByCode(anyString())).then(TestUtils.REQUEST_PARTNER_ANSWER);
        partnerCodeValidator = new PartnerCodeValidatorImpl(requestPartnerDao);
    }

    @Test
    public void testNullPartnerCode() {
        exception.expect(EpayException.class);
        partnerCodeValidator.validatePartnerCode(null);
    }

    @Test
    public void testNoneNullPartnerCode() {

        boolean validatePartnerCode = partnerCodeValidator.validatePartnerCode("Test");
        assertTrue(validatePartnerCode);

    }

}