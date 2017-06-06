package info.palamarchuk.api.cooking;

import info.palamarchuk.api.cooking.validation.RecipeInfoAddValidator;
import info.palamarchuk.api.cooking.entity.RecipeInfo;
import info.palamarchuk.api.cooking.validation.RecipeInfoUpdateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recipes/infos")
public class RecipeInfoEndpoint {

    RecipeInfoService service;

    @Autowired
    public RecipeInfoEndpoint(RecipeInfoService recipeInfoService) {
        this.service = recipeInfoService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseData<RecipeInfo>> getRecipeInfo(@PathVariable("id") long id) {
        return new ResponseData<>(service.getById(id)).export();
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseData<RecipeInfo>> addRecipeInfo(@RequestBody RecipeInfo candidate, BindingResult result) {
        new RecipeInfoAddValidator(service).validate(candidate, result);
        if (result.hasErrors()) {
            return new ErrorResponseData(result.getAllErrors()).export(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        service.add(candidate);
        return new ResponseData<>(candidate).export();
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseData<RecipeInfo>> updateRecipeInfo(@PathVariable("id") long id, @RequestBody RecipeInfo candidate, BindingResult result) {
        if (candidate.getId() == null) {
            candidate.setId(id);
        }
        RecipeInfo existing = service.getById(id);
        new RecipeInfoUpdateValidator(service, existing).validate(candidate, result);
        if (result.hasErrors()) {
            return new ErrorResponseData(result.getAllErrors()).export(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        service.update(candidate);
        return new ResponseData<>(candidate).export();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteRecipe(@PathVariable("id") long id) {
        RecipeInfo recipeInfo = service.getById(id);
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
