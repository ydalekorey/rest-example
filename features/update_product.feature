@UpdateProdut
Feature:
  As a user, I want to update existing product entries in my product catalogue database, so that I can ensure the database is kept up-to-date.

  Scenario: update valid product
    Given that I am passing valid <”Product Code”> , <”Product Name”> and <”Price”>
    When I attempt to update this existing data in the product catalogue with a new <”Price”>
    Then I receive a success message
    And the data has been updated in the database
    And a duplicate row of data has not been created.

