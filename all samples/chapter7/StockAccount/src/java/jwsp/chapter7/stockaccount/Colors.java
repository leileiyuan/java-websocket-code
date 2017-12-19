package jwsp.chapter7.stockaccount;

public class Colors {
    public static String WEB_RED_COLOR = "#ff0000";
    public static String WEB_GREEN_COLOR = "##33cc00";
    public static String WEB_GOLD_COLOR = "#ffcc66";
    public static String WEB_SILVER_COLOR = "#cccccc";
    public static String WEB_BRONZE_COLOR = "#cc6600";
    public static String WEB_ROYAL_COLOR = "#33cc00";
    public static String WEB_DARK_GRAY_COLOR = "#999999";
    public static String WEB_STOCK_DATA_GOLD = "#ffffcc";
    public static String WEB_PALE_GRAY_COLOR = "#dddddd";
    
    public static String getBannerColor(MemberLevel memberLevel) {
        String bannerColor;
        if (memberLevel == MemberLevel.GOLD) {
            return Colors.WEB_GOLD_COLOR;
        } else if (memberLevel == MemberLevel.SILVER) {
            return Colors.WEB_SILVER_COLOR;
        } else {
            return Colors.WEB_BRONZE_COLOR;
        }

    }
    
    public static String getTitleColor(MemberLevel memberLevel) {
        System.out.println("get title color " + memberLevel);
        if (memberLevel == MemberLevel.GOLD) {
            return Colors.WEB_ROYAL_COLOR;
        } else if (memberLevel == MemberLevel.SILVER) {
            return Colors.WEB_SILVER_COLOR;
        } else {
            return Colors.WEB_DARK_GRAY_COLOR;
        }
    }
    
    public static String getDataColor(MemberLevel memberLevel) {
        if (memberLevel == MemberLevel.GOLD) {
            return Colors.WEB_GOLD_COLOR;
        } else {
            return Colors.WEB_PALE_GRAY_COLOR;
        }
    }
}
