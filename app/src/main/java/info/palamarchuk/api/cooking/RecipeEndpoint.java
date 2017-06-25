package info.palamarchuk.api.cooking;

import info.palamarchuk.api.cooking.entity.Recipe;
import info.palamarchuk.api.cooking.entity.RecipeIngredient;
import info.palamarchuk.api.cooking.service.RecipeService;
import info.palamarchuk.api.cooking.util.CurrentUrlService;
import info.palamarchuk.api.cooking.validation.RecipeAddValidator;
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

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseData<Recipe>> get(@PathVariable("id") long id) {
        return new ResponseData<>(service.getById(id)).export();
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseData<List<Recipe>>> getAll() {
        return new ResponseData<>(service.getAll()).export();
    }

    @GetMapping(value = "/{id}/ingredients", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseData<List<RecipeIngredient>>> getIngredients(@PathVariable("id") long id) {
        Recipe recipe = service.getById(id);
        return new ResponseData<>(recipe.getIngredients()).export();
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity addRecipe(@RequestBody Recipe candidate, BindingResult result, @Autowired CurrentUrlService urlService) {
        new RecipeAddValidator(service).validate(candidate, result);
        if (result.hasErrors()) {
            return new ErrorResponseData(result.getAllErrors()).export(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        service.add(candidate);
        return ResponseEntity.created(urlService.getUrl(candidate.getId())).build();
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity updateRecipe(@PathVariable("id") long id, @RequestBody Recipe patch, @Autowired CurrentUrlService urlService) {
        Recipe current = service.getById(id);
        if (current == null) {
            return ResponseEntity.notFound().build(); // @todo Provide additional information
        }

        if (patch.getCookTime() != null) {
            current.setCookTime(patch.getCookTime());
        }
        if (patch.getPrecookTime() != null) {
            current.setCookTime(patch.getPrecookTime());
        }
        if (current.getTitle() != null) {
            current.setTitle(patch.getTitle());
        }

        service.update(current);
        return ResponseEntity.noContent().location(urlService.getUrl()).build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteRecipe(@PathVariable("id") long id) {
        Recipe current = service.getById(id);
        if (current == null) {
            return ResponseEntity.notFound().build();
        }
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
