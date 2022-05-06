package org.ga4gh.starterkit.passport.broker.model;

import java.util.ArrayList;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test
public class PassportUserTest {

    @DataProvider(name = "cases")
    public Object[][] getData() {
        return new Object[][] {
            {
                "85ff5a54-48b9-4294-a91d-2be50bd2a77d",
                "ana.researcher@institute.org",
                "Ana",
                "Researcher",
                new ArrayList<PassportVisaAssertion>() {{
                    add(new PassportVisaAssertion(0L, "active"));
                }}
            }
        };
    }

    @Test(dataProvider = "cases")
    public void testPassportUserNoArgsConstructor(
        String id, String username, String firstName, String lastName,
        String email, String passwordSalt, String passwordHash,
        List<PassportVisaAssertion> passportVisaAssertions
    ) {
        PassportUser user = new PassportUser();
        user.setId(id);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassportVisaAssertions(passportVisaAssertions);

        Assert.assertEquals(user.getId(), id);
        Assert.assertEquals(user.getEmail(), email);
        Assert.assertEquals(user.getFirstName(), firstName);
        Assert.assertEquals(user.getLastName(), lastName);
        Assert.assertEquals(user.getPassportVisaAssertions(), passportVisaAssertions);
    }
}
