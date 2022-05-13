package org.ga4gh.starterkit.passport.broker.model;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class MintRequestBody {
    private String researcherId;
    private String researcherEmail;
    private String researcherFirstName;
    private String researcherLastName;
    private List<String> requestedVisas;
}
