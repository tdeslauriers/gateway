package world.deslauriers.model;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.Relation;
import io.micronaut.data.jdbc.annotation.JoinTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Introspected
@MappedEntity(value = "user")
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = -5573871193358646152L;

    @Id
    @GeneratedValue(GeneratedValue.Type.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;

    @Email
    private String username;
    private String password;
    private LocalDate accountCreated;
    private Boolean enabled;
    private Boolean isAccountExpired;
    private Boolean isAccountLocked;
    private Boolean isPasswordExpired;

    @Relation(value = Relation.Kind.ONE_TO_MANY, mappedBy = "user")
    @JoinTable(name = "user_role")
    private Set<UserRole> userRoles = new HashSet<>();


}
