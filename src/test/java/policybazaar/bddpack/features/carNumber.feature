Feature: CarNumber
  Scenario: Validating car number input field
    Given user opens policy bazaar website
    When user hover on Insurance Products
    Then user click on car insurance link
    When user enters valid car number
    Then user should see the form

  Scenario Outline: Validating the form
    Given user opens policy bazaar website
    When user hover on Insurance Products
    Then user click on car insurance link
    When user enters valid car number
    Then user should see the form
    When user enters the <Username> valid full name
    And invalid <email id>
    And invalid <phone number>
    Then user should see the error message
    Examples:
      | Username | email id       | phone number |
      | username | username@gmail | 00456783     |
