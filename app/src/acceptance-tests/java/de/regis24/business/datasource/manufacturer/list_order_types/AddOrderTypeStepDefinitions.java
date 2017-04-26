package de.regis24.business.datasource.manufacturer.list_order_types;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import cucumber.api.DataTable;
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

public class AddOrderTypeStepDefinitions extends SpringStepDefinition {

  private Long orderTypeId;
  private Long productTypeId;
  private Long manufacturerId;

  private Response response;
  private List<Long> existingOrderTypes;

  @When("^I add (.*) to (.*) of manufacturer ID (.*)$")
  public void assign(Long orderTypeId, Long productTypeId, Long manufacturerId) throws Exception {
    this.orderTypeId = orderTypeId;
    this.productTypeId = productTypeId;
    this.manufacturerId = manufacturerId;

    existingOrderTypes = dbFixture.findOrderTypes(manufacturerId);

    executeRequest(new Long[]{orderTypeId});
  }

  @Then("the result should be order type added")
  public void orderTypeWasAdded() throws Exception {
    assertThat(response.code(), equalTo(HttpStatus.CREATED.value()));
    List<Long> orderTypes = dbFixture.findOrderTypes(manufacturerId);
    assertTrue(orderTypes.contains(orderTypeId));
  }

  @Then("the result should be order type not added")
  public void orderTypeWasNotAdded() throws Exception {
    assertThat(response.code(), equalTo(HttpStatus.BAD_REQUEST.value()));
    List<Long> actualOrderTypes = dbFixture.findOrderTypes(manufacturerId);
    assertEquals(actualOrderTypes.size(), existingOrderTypes.size());
    assertTrue(existingOrderTypes.containsAll(actualOrderTypes));
  }

  @When("^I add to the manufacturer ID (.*) and product type (.*) the order types$")
  public void addMultipleOrderTypes(Long manufacturerId, Long productTypeId,
      DataTable orderTypeTable) throws Exception {
    this.manufacturerId = manufacturerId;
    this.productTypeId = productTypeId;
    List<Long> orderTypes = orderTypeTable.asList(Long.class);

    executeRequest(orderTypes.toArray(new Long[0]));
  }

  @Then("^the manufacturer should have the following order types$")
  public void shouldHaveTheOrderTypes(DataTable orderTypeTable) throws Exception {
    assertThat(response.code(), equalTo(HttpStatus.CREATED.value()));
    List<Long> orderTypes = orderTypeTable.asList(Long.class);
    assertTrue(dbFixture.findOrderTypes(manufacturerId).containsAll(orderTypes));
  }

  @Then("^then the manufacturer has still no assigned order types$")
  public void shouldHaveNoOrderTypes() throws Exception {
    assertThat(response.code(), equalTo(HttpStatus.BAD_REQUEST.value()));
    List<Long> orderTypes = dbFixture.findOrderTypes(this.manufacturerId);
    assertTrue(orderTypes.isEmpty());
  }

  private void executeRequest(Long[] orderTypes) throws Exception {
    String target = String.format("%s/%d/orders/types", getApiUrlBase(), this.manufacturerId);

    Map<String, Object> data = new HashMap<>();
    data.put("product_type", this.productTypeId);
    data.put("order_types", orderTypes);

    String requestBody = objectMapper.writeValueAsString(data);
    MediaType mediaType =
        MediaType.parse(org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE);

    response = client().newCall(new Request.Builder()
        .url(target)
        .post(RequestBody.create(mediaType, requestBody))
        .build()
    ).execute();
  }
}
