package info.palamarchuk.api.cooking;

import info.palamarchuk.api.cooking.entity.Ingredient;
import info.palamarchuk.api.cooking.entity.IngredientInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseData<Collection<Ingredient>> getIngredients() {
        return new ResponseData<>(service.getAll());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseData<Ingredient> getIngredient(@PathVariable("id") int id) {
        return new ResponseData<>(service.getById(id));
    }

    @GetMapping(value = "/{id}/infos", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseData<List<IngredientInfo>>> getInfos(@PathVariable("id") int id) {
        Ingredient ingredient = service.getById(id);
        return new ResponseData<>(ingredient.getInfos()).export();
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseData<Ingredient>> addIngredient(@RequestBody Ingredient ingredient) {
        try {
            service.add(ingredient);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        /*
        // this approach can be used to implement the pure REST standard when a location to a just created resource is sent
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
            .buildAndExpand(ingredient.getId()).toUri();
        return ResponseEntity.created(location).build();
        */
        return new ResponseData<>(ingredient).export();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteRecipe(@PathVariable("id") int id) {
        Ingredient current = service.getById(id);
        if (current == null) {
            return ResponseEntity.notFound().build();
        }
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
