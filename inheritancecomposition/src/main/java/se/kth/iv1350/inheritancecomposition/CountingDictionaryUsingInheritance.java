/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package se.kth.iv1350.inheritancecomposition;

import java.util.*;

/**
 * A class which inherits HashMap.
 */
public class CountingDictionaryUsingInheritance extends HashMap {
    private int faultySearches;
    
    CountingDictionaryUsingInheritance() {
        this.put("Inheritance", "Arv");
        this.put("Composition", "Komposition");
        this.put("Encapsulation", "Inkapsling");
        this.put("Cohesion", "Sammanhållning");
        this.put("Coupling", "Koppling");
        faultySearches = 0;
    }
    
    @Override
    public Object get(Object o) {
        if(!this.containsKey(o)) {
            faultySearches += 1;
        }
        
        return super.get(o);
    }
    
    public int getFaultySearches() {
        return faultySearches;
    }
}
