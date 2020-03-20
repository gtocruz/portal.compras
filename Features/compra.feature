@Description
Feature: Customer buying cycle
  As an end user I want to access an e-commerce page, search for a product, 
  validate my search, choose a product, add it to my cart and validate the product in my cart

  Background: 
    Given I set up my Chrome Driver

  @Search
  Scenario: Book search is successful
    Given I am on Magalu HomePage
    And I enter the book name in the search field
    When I click on the search button
    Then search results are displayed
  
  @Fails  
  Scenario: Book search fails
    Given I am on Magalu HomePage
    And I enter the wrong book name in the search field
    When I click on the search button
    Then no search results are displayed
    
  @ViewProduct
  Scenario: View product page
  	Given I am on the search results page
    When I click on the product I want to buy
    Then I am redirected to the product page
    
  @ShoppingCart
  Scenario: Product is added to the shopping cart
  	Given I am on the product page
    When I click on the buy button
    Then the product is added to my cart
