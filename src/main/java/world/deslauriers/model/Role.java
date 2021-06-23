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
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Introspected
@MappedEntity(value = "role")
public class Role implements Serializable {

    @Serial
    private static final long serialVersionUID = 4588331118056480974L;

    @Id
    @GeneratedValue(GeneratedValue.Type.IDENTITY)
    private Long id;
    private String role;

    @Relation(value = Relation.Kind.ONE_TO_MANY, mappedBy = "role")
    @JoinTable(name = "user_role")
    private Set<UserRole> userRoles = new HashSet<>();
}
