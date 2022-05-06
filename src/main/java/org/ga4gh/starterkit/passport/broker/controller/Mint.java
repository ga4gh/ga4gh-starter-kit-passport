package org.ga4gh.starterkit.passport.broker.controller;

import java.util.HashMap;
import java.util.HashSet;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.ga4gh.starterkit.common.exception.BadRequestException;
import org.ga4gh.starterkit.passport.broker.model.MintRequestBody;
import org.ga4gh.starterkit.passport.broker.model.PassportUser;
import org.ga4gh.starterkit.passport.broker.model.PassportVisa;
import org.ga4gh.starterkit.passport.broker.model.PassportVisaAssertion;
import org.ga4gh.starterkit.passport.broker.utils.hibernate.PassportBrokerHibernateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/ga4gh/passport/v1/mint")
public class Mint {

    @Autowired
    private PassportBrokerHibernateUtil hibernateUtil;

    @PostMapping
    public String mintPassport(@RequestBody MintRequestBody mintRequestBody) {
        // Validation phase
        // Validation step: check the user id exists
        PassportUser researcher =  hibernateUtil.readEntityObject(PassportUser.class, mintRequestBody.getResearcherId(), true);
        if (researcher == null) {
            throw new BadRequestException("No user/researcher found by that id");
        }

        // Validation step: check the user has the requested visas authorized
        HashSet<String> researcherActiveVisaIds = new HashSet<>();
        HashMap<String, PassportVisa> researcherVisaMap = new HashMap<>();
        for (PassportVisaAssertion visaAssertion : researcher.getPassportVisaAssertions()) {
            PassportVisa visa = visaAssertion.getPassportVisa();
            String visaId = visa.getId();
            String status = visaAssertion.getStatus();
            researcherVisaMap.put(visaId, visa);
            if (status.equals("active")) {
                researcherActiveVisaIds.add(visaId);
            }
        }

        for (String requestedVisa : mintRequestBody.getRequestedVisas()) {
            if (researcherActiveVisaIds.contains(requestedVisa) == false) {
                throw new BadRequestException("A visa was requested that the researcher does not have authorized access to");
            }
        }

        // Construct JWT

        // Construct JWT for each requested visa
        String[] visaJwts = new String[mintRequestBody.getRequestedVisas().size()];
        int i = 0;
        for (String requestedVisa : mintRequestBody.getRequestedVisas()) {
            String visaJwt = JWT.create()
                .withClaim("iat", 1580000000)
                .withClaim("exp", 1581208000)
                .withClaim("ga4gh_visa_v1", new HashMap<String, Object>() {{
                    put("type", "AffiliationAndRole");
                    put("asserted", 1549680000);
                    put("value", "faculty@med.stanford.edu");
                    put("source", "https://grid.ac/institutes/grid.240952.8");
                    put("by", "so");
                }})
                .sign(Algorithm.HMAC256("secret"));
            
            visaJwts[i] = visaJwt;
            i++;
        }

        // Construct Final JWT
        return JWT.create()
            // JWT header - nothing needed as we only include 'typ' and 'alg'
            // JWT payload
            .withClaim("iss", "<foo>")
            .withArrayClaim("ga4gh_passport_v1", visaJwts)
            // JWT signature
            .sign(Algorithm.HMAC256("secret"));
    }
}
