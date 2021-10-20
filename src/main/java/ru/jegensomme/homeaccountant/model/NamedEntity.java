package ru.jegensomme.homeaccountant.model;

import lombok.*;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@NoArgsConstructor
@MappedSuperclass
public abstract class NamedEntity extends BaseEntity {
    @NotBlank
    @Size(min = 2, max = 100)
    @Column(name = "name")
    private @NotNull String name;

    public NamedEntity(@NotNull Integer id, @NotNull String name) {
        super(id);
        this.name = name;
    }
}
