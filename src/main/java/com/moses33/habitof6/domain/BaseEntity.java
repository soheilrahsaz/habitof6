package com.moses33.habitof6.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;


@MappedSuperclass
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity that = (BaseEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
