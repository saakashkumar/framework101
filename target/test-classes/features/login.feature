Feature: Login Functionality

  Scenario: Successful Login
    Given I launch the login page
    When I enter "tomsmith" into the username field
    And I enter "SuperSecretPassword!" into the password field
    And I click the login button
    Then I should see "You logged into a secure area!" in the success message

  Scenario: Invalid Login
    Given I launch the login page
    When I enter "wronguser" into the username field
    And I enter "wrongpass" into the password field
    And I click the login button
    Then I should see "Your username is invalid!" in the error message

  Scenario: Empty Username
    Given I launch the login page
    When I enter "" into the username field
    And I enter "SuperSecretPassword!" into the password field
    And I click the login button
    Then I should see "Your username is invalid!" in the error message

  Scenario: Empty Password
    Given I launch the login page
    When I enter "tomsmith" into the username field
    And I enter "" into the password field
    And I click the login button
    Then I should see "Your password is invalid!" in the error message