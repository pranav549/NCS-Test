Feature: Validate the phone Specification on Amazon

  Scenario: Validate the I phone Specification
    Given User is on Amazon page
    When User Enter the phone as "I Phone 13" in search box
    And User Select the Phone
    Then User Select Specifcation as "256 GB"
    And User Select color color as "Green"
    And User get the Price
    Then User click on add cart to cart button
    Then Validate the Phone Price
    Then Validate the Phone Specification
    Then Validate the Color Name
    And Quit the session
