package se.kth.iv1350.pos.integration;

import java.util.HashMap;

/**
 * Database for handling members.
 */
public class MemberDatabase {
    private static final MemberDatabase memberdb = new MemberDatabase();
    
    private HashMap<String, String> memberlist;

    /**
     * Constructor. Instantiates registered members.
     */
    private MemberDatabase() {
        memberlist = new HashMap<>();
        memberlist.put("8405231243", "Lars Svensson");
        memberlist.put("9510018523", "Maria Larsson");
        memberlist.put("2004250225", "Josefina Dahlberg");
    }
    
    public static MemberDatabase getMemberDatabase() {
        return memberdb;
    }

    /**
     * Validates whether a customer is registered as a member or not.
     * 
     * @param customerID    the customer's member ID
     * @return  boolean for whether the customer is registered as a member or not
     */
    public boolean validateCustomerID(String customerID) {
        boolean customerIsMember = memberlist.containsKey(customerID);
        return customerIsMember;
    }

}
