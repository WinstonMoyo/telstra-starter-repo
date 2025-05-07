package stepDefinitions;

import au.com.telstra.simcardactivator.SimCardActivator;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ContextConfiguration;
import io.cucumber.java.en.*;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.*;
import static org.junit.jupiter.api.Assertions.*;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = SimCardActivator.class, loader = SpringBootContextLoader.class)
public class SimCardActivatorStepDefinitions {

    @Autowired
    private TestRestTemplate restTemplate;
    private String iccid;
    private String email;
    private ResponseEntity<String> postResponse;
    private ResponseEntity<Map> getResponse;

    @Given("the SIM card ICCID is {string} and the email is {string}")
    public void givenSimCardDetails(String iccid, String email) {
        this.iccid = iccid;
        this.email = email;
    }

    @When("I send an activation request")
    public void sendActivationRequest() {
        String url = "http://localhost:8080/Activate";
        Map<String, String> request = new HashMap<>();
        request.put("iccid", iccid);
        request.put("customerEmail", email);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(request, headers);

        postResponse = restTemplate.postForEntity(url, entity, String.class);
    }

    @Then("the activation should be successful")
    public void activationShouldBeSuccessful() {
        assertEquals(HttpStatus.OK, postResponse.getStatusCode());
        assertTrue(postResponse.getBody().contains("successful"));
    }

    @Then("the activation should fail")
    public void activationShouldFail() {
        assertEquals(HttpStatus.OK, postResponse.getStatusCode());
        assertTrue(postResponse.getBody().contains("failed"));
    }

    @Then("I should be able to retrieve the record with ID {int}")
    public void retrieveRecordById(int id) {
        String url = "http://localhost:8080/SimCard?simCardId=" + id;
        getResponse = restTemplate.getForEntity(url, Map.class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
    }

    @Then("the record should show success")
    public void recordShouldShowSuccess() {
        assertTrue((Boolean) getResponse.getBody().get("active"));
    }

    @Then("the record should show failure")
    public void recordShouldShowFailure() {
        assertFalse((Boolean) getResponse.getBody().get("active"));
    }
}
