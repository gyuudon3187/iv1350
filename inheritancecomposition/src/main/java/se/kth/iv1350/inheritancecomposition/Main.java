/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package se.kth.iv1350.inheritancecomposition;

/**
 * The main class responsible for the execution of the program.
 */
public class Main {

    public static void main(String[] args) {
        CountingDictionaryUsingComposition compositionDictionary = new CountingDictionaryUsingComposition();
        System.out.println("<<CountingDictionaryUsingComposition>>");
        System.out.println(compositionDictionary.get("Encapsulation"));
        System.out.println(compositionDictionary.get("UML")); // Non-existent entry
        System.out.println(compositionDictionary.get("Architectural pattern")); // Non-existent entry
        System.out.println(compositionDictionary.get("Cohesion"));
        System.out.println("Faulty searches: " + compositionDictionary.getFaultySearches());
        System.out.println(compositionDictionary.get("Design pattern"));
        System.out.println("Faulty searches: " + compositionDictionary.getFaultySearches());
        System.out.println(compositionDictionary.get("Inheritance"));
        System.out.println(compositionDictionary.get("Composition"));
        System.out.println(compositionDictionary.get("Coupling"));
        System.out.println("Faulty searches: " + compositionDictionary.getFaultySearches());
        System.out.println("\n");
        CountingDictionaryUsingInheritance inheritanceDictionary = new CountingDictionaryUsingInheritance();
        System.out.println("<<CountingDictionaryUsingInheritance>>");
        System.out.println(inheritanceDictionary.get("Encapsulation"));
        System.out.println(inheritanceDictionary.get("UML"));
        System.out.println(inheritanceDictionary.get("Architectural pattern"));
        System.out.println(inheritanceDictionary.get("Cohesion"));
        System.out.println("Faulty searches: " + inheritanceDictionary.getFaultySearches());
        System.out.println(inheritanceDictionary.get("Design pattern"));
        System.out.println("Faulty searches: " + inheritanceDictionary.getFaultySearches());
        System.out.println(inheritanceDictionary.get("Inheritance"));
        System.out.println(inheritanceDictionary.get("Composition"));
        System.out.println(inheritanceDictionary.get("Coupling"));
        System.out.println("Faulty searches: " + inheritanceDictionary.getFaultySearches());
    }
}
