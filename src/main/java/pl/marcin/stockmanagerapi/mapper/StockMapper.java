package pl.marcin.stockmanagerapi.mapper;

import org.mapstruct.Mapper;
import pl.marcin.stockmanagerapi.dto.StockDto;
import pl.marcin.stockmanagerapi.entity.Stock;

@Mapper(componentModel = "spring")
public interface StockMapper {

    StockDto StockToStockDto(Stock stock);

    Stock StockDtoToStock(StockDto stockDto);

}