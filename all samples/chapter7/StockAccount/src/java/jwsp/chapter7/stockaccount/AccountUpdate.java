
package jwsp.chapter7.stockaccount;

import java.util.ArrayList;
import java.util.List;


public class AccountUpdate {
    private List<StockData> stocks = new ArrayList<>();
    private Account account;
    
    public AccountUpdate(List<StockData> stocks, Account account) {
        this.stocks = stocks;
        this.account = account;
    }
    
    public List<StockData> getStocks() {
        return this.stocks;
    }
    
    public Account getAccount() {
        return this.account;
    }
    
    
}
