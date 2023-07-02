package pl.marcin.stockmanagerapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.marcin.stockmanagerapi.dto.StockDto;
import pl.marcin.stockmanagerapi.entity.Stock;

import java.util.List;


@Mapper(componentModel = "spring")
public interface StockMapper {

    //@Mapping(target = "availableQuantity", expression = "java(stock.getCurrentQuantity() - stock.getReservedQuantity())")
    //??
    StockDto stockToStockDto(Stock stock);

    List<StockDto> stocksToStockDto(List<Stock> stocks);

    @Mapping(target = "product.id", source = "stockDto.productId")
    Stock stockDtoToStock(StockDto stockDto);

}