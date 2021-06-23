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

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Introspected
@MappedEntity(value = "user_role")
public class UserRole implements Serializable {

    @Serial
    private static final long serialVersionUID = 1108904291833271635L;
    
    @Id
    @GeneratedValue(GeneratedValue.Type.IDENTITY)
    private Long id;

    @Relation(Relation.Kind.MANY_TO_ONE)
    @JoinTable(name = "user")
    private User user;

    @Relation(Relation.Kind.MANY_TO_ONE)
    @JoinTable(name = "role")
    private Role role;
}
