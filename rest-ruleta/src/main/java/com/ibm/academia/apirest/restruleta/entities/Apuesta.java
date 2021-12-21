package com.ibm.academia.apirest.restruleta.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Apuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Color color;

    @Min(value = 0, message = "No puede ser negativo")
    @Max(value = 36, message = "No puede ser más de 36")
    private Integer numero;

    @NotNull(message = "No puede ser nulo")
    @Max(value = 10000, message = "No puede ser mas de 10000")
    @Positive
    private BigDecimal dineroApostado;

    @NotNull(message = "No puede ser nulo")
    @NotBlank(message = "No puede ser vacio")
    @Column(length = 60)
    private String nombreApostador;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Resultado resultado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_ruleta")
    @JsonIgnore
    private Ruleta ruleta;

    @Column(updatable = false)
    private Date createdAt;

    private Date updatedAt;

    @PrePersist
    private void antesPersistir()
    {
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

}
