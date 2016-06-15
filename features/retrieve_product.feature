@RetrieveProduct
Feature:
  As a user, I want to read existing product entries from my product catalogue database, so that I can review what products currently exist in the system.

  Scenario:
    Given that I am passing valid <Product Code>
    When I attempt to bring back all related data
    Then I receive a success message
    And the data returned is <Product Name> and <Price>

  Example:
  |Product Code         |Product Name        |Price        |
  |FG001                |Red Umbrella        |12.59        |

