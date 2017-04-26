package de.regis24.business.datasource.manufacturer.list_products;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
  features = {
    "classpath:de/regis24/business/datasource/manufacturer/list_products/listing_products.feature",
    "classpath:de/regis24/business/datasource/manufacturer/list_products/add_product.feature"
  },
  plugin = {"html:target/cucumber-html-report", "json:target/cucumber-json-report.json"}
)
public class ListProductAT {
}
