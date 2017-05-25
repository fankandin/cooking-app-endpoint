package info.palamarchuk.api.cooking;

import info.palamarchuk.api.cooking.entity.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;

/**
 * Web-endpoint for the application.
 */
@RestController
//@Api(description = "Endpoint for managing recipe")
@RequestMapping("/recipes")
//@CrossOrigin(origins = "http://localhost:8081", maxAge = 4800, allowCredentials = "false")
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

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseData<Collection<Recipe>> getRecipes() {
        return new ResponseData<>(recipeService.getAll());
    }

    @GetMapping(value = "/{recipe_id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//  @ApiOperation(
//      value = "List all recipe")
//  @ApiResponses({
//      @ApiResponse(code = 200, message = "OK"),
//      @ApiResponse(code = 500, message = "Error during processing", response = Void.class)
//  })
    public ResponseData<Recipe> getRecipe(@PathVariable("recipe_id") int id) {
        return new ResponseData<>(recipeService.getById(id));
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseData<Collection<Recipe>>> addRecipe(@RequestBody Recipe recipe) {
        try {
            recipeService.add(recipe);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity(new ResponseData<>(Collections.singletonList(recipe)), HttpStatus.CREATED);
    }

}
