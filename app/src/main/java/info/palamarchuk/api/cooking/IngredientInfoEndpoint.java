package info.palamarchuk.api.cooking;

import info.palamarchuk.api.cooking.entity.IngredientInfo;
import info.palamarchuk.api.cooking.validation.IngredientInfoAddValidator;
import info.palamarchuk.api.cooking.validation.IngredientInfoUpdateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ingredients/infos")
public class IngredientInfoEndpoint {

    IngredientInfoService service;

    @Autowired
    public IngredientInfoEndpoint(IngredientInfoService ingredientInfoService) {
        this.service = ingredientInfoService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseData<IngredientInfo>> getIngredientInfo(@PathVariable("id") int id) {
        return new ResponseData<>(service.getById(id)).export();
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseData<IngredientInfo>> addIngredientInfo(@RequestBody IngredientInfo candidate, BindingResult result) {
        new IngredientInfoAddValidator(service).validate(candidate, result);
        if (result.hasErrors()) {
            return new ErrorResponseData(result.getAllErrors()).export(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        service.add(candidate);
        return new ResponseData<>(candidate).export();
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseData<IngredientInfo>> updateIngredientInfo(@PathVariable("id") int id, @RequestBody IngredientInfo candidate, BindingResult result) {
        if (candidate.getId() == null) {
            candidate.setId(id);
        }
        IngredientInfo current = service.getById(id);
        new IngredientInfoUpdateValidator(service, current).validate(candidate, result);
        if (result.hasErrors()) {
            return new ErrorResponseData(result.getAllErrors()).export(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        if (candidate.getLanguageId() != null && candidate.getLanguageId() != current.getLanguageId()) {
            current.setLanguageId(candidate.getLanguageId());
        }
        if (candidate.getName() != null && candidate.getName() != current.getName()) {
            current.setName(candidate.getName());
        }
        if (candidate.getNameExtra() != null && candidate.getNameExtra() != current.getNameExtra()) {
            current.setNameExtra(candidate.getNameExtra());
        }
        if (candidate.getNote() != null && candidate.getNote() != current.getNote()) {
            current.setNote(candidate.getNote());
        }
        service.update(current);
        return new ResponseData<>(current).export();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteRecipe(@PathVariable("id") int id) {
        IngredientInfo current = service.getById(id);
        if (current == null) {
            return ResponseEntity.notFound().build();
        }
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}