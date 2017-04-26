Feature: listing of available order types for a manufacturer and a product

  As an employee
  I want to be able to see a preselection of order types
  So I can choose from the list and later on add the order type to the product

  #note: the current solution supports only the scenario where an order type is not used by more then one product type. that means that one order type can only be assigned to one product.
  #this is the case in our db for product type: 1, 4, 5, 31, 34, 35, 154.

  Background:
    Given the following combinations of product, order_type and order_name are defined
      | product_type_id | order_type_id | order_name |
      | 1               | 11            | Name11     |
      | 1               | 111           | Name111    |
      | 2               | 22            | Name22     |
    And the following manufacturers, orders and products assignments
      | manufacturer_id | product_type_id | order_type_id |
      # has two products, but no order types assigned yet
      | 1               | 1               |               |
      | 1               | 2               |               |
      # has two products and one order type assigned
      | 2               | 1               | 11            |
      | 2               | 2               |               |
      # has two products and two order types assigned
      | 3               | 1               | 11            |
      | 3               | 2               | 22            |
      # has two products and all three available order types assigned
      | 4               | 1               | 11            |
      | 4               | 1               | 111           |
      | 4               | 2               | 22            |

  # Manufacturer 1
  Scenario: manufacturer has no assigned order types
    When I request the list of order types for manufacturer ID 1 and product type 1
    Then the listed order types and names should be the following
      | order_type | order_name |
      | 11         | Name11     |
      | 111        | Name111    |

  # Manufacturer 1
  Scenario: manufacturer has no assigned order types
    When I request the list of order types for manufacturer ID 1 and product type 2
    Then the listed order types and names should be the following
      | order_type | order_name |
      | 22         | Name22     |

  # Manufacturer 2
  Scenario: manufacturer has few assigned order types
    When I request the list of order types for manufacturer ID 2 and product type 1
    Then the listed order types and names should be the following
      | order_type | order_name |
      | 111        | Name111    |

  # Manufacturer 2
  Scenario: manufacturer has few assigned order types
    When I request the list of order types for manufacturer ID 2 and product type 2
    Then the listed order types and names should be the following
      | order_type | order_name |
      | 22         | Name22     |

  # Manufacturer 3
  Scenario: manufacturer has few assigned order types
    When I request the list of order types for manufacturer ID 3 and product type 1
    Then the listed order types and names should be the following
      | order_type | order_name |
      | 111        | Name111    |

  # Manufacturer 3
  Scenario: manufacturer has few assigned order types
    When I request the list of order types for manufacturer ID 3 and product type 3
    Then the listed order types and names should be empty

  # Manufacturer 4
  Scenario: manufacturer has all available order types already assigned
    When I request the list of order types for manufacturer ID 4 and product type 1
    Then the listed order types and names should be empty