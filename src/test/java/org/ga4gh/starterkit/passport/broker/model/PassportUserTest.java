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
                new ArrayList<PassportVisaAssertion>() {{
                    add(new PassportVisaAssertion(0L, "active"));
                }}
            }
        };
    }

    @Test(dataProvider = "cases")
    public void testPassportUserNoArgsConstructor(String id, List<PassportVisaAssertion> passportVisaAssertions) {
        PassportUser user = new PassportUser();
        user.setId(id);
        user.setPassportVisaAssertions(passportVisaAssertions);

        Assert.assertEquals(user.getId(), id);
        Assert.assertEquals(user.getPassportVisaAssertions(), passportVisaAssertions);
    }
}
