package pl.marcin.stockmanagerapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "order_reservation")
@AllArgsConstructor
@NoArgsConstructor
public class OrderReservation {
    Long quantity;
}
