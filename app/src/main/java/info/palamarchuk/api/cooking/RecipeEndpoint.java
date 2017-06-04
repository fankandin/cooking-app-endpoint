package info.palamarchuk.api.cooking;

import info.palamarchuk.api.cooking.data.RecipeIngredientAddValidator;
import info.palamarchuk.api.cooking.data.RecipeIngredientPatch;
import info.palamarchuk.api.cooking.entity.Ingredient;
import info.palamarchuk.api.cooking.entity.Recipe;
import info.palamarchuk.api.cooking.entity.RecipeInfo;
import info.palamarchuk.api.cooking.entity.RecipeIngredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.List;

/**
 * Web-endpoint for the application.
 */
@RestController
@RequestMapping("/recipes")
public class RecipeEndpoint {

    private final RecipeService recipeService;
    private final RecipeIngredientService recipeIngredientService;
    private final IngredientService ingredientService;

    @Autowired
    public RecipeEndpoint(RecipeService recipeService, RecipeIngredientService recipeIngredientService, IngredientService ingredientService) {
        this.recipeService = recipeService;
        this.recipeIngredientService = recipeIngredientService;
        this.ingredientService = ingredientService;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseData<List<Recipe>>> getRecipes() {
        return new ResponseData<>(recipeService.getAll()).export();
    }

    @GetMapping(value = "/{recipe_id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseData<Recipe>> getRecipe(@PathVariable("recipe_id") long id) {
        return new ResponseData<>(recipeService.getById(id)).export();
    }

    @GetMapping(value = "/{recipe_id}/ingredients", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseData<List<RecipeIngredient>>> getIngredients(@PathVariable("recipe_id") long id) {
        return new ResponseData<>(recipeIngredientService.getAllByRecipeId(id)).export();
    }

    @GetMapping(value = "/{recipe_id}/infos", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseData<List<RecipeInfo>>> getInfos(@PathVariable("recipe_id") long id) {
        Recipe recipe = recipeService.getById(id);
        return new ResponseData<>(recipe.getInfos()).export();
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseData<Recipe>> addRecipe(@RequestBody Recipe candidate) {
        try {
            recipeService.add(candidate);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        /*
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
            .buildAndExpand(candidate.getId()).toUri();
        return ResponseEntity.created(location).build();
        */
        return new ResponseData<>(candidate).export();
    }

    @PostMapping(value = "/{recipe_id}/ingredients", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseData<RecipeIngredient>> addRecipeIngredient(@PathVariable("recipe_id") long recipeId, @RequestBody RecipeIngredientPatch data, BindingResult result) {
        Recipe recipe = recipeService.getById(recipeId);
        if (recipe == null) {
            return ResponseEntity.notFound().build(); // @todo Provide additional information
        }
        Ingredient ingredient = ingredientService.getById(data.ingredientId);
        if (ingredient == null) {
            return ResponseEntity.notFound().build(); // @todo Provide additional information
        }
        new RecipeIngredientAddValidator().validate(data, result);
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build(); // @todo Provide additional information
        }
        RecipeIngredient candidate = new RecipeIngredient(recipe.getId(), ingredient.getId(), data.amount, data.measurement);
        if (data.isAmountNetto != null) {
            candidate.setAmountNetto(data.isAmountNetto);
        }

        try {
            recipeIngredientService.add(candidate);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        /*
        URI location = ServletUriComponentsBuilder.fromCurrentServletMapping().path("/recipes-ingredients/{id}")
            .buildAndExpand(candidate.getId()).toUri();
        return ResponseEntity.created(location).build();
        */
        return new ResponseData<>(candidate).export();
    }

    @PutMapping(value = "/{recipe_id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseData<Recipe>> updateRecipe(@PathVariable("recipe_id") long id, @RequestBody Recipe candidate) {
        Recipe current = recipeService.getById(id);
        if (current == null) {
            return ResponseEntity.notFound().build(); // @todo Provide additional information
        }

        // only touch fields which are really changed
        if (candidate.getCookTime() != null && current.getCookTime() != candidate.getCookTime()) {
            current.setCookTime(candidate.getCookTime());
        }
        if (candidate.getPrecookTime() != null && current.getPrecookTime() != candidate.getPrecookTime()) {
            current.setCookTime(candidate.getPrecookTime());
        }
        if (current.getName() != candidate.getName()) {
            current.setName(candidate.getName());
        }

        try {
            recipeService.update(candidate);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return new ResponseData<>(candidate).export();
    }

    @DeleteMapping(value = "/{recipe_id}")
    public ResponseEntity deleteRecipe(@PathVariable("recipe_id") long id) {
        try {
            recipeService.deleteById(id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.noContent().build();
    }
}
