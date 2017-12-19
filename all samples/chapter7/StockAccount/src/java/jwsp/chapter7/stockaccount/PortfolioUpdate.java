package jwsp.chapter7.stockaccount;

import java.util.*;

public class PortfolioUpdate {
    private List<StockData> stocks = new ArrayList<>();
    private DataOptions options;
    private MemberLevel memberLevel;

    PortfolioUpdate(List<StockData> stocks, DataOptions options, MemberLevel memberLevel) {
        this.stocks = stocks;
        this.options = options;
        this.memberLevel = memberLevel;  
    }
    
    public MemberLevel getMemberLevel() {
        return this.memberLevel;
    }
    
    public DataOptions getOptions() {
        return this.options;
    }
    
    public List<StockData> getStocks() {
        return this.stocks;
    }
    


 
    
    
    
}
