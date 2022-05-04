package se.kth.iv1350.pos.integration;

import java.util.ArrayList;
import java.util.Arrays;
import se.kth.iv1350.pos.dto.ItemDTO;
import se.kth.iv1350.pos.dto.SaleDTO;
import se.kth.iv1350.pos.model.Item;

/**
 * External system handling the inventory.
 */
public class ExternalInventorySystem {
    private ArrayList<Item> inventoryLog;

    /**
     * Constructor. Instantiates all items in inventory.
     */
    public ExternalInventorySystem() {
        inventoryLog = new ArrayList<>(
                Arrays.asList(new Item("Yoghurt",
                                        16.00,
                                        0.12,
                                        452283101,
                                        1,
                                        10),
                             new Item("Banana",
                                        20.00,
                                        0.12,
                                        452283102,
                                        1,
                                        20),
                             new Item("Tobacco",
                                        57.00,
                                        0.25,
                                        452283103,
                                        1,
                                        2),
                             new Item("Detergent",
                                        32.00,
                                        0.12,
                                        452283104,
                                        1,
                                        5),
                             new Item("Milk",
                                        10.00,
                                        0.12,
                                        452283105,
                                        1,
                                        3)));
    }
    
    /**
     * Subtracts the inventory quantity of an item with a specified identifier
     * by a specified quantity.
     * 
     * @param quantityToBeSubtracted    the quantity by which the item's inventory quantity is to be subtracted
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
     */
    public ItemDTO fetchItemInfo(int itemIdentifier) {
        ItemDTO containerOfItemInfo = null;
        
        for(Item item: inventoryLog) {
            if(item.getItemIdentifier() == itemIdentifier) {
                containerOfItemInfo = new ItemDTO(item);
            }
        }

        return containerOfItemInfo;
    }

    /**
     * Verify whether the specified item identifier corresponds to an
     * item stored within the inventory log or not.
     * 
     * @param itemIdentifier    the item identifier to be verified
     * @return                  whether an item exists in inventory or not
     */
    public boolean verifyIdentifier(int itemIdentifier) {
        boolean itemExists = false;
            for(Item item: inventoryLog) {
                if(item.getItemIdentifier() == itemIdentifier) {
                    itemExists = true;
                    break;
                }
            }
            
            return itemExists;
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

}
