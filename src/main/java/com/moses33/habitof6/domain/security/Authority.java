package com.moses33.habitof6.domain.security;

import com.moses33.habitof6.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import java.util.Set;

@Entity
@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Authority extends BaseEntity {

    @Length(max = 64)
    private String name;

    @ManyToMany(mappedBy = "authorities")
    private Set<Role> roles;
}
