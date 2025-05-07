Feature: SIM Card Activation

    Scenario: Successful SIM card activation
        Given the SIM card ICCID is "1255789453849037777" and the email is "success@example.com"
        When I send an activation request
        Then the activation should be successful
        And I should be able to retrieve the record with ID 1
        And the record should show success

    Scenario: Failed SIM card activation
        Given the SIM card ICCID is "8944500102198304826" and the email is "fail@example.com"
        When I send an activation request
        Then the activation should fail
        And I should be able to retrieve the record with ID 2
        And the record should show failure