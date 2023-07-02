package pl.marcin.stockmanagerapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Data
@Entity
@Table(name = "stock")
@NoArgsConstructor
@AllArgsConstructor
public class Stock {
    @Id
    private Long id;
    @NotNull
    @Column(nullable = false)
    private Long currentQuantity;
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;
}
