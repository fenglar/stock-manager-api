package pl.marcin.stockmanagerapi.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@Slf4j
public class StockUpdateReader extends JdbcCursorItemReader<StockUpdate> {

    public StockUpdateReader(DataSource dataSource)  {
        this.setDataSource(dataSource);
        this.setSql("SELECT id FROM stock_updates");
        this.setRowMapper(new BeanPropertyRowMapper<>(StockUpdate.class));
        this.setFetchSize(10);
    }

    @Override
    public StockUpdate read() throws Exception {
        StockUpdate stockUpdate = super.read();
        if (stockUpdate != null) {
            log.info("Read stock update: ID={}, Product ID={}, Quantity={}", stockUpdate.getId(), stockUpdate.getProductId(), stockUpdate.getQuantity());
        }
        return stockUpdate;
    }
}
