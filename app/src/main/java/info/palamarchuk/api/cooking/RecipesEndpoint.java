package info.palamarchuk.api.cooking;

import info.palamarchuk.api.cooking.entity.Recipe;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * Web-endpoint for the application.
 */
@RestController
@Api(description = "Endpoint for managing recipe")
public class RecipesEndpoint {

  private final RecipeService recipeService;

  /**
   * Constructor.
   *
   * @param recipeService Recipe Service
   */
  @Autowired
  public RecipesEndpoint(RecipeService recipeService) {
    this.recipeService = recipeService;
  }

  @GetMapping(value = "/recipes/{recipe_id}",
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ApiOperation(
      value = "List all recipe")
  @ApiResponses({
      @ApiResponse(code = 200, message = "OK"),
      @ApiResponse(code = 500, message = "Error during processing", response = Void.class)
  })
  public ResponseData<Collection<Recipe>> getRecipe(@PathVariable("recipe_id") int id) {
    return new ResponseData<>(recipeService.findById(id));
  }

}
