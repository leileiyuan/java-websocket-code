
package jwsp.chapter7.stockaccount;

import java.util.*;
import java.security.*;

public class Account {
    private Map<String, Integer> accountInfo = new HashMap<>();
    private int accountNumber = 123456789;
    private Principal userPrincipal;
    private MemberLevel memberLevel;
    
    public Account(Principal userPrincipal, MemberLevel memberLevel) {
        this.accountInfo.put("AAPL", 3);
        this.accountInfo.put("GE", 10);
        this.accountInfo.put("JNJ", 3);
        this.accountInfo.put("JNK", 2);
        this.accountInfo.put("ORCL", 4);
        this.accountInfo.put("PZA", 5);
        this.userPrincipal = userPrincipal;
        this.memberLevel = memberLevel;
    }
    
    public int getAccountNumber() {
        return this.accountNumber;
    }
    
    public MemberLevel getMemberLevel() {
        return this.memberLevel;
    }
    
    public String getUsername() {
        return (userPrincipal != null) ? userPrincipal.getName() : "unknown";
    }
    
    
    
    public List<String> getStocks() {
        return new ArrayList<>(this.accountInfo.keySet());
    }
    
    public int getNumberShares(String symbol) {
        return this.accountInfo.get(symbol);
    }
    
}
