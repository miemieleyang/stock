package stockTest;

import comstock.domain.Stock;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import comstock.dao.StockDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class TestStock {
    public static void main(String[] args) {
        testSelect();
    }

    private static void testSelect() {

        ApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-mybatis.xml");

        SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) context.getBean("sqlSessionFactory");
        SqlSession session = sqlSessionFactory.openSession(true);
        try {
            StockDao stockDao = session.getMapper(StockDao.class);
            //Stock stock = stockDao.selectStock("12345");
            stockDao.deleteAll();
            Stock stock = new Stock();
            stock.setStockCode("1122");
            stock.setStockName("yili");
            stock.setStockPrice(33.3);
            stock.setStockMarketValue(33333);
            stockDao.addStock(stock);
            System.out.println(stock);
        } finally {
            session.close();
        }
    }

}
