@RetrieveProduct
Feature:
  As a user, I want to read existing product entries from my product catalogue database, so that I can review what products currently exist in the system.

  Scenario Outline:
    Given that a valid product with <Product Code> , <Product Name> and <Price> exists in catalogue
    And that I am passing valid <Product Code> to get product
    When I attempt to bring back all related data
    Then I receive a getting success message
    And the data returned is <Product Name> and <Price>

  Examples:
  |Product Code         |Product Name        |Price        |
  |FG001                |Red Umbrella        |12.59        |

