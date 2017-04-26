Feature: assignment of order types

  As an employee
  I want to be able to configure predefined order types for products
  So I can do my configurations more efficiently and avoid misconfigurations

  Background:
    Given the following combinations of product, order_type and order_name are defined
      | product_type_id | order_type_id | order_name |
      | 1               | 11            | Name11     |
      | 1               | 111           | Name111    |
      | 2               | 22            | Name22     |
    And the following manufacturers, orders and products assignments
      | manufacturer_id | product_type_id | order_type_id |
      #has no product added
#      | 1               |                 |               |
      #has all products added but no order types
      | 2               | 1               |               |
      | 2               | 2               |               |
      #has all products and one order type added
      | 3               | 1               | 11            |
      | 3               | 2               |               |
      #has all products and two order type added
      | 4               | 1               | 11            |
      | 4               | 1               | 111           |
      | 4               | 2               |               |
      #has all products and all order type added
      | 5               | 1               | 11            |
      | 5               | 1               | 111           |
      | 5               | 2               | 22            |


  Scenario: assigning multiple order types, only allowed
    When I add to the manufacturer ID 2 and product type 1 the order types
      | 11 |
      | 111 |
    Then the manufacturer should have the following order types
      | 11 |
      | 111 |

  Scenario: assigning multiple order types, not all allowed
    When I add to the manufacturer ID 2 and product type 1 the order types
      | 11 |
      | 333 |
    Then then the manufacturer has still no assigned order types

 # Manufacturer 1
  Scenario Outline: negative case - trying to add order types to a manufacturer that has no added product and order type
    When I add <order_type_id> to <product_type_id> of manufacturer ID 1
    Then the result should be <result>
    Examples:
      | product_type_id | order_type_id | result               |
      | 1               | 11            | order type not added |
      | 1               | 111           | order type not added |
      | 2               | 22            | order type not added |
 # Manufacturer 2
  Scenario Outline: adding order types to a manufacturer that has all available products added but no order types
    When I add <order_type_id> to <product_type_id> of manufacturer ID 2
    Then the result should be <result>
    Examples:
      | product_type_id | order_type_id | result           |
      | 1               | 11            | order type added |
      | 1               | 111           | order type added |
      | 2               | 22            | order type added |
# Manufacturer 3
  Scenario Outline: adding order types to a manufacturer that has all available products and one order type added
    When I add <order_type_id> to <product_type_id> of manufacturer ID 3
    Then the result should be <result>
    Examples:
      | product_type_id | order_type_id | result               |
      | 1               | 11            | order type not added |
      | 1               | 111           | order type added     |
      | 2               | 22            | order type added     |
 # Manufacturer 4
  Scenario Outline: adding order types to a manufacturer that has all available products and two order types added
    When I add <order_type_id> to <product_type_id> of manufacturer ID 4
    Then the result should be <result>
    Examples:
      | product_type_id | order_type_id | result               |
      | 1               | 11            | order type not added |
      | 1               | 111           | order type not added |
      | 2               | 22            | order type added     |
 # Manufacturer 5
  Scenario Outline: adding order types to a manufacturer that has all available products and all order types added
    When I add <order_type_id> to <product_type_id> of manufacturer ID 5
    Then the result should be <result>
    Examples:
      | product_type_id | order_type_id | result               |
      | 1               | 11            | order type not added |
      | 1               | 111           | order type not added |
      | 2               | 22            | order type not added |
 # Manufacturer 5
  Scenario Outline: negative case adding invalid or non existing order types to a manufacturer
    When I add <order_type_id> to <product_type_id> of manufacturer ID 5
    Then the result should be <result>
    Examples:
      | product_type_id | order_type_id | result               |
      #invalid combination
      | 1               | 22            | order type not added |
      | 2               | 11            | order type not added |
      | 2               | 111           | order type not added |
#      | 2               | xy1           | order type not added |
      #non existing order types
      | 1               | 0             | order type not added |
#      | 2               | NULL          | order type not added |
#      | 2               |               | order type not added |