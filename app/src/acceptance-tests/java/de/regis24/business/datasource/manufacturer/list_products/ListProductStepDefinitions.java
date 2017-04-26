package de.regis24.business.datasource.manufacturer.list_products;

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

public class ListProductStepDefinitions extends SpringStepDefinition {

  private Response response;
  private Long manufacturerId;

  @When("I request the list of allowed products for manufacturer ID (\\d+)")
  public void requestList(Long manufacturerId) throws Exception {
    this.manufacturerId = manufacturerId;

    OkHttpClient client = new OkHttpClient();
    String url = getApiUrlBase().concat(String.format("/%d/products/types/allowed", manufacturerId));

    Request request = new Request.Builder().url(url)
        .get()
        .build();
    response = client.newCall(request).execute();
  }

  @Then("^the listed products should be the following$")
  // product_type_id | product_name
  public void validateListed(DataTable data) throws Exception {
    assertThat(response.code(), is(200));
    DocumentContext jsonObj = JsonPath.parse(response.body().string());

    assertThat(((JSONArray)jsonObj.read("$.data[*]")).size(), is(data.asMaps(String.class, String.class).size()));
    for (Map<String, String> entry : data.asMaps(String.class, String.class)) {
      assertThat(
          jsonObj.read(String.format("$.data[?(@['productType'] == %s && @['name'] == '%s')]",
              entry.get("product_type_id"),
              entry.get("product_name")
          )),
          is(notNullValue())
      );
    }
  }

  @Then("^the listed products should be empty$")
  public void validateListedEmpty() throws Exception {
    String body = response.body().string();
    JSONArray result = JsonPath.parse(body).read("$.data"); // must be empty
    assertThat(result.size(), is(0));
  }

}
