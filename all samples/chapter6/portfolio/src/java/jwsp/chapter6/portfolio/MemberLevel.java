package jwsp.chapter6.portfolio;


public enum MemberLevel {
    
    BRONZE("Bronze"),
    SILVER("Silver"),
    GOLD("Gold");
      
    private String level;
    
    MemberLevel(String level) {
        this.level = level;
    }
    
    @Override
    public String toString() {
        return "Member Level: " + this.level;
    }
    
    public static MemberLevel create(String rawString) throws UnknownMemberLevelException {
        switch (rawString) {
            case "gold":
                return GOLD;
            case "silver":
                return SILVER;
            case "bronze":
                return BRONZE;
            default: 
                throw new UnknownMemberLevelException("Bad member: " + rawString);
        }
    }
}
   
    
