@UpdateProduct
Feature:
  As a user, I want to update existing product entries in my product catalogue database, so that I can ensure the database is kept up-to-date.

  Scenario Outline: update valid product
    Given that a valid product with <Product Code> , <Product Name> and <Price> exists in catalogue
    When I attempt to update this existing data with <Product Code> in the product catalogue with a <New Price>
    Then I receive update success message
    And the data has been updated in the database

    Examples:
      |Product Code         |Product Name        |Price        |New Price     |
      |FG001                |Red Umbrella        |12.59        |66.66         |