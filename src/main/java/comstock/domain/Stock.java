package comstock.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Builder
@Data
@Component
public class Stock implements Serializable {
    private int id;
    private String stockCode;
    private String stockName;
    private double stockPrice;
    private double stockMarketValue;

    public Stock(){
    }

    public Stock(int id, String stockCode, String stockName, double stockPrice, double stockMarketValue) {
        this.id = id;
        this.stockCode = stockCode;
        this.stockName = stockName;
        this.stockPrice = stockPrice;
        this.stockMarketValue = stockMarketValue;
    }
}
