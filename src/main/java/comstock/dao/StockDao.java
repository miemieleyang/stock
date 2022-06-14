package comstock.dao;

import comstock.domain.Stock;

public interface StockDao {

    Stock selectStock(String code);

    void addStock(Stock stock);

    void updateStock(Stock stock);

    void deleteStock(Integer code);

    void deleteAll();
}
