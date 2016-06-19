@UpdateProduct
Feature:
  As a user, I want to update existing product entries in my product catalogue database, so that I can ensure the database is kept up-to-date.

  Scenario: update valid product
    Given that a valid product with FG001 , Red Umbrella and 12.59 exists in catalogue
    When I attempt to update this existing data in the product catalogue with a new 66.66
    Then I receive a success message
    And the data has been updated in the database
