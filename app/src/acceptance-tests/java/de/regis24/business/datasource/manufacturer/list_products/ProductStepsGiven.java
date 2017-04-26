package de.regis24.business.datasource.manufacturer.list_products;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import de.regis24.business.datasource.manufacturer.ManufacturerDbFixturesHelper;
import java.util.ArrayList;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public class ProductStepsGiven {
  @Resource
  private Environment environment;

  @Autowired
  protected ManufacturerDbFixturesHelper dbFixture;

  @Given("^the following products are defined$")
  public void setupProducts(DataTable data) {
    deleteAllValidProductTypes();

    // product_type_id | product_name
    for (Map<String, String> entry : data.asMaps(String.class, String.class)) {
      long entryProductTypeId = Long.parseLong(entry.get("product_type_id"));

      dbFixture.deleteProductType(entryProductTypeId);
      dbFixture.insertProductType(entryProductTypeId, entry.get("product_name"));
    }
  }

  @Given("^the following products assigned to manufacturers$")
  public void setupAssignments(DataTable data) {
    // manufacturer_id | product_type_id
    ArrayList<Long> manufacturersCleaned = new ArrayList();

    for (Map<String, String> entry : data.asMaps(String.class, String.class)) {
      long entryManufacturerId = Long.parseLong(entry.get("manufacturer_id"));

      if (!manufacturersCleaned.contains(entryManufacturerId)) {
        dbFixture.deleteProductAssignments(entryManufacturerId);
        manufacturersCleaned.add(entryManufacturerId);
      }

      if (!entry.get("product_type_id").isEmpty()) {
        dbFixture.insertProductAssignment(
            entryManufacturerId,
            Long.parseLong(entry.get("product_type_id"))
        );
      }
    }
  }

  /**
   * Removes all valid product types in "anfrage_arten" to isolate the test fixtures.
   * Valid product types are predefined as configuration parameter.
   */
  private void deleteAllValidProductTypes() {
    String propertyValidProds = environment.getProperty("valid_product_types");
    for (String validProductTypeId : propertyValidProds.split(",")) {
      dbFixture.deleteProductType(Long.parseLong(validProductTypeId));
    }
  }

}
