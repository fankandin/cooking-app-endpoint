package info.palamarchuk.api.cooking;

import info.palamarchuk.api.cooking.entity.Ingredient;
import info.palamarchuk.api.cooking.entity.IngredientInfo;
import info.palamarchuk.api.cooking.util.CurrentUrlService;
import info.palamarchuk.api.cooking.validation.IngredientAddValidator;
import info.palamarchuk.api.cooking.validation.RecipeAddValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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
    public ResponseData<Ingredient> get(@PathVariable("id") int id) {
        return new ResponseData<>(service.getById(id));
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseData<Collection<Ingredient>> getAll() {
        return new ResponseData<>(service.getAll());
    }

    @GetMapping(value = "/{id}/infos", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseData<List<IngredientInfo>>> getInfos(@PathVariable("id") int id) {
        Ingredient ingredient = service.getById(id);
        return new ResponseData<>(ingredient.getInfos()).export();
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseData<Ingredient>> add(@RequestBody Ingredient candidate, @Autowired CurrentUrlService currentUrlService, BindingResult result) {
        new IngredientAddValidator(service).validate(candidate, result);
        if (result.hasErrors()) {
            return new ErrorResponseData(result.getAllErrors()).export(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        service.add(candidate);
        URI url = currentUrlService.getUrl(candidate.getId());
        return new ResponseData<>(candidate).exportCreated(url);
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity patch(@PathVariable("id") int id, @RequestBody Ingredient patch) {
        Ingredient current = service.getById(id);
        if (current == null) {
            return ResponseEntity.notFound().build(); // @todo Provide additional information
        }
        if (patch.getName() != null) {
            current.setName(patch.getName());
        }

        service.update(current);
        return new ResponseData<>(current).export();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable("id") int id) {
        Ingredient current = service.getById(id);
        if (current == null) {
            return ResponseEntity.notFound().build();
        }
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
