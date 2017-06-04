package info.palamarchuk.api.cooking;

import info.palamarchuk.api.cooking.entity.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/ingredients")
public class IngredientEndpoint {

    private final IngredientService ingredientService;

    @Autowired
    public IngredientEndpoint(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseData<Collection<Ingredient>> getIngredients() {
        return new ResponseData<>(ingredientService.getAll());
    }

    @GetMapping(value = "/{ingredient_id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseData<Ingredient> getIngredient(@PathVariable("ingredient_id") int id) {
        return new ResponseData<>(ingredientService.getById(id));
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseData<Ingredient>> addIngredient(@RequestBody Ingredient ingredient) {
        try {
            ingredientService.add(ingredient);
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
}
