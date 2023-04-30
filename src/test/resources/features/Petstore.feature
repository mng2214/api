Feature: Petstore API
  
  #Background: Given User has petstore endpoint

  Scenario: List pets API
    Given User has petstore endpoint
    When User sends get request to list pets
    Then Status code 200
    And Response contains list of pets

  Scenario Outline: Get non existing pet
    Given User has petstore endpoint
    When User sends GET request to list <petIdType> pet by ID
    Then Status code 404
    And Error message is '<errorMsg>'

    Examples:
      | petIdType    | errorMsg                                                                      |
      | non-existing | Pet not found                                                                 |
      | invalidId    | java.lang.NumberFormatException: For input string: \"0987654321234567890ABC\" |