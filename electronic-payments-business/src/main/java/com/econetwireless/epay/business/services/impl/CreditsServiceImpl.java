package com.econetwireless.epay.business.services.impl;

import com.econetwireless.epay.business.integrations.api.ChargingPlatform;
import com.econetwireless.epay.business.services.api.CreditsService;
import com.econetwireless.epay.dao.subscriberrequest.api.SubscriberRequestDao;
import com.econetwireless.epay.domain.SubscriberRequest;
import com.econetwireless.utils.constants.SystemConstants;
import com.econetwireless.utils.enums.ResponseCode;
import com.econetwireless.utils.messages.AirtimeTopupRequest;
import com.econetwireless.utils.messages.AirtimeTopupResponse;
import com.econetwireless.utils.pojo.INCreditRequest;
import com.econetwireless.utils.pojo.INCreditResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

/**
 * Created by tnyamakura on 17/3/2017.
 */
@Transactional
public class CreditsServiceImpl implements CreditsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreditsServiceImpl.class);

    private ChargingPlatform chargingPlatform;
    private SubscriberRequestDao subscriberRequestDao;

    public CreditsServiceImpl(ChargingPlatform chargingPlatform, SubscriberRequestDao subscriberRequestDao) {
        this.chargingPlatform = chargingPlatform;
        this.subscriberRequestDao = subscriberRequestDao;
    }

    private static void changeSubscriberRequestStatusOnCredit(final SubscriberRequest subscriberRequest, final INCreditResponse inCreditResponse) {
        LOGGER.info("SubscriberRequest: {}, INCreditResponse : {}", subscriberRequest, inCreditResponse);
        final boolean isSuccessfulResponse = inCreditResponse.getResponseCode() != null && ResponseCode.SUCCESS.getCode().equalsIgnoreCase(inCreditResponse.getResponseCode());
        if (!isSuccessfulResponse) {
            subscriberRequest.setStatus(SystemConstants.STATUS_FAILED);
        } else {
            subscriberRequest.setStatus(SystemConstants.STATUS_SUCCESSFUL);
            subscriberRequest.setBalanceAfter(inCreditResponse.getBalance());
            subscriberRequest.setBalanceBefore(inCreditResponse.getBalance() - subscriberRequest.getAmount());
        }
    }

    private static SubscriberRequest populateSubscriberRequest(final AirtimeTopupRequest airtimeTopupRequest) {
        final SubscriberRequest subscriberRequest = new SubscriberRequest();
        subscriberRequest.setRequestType(SystemConstants.REQUEST_TYPE_AIRTIME_TOPUP);
        String partnerCode = null;
        String msisdn = null;
        String referenceNumber = null;
        double amount = 0;

        if (!ObjectUtils.isEmpty(airtimeTopupRequest)) {
            partnerCode = airtimeTopupRequest.getPartnerCode();
            msisdn = airtimeTopupRequest.getMsisdn();
            referenceNumber = airtimeTopupRequest.getReferenceNumber();
            amount = airtimeTopupRequest.getAmount();
        }
        subscriberRequest.setPartnerCode(partnerCode);
        subscriberRequest.setMsisdn(msisdn);
        subscriberRequest.setReference(referenceNumber);
        subscriberRequest.setAmount(amount);
        return subscriberRequest;
    }

    private static INCreditRequest populate(final AirtimeTopupRequest airtimeTopupRequest) {
        final INCreditRequest inCreditRequest = new INCreditRequest();
        String partnerCode = null;
        String msisdn = null;
        String referenceNumber = null;
        double amount = 0;

        if (!ObjectUtils.isEmpty(airtimeTopupRequest)) {
            partnerCode = airtimeTopupRequest.getPartnerCode();
            msisdn = airtimeTopupRequest.getMsisdn();
            referenceNumber = airtimeTopupRequest.getReferenceNumber();
            amount = airtimeTopupRequest.getAmount();
        }
        inCreditRequest.setAmount(amount);
        inCreditRequest.setMsisdn(msisdn);
        inCreditRequest.setPartnerCode(partnerCode);
        inCreditRequest.setReferenceNumber(referenceNumber);
        return inCreditRequest;
    }

    @Override
    public AirtimeTopupResponse credit(final AirtimeTopupRequest airtimeTopupRequest) {
        LOGGER.info("Credit airtime Request : {}", airtimeTopupRequest);


        final SubscriberRequest subscriberRequest = populateSubscriberRequest(airtimeTopupRequest);

        LOGGER.info("Credit SubscriberRequest: START {}", subscriberRequest);

        final SubscriberRequest createdSubscriberRequest = subscriberRequestDao.save(subscriberRequest);

        LOGGER.info("Credit SubscriberRequest: END {}", createdSubscriberRequest);


        final INCreditRequest inCreditRequest = populate(airtimeTopupRequest);

        LOGGER.info("Credit INCreditResponse: END {}", inCreditRequest);

        final INCreditResponse inCreditResponse = chargingPlatform.creditSubscriberAccount(inCreditRequest);

        LOGGER.info("Credit INCreditResponse: END {}", inCreditResponse);

        changeSubscriberRequestStatusOnCredit(createdSubscriberRequest, inCreditResponse);

        subscriberRequestDao.save(createdSubscriberRequest);

        final AirtimeTopupResponse airtimeTopupResponse = new AirtimeTopupResponse();
        airtimeTopupResponse.setResponseCode(inCreditResponse.getResponseCode());
        airtimeTopupResponse.setNarrative(inCreditResponse.getNarrative());

        String msisdn = ObjectUtils.isEmpty(airtimeTopupRequest) ? null : airtimeTopupRequest.getMsisdn();
        airtimeTopupResponse.setMsisdn(msisdn);
        airtimeTopupResponse.setBalance(inCreditResponse.getBalance());

        if (!ObjectUtils.isEmpty(airtimeTopupRequest)) {
            LOGGER.info("Finished Airtime Credit :: Msisdn : {}, response code : {}", airtimeTopupRequest.getMsisdn(), inCreditResponse.getResponseCode());
        }
        return airtimeTopupResponse;
    }

}
