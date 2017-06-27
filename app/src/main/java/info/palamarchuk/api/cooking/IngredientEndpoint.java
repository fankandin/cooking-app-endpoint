package info.palamarchuk.api.cooking;

import info.palamarchuk.api.cooking.entity.Ingredient;
import info.palamarchuk.api.cooking.entity.IngredientTranslation;
import info.palamarchuk.api.cooking.service.IngredientService;
import info.palamarchuk.api.cooking.util.CurrentUrlService;
import info.palamarchuk.api.cooking.validation.IngredientAddValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/ingredients")
public class IngredientEndpoint {

    private final IngredientService service;

    @Autowired
    public IngredientEndpoint(IngredientService service) {
        this.service = service;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseData<Ingredient> get(@PathVariable("id") long id) {
        return new ResponseData<>(service.getById(id));
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseData<Collection<Ingredient>> getAll() {
        return new ResponseData<>(service.getAll());
    }

    @GetMapping(value = "/{id}/translations", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseData<List<IngredientTranslation>>> getTranslations(@PathVariable("id") long id) {
        Ingredient ingredient = service.getById(id);
        return new ResponseData<>(ingredient.getTranslations()).export();
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity add(@RequestBody Ingredient candidate, BindingResult result, @Autowired CurrentUrlService urlService) {
        new IngredientAddValidator(service).validate(candidate, result);
        if (result.hasErrors()) {
            return new ErrorResponseData(result.getAllErrors()).export(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        service.add(candidate);
        return ResponseEntity.created(urlService.getUrl(candidate.getId())).build();
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity patch(@PathVariable("id") long id, @RequestBody Ingredient patch, @Autowired CurrentUrlService urlService) {
        Ingredient current = service.getById(id);
        if (current == null) {
            return ResponseEntity.notFound().build(); // @todo Provide additional information
        }
        if (patch.getName() != null) {
            current.setName(patch.getName());
        }

        service.update(current);
        return ResponseEntity.noContent().location(urlService.getUrl()).build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable("id") long id) {
        Ingredient current = service.getById(id);
        if (current == null) {
            return ResponseEntity.notFound().build();
        }
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
