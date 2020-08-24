package com.otus.homework.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * An authority (a security role) used by Spring Security.
 */
@AllArgsConstructor
@Data
@Document(collection = "authority")
public class Authority implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Authority)) {
            return false;
        }
        return Objects.equals(name, ((Authority) o).name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Authority{" +
            "name='" + name + '\'' +
            "}";
    }
}
