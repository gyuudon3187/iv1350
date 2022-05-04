package se.kth.iv1350.pos.dto;

import se.kth.iv1350.pos.model.Item;

/**
 * Class for containing and transferring immutable information about
 * a specific item.
 */
public class ItemDTO {

	private String itemName;

	private double itemPrice;

	private double itemVATRate;

	private int itemIdentifier;

	private int itemQuantityInSale;

	private int itemQuantityInInventory;
        
        /**
         * Constructor which instantiates its attributes by copying information
         * from an existing item.
         * 
         * @param itemToCopy    the item to be copied
         */
        public ItemDTO(Item itemToCopy) {
            this.itemName = itemToCopy.getItemName();
            this.itemPrice = itemToCopy.getItemPrice();
            this.itemVATRate = itemToCopy.getItemVATRate();
            this.itemIdentifier = itemToCopy.getItemIdentifier();
            this.itemQuantityInSale = itemToCopy.getItemQuantityInSale();
            this.itemQuantityInInventory = itemToCopy.getItemQuantityInInventory();
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
	public ItemDTO(String itemName, double itemPrice, int itemVATRate,
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
		return this.itemName;
	}

        /**
         * Get the price of the item.
         * 
         * @return  the item's price
         */
	public double getItemPrice() {
		return this.itemPrice;
	}

        /**
         * Get the tax rate of the item.
         * 
         * @return  the item's tax rate
         */
	public double getItemVATRate() {
		return this.itemVATRate;
	}

        /**
         * Get the identifier used to distinguish the item from other items.
         * 
         * @return  the item's identifier
         */
	public int getItemIdentifier() {
		return this.itemIdentifier;
	}

        /**
         * Get the quantity of the item registered in the sale.
         * 
         * @return  quantity of the item in the sale
         */
	public int getItemQuantityInSale() {
		return this.itemQuantityInSale;
	}

        /**
         * Get the quantity of the item that remains in the inventory.
         * 
         * @return  quantity of the item in the inventory
         */
	public int getItemQuantityInventory() {
		return this.itemQuantityInInventory;
	}

}
