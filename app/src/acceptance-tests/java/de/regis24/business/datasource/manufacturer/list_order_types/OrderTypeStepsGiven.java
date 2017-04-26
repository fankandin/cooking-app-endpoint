package de.regis24.business.datasource.manufacturer.list_order_types;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import de.regis24.business.datasource.manufacturer.ManufacturerDbFixturesHelper;
import java.util.ArrayList;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderTypeStepsGiven {

  @Autowired
  protected ManufacturerDbFixturesHelper dbFixture;

  @Given("^the following combinations of product, order_type and order_name are defined$")
  public void setupPermits(DataTable data) {
    // product_type_id | order_type | order_name
    ArrayList<Long> orderTypesCleaned = new ArrayList();
    ArrayList<Long> productsCleaned = new ArrayList();

    for (Map<String, String> entry : data.asMaps(String.class, String.class)) {
      long entryOrderTypeId = Long.parseLong(entry.get("order_type_id"));
      long entryProductTypeId = Long.parseLong(entry.get("product_type_id"));

      if (!orderTypesCleaned.contains(entryOrderTypeId)) {
        dbFixture.deleteOrderType(entryOrderTypeId);
        dbFixture.deleteOrderPermitsByOrder(entryOrderTypeId);
        orderTypesCleaned.add(entryOrderTypeId);
      }

      dbFixture.insertOrderType(entryOrderTypeId, entry.get("order_name"));
      if (!productsCleaned.contains(entryProductTypeId)) {
        dbFixture.deleteOrderPermitsByProduct(entryProductTypeId);
        productsCleaned.add(entryProductTypeId);
      }
      dbFixture
          .insertOrderTypePermit(entryOrderTypeId, entryProductTypeId);
    }
  }

  @Given("^the following manufacturers, orders and products assignments$")
  public void setupAssignments(DataTable data) {
    // manufacturer_id | product_type_id | order_type |
    ArrayList<Long> manufacturersDeleted = new ArrayList();

    for (Map<String, String> entry : data.asMaps(String.class, String.class)) {
      long entryManufacturerId = Long.parseLong(entry.get("manufacturer_id"));

      if (!manufacturersDeleted.contains(entryManufacturerId)) {
        dbFixture.deleteProductAssignments(entryManufacturerId);
        dbFixture.deleteOrderTypeAssignments(entryManufacturerId);
        manufacturersDeleted.add(entryManufacturerId);
      }

      dbFixture.insertProductAssignment(
          entryManufacturerId,
          Long.parseLong(entry.get("product_type_id"))
      );

      if (!entry.get("order_type_id").isEmpty()) {
        dbFixture.insertOrderTypeAssignment(
            entryManufacturerId,
            Long.parseLong(entry.get("order_type_id"))
        );
      }
    }
  }

}
