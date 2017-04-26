package de.regis24.business.datasource.manufacturer.list_products;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import de.regis24.business.datasource.manufacturer.SpringStepDefinition;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.http.HttpStatus;

public class AddProductStepDefinitions extends SpringStepDefinition {

  private long productTypeId;
  private long manufacturerId;
  private Response response;
  private List<Long> existingProducts;

  @When("^I add (.*) to manufacturer ID (.*)$")
  public void assignProduct(long productTypeId, long manufacturerId) throws Exception {
    this.productTypeId = productTypeId;
    this.manufacturerId = manufacturerId;
    existingProducts = dbFixture.findProductsForManufacturer(manufacturerId);

    String target = String.format("%s/%d/products", getApiUrlBase(), this.manufacturerId);

    Map<String, Object> data = new HashMap<>();
    data.put("product_types", new Long[]{this.productTypeId});

    String requestBody = objectMapper.writeValueAsString(data);

    response = client().newCall(new Request.Builder()
        .url(target)
        .post(RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), requestBody))
        .build()
    ).execute();
  }

  @Then("^the result should be product added$")
  public void verifyProductAdded() throws Exception {
    assertThat(response.code(), is(HttpStatus.CREATED.value()));
    assertTrue(
        dbFixture.findProductsForManufacturer(this.manufacturerId).contains(this.productTypeId));
  }

  @Then("^the result should be product not added$")
  public void verifyProductNotAdded() throws Exception {
    assertThat(response.code(), is(HttpStatus.BAD_REQUEST.value()));
    List<Long> actualProducts = dbFixture.findProductsForManufacturer(manufacturerId);
    assertEquals(actualProducts.size(), existingProducts.size());
    assertTrue(existingProducts.containsAll(actualProducts));
  }
}
