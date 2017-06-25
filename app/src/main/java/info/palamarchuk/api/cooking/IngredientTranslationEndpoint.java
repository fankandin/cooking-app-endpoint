package info.palamarchuk.api.cooking;

import info.palamarchuk.api.cooking.entity.IngredientTranslation;
import info.palamarchuk.api.cooking.service.IngredientTranslationService;
import info.palamarchuk.api.cooking.util.CurrentUrlService;
import info.palamarchuk.api.cooking.validation.IngredientInfoAddValidator;
import info.palamarchuk.api.cooking.validation.IngredientInfoUpdateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ingredients/translations")
public class IngredientTranslationEndpoint {

    IngredientTranslationService service;

    @Autowired
    public IngredientTranslationEndpoint(IngredientTranslationService ingredientTranslationService) {
        this.service = ingredientTranslationService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseData<IngredientTranslation>> getIngredientInfo(@PathVariable("id") int id) {
        return new ResponseData<>(service.getById(id)).export();
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity addIngredientInfo(@RequestBody IngredientTranslation candidate, BindingResult result, @Autowired CurrentUrlService urlService) {
        new IngredientInfoAddValidator(service).validate(candidate, result);
        if (result.hasErrors()) {
            return new ErrorResponseData(result.getAllErrors()).export(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        service.add(candidate);
        return ResponseEntity.created(urlService.getUrl(candidate.getId())).build();
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity updateIngredientInfo(@PathVariable("id") int id, @RequestBody IngredientTranslation patch, BindingResult result, @Autowired CurrentUrlService urlService) {
        if (patch.getId() == null) {
            patch.setId(id);
        }
        IngredientTranslation current = service.getById(id);
        new IngredientInfoUpdateValidator(service, current).validate(patch, result);
        if (result.hasErrors()) {
            return new ErrorResponseData(result.getAllErrors()).export(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        if (patch.getLanguageId() != null && patch.getLanguageId() != current.getLanguageId()) {
            current.setLanguageId(patch.getLanguageId());
        }
        if (patch.getName() != null && patch.getName() != current.getName()) {
            current.setName(patch.getName());
        }
        if (patch.getNameExtra() != null && patch.getNameExtra() != current.getNameExtra()) {
            current.setNameExtra(patch.getNameExtra());
        }
        if (patch.getNote() != null && patch.getNote() != current.getNote()) {
            current.setNote(patch.getNote());
        }

        service.update(current);
        return ResponseEntity.noContent().location(urlService.getUrl()).build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteRecipe(@PathVariable("id") int id) {
        IngredientTranslation current = service.getById(id);
        if (current == null) {
            return ResponseEntity.notFound().build();
        }
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
