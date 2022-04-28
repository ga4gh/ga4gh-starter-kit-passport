package org.ga4gh.starterkit.passport.broker.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import org.ga4gh.starterkit.common.hibernate.HibernateEntity;
import org.ga4gh.starterkit.passport.broker.utils.SerializeView;
import org.hibernate.Hibernate;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "passport_visa")
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PassportVisa implements HibernateEntity<String> {

    /* Non-relational entity attributes */

    @Id
    @Column(name = "id")
    @JsonView(SerializeView.Always.class)
    private String id;

    @Column(name = "visa_name")
    @JsonView(SerializeView.Always.class)
    private String visaName;

    /* Relational entity attributes */

    @OneToMany(mappedBy = "passportVisa",
               fetch = FetchType.LAZY,
               cascade = CascadeType.ALL,
               orphanRemoval = true
    )
    @JsonView(SerializeView.VisaRelational.class)
    private List<PassportVisaAssertion> passportVisaAssertions;

    /* Constructors */

    public PassportVisa() {
        passportVisaAssertions = new ArrayList<>();
    }

    public PassportVisa(String id, String visaName) {
        this.id = id;
        this.visaName = visaName;
        passportVisaAssertions = new ArrayList<>();
    }

    @Override
    public void loadRelations() {
        Hibernate.initialize(getPassportVisaAssertions());
    }
}
