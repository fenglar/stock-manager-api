package pl.marcin.stockmanagerapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.marcin.stockmanagerapi.dto.StockDto;
import pl.marcin.stockmanagerapi.entity.Stock;

@Mapper(componentModel = "spring")
public interface StockMapper {

    @Mapping(target = "availableQuantity", expression = "java(stock.getCurrentQuantity() - stock.getReservedQuantity())")
    StockDto StockToStockDto(Stock stock);

    Stock StockDtoToStock(StockDto stockDto);

}