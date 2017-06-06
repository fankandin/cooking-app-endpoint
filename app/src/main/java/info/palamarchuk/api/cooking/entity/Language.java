package info.palamarchuk.api.cooking.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "language")
public class Language implements Serializable {

    @Id
    short id;

    String code;
}
