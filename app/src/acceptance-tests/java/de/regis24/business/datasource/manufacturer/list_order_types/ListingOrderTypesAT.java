package de.regis24.business.datasource.manufacturer.list_order_types;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
  features = {
    "classpath:de/regis24/business/datasource/manufacturer/list_order_types/listing_of_order_types.feature",
    "classpath:de/regis24/business/datasource/manufacturer/list_order_types/add_order_types.feature"
  },
  plugin = {"html:target/cucumber-html-report", "json:target/cucumber-json-report.json"}
)
public class ListingOrderTypesAT {
}
