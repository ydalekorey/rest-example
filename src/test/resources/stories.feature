EPIC:

As a user, I want to edit product entries in my product catalogue database, so that I have control over what products appears on my website.



As a user, I want to read existing product entries from my product catalogue database, so that I can review what products currently exist in the system.

Given that I am passing valid <”Product Code”>
When I attempt to bring back all related data
Then I receive a success message
AND the data returned is <”Product Name”> and <”Price”>

--

As a user, I want to update existing product entries in my product catalogue database, so that I can ensure the database is kept up-to-date.

Given that I am passing valid <”Product Code”> , <”Product Name”> and <”Price”>
When I attempt to update this existing data in the product catalogue with a new <”Price”>
Then I receive a success message
AND the data has been updated in the database
AND a duplicate row of data has not been created.

--

As a user, I want to delete product entries from my product catalogue database, so that I can safely purge products from the system.

Given that I am passing valid <”Product Code”>
When I attempt to delete thie related data in the product catalogue
Then I receive a success message
AND the data has been deleted in the database