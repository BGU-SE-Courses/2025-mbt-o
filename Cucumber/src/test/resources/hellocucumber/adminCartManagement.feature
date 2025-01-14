Feature: Admin Cart Quantity Management
  As an admin
  I want to modify cart quantities
  So that I can manage customer orders effectively

  Background:
    Given the OpenCart admin panel is accessible
    And I am logged in as administrator
    And there is an existing cart with multiple items

  Scenario: Admin reduces product quantity to one
    Given a customer cart has "2" quantities of "iPhone"
    When I change the quantity to "1" for the product
    Then the cart should show updated quantity "1"
    And the cart total should be recalculated correctly

  Scenario: Admin attempts invalid quantity modification
    Given a customer cart has "2" quantities of "iPhone"
    When I attempt to change the quantity to "0"
    Then I should see an invalid quantity message
    And the cart quantity should remain "2"

  Scenario: Admin modifies quantity with concurrent customer access
    Given a customer cart has "2" quantities of "iPhone"
    And the customer is actively viewing their cart
    When I change the quantity to "1" for the product
    Then the customer should see a cart update notification
    And the cart should show updated quantity "1"

  Scenario: Admin modifies quantity for non-existent product
    Given a customer cart has "2" quantities of "iPhone"
    And the product has been deleted from the catalog
    When I attempt to modify the cart quantity
    Then I should see a product not found message

  Scenario: Verify order total recalculation
    Given a customer cart has "2" quantities of "iPhone" priced at "$500" each
    When I change the quantity to "1" for the product
    Then the cart total should be "$500"
    And any applied discounts should be recalculated