package info.palamarchuk.api.cooking;

import info.palamarchuk.api.cooking.entity.Language;
import info.palamarchuk.api.cooking.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/languages")
public class LanguageEndpoint {

    private final LanguageService service;

    @Autowired
    public LanguageEndpoint(LanguageService service) {
        this.service = service;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @Cacheable
    public ResponseEntity<List<Language>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

}
