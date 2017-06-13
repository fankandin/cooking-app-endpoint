package info.palamarchuk.api.cooking;

import info.palamarchuk.api.cooking.entity.Recipe;
import info.palamarchuk.api.cooking.entity.RecipeInfo;
import info.palamarchuk.api.cooking.entity.RecipeIngredient;
import info.palamarchuk.api.cooking.validation.RecipeAddValidator;
import info.palamarchuk.api.cooking.validation.RecipeInfoAddValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Web-endpoint for the application.
 */
@RestController
@RequestMapping("/recipes")
public class RecipeEndpoint {

    private final RecipeService service;

    @Autowired
    public RecipeEndpoint(RecipeService service) {
        this.service = service;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseData<List<Recipe>>> getRecipes() {
        return new ResponseData<>(service.getAll()).export();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseData<Recipe>> getRecipe(@PathVariable("id") long id) {
        return new ResponseData<>(service.getById(id)).export();
    }

    @GetMapping(value = "/{id}/ingredients", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseData<List<RecipeIngredient>>> getIngredients(@PathVariable("id") long id) {
        Recipe recipe = service.getById(id);
        return new ResponseData<>(recipe.getIngredients()).export();
    }

    @GetMapping(value = "/{id}/infos", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseData<List<RecipeInfo>>> getInfos(@PathVariable("id") long id) {
        Recipe recipe = service.getById(id);
        return new ResponseData<>(recipe.getInfos()).export();
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseData<Recipe>> addRecipe(@RequestBody Recipe candidate, BindingResult result) {
        new RecipeAddValidator(service).validate(candidate, result);
        if (result.hasErrors()) {
            return new ErrorResponseData(result.getAllErrors()).export(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        service.add(candidate);
        return new ResponseData<>(candidate).export();
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseData<Recipe>> updateRecipe(@PathVariable("id") long id, @RequestBody Recipe candidate) {
        Recipe current = service.getById(id);
        if (current == null) {
            return ResponseEntity.notFound().build(); // @todo Provide additional information
        }

        if (candidate.getCookTime() != null) {
            current.setCookTime(candidate.getCookTime());
        }
        if (candidate.getPrecookTime() != null) {
            current.setCookTime(candidate.getPrecookTime());
        }
        if (current.getName() != null) {
            current.setName(candidate.getName());
        }

        service.update(candidate);
        return new ResponseData<>(candidate).export();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteRecipe(@PathVariable("id") long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
