package comstock;

import comstock.domain.Stock;
import org.springframework.stereotype.Component;

@Component
public interface StockMapper {

    /**
     * 查询股票信息
     * @param
     * @return
     */
    public Stock selectStock(String code);

    /**
     * 新增股票
     */
    public void addStock(Stock stock);

    /**
     * 删除单个股票
     */
    public void deleteStock(Integer code);

    /**
     * 更新股票信息
     */
    public void updateStock(Stock stock);

    /**
     * 删除全部股票
     */
    public void deleteAll();
}
