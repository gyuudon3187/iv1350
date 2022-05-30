/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package se.kth.iv1350.inheritancecomposition;

import java.util.*;

/**
 * A class which uses HashMap through composition.
 */
public class CountingDictionaryUsingComposition {
    private HashMap dictionary = new HashMap<String,String>();
    private int faultySearches;
    
    CountingDictionaryUsingComposition() {
        dictionary.put("Inheritance", "Arv");
        dictionary.put("Composition", "Komposition");
        dictionary.put("Encapsulation", "Inkapsling");
        dictionary.put("Cohesion", "Sammanhållning");
        dictionary.put("Coupling", "Koppling");
        faultySearches = 0;
    }
    
    public Object get(Object o) {
        if(!dictionary.containsKey(o)) {
            faultySearches += 1;
        }
        
        return dictionary.get(o);
    }
    
    public int getFaultySearches() {
        return faultySearches;
    }
}
