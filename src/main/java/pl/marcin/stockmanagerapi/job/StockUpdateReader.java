package pl.marcin.stockmanagerapi.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class StockUpdateReader extends JdbcCursorItemReader<StockUpdate> {

    @Autowired
    public StockUpdateReader(DataSource dataSource)  {
        this.setDataSource(dataSource);
        this.setSql("SELECT id, product_id, quantity FROM stock_updates");
        this.setRowMapper(new BeanPropertyRowMapper<>(StockUpdate.class));
    }
}
