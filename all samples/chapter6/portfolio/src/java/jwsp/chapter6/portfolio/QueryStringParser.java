package jwsp.chapter6.portfolio;

import java.util.*;

// workaround for bug in glassfish parsing the query string
public class QueryStringParser {
    
    
    public static Map<String, List<String>> parse(String queryString) {
       
       Map<String, List<String>> map = new HashMap<>();
       if (queryString == null) {
            return map;
        }
       StringTokenizer st = new StringTokenizer(queryString, "&");
       while (st.hasMoreTokens()) {
           String nextKV = st.nextToken();
           String key = "";
           String value = "";
           StringTokenizer stt = new StringTokenizer(nextKV, "=");
           key = stt.nextToken();
           if (stt.hasMoreTokens()) {
               value = stt.nextToken();
           }
           add(key, value, map);  
       }
       return map;
    }
    
    private static void add(String key, String value, Map<String, List<String>> map) {
        if (map.containsKey(key)) {
            map.get(key).add(value);
        } else {
            List<String> l = new ArrayList<>();
            l.add(value);
            map.put(key, l);
        }
    }
}
