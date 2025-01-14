Feature: Shopping Cart Product Management
  As a customer
  I want to add products to my cart multiple times
  So that I can purchase multiple quantities of the same item

  Background:
    Given the OpenCart store is accessible
    And I am logged in as a customer
    And I am on the product listing page

  Scenario: Add same product to cart twice
    Given the product "iPhone" is available in stock
    When I add the product to cart
    And I add the same product to cart again
    Then the cart should show quantity "2" for the product
    And the cart total should be calculated correctly

  Scenario: Attempt to add out-of-stock product
    Given the product "iPhone" is out of stock
    When I try to add the product to cart
    Then I should see an out of stock message
    And the cart should remain empty

  Scenario: Add product with specific quantity directly
    Given the product "iPhone" is available in stock
    When I set the quantity to "2"
    And I add the product to cart
    Then the cart should show quantity "2" for the product
    And the cart total should be calculated correctly

  Scenario: Verify cart persistence after logout
    Given I have added "2" quantities of "iPhone" to cart
    When I log out and log back in
    Then the cart should retain quantity "2" for the product

  Scenario: Add maximum allowed quantity
    Given the product "iPhone" has "5" items in stock
    When I set the quantity to "6"
    And I try to add the product to cart
    Then I should see a maximum quantity exceeded message
    And the cart should remain empty