package org.ga4gh.starterkit.passport.broker.controller;

import org.ga4gh.starterkit.passport.broker.app.PassportBroker;
import org.ga4gh.starterkit.passport.broker.app.PassportBrokerSpringConfig;
import org.ga4gh.starterkit.passport.broker.model.MintRequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@ContextConfiguration(classes = {PassportBroker.class, PassportBrokerSpringConfig.class, Mint.class})
@WebAppConfiguration
public class MintTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private WebApplicationContext webAppContext;

    private MockMvc mockMvc;

    @BeforeMethod
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
    }

    @DataProvider(name = "mintCases")
    public Object[][] mintCases() {
        return new Object[][] {
            {}
        };
    }

    @Test(dataProvider = "mintCases")
    public void testMintPassport(
        String researcherId, String researcherEmail, String researcherFirstName,
        String researcherLastName, List<String> requestedVisas,
        ResultMatcher expStatus, boolean expSuccess
    ) throws Exception {
        MintRequestBody requestBody = new MintRequestBody();
        requestBody.setResearcherId(researcherId);
        requestBody.setResearcherEmail(researcherEmail);
        requestBody.setResearcherFirstName(researcherFirstName);
        requestBody.setResearcherLastName(researcherLastName);
        requestBody.setRequestedVisas(requestedVisas);


        MvcResult result = mockMvc.perform(
            post("/admin/ga4gh/passport/v1/mint")
            .content("")
            .header("Content-Type", "application/json")
        )
            .andExpect(expStatus)
            .andReturn();
        
        if (expSuccess) {
            String responseBody = result.getResponse().getContentAsString();
            ObjectMapper mapper = new ObjectMapper();
            System.out.println("***");
            System.out.println(responseBody);
            System.out.println("***");
            Assert.assertEquals("a", "b");
        }
    }
}
