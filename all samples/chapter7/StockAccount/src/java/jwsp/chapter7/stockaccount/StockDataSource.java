package jwsp.chapter7.stockaccount;

import java.util.*;

public class StockDataSource {
    private List<StockDataSourceListener> listeners = new ArrayList<>();
    private DataOptions options;
    private MemberLevel memberLevel;
    private Thread updateThread;
    private boolean shouldUpdate = true;
    private int updateCount = 0;
    private List<String> stockSymbols;
    private List<StockData> stockQuotes;
    private int averageQuotePeriod; 
    
    public StockDataSource(DataOptions options, MemberLevel memberLevel) {
        this.options = options;
        this.memberLevel = memberLevel;
        if (memberLevel == MemberLevel.GOLD) {
            this.averageQuotePeriod = 750;
        } else if (memberLevel == MemberLevel.SILVER) {
            this.averageQuotePeriod = 2000;
        } else {
            this.averageQuotePeriod = 5000;
        }
        this.stockSymbols = new ArrayList<>();
        this.stockSymbols.add("AAPL");
        this.stockSymbols.add("GE");
        this.stockSymbols.add("JNJ");
        this.stockSymbols.add("JNK");
        this.stockSymbols.add("ORCL");
        this.stockSymbols.add("PZA");
        this.stockQuotes = this.getQuotes();
    }
    
    private List<StockData> getQuotes() {
        List<StockData> quotes = new ArrayList<>(); 
        for (String symbol : this.stockSymbols) {
            quotes.add(this.computeStockDataFor(symbol));
        }
        return quotes;
    }
    
     private void updateQuotesToNotify() {
        List<StockData> quotes = this.getQuotes();
        List<StockData> stocksToUse = new ArrayList<>();
        for (int i = 0; i < quotes.size(); i++) {
            if (this.generateRandomOnOff()) {
                stocksToUse.add(quotes.get(i));
            } else {
                stocksToUse.add(stockQuotes.get(i));
            }
        }
        
        stockQuotes = stocksToUse;
    }
     
    public boolean generateRandomOnOff() {
        int i = Math.round( (float) 0.25 + (float) Math.random());
        return (i==0) ? true : false;
    } 
    
    public int generateRandomQuotePeriod() {
        return this.averageQuotePeriod + (int) (((float) 0.5 - (float) Math.random()) * this.averageQuotePeriod);
    }
    
    public void start() {
        this.updateThread = new Thread() {
            public void run() {
                while (shouldUpdate) {
                    doUpdate();
                    try {this.sleep(generateRandomQuotePeriod());} catch (Exception r) {}
                }
            }
        };
        this.updateThread.start();
    }
    
    
    public void doUpdate() {
        this.updateQuotesToNotify(); 
        this.notifyListeners(stockQuotes);
        updateCount++;
    }
    
    public void stop() {
        this.shouldUpdate = false;
        this.updateCount = 0;
    }
    
    public void addStockDataSourceListener(StockDataSourceListener sdsl) {
        this.listeners.add(sdsl);
    }
    
    private void notifyListeners(List<StockData> stocks) {
        for (StockDataSourceListener l : this.listeners) {
            PortfolioUpdate pu = new PortfolioUpdate(stocks, this.options, this.memberLevel);
            l.handleNewStockData(pu);
        }       
    }
    
 
    public StockData computeStockDataFor(String ticker) {
       switch(ticker) {
           case "ORCL":
               return new StockData("ORCL", this.computePrice(35.23, 0.5), 34.96, 1128000 + 38430 *updateCount);
           case "GE": 
               return new StockData("GE", this.computePrice(22.4, 0.3), 22.51, 9600240 + 45670*updateCount);
           case "AAPL": 
               return new StockData("AAPL", this.computePrice(450, 4), 448.83, 6583000 + 6780* updateCount); 
           case "PZA": 
               return new StockData("PZA", this.computePrice(25.57, 0.2), 25.58, 33000 + 426* updateCount); 
           case "JNK": 
               return new StockData("JNK", this.computePrice(42.51, 0.69), 42.01, 4583000 + 1380* updateCount); 
           case "JNJ": 
               return new StockData("JNJ", this.computePrice(87.9, 1.3), 87.29, 2143000 + 9880* updateCount); 
           default:
               throw new RuntimeException("stock " + ticker + " not found");
       }            
    }
    
    private double computePrice(double guide, double maxChange) {
        double rand = Math.random();
        double change = maxChange * 2* (rand - 0.5);
        return guide + change;
        
    }
    
}
