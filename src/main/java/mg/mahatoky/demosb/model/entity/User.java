package mg.mahatoky.demosb.model.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

/**
 * @author mtk_ext
 */
@Data
@Entity(name = "users")
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstname;
    private String lastname;
    private String mail;
}
