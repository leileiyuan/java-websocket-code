
package jwsp.chapter6.portfolio;


public class StockData {
    private String ticker;
    private double price;
    private double openingPrice;
    private long volume;
    
    public StockData(String ticker, double price, double openingPrice, long volume) {
        this.ticker = ticker;
        this.price = price;
        this.openingPrice = openingPrice;
        this.volume = volume;
    }
    
    public double getOpenPrice() {
        return this.openingPrice;
    }
    
    public String getTicker() {
        return this.ticker;
    }
    
    public double getPrice() {
        return this.price;
    }
    
    public long getVolume() {
        return this.volume;
    }
    
    public double getPriceChangePercentage() {
        return 100 * (this.price - this.openingPrice) / this.openingPrice;
    }
    
}
