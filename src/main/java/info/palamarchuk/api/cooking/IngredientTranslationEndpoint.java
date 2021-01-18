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

import info.palamarchuk.api.cooking.entity.IngredientTranslation;
import info.palamarchuk.api.cooking.service.IngredientTranslationService;
import info.palamarchuk.api.cooking.util.CurrentUrlService;
import info.palamarchuk.api.cooking.validation.IngredientTranslationAddValidator;
import info.palamarchuk.api.cooking.validation.IngredientTranslationUpdateValidator;

@RestController
@RequestMapping("/ingredients/translations")
public class IngredientTranslationEndpoint {

    IngredientTranslationService service;

    @Autowired
    public IngredientTranslationEndpoint(IngredientTranslationService ingredientTranslationService) {
        this.service = ingredientTranslationService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IngredientTranslation> get(@PathVariable("id") long id) {
        return ResponseEntity.of(service.getById(id));
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity add(@RequestBody IngredientTranslation candidate, BindingResult result, @Autowired CurrentUrlService urlService) {
        new IngredientTranslationAddValidator(service).validate(candidate, result);
        if (result.hasErrors()) {
            return new ErrorResponseData(result.getAllErrors()).export(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        service.add(candidate);
        return ResponseEntity.created(urlService.getUrl(candidate.getId())).build();
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity patch(@PathVariable("id") long id, @RequestBody IngredientTranslation patch, BindingResult result, @Autowired CurrentUrlService urlService) {
        if (patch.getId() == null) {
            try {
                patch.setId(Math.toIntExact(id));
            } catch (ArithmeticException e) {
                return ResponseEntity.notFound().build();
            }
        }

        return service.getById(id)
                .map(current -> {
                    new IngredientTranslationUpdateValidator(service, current).validate(patch, result);
                    if (result.hasErrors()) {
                        return new ErrorResponseData(result.getAllErrors()).export(HttpStatus.UNPROCESSABLE_ENTITY);
                    }
                    patchEntity(patch, current);

                    service.update(current);
                    return ResponseEntity.noContent().location(urlService.getUrl()).build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable("id") long id) {
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private void patchEntity(final IngredientTranslation patch, final IngredientTranslation current) {
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
    }
}
