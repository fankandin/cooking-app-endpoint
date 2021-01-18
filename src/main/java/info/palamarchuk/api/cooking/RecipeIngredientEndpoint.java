package info.palamarchuk.api.cooking;

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

import info.palamarchuk.api.cooking.data.RecipeIngredientPatch;
import info.palamarchuk.api.cooking.entity.RecipeIngredient;
import info.palamarchuk.api.cooking.service.RecipeIngredientService;
import info.palamarchuk.api.cooking.util.CurrentUrlService;
import info.palamarchuk.api.cooking.validation.RecipeIngredientAddValidator;
import info.palamarchuk.api.cooking.validation.RecipeIngredientUpdateValidator;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/recipes/ingredients")
@RequiredArgsConstructor
public class RecipeIngredientEndpoint {

    private final RecipeIngredientService service;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RecipeIngredient> get(@PathVariable("id") long id) {
        return ResponseEntity.of(service.getById(id));
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RecipeIngredient> add(@RequestBody RecipeIngredientPatch data, BindingResult result, @Autowired CurrentUrlService urlService) {
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

    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity patch(@PathVariable("id") long id, @RequestBody RecipeIngredientPatch patch, BindingResult result, @Autowired CurrentUrlService urlService) {
        return service.getById(id)
                .map(current -> {
                    new RecipeIngredientUpdateValidator(service, current).validate(patch, result);
                    if (result.hasErrors()) {
                        return new ErrorResponseData(result.getAllErrors()).export(HttpStatus.UNPROCESSABLE_ENTITY);
                    }

                    patchEntity(patch, current);

                    service.update(current);
                    return ResponseEntity.noContent().location(urlService.getUrl()).build();
                }).orElseGet(() -> ResponseEntity.notFound().build()); // @todo Provide additional information)
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<RecipeIngredient> delete(@PathVariable("id") long id) {
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private void patchEntity(final RecipeIngredientPatch patch, final RecipeIngredient current) {
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
    }
}
