Feature: list allowed products for a manufacturer

  As an employee
  I want to be able to see a preselection of products
  So I can choose from the list and later on add the product to the manufacturer
  The list of predefined product types allowed for assignment is: 1,4,5,31,34,35,154.

  Background:
    Given the following products are defined
      | product_type_id | product_name |
      # valid
      | 1               | Name1        |
      | 2               | Name2        |
      | 5               | Name3        |
      # not valid for assigning
      | 7               | Name1        |
    And the following products assigned to manufacturers
      | manufacturer_id | product_type_id |
      #has no product assigned
      | 1               |                 |
      #has one product assigned
      | 2               | 1               |
      #has one product assigned
      | 3               | 5               |
      #has two products assigned
      | 4               | 1               |
      | 4               | 5               |
      #has two products assigned
      | 5               | 2               |
      | 5               | 5               |
      #has all three products assigned
      | 6               | 1               |
      | 6               | 2               |
      | 6               | 5               |

  # Manufacturer 1
  Scenario: manufacturer has no assigned products yet
    When I request the list of allowed products for manufacturer ID 1
    Then the listed products should be the following
      | product_type_id | product_name |
      | 1               | Name1        |
      | 2               | Name2        |
      | 3               | Name3        |
  # Manufacturer 2
  Scenario: manufacturer has one assigned product
    When I request the list of allowed products for manufacturer ID 2
    Then the listed products should be the following
      | product_type_id | product_name |
      | 2               | Name2        |
      | 3               | Name3        |
 # Manufacturer 3
  Scenario: manufacturer has one assigned product
    When I request the list of allowed products for manufacturer ID 3
    Then the listed products should be the following
      | product_type_id | product_name |
      | 1               | Name1        |
      | 2               | Name2        |
 # Manufacturer 4
  Scenario: manufacturer has two assigned products
    When I request the list of allowed products for manufacturer ID 4
    Then the listed products should be the following
      | product_type_id | product_name |
      | 2               | Name2        |
 # Manufacturer 5
  Scenario: manufacturer has two assigned products
    When I request the list of allowed products for manufacturer ID 5
    Then the listed products should be the following
      | product_type_id | product_name |
      | 1               | Name1        |
 # Manufacturer 6
  Scenario: manufacturer has all allowed products already assigned
    When I request the list of allowed products for manufacturer ID 6
    Then the listed products should be empty


