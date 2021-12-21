package com.ibm.academia.apirest.restruleta.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Ruleta implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Estado estado;

    @OneToMany(mappedBy = "ruleta", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Apuesta> apuestas;

    @Column(updatable = false)
    private Date createdAt;

    private Date updatedAt;

    @PrePersist
    private void antesPersistir()
    {
        this.createdAt = new Date();
        this.updatedAt = new Date();
        this.estado = Estado.CERRADO;
    }

    @PreUpdate
    private void antesActualizar()
    {
        this.updatedAt = new Date();
    }


    private static final long serialVersionUID = -369308483899337207L;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Ruleta ruleta = (Ruleta) o;
        return id != null && Objects.equals(id, ruleta.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "estado = " + estado + ", " +
                "createdAt = " + createdAt + ", " +
                "updatedAt = " + updatedAt + ")";
    }
}
