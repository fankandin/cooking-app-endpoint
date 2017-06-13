package info.palamarchuk.api.cooking.util;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Service
public class CurrentUrlService {
    /**
     * Gets URI of the current resource by its ID.
     * @param id
     * @return
     */
    public URI getUrl(long id) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
            .buildAndExpand(id).toUri();
    }
}
