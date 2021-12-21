package com.ibm.academia.apirest.restruleta.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Resultado resultado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_ruleta")
    @JsonIgnore
    private Ruleta ruleta;


}
