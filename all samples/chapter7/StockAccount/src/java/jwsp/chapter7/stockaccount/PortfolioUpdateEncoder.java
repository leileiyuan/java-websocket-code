
package jwsp.chapter7.stockaccount;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Date;
import javax.websocket.*;

public class PortfolioUpdateEncoder implements Encoder.Text<PortfolioUpdate> {
    
    
    @Override
    public void init(EndpointConfig config) {}
    
    @Override
    public void destroy() {}
 
    @Override
    public String encode(PortfolioUpdate pu) {
        String bannerC = Colors.getBannerColor(pu.getMemberLevel());
        String titleC = Colors.getTitleColor(pu.getMemberLevel());
        String dataC = Colors.getDataColor(pu.getMemberLevel());
        
        StringBuilder sb = new StringBuilder();

        sb.append("<table border='0' cellpadding='0' cellspacing='0' width='360'>");
        sb.append("<tr>");
        
        for (StockData s : pu.getStocks()) {
            sb.append("<td bgcolor='" + titleC + "' valign='top' align='center'><small>" + s.getTicker() + "</small><br>");
            sb.append("</td>");
        }
        sb.append("</tr>");
        sb.append("<tr>");
        for (StockData s : pu.getStocks()) {
        
            if (pu.getOptions().currentPrice) {
                sb.append("<td bgcolor='" + dataC + "' valign='top' align='center'><small>" + this.getFormattedPrice(s.getPrice()) + "</small><br>");
                sb.append("</td>");
            }
        }
        sb.append("</tr>");
        
        sb.append("<tr>");
        for (StockData s : pu.getStocks()) {
            if (pu.getOptions().percentChange) {
                sb.append("<td bgcolor='" + dataC + "' valign='top' align='center'><small>" + this.getDelta(s) + "</small><br>");
                sb.append("</td>");
            }
        }
        sb.append("</tr>");

        sb.append("</table>");
        sb.append("<small>");
        if (pu.getMemberLevel()==MemberLevel.GOLD) {
            DateFormat timeFormatter = DateFormat.getTimeInstance(DateFormat.DEFAULT);
            sb.append("Realtime quotes, accurate at " + timeFormatter.format(new Date()));
        } else if (pu.getMemberLevel()==MemberLevel.SILVER) {
            sb.append("Quotes delayed about 5 minutes");
        } else {
            sb.append("Quotes delayed about 20 minutes");
        }
        sb.append("</small>");
        return sb.toString();
    }
    

    
       
    private String getFormattedPrice(double price) {
        DecimalFormat myFormatter = new DecimalFormat("##.##");
        String output = myFormatter.format(price);
        return output;
    }
    
    private String getDelta(StockData sd) {
        StringBuilder sb = new StringBuilder();
        DecimalFormat myFormatter = new DecimalFormat("##.##");
        String output = myFormatter.format(sd.getPriceChangePercentage());
        String color;
        if (sd.getPriceChangePercentage() < 0) {
            color = Colors.WEB_RED_COLOR;
        } else {
            color = Colors.WEB_GREEN_COLOR;
        }
        sb.append("<font color='" + color + "'>" + output + "%</font>");
        return sb.toString();
    }
    

    
}
