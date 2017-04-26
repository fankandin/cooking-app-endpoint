package de.regis24.business.datasource.manufacturer.list_order_types;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import cucumber.api.DataTable;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import de.regis24.business.datasource.manufacturer.SpringStepDefinition;
import java.util.Map;
import net.minidev.json.JSONArray;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ListingOrderTypeStepDefinitions extends SpringStepDefinition {

  private Response response;
  private Long manufacturerId;
  private Long productTypeId;

  @When("^I request the list of order types for manufacturer ID (\\d+) and product type (\\d+)$")
  public void listOrderTypesAllowed(Long manufacturerId, Long productTypeId) throws Exception {
    this.manufacturerId = manufacturerId;
    this.productTypeId = productTypeId;

    OkHttpClient client = new OkHttpClient();
    String url = getApiUrlBase().concat(
        String.format("/%d/orders/types/allowed?product_type_id=%d", manufacturerId,
            productTypeId));

    Request request = new Request.Builder().url(url)
        .get()
        .build();
    response = client.newCall(request).execute();
  }

  @Then("^the listed order types and names should be the following$")
  // order_type | order_name
  public void validateListed(DataTable data) throws Exception {
    assertThat(response.code(), is(200));
    DocumentContext jsonObj = JsonPath.parse(response.body().string());

    assertThat(((JSONArray)jsonObj.read("$.data[*]")).size(), is(data.asMaps(String.class, String.class).size()));
    for (Map<String, String> entry : data.asMaps(String.class, String.class)) {
      assertThat(
          jsonObj.read(String.format("$.data[?(@['id'] == %s && @['name'] == '%s')]",
              entry.get("order_type"),
              entry.get("order_name")
          )),
          is(notNullValue())
      );
    }
  }

  @Then("^the listed order types and names should be empty$")
  public void validateListedEmpty() throws Exception {
    String body = response.body().string();
    JSONArray result = JsonPath.parse(body).read("$.data"); // must be empty
    assertThat(result.size(), is(0));
  }

}
