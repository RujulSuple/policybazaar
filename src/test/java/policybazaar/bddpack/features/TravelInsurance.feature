Feature: TravelInsurance
  Scenario: Getting travel insurance for 2 students
    Given user opens policy bazaar website
    When user hover on Insurance Products
    Then user click on Travel Insurance link
    When user enters any european country and click on continue button
      | Country |
      | France  |
    Then user should see When are you travelling?
    When user select start date
    And end date

