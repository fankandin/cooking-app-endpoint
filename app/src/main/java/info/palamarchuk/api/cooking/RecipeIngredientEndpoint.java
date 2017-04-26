package info.palamarchuk.api.cooking;

import info.palamarchuk.api.cooking.data.RecipeIngredientPatch;
import info.palamarchuk.api.cooking.entity.RecipeIngredient;
import info.palamarchuk.api.cooking.service.RecipeIngredientService;
import info.palamarchuk.api.cooking.util.CurrentUrlService;
import info.palamarchuk.api.cooking.validation.RecipeIngredientAddValidator;
import info.palamarchuk.api.cooking.validation.RecipeIngredientUpdateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recipes/ingredients")
public class RecipeIngredientEndpoint {

    private final RecipeIngredientService service;

    @Autowired
    public RecipeIngredientEndpoint(RecipeIngredientService service) {
        this.service = service;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseData<RecipeIngredient> get(@PathVariable("id") long id) {
        return new ResponseData<>(service.getById(id));
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity add(@RequestBody RecipeIngredientPatch data, BindingResult result, @Autowired CurrentUrlService urlService) {
        new RecipeIngredientAddValidator().validate(data, result);
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build(); // @todo Provide additional information
        }
        RecipeIngredient candidate = new RecipeIngredient(data.recipeId, data.ingredientId, data.amount, data.measurement);
        if (data.isAmountNetto != null) {
            candidate.setAmountNetto(data.isAmountNetto);
        }

        service.add(candidate);
        return ResponseEntity.created(urlService.getUrl(candidate.getId())).build();
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity patch(@PathVariable("id") long id, @RequestBody RecipeIngredientPatch patch, BindingResult result, @Autowired CurrentUrlService urlService) {
        RecipeIngredient current = service.getById(id);
        if (current == null) {
            return ResponseEntity.notFound().build(); // @todo Provide additional information
        }

        new RecipeIngredientUpdateValidator(service, current).validate(patch, result);
        if (result.hasErrors()) {
            return new ErrorResponseData(result.getAllErrors()).export(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        // only touch fields which are really changed
        // it is allowed to change ingredientId, but it is not allowed to change recipeId
        if (patch.ingredientId != null) {
            current.setIngredientId(patch.ingredientId); // updated.ingredientId itself is empty
        }
        if (patch.amount != null) {
            current.setAmount(patch.amount);
        }
        if (patch.measurement != null) {
            current.setMeasurement(patch.measurement);
        }
        if (patch.isAmountNetto != null) {
            current.setAmountNetto(patch.isAmountNetto);
        }
        if (patch.preparation != null) {
            current.setPreparation(patch.preparation);
        }

        service.update(current);
        return ResponseEntity.noContent().location(urlService.getUrl()).build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable("id") long id) {
        RecipeIngredient current = service.getById(id);
        if (current == null) {
            return ResponseEntity.notFound().build();
        }
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
