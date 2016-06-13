@DeleteProduct
Feature:
  As a user, I want to delete product entries from my product catalogue database, so that I can safely purge products from the system.

  Scenario: delete valid product

    Given that I am passing valid <Product Code>
    When I attempt to delete thie related data in the product catalogue
    Then I receive a success message
    And the data has been deleted in the database

  Example:
  |Product Code         |Product Name        |Price        |
  |FG001                |Red Umbrella        |12.59        |