/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package au.com.telstra.simcardactivator;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import java.util.Map;
import java.util.HashMap;
import org.springframework.http.MediaType;

/**
 *
 * @author Winston Moyo
 */
@RestController
public class MyRestController {

    @PostMapping("/Activate")
    public ResponseEntity<String> activate(@RequestBody Activation activationRequest) {
        try {
            String iccid = activationRequest.getIccid();

            Map<String, String> payload = new HashMap<>();
            payload.put("iccid", iccid);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(payload, headers);

            RestTemplate restTemplate = new RestTemplate();

            ActuatorResponse response = restTemplate.postForObject("http://localhost:8444/actuate", requestEntity, ActuatorResponse.class);

            if (response != null && response.isSuccess()) {
                System.out.println("Activation successful for ICCID: " + iccid);
                return ResponseEntity.ok("Activation successful.");
            } else {
                System.out.println("Activation failed for ICCID: " + iccid);
                return ResponseEntity.ok("Activation failed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Internal server error");
        }

    }

}

class Activation {

    private String iccid;
    private String customerEmail;

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }
}

class ActuatorResponse {

    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
