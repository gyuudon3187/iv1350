/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package se.kth.iv1350.pos.view;

import java.util.*;
import se.kth.iv1350.pos.controller.Controller;

/**
 *
 */
public class AdminView implements View {
    private Controller contr;
    private ViewFactory viewFactory;
    private Scanner in;
    
    public AdminView(Controller contr, ViewFactory viewFactory) {
        this.contr = contr;
        this.viewFactory = viewFactory;
        in = new Scanner(System.in);
    }

    @Override
    public void runProgram() {
        boolean ongoingSession = true;
        
        while(ongoingSession) {
            printCurrentlyAddedObservers();
            ongoingSession = displayObserverOptions();
        }
    }
    
    private void addObserver() throws InvalidUserInputException,
                                       ObserverAlreadyAddedException{
        System.out.println("\nEnter the class name of the observer you wish to add.");
        String nameOfObserverToBeAdded = in.nextLine();
        
        validateAbsenceOfObserver(nameOfObserverToBeAdded);
        contr.addTotalRevenueObserver(viewFactory.getView(nameOfObserverToBeAdded));
    }
    
    private void validateAbsenceOfObserver(String nameOfObserverToBeAdded)
                                            throws InvalidUserInputException,
                                                   ObserverAlreadyAddedException{
        nameOfObserverToBeAdded = in.nextLine();
        
        if(isNotAddableAsObserver(nameOfObserverToBeAdded)) {
            throw new InvalidUserInputException();
        }

        if(isAlreadyAddedToObservers(nameOfObserverToBeAdded)) {
            throw new ObserverAlreadyAddedException();
        }
    }
    
    private void validateOccurrenceOfObserver(String nameOfObserverToBeRemoved)
                                                throws ObserverAlreadyRemovedException {
        nameOfObserverToBeRemoved = in.nextLine();
        
        if(!isAlreadyAddedToObservers(nameOfObserverToBeRemoved)) {
            throw new ObserverAlreadyRemovedException();
        }
    }
    
    private boolean displayObserverOptions() {
        System.out.println("\nEnter 'add' to add observers, and 'remove' to remove them."
                + "\nAlternatively, enter 'back' to go back to the previous menu.");
        
        while(true) {
            String userInput = in.nextLine();
            switch(userInput) {
                case "add" -> {
                    try {
                        addObserver();
                    } catch(InvalidUserInputException iuie) {
                        System.out.println("The name of the view you attempted to"
                                + " add as an observer is not an elligible observer."
                                + " Please try with another name.");
                    } catch(ObserverAlreadyAddedException oaae) {
                        System.out.println("The observer you attempted to add"
                                + " is already added as an observer.");
                    }
                }
                case "remove" -> {
                    try {
                        removeObserver();
                    } catch(InvalidUserInputException iuie) {
                        System.out.println("The name of the observer you attempted"
                                + " to remove is not an elligible observer."
                                + " Please try with another name.");
                    } catch(ObserverAlreadyRemovedException oare) {
                        System.out.println("The observer cannot be removed because"
                                + " it is not registered as an observer.");
                    }
                }
                case "back" -> {
                    return false;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }
    
    private boolean isAlreadyAddedToObservers(String nameOfObserverToBeAdded) {
        LinkedList<?> observers = contr.getTotalRevenueObservers();
        
        for(Object observer : observers) {
            if(observer.getClass().getName().equalsIgnoreCase(nameOfObserverToBeAdded)) {
                return true;
            } 
        }
        
        return false;
    }
    
    private boolean isNotAddableAsObserver(String nameOfObserverToBeAdded) {
        return nameOfObserverToBeAdded.equalsIgnoreCase("admin") || 
               nameOfObserverToBeAdded.equalsIgnoreCase("CashierView");
    }
    
    private void printCurrentlyAddedObservers() {
        System.out.println("<<Currently added observers>>");
        LinkedList<?> observers = contr.getTotalRevenueObservers();
        for(Object observer : observers) {
            System.out.println(observer.getClass().getName());
        }
    }

    private void removeObserver() throws ObserverAlreadyRemovedException,
                                          InvalidUserInputException {
        System.out.println("\nEnter the class name of the observer you wish to remove.");
        String nameOfObserverToBeAdded = in.nextLine();
        
        validateOccurrenceOfObserver(nameOfObserverToBeAdded);
        contr.removeTotalRevenueObserver(viewFactory.getView(nameOfObserverToBeAdded));
    }
}
