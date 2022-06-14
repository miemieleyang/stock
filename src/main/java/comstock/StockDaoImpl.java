package comstock;

import comstock.dao.StockDao;
import comstock.domain.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class StockDaoImpl implements StockDao {

    @Resource
    public StockMapper stockMapper;

    @Override
    public Stock selectStock(String code) {
        return stockMapper.selectStock(code);
    }

    @Override
    public void addStock(Stock stock) {
        stockMapper.addStock(stock);
    }

    @Override
    public void updateStock(Stock stock) {
        stockMapper.updateStock(stock);
    }

    @Override
    public void deleteStock(Integer code) {
        stockMapper.deleteStock(code);
    }

    @Override
    public void deleteAll() {
        stockMapper.deleteAll();
    }
}
