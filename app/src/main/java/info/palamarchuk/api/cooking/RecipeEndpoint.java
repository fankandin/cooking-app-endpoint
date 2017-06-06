package info.palamarchuk.api.cooking;

import info.palamarchuk.api.cooking.entity.Recipe;
import info.palamarchuk.api.cooking.entity.RecipeInfo;
import info.palamarchuk.api.cooking.entity.RecipeIngredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ResponseData<Recipe>> addRecipe(@RequestBody Recipe candidate) {
        try {
            service.add(candidate);
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

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseData<Recipe>> updateRecipe(@PathVariable("id") long id, @RequestBody Recipe candidate) {
        Recipe current = service.getById(id);
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

        service.update(candidate);
        return new ResponseData<>(candidate).export();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteRecipe(@PathVariable("id") long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
