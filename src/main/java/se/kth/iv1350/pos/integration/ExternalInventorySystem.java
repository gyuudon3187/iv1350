package se.kth.iv1350.pos.integration;

import java.util.ArrayList;
import java.util.Arrays;
import se.kth.iv1350.pos.dto.ItemDTO;
import se.kth.iv1350.pos.dto.SaleDTO;
import se.kth.iv1350.pos.model.Item;
import se.kth.iv1350.pos.util.Decoder;

/**
 * External system handling the inventory.
 */
public class ExternalInventorySystem {
    private static final ExternalInventorySystem extInvSys =
            new ExternalInventorySystem();
    private ArrayList<Item> inventoryLog;
    private Decoder decoder;

    /**
     * Constructor. Instantiates all items in inventory.
     */
    private ExternalInventorySystem() {
        decoder = new Decoder();
        
        double twelveVATRate = 0.12;
        double twentyfiveVATRate = 0.25;
        
        int initialQuantityInSaleForAllItems = 1;
        
        String yoghurtName = "Yoghurt";
        double yoghurtPrice = 16.00;
        int yoghurtIdentifier = 452283101;
        int yoghurtQuantityInInventory = 10;
        
        String bananaName = "Banana";
        double bananaPrice = 20.00;
        int bananaIdentifier = 452283102;
        int bananaQuantityInInventory = 20;
        
        String tobaccoName = "Tobacco";
        double tobaccoPrice = 57.00;
        int tobaccoIdentifier = 452283103;
        int tobaccoQuantityInInventory = 40;
        
        String detergentName = "Detergent";
        double detergentPrice = 32.00;
        int detergentIdentifier = 452283104;
        int detergentQuantityInInventory = 5;
        
        String milkName = "Milk";
        double milkPrice = 10.00;
        int milkIdentifier = 452283105;
        int milkQuantityInInventory = 3;
        
        // Seminar 4 task 1 b)
        String dummyForCausingDatabaseFailureName = "Dummy";
        double dummyForCausingDatabaseFailurePrice = 0;
        int dummyForCausingDatabaseFailureIdentifier = 452283106;
        int dummyForCausingDatabaseFailureQuantityInInventory = 0;
        
        
        inventoryLog = new ArrayList<>(
                Arrays.asList(new Item(yoghurtName,
                                        yoghurtPrice,
                                        twelveVATRate,
                                        yoghurtIdentifier,
                                        initialQuantityInSaleForAllItems,
                                        yoghurtQuantityInInventory),
                             new Item(bananaName,
                                        bananaPrice,
                                        twelveVATRate,
                                        bananaIdentifier,
                                        initialQuantityInSaleForAllItems,
                                        bananaQuantityInInventory),
                             new Item(tobaccoName,
                                        tobaccoPrice,
                                        twentyfiveVATRate,
                                        tobaccoIdentifier,
                                        initialQuantityInSaleForAllItems,
                                        tobaccoQuantityInInventory),
                             new Item(detergentName,
                                        detergentPrice,
                                        twelveVATRate,
                                        detergentIdentifier,
                                        initialQuantityInSaleForAllItems,
                                        detergentQuantityInInventory),
                             new Item(milkName,
                                        milkPrice,
                                        twelveVATRate,
                                        milkIdentifier,
                                        initialQuantityInSaleForAllItems,
                                        milkQuantityInInventory),
                             new Item(dummyForCausingDatabaseFailureName,
                                        dummyForCausingDatabaseFailurePrice,
                                        twelveVATRate,
                                        dummyForCausingDatabaseFailureIdentifier,
                                        initialQuantityInSaleForAllItems,
                                        dummyForCausingDatabaseFailureQuantityInInventory)));
    }
    
    /**
     * Subtracts the inventory quantity of an item with a specified identifier
     * by a specified quantity.
     * 
     * @param quantityToBeSubtracted    the quantity by which the item's 
     *                                      inventory quantity is to be
     *                                      subtracted
     * @param itemIdentifier            the identifier of the subtracted item
     */
    private void subtractItemQuantityInInventory(int quantityToBeSubtracted,
                                                    int itemIdentifier){
        for(Item item: inventoryLog) {
            if(itemIdentifier == item.getItemIdentifier()) {
                int currentQuantityInInventory = item.getItemQuantityInInventory();
                item.setItemQuantityInInventory(
                        currentQuantityInInventory - quantityToBeSubtracted);
                break;
            }
        }
    }

    /**
     * Fetch all information about an item of a specified identifier.
     * 
     * @param itemIdentifier    identifier of the item
     * @return                  a data container with all item information
     * @throws ItemIdentifierFormatException    if the item identifier is not
     *                                           9 digits
     * @throws ItemIdentifierNotFoundException  if no item with the passed
     *                                           identifier doesn't exist
     * @throws ItemNotInInventoryException      if the requested quantity
     *                                           of the item does not exist
     *                                           in inventory
     * @throws DatabaseFailureException         if server cannot be accessed
     *                                           when attempting to fetch item
     */
    public ItemDTO fetchItemInfo(int itemIdentifier) throws ItemIdentifierFormatException,
                                                             ItemIdentifierNotFoundException,
                                                             ItemNotInInventoryException,
                                                             DatabaseFailureException {
        // Seminar 4 task 1 b)
        if(itemIdentifier == 452283106) {
            throw new DatabaseFailureException(
                    "Issue: Attempt to access an item identifier when the server"
                    + " was not running.\n"
                    + "Solution: Inform the customer about the issue.");
        }
        
        // Seminar 4 task 1 a)
        if(decoder.isItemIdentifier(itemIdentifier) == false) {
            throw new ItemIdentifierFormatException(
                    "The scanned item identifier isn't 9 digits and"
                    + " is therefore no valid identifier", itemIdentifier);
        }
        
        for(Item item : inventoryLog) {
            if(item.getItemIdentifier() == itemIdentifier) {
                
                ItemDTO containerOfItemInfo = new ItemDTO(item);
                int itemQuantityInInventory =
                        containerOfItemInfo.getItemQuantityInventory();
            
                if(itemQuantityInInventory == 0) {
                    throw new ItemNotInInventoryException(
                            "Issue: The customer requested the item with identifier  "
                            + containerOfItemInfo.getItemIdentifier() 
                            + ", which there is none of in inventory.\n"
                            + "Solution: The error should be handled by adjusting the"
                            + " stock information with the correct quantity.",
                            containerOfItemInfo);
                }
                
                return containerOfItemInfo;
            }
        }

        throw new ItemIdentifierNotFoundException(
                "A non-existent item identifier was scanned.", itemIdentifier);
    }
    
    /**
     * Gets the only (singleton) instance of the external inventory system.
     * 
     * @return  the only (singleton) instsance of the external inventory system
     */
    public static ExternalInventorySystem getExternalInventorySystem() {
        return extInvSys;
    }

    /**
     * Update the inventory log with the decreased quantity of an item
     * after a sale.
     * 
     * @param completedSale a completed sale
     */
    public void updateInventoryLog(SaleDTO completedSale) {
        for(ItemDTO item: completedSale.getItemsInSale()) {
            int itemIdentifier = item.getItemIdentifier();
            int soldQuantity = item.getItemQuantityInSale();
            this.subtractItemQuantityInInventory(soldQuantity, itemIdentifier);
            
        }
    }
    
    /**
     * Sets the inventory quantity of an specified item.
     * 
     * @param itemToBeAddedToSale
     * @param newQuantity 
     */
    public void setItemQuantityInInventory(ItemDTO itemToBeAddedToSale,
                                            int newQuantity) {
        int itemIdentifier = itemToBeAddedToSale.getItemIdentifier();
        for(Item item : inventoryLog) {
            if(item.getItemIdentifier() == itemIdentifier) {
                item.setItemQuantityInInventory(newQuantity);
                break;
            }
        }
    }

}
