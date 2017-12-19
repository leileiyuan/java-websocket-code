
package jwsp.chapter6.portfolio;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Date;
import javax.websocket.*;

public class PortfolioUpdateEncoder implements Encoder.Text<PortfolioUpdate> {
    private static String WEB_RED_COLOR = "#ff0000";
    private static String WEB_GREEN_COLOR = "##33cc00";
    private static String WEB_GOLD_COLOR = "#ffcc66";
    private static String WEB_SILVER_COLOR = "#cccccc";
    private static String WEB_BRONZE_COLOR = "#cc6600";
    private static String WEB_ROYAL_COLOR = "#ccccff";
    private static String WEB_DARK_GRAY_COLOR = "#999999";
    private static String WEB_PALE_YELLOW_COLOR = "#ffffcc";
    private static String WEB_PALE_GRAY_COLOR = "#cccccc";
    
    public void init(EndpointConfig config) {  
    }
    
    public void destroy() {}
    
    public String encode(PortfolioUpdate pu) {
        String bannerC = this.getBannerColor(pu.getMemberLevel());
        String titleC = getTitleColor(pu.getMemberLevel());
        String dataC = getDataColor(pu.getMemberLevel());
        
        StringBuilder sb = new StringBuilder();
        sb.append("<table border='0' cellpadding='2' cellspacing='2' width='30%'>");
        sb.append("<tr>");
        
        
        sb.append("<td bgcolor='" + bannerC + "'valign='top' align='center'>" + pu.getMemberLevel() + " <br>");
        sb.append("</td>");
        sb.append("</tr>");
        sb.append("</table>");
        sb.append("<table border='0' cellpadding='2' cellspacing='2' width='30%'>");
        sb.append("<tr>");
        sb.append("<td bgcolor='" + titleC + "'valign='top' align='center'>Stock<br>");
        sb.append("</td>");
        if (pu.getOptions().currentPrice) {
            sb.append("<td bgcolor='" + titleC + "'valign='top' align='center'>Price<br>");
            sb.append("</td>");
        }
        if (pu.getOptions().openPrice) {
            sb.append("<td bgcolor='" + titleC + "'valign='top' align='center'>Open Price<br>");
            sb.append("</td>");
        }
        if (pu.getOptions().percentChange) {
            sb.append("<td bgcolor='" + titleC + "'valign='top' align='center'>Change<br>");
            sb.append("</td>");
        }
        if (pu.getOptions().volume) {
            sb.append("<td bgcolor='" + titleC + "'valign='top' align='center'>Volume<br>");
            sb.append("</td>");
        }
        sb.append("</tr>");
        for (StockData s : pu.getStocks()) {
            sb.append("<tr>");
            sb.append("<td bgcolor='" + titleC + "' valign='top' align='center'>" + s.getTicker() + "<br>");
            sb.append("</td>");
            if (pu.getOptions().currentPrice) {
                sb.append("<td bgcolor='" + dataC + "' valign='top' align='center'>" + this.getFormattedPrice(s.getPrice()) + "<br>");
                sb.append("</td>");
            }
            if (pu.getOptions().openPrice) {
               sb.append("<td bgcolor='" + dataC + "' valign='top' align='center'>" + this.getFormattedPrice(s.getOpenPrice()) + "<br>");
                sb.append("</td>"); 
                
            }
            if (pu.getOptions().percentChange) {
                sb.append("<td bgcolor='" + dataC + "' valign='top' align='center'>" + this.getDelta(s) + "<br>");
                sb.append("</td>");
            }
            
            if (pu.getOptions().volume) {
                sb.append("<td bgcolor='" + dataC + "' valign='top' align='center'>" + s.getVolume() + "<br>");
                sb.append("</td>");
            }
            sb.append("</tr>");
        }
        sb.append("</table>");
        if (pu.getMemberLevel()==MemberLevel.GOLD) {
            DateFormat timeFormatter = DateFormat.getTimeInstance(DateFormat.DEFAULT);
            sb.append("<br>Realtime quotes, accurate at " + timeFormatter.format(new Date()));
        } else if (pu.getMemberLevel()==MemberLevel.SILVER) {
            sb.append("<br>Quotes delayed about 5 minutes");
        } else {
            sb.append("<br>Quotes delayed about 20 minutes");
        }
        return sb.toString();
    }
    
    private String getTitleColor(MemberLevel memberLevel) {
        if (memberLevel == MemberLevel.GOLD) {
            return WEB_ROYAL_COLOR;
        } else {
            return WEB_DARK_GRAY_COLOR;
        }
    }
    
    private String getDataColor(MemberLevel memberLevel) {
        if (memberLevel == MemberLevel.GOLD) {
            return WEB_PALE_YELLOW_COLOR;
        } else {
            return WEB_PALE_GRAY_COLOR;
        }
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
            color = WEB_RED_COLOR;
        } else {
            color = WEB_GREEN_COLOR;
        }
        sb.append("<font color='" + color + "'>" + output + "%</font>");
        return sb.toString();
    }
    
    private String getBannerColor(MemberLevel memberLevel) {
        String bannerColor;
        if (memberLevel == MemberLevel.GOLD) {
            return WEB_GOLD_COLOR;
        } else if (memberLevel == MemberLevel.SILVER) {
            return WEB_SILVER_COLOR;
        } else {
            return WEB_BRONZE_COLOR;
        }

    }
    
}
