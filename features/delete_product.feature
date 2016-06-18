@DeleteProduct
Feature:
  As a user, I want to delete product entries from my product catalogue database, so that I can safely purge products from the system.

  Scenario Outline: delete valid product

    Given that a valid product with <Product Code> , <Product Name> and <Price> exists in catalogue
    And that I am passing valid <Product Code> to remove
    When I attempt to delete the related data in the product catalogue
    Then I receive a remove success message
    And the data has been deleted in the database

  Examples:
  |Product Code         |Product Name        |Price        |
  |FG001                |Red Umbrella        |12.59        |