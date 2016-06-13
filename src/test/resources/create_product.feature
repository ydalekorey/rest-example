@CreateProduct
Feature:
  As a user, I want to create new product entries in my product catalogue database, so that I can store new products into the system.

  Scenario: valid product

    Given that I am passing valid <Product Code> , <Product Name> and <Price>
    When I attempt to add this data to the product catalogue
    Then I receive a success message
    And the data has been entered into the database.

  Example:
  |Product Code         |Product Name        |Price        |
  |FG001                |Red Umbrella        |12.59        |

  Scenario: not valid product

    Given that I am passing valid <Product Code> and <Product Name> but invalid <Price>
    When I attempt to add this data to the product catalogue
    Then I receive an appropriate error response
    And the data has NOT been entered into the database.

  Example:
  |Product Code        |Product Name       |Price         |
  |FG001               |Red Umbrella       |12.a59        |

