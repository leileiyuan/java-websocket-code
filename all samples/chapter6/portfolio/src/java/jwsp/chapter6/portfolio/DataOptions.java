
package jwsp.chapter6.portfolio;


public class DataOptions {
        public static String CURRENT_PRICE = "current-price";
        public static String OPEN_PRICE = "open-price";
        public static String PERCENTAGE_CHANGE = "percentage-change";
        public static String VOLUME = "volume";
        boolean openPrice = false;
        boolean percentChange = false;
        boolean volume = false;
        boolean currentPrice = false;
        
        
        public DataOptions() {}
        
        public DataOptions currentPrice(boolean currentPrice) {
            this.currentPrice = currentPrice;
            return this;
        }
        
        public DataOptions openPrice(boolean openPrice) {
            this.openPrice = openPrice;
            return this;
        }
        
        public DataOptions percentChange(boolean percentChange) {
            this.percentChange = percentChange;
            return this;
        }  
        
        public DataOptions volume(boolean volume) {
            this.volume = volume;
            return this;
        }
        
    }