package testutils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

// Simple method to verify JWTs during tests
public class TestJwtVerifier {

    private static final Map<String, String> SECRETS_MAP = new HashMap<>() {{
        put("https://ga4gh.org/", "insecureSecretPleaseOverride");
        put("StarterKitDatasetsControlledAccessGrants@https://datasets.starterkit.ga4gh.org/" , "87A3B5D68FD88197254D9889B4AAB");
        put("DatasetAlpha@https://datasets.starterkit.ga4gh.org/" , "BF4BF43A157FB51B49F7AE13B7216");
        put("DatasetBeta@https://datasets.starterkit.ga4gh.org/" , "C5911B4A7BC2B343C6B7C55FED19D");
        put("DatasetGamma@https://datasets.starterkit.ga4gh.org/" , "6893F3226CF379E52281CCB4584F7");
    }};

    public static void verify(String rawPassportJwt) {
        DecodedJWT decodedPassportJwt = JWT.decode(rawPassportJwt);
        String iss = decodedPassportJwt.getClaim("iss").asString();
        String passportSecret = SECRETS_MAP.get(iss);
        JWTVerifier passportVerifier = JWT
            .require(Algorithm.HMAC256(passportSecret))
            .build();
        DecodedJWT verifiedPassportJwt = passportVerifier.verify(rawPassportJwt);

        // verify each visa in the JWT
        List<String> rawVisaJwts = verifiedPassportJwt.getClaim("ga4gh_passport_v1").asList(String.class);
        List<String> visaNames = verifiedPassportJwt.getClaim("contained_visas").asList(String.class);

        for (int i = 0; i < rawVisaJwts.size(); i++) {
            String rawVisaJwt = rawVisaJwts.get(i);
            String visaSecret = SECRETS_MAP.get(visaNames.get(i));
            JWTVerifier visaVerifier = JWT
                .require(Algorithm.HMAC256(visaSecret))
                .build();
            visaVerifier.verify(rawVisaJwt);
        }
    }
}
