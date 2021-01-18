package info.palamarchuk.api.cooking;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import info.palamarchuk.api.cooking.entity.Recipe;
import info.palamarchuk.api.cooking.entity.RecipeIngredient;
import info.palamarchuk.api.cooking.service.RecipeIngredientService;
import info.palamarchuk.api.cooking.service.RecipeService;
import info.palamarchuk.api.cooking.util.CurrentUrlService;
import info.palamarchuk.api.cooking.validation.RecipeAddValidator;
import lombok.RequiredArgsConstructor;

/**
 * Web-endpoint for the application.
 */
@RestController
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class RecipeEndpoint {

    private final RecipeService recipeService;
    private final RecipeIngredientService recipeIngredientService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Recipe> get(@PathVariable("id") long id) {
        return ResponseEntity.of(recipeService.getById(id));
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Recipe>> getAll() {
        return ResponseEntity.ok(recipeService.getAll());
    }

    @GetMapping(value = "/{id}/ingredients", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RecipeIngredient>> getIngredients(@PathVariable("id") long id) {
        return ResponseEntity.ok(recipeIngredientService.getAllByRecipeId(id));
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity add(@RequestBody Recipe candidate, BindingResult result, @Autowired CurrentUrlService urlService) {
        new RecipeAddValidator(recipeService).validate(candidate, result);
        if (result.hasErrors()) {
            return new ErrorResponseData(result.getAllErrors()).export(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        recipeService.add(candidate);
        return ResponseEntity.created(urlService.getUrl(candidate.getId())).build();
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity patch(@PathVariable("id") long id, @RequestBody Recipe patch, @Autowired CurrentUrlService urlService) {
        return recipeService.getById(id)
                .map(current -> {
                    patchEntity(patch, current);

                    recipeService.update(current);
                    return ResponseEntity.noContent().location(urlService.getUrl()).build();
                }).orElseGet(() -> ResponseEntity.notFound().build()); // @todo Provide additional information
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable("id") long id) {
        if (!recipeService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        recipeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private void patchEntity(final Recipe patch, final Recipe current) {
        if (current.getTitle() != null) {
            current.setTitle(patch.getTitle());
        }
        if (patch.getCookTime() != null) {
            current.setCookTime(patch.getCookTime());
        }
        if (patch.getPrecookTime() != null) {
            current.setCookTime(patch.getPrecookTime());
        }
        if (current.getAnnotation() != null) {
            current.setAnnotation(patch.getAnnotation());
        }
        if (current.getMethod() != null) {
            current.setMethod(patch.getMethod());
        }
        if (current.getLanguageId() != null) {
            current.setLanguageId(patch.getLanguageId());
        }
    }
}
