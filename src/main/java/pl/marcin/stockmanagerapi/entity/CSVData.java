package pl.marcin.stockmanagerapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class CSVData {
    private Long productId;
    private Long quantity;
}
