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
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import org.ga4gh.starterkit.common.hibernate.HibernateEntity;
import org.ga4gh.starterkit.passport.broker.utils.SerializeView;
import org.hibernate.Hibernate;
import org.springframework.lang.NonNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "passport_user")
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PassportUser implements HibernateEntity<String> {

    /* Non-relational entity attributes */

    @Id
    @Column(name = "id")
    @NonNull
    @JsonView(SerializeView.Always.class)
    private String id;

    @Column(name = "email")
    @NonNull
    @JsonProperty(required = true)
    @JsonView(SerializeView.Always.class)
    private String email;

    @Column(name = "first_name")
    @NonNull
    @JsonView(SerializeView.Always.class)
    private String firstName;

    @Column(name = "last_name")
    @NonNull
    @JsonView(SerializeView.Always.class)
    private String lastName;

    /* Relational entity attributes */

    @OneToMany(mappedBy = "passportUser",
               fetch = FetchType.LAZY,
               cascade = CascadeType.ALL,
               orphanRemoval = true)
    @JsonView(SerializeView.UserRelational.class)
    private List<PassportVisaAssertion> passportVisaAssertions;

    /* Constructors */

    public PassportUser() {
        passportVisaAssertions = new ArrayList<>();
    }

    @Override
    public void loadRelations() {
        Hibernate.initialize(getPassportVisaAssertions());
    }
}
