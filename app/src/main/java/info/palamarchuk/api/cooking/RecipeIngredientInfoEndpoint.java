package info.palamarchuk.api.cooking;

import info.palamarchuk.api.cooking.entity.RecipeIngredientInfo;
import info.palamarchuk.api.cooking.validation.RecipeIngredientInfoAddValidator;
import info.palamarchuk.api.cooking.validation.RecipeIngredientInfoUpdateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recipes/ingredients/infos")
public class RecipeIngredientInfoEndpoint {

    RecipeIngredientInfoService service;

    @Autowired
    public RecipeIngredientInfoEndpoint(RecipeIngredientInfoService ingredientInfoService) {
        this.service = ingredientInfoService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseData<RecipeIngredientInfo>> getRecipeIngredientInfo(@PathVariable("id") long id) {
        return new ResponseData<>(service.getById(id)).export();
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseData<RecipeIngredientInfo>> addRecipeIngredientInfo(@RequestBody RecipeIngredientInfo candidate, BindingResult result) {
        new RecipeIngredientInfoAddValidator(service).validate(candidate, result);
        if (result.hasErrors()) {
            return new ErrorResponseData(result.getAllErrors()).export(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        service.add(candidate);
        return new ResponseData<>(candidate).export();
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseData<RecipeIngredientInfo>> updateRecipeIngredientInfo(@PathVariable("id") long id, @RequestBody RecipeIngredientInfo candidate, BindingResult result) {
        if (candidate.getId() == null) {
            candidate.setId(id);
        }
        RecipeIngredientInfo existing = service.getById(id);
        new RecipeIngredientInfoUpdateValidator(service, existing).validate(candidate, result);
        if (result.hasErrors()) {
            return new ErrorResponseData(result.getAllErrors()).export(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        service.update(candidate);
        return new ResponseData<>(candidate).export();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteRecipe(@PathVariable("id") long id) {
        RecipeIngredientInfo current = service.getById(id);
        if (current == null) {
            return ResponseEntity.notFound().build();
        }
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
