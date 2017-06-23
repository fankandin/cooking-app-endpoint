package info.palamarchuk.api.cooking;

import info.palamarchuk.api.cooking.entity.RecipeIngredientInfo;
import info.palamarchuk.api.cooking.validation.RecipeIngredientAddValidator;
import info.palamarchuk.api.cooking.data.RecipeIngredientPatch;
import info.palamarchuk.api.cooking.entity.RecipeIngredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipes/ingredients")
public class RecipeIngredientEndpoint {

    private final RecipeIngredientService service;

    @Autowired
    public RecipeIngredientEndpoint(RecipeIngredientService service) {
        this.service = service;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseData<RecipeIngredient> getRecipeIngredient(@PathVariable("id") long id) {
        return new ResponseData<>(service.getById(id));
    }

    @GetMapping(value = "/{id}/infos", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseData<List<RecipeIngredientInfo>>> getInfos(@PathVariable("id") long id) {
        RecipeIngredient recipeIngredient = service.getById(id);
        return new ResponseData<>(recipeIngredient.getInfos()).export();
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseData<RecipeIngredient>> addRecipeIngredient(@RequestBody RecipeIngredientPatch data, BindingResult result) {
        new RecipeIngredientAddValidator().validate(data, result);
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build(); // @todo Provide additional information
        }
        RecipeIngredient candidate = new RecipeIngredient(data.recipeId, data.ingredientId, data.amount, data.measurement);
        if (data.isAmountNetto != null) {
            candidate.setAmountNetto(data.isAmountNetto);
        }

        service.add(candidate);
        return new ResponseData<>(candidate).export();
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseData<RecipeIngredient>> patchRecipe(@PathVariable("id") long id, @RequestBody RecipeIngredientPatch patch) {
        RecipeIngredient current = service.getById(id);
        if (current == null) {
            return ResponseEntity.notFound().build(); // @todo Provide additional information
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

        service.update(current);
        return new ResponseData<>(current).export();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteRecipe(@PathVariable("id") long id) {
        RecipeIngredient current = service.getById(id);
        if (current == null) {
            return ResponseEntity.notFound().build();
        }
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
