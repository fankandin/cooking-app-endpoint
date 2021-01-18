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

import info.palamarchuk.api.cooking.entity.Ingredient;
import info.palamarchuk.api.cooking.entity.IngredientTranslation;
import info.palamarchuk.api.cooking.service.IngredientService;
import info.palamarchuk.api.cooking.util.CurrentUrlService;
import info.palamarchuk.api.cooking.validation.IngredientAddValidator;
import info.palamarchuk.api.cooking.validation.IngredientUpdateValidator;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/ingredients")
@RequiredArgsConstructor
public class IngredientEndpoint {

    private final IngredientService service;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Ingredient> get(@PathVariable("id") long id) {
        return ResponseEntity.of(service.getById(id));
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Ingredient>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping(value = "/{id}/translations", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<IngredientTranslation>> getTranslations(@PathVariable("id") long id) {
        return service.getById(id)
                .map(ingredient -> ResponseEntity.ok(ingredient.getTranslations()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity add(@RequestBody Ingredient candidate, BindingResult result, @Autowired CurrentUrlService urlService) {
        new IngredientAddValidator(service).validate(candidate, result);
        if (result.hasErrors()) {
            return new ErrorResponseData(result.getAllErrors()).export(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        service.add(candidate);
        return ResponseEntity.created(urlService.getUrl(candidate.getId())).build();
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity patch(@PathVariable("id") long id, @RequestBody Ingredient patch, BindingResult result, @Autowired CurrentUrlService urlService) {
        return service.getById(id)
                .map(current -> {
                    new IngredientUpdateValidator(service).validate(patch, result);
                    if (result.hasErrors()) {
                        return new ErrorResponseData(result.getAllErrors()).export(HttpStatus.UNPROCESSABLE_ENTITY);
                    }
                    current.setName(patch.getName());
                    service.update(current);
                    return ResponseEntity.noContent().location(urlService.getUrl()).build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build()); // @todo Provide additional information
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable("id") long id) {
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
