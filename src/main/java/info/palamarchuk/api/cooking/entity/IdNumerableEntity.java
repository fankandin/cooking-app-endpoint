package info.palamarchuk.api.cooking.entity;

import java.io.Serializable;

/**
 * This interface lets to write generic tests for services use entities.
 */
public interface IdNumerableEntity extends Serializable {
    Number getId();
}
