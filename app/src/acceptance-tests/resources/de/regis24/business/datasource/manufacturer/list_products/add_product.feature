Feature: add a product to a manufacturer

  As an employee
  I want to be able to add a product to a manufacturer
  So I can do more configurations on my own and be less dependent from the it
  The list of predefined product types allowed for assignment is: 1,4,5,31,34,35,154.

  Background:
    Given the following products are defined
      | product_type_id | product_name |
      # valid
      | 1               | Name1        |
      | 2               | Name2        |
      # not valid for assigning
      | 7               | Name1        |
    And the following products assigned to manufacturers
      | manufacturer_id | product_type_id |
      #has no product assigned
      | 1               |                 |
      #has one product assigned
      | 2               | 1               |
      #has one product assigned
      | 3               | 2               |
      #has two products assigned
      | 4               | 1               |
      | 4               | 2               |

  # Manufacturer 1
  Scenario Outline: adding products to a manufacturer that has no assigned product
    When I add <product_type_id> to manufacturer ID 1
    Then the result should be <result>
    Examples:
      | product_type_id | result        |
      | 1               | product added |
      | 2               | product added |
 # Manufacturer 2
  Scenario Outline: adding products to a manufacturer that has one assigned product
    When I add <product_type_id> to manufacturer ID 2
    Then the result should be <result>
    Examples:
      | product_type_id | result            |
      | 1               | product not added |
      | 2               | product added     |
 # Manufacturer 3
  Scenario Outline: adding products to a manufacturer that has one assigned product
    When I add <product_type_id> to manufacturer ID 3
    Then the result should be <result>
    Examples:
      | product_type_id | result            |
      | 2               | product not added |
      | 1               | product added     |
# Manufacturer 4
  Scenario Outline: adding products to a manufacturer that has all products assigned
    When I add <product_type_id> to manufacturer ID 4
    Then the result should be <result>
    Examples:
      | product_type_id | result            |
      | 1               | product not added |
      | 2               | product not added |
 # Manufacturer 1
  Scenario Outline: negative case adding undefined or non existing products for a manufacturer
    When I add <product_type_id> to manufacturer ID 1
    Then the result should be <result>
    Examples:
      | product_type_id | result            |
      #undefined product
      | 3               | product not added |
#      | xy1             | product not added |
      #non existing products
      | 0               | product not added |
#      | NULL            | product not added |
#      |                 | product not added |