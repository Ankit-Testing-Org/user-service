Feature: User Registration

  Scenario: Successful User Registration
    Given I navigate to the registration page
    When I fill in the registration form with valid data
    And I submit the form
    Then I should see a confirmation message
    And the user should be registered successfully

  Scenario: Registration with Existing Email
    Given I navigate to the registration page
    When I fill in the registration form with an existing email
    And I submit the form
    Then I should see an error message