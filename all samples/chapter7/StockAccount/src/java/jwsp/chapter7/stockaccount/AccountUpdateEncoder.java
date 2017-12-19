
package jwsp.chapter7.stockaccount;

import java.text.DecimalFormat;
import javax.websocket.*;

public class AccountUpdateEncoder implements Encoder.Text<AccountUpdate>  {
     
    
    public void init(EndpointConfig config) {}
    
    public void destroy() {}
    
    public String encode(AccountUpdate update) {
        
        String titleColor = Colors.getTitleColor(update.getAccount().getMemberLevel());
        String dataColor = Colors.getDataColor(update.getAccount().getMemberLevel());
        StringBuilder sb = new StringBuilder();
        sb.append("<table border='0' cellpadding='2' cellspacing='2' width='100%'>");
        sb.append("<tr>");
        sb.append("<td align='center' bgcolor='"+titleColor+"'>");
        sb.append("<b>");
        sb.append(update.getAccount().getUsername() + "'s holdings");
        sb.append("</b>");
        sb.append("</td>");
        sb.append("<tr>");
        sb.append("</table>");
        sb.append("<table border='0' cellpadding='2' cellspacing='2' width='100%'>");
        sb.append("<tr>");
        sb.append("<td align='center' bgcolor='"+titleColor+"'>Stock");
        sb.append("</td>");
        sb.append("<td align='center' bgcolor='"+titleColor+"'>Position");
        sb.append("</td>");
        sb.append("<td align='center' bgcolor='"+titleColor+"'>Value");
        sb.append("</td>");
        sb.append("</tr>");
        double totalValue = 0;
        for (StockData sd : update.getStocks()) {
            int numberOwned = update.getAccount().getNumberShares(sd.getTicker());
            double stockValue = numberOwned * sd.getPrice();
            sb.append("<tr>");
            sb.append("<td bgcolor='"+dataColor+"' align='center'>" + sd.getTicker());
            sb.append("</td>");
            sb.append("<td bgcolor='"+dataColor+"' align='center'>" + numberOwned);
            sb.append("</td>");
            sb.append("<td bgcolor='"+dataColor+"' align='center'>$" + this.getFormattedPrice(stockValue));
            sb.append("</td>");
            sb.append("</tr>");
            totalValue = totalValue + stockValue;
        }
        sb.append("<tr>");
        sb.append("<td>");
        sb.append("</td>");
        sb.append("<b>");
        sb.append("<td bgcolor='"+titleColor+"'><b>Account Total</b>");
        sb.append("</td>");
        sb.append("<td align='center' bgcolor='"+titleColor+"'><b>$" + this.getFormattedPrice(totalValue) + "</b>");
        sb.append("</td>");
        sb.append("</b>");
        sb.append("</tr>");
        
        sb.append("</table>");
        
        return sb.toString();
    }
    
    private String getFormattedPrice(double price) {
        DecimalFormat myFormatter = new DecimalFormat("#,###.##");
        String output = myFormatter.format(price);
        return output;
    }
}
