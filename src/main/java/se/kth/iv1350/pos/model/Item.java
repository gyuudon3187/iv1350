package se.kth.iv1350.pos.model;

import se.kth.iv1350.pos.dto.ItemDTO;

/**
 * Class containing all information about a single item.
 */
public class Item {

	private String itemName;

	private double itemPrice;

	private double itemVATRate;

	private int itemIdentifier;

	private int itemQuantityInSale;

	private int itemQuantityInInventory;

        /**
         * Constructor which instantiates its attributes by copying information
         * from an existing DTO of an item.
         * 
         * @param itemDTOToCopy    the item DTO to be copied
         */
	public Item(ItemDTO itemDTOToCopy) {
            this.itemName = itemDTOToCopy.getItemName();
            this.itemPrice = itemDTOToCopy.getItemPrice();
            this.itemVATRate = itemDTOToCopy.getItemVATRate();
            this.itemIdentifier = itemDTOToCopy.getItemIdentifier();
            this.itemQuantityInSale = itemDTOToCopy.getItemQuantityInSale();
            this.itemQuantityInInventory = itemDTOToCopy.getItemQuantityInventory();
	}
        
        /**
         * Constructor.
         * 
         * @param itemName                  the item's name
         * @param itemPrice                 price of the item
         * @param itemVATRate               the item's tax rate
         * @param itemIdentifier            used for distinguishing the item
         * @param itemQuantityInSale        quantity of the item in the sale
         * @param itemQuantityInInventory   quantity of the item in the inventory
         */
        public Item(String itemName, double itemPrice, double itemVATRate,
                int itemIdentifier, int itemQuantityInSale, int itemQuantityInInventory) {
            this.itemName = itemName;
            this.itemPrice = itemPrice;
            this.itemVATRate = itemVATRate;
            this.itemIdentifier = itemIdentifier;
            this.itemQuantityInSale = itemQuantityInSale;
            this.itemQuantityInInventory = itemQuantityInInventory;
        }
        
        /**
         * Get the name of the item.
         * 
         * @return  the item's name
         */
        public String getItemName() {
            return itemName;
        }
        
        /**
         * Get the price of the item.
         * 
         * @return  the item's price
         */
        public double getItemPrice() {
            return itemPrice;
        }
        
        /**
         * Get the tax rate of the item.
         * 
         * @return  the item's tax rate
         */
        public double getItemVATRate() {
            return itemVATRate;
        }
        
        /**
         * Get the identifier used to distinguish the item from other items.
         * 
         * @return  the item's identifier
         */
        public int getItemIdentifier() {
            return itemIdentifier;
        }
        
        /**
         * Get the quantity of the item registered in the sale.
         * 
         * @return  quantity of the item in the sale
         */
        public int getItemQuantityInSale() {
            return itemQuantityInSale;
        }
        
        /**
         * Get the quantity of the item that remains in the inventory.
         * 
         * @return  quantity of the item in the inventory
         */
        public int getItemQuantityInInventory() {
            return itemQuantityInInventory;
        }
        
        /**
         * Set the quantity of an item in the inventory.
         * 
         * @param newQuantity   the new quantity to which the item quantity is to be set
         */
        public void setItemQuantityInInventory(int newQuantity) {
            itemQuantityInInventory = newQuantity;
        }
        
        /**
         * Set the quantity of an item in the sale.
         * 
         * @param newQuantity   the new quantity to which the item quantity is to be set
         */
        public void setItemQuantityInSale(int newQuantity) {
            itemQuantityInSale = newQuantity;
        }
}
