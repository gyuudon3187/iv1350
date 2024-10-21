# Point of Sale System (POS) CLI Application
This repository contains a command-line interface (CLI) program developed as part of a school assignment. The program simulates a Point of Sale (POS) system, allowing users to interact with various views such as `CashierView`, `AdminView`, `TotalRevenueView`, and `TotalRevenueFileOutput`. The program manages item scanning, discounts, payments, and revenue tracking.

## Features
**CashierView:** Primary mode where items are scanned, quantities can be modified, discounts applied, and payments processed.

**AdminView:** Manage revenue "observers" (i.e., views tracking the total revenue).

**TotalRevenueView:** Inspect the current total revenue.

**TotalRevenueFileOutput:** Output the total revenue to a text file.

## How to Run the Program
1. Clone the repository
   ```
   git clone https://github.com/gyuudon3187/iv1350/tree/main
   ```
2. Navigate to the source directory:
   ```
   cd iv1350/src/main/java
   ```
3. Compile the main Java class:
   ```
   javac se/kth/iv1350/pos/startup/Main.java
   ```
4. Run the program:
   ```
   java se/kth/iv1350/pos/startup/Main
   ```
## Program flow
### CashierView
This view allows the user to scan items, modify their quantities, apply customer discounts, and process payments. After completing a sale, the total revenue is updated, and an option is provided to print the receipt to a text file.

**Example:**
```
Please select view mode:
Admin view (enter 'AdminView')
Cashier view (enter 'CashierView')
Total revenue view (enter 'TotalRevenueView')
Total revenue output view (enter 'TotalRevenueFileOutput')
>> CashierView

If you wish to scan items, enter 'scan'.
If you wish to modify the quantity of the latest scanned item, enter 'quantity'.
If you wish to end the sale, enter 'end'.
>> scan

Please scan the item identifier
>> 452283101

Item succesfully added.
Do you wish to scan another item? (y/n)
>> n

[Summary of ongoing sale]

Items in sale:
Yoghurt x1              16.0 kr

Running total:16.0

If you wish to scan items, enter 'scan'.
If you wish to modify the quantity of the latest scanned item, enter 'quantity'.
If you wish to end the sale, enter 'end'.
>> quantity

Set a new quantity:
>> 2

[Summary of ongoing sale]

Items in sale:
Yoghurt x2              16.0 kr

Running total:32.0

If you wish to scan items, enter 'scan'.
If you wish to modify the quantity of the latest scanned item, enter 'quantity'.
If you wish to end the sale, enter 'end'.
>> end

[Summary of ongoing sale]

Items in sale:
Yoghurt x2              16.0 kr

Running total:32.0
Total price: 32.00
Total VAT: 3.84

Does the customer request discount? (y/n)
>> y

Please enter the customer's identification:
>> 9510018523

[Summary of ongoing sale]

Items in sale:
Yoghurt x2              16.0 kr

Running total:24.0

Total price: 24.00
Total VAT: 3.84

Enter the amount of cash you wish to pay by:
>> 30

21/10/2024 19:46:29
Total Revenue: 24.0

Thank you for shopping at Daniel's Supermarket!
Your receipt has been printed and can be found over at /home/user/Programming/Java/iv1350/src/main/java
We're looking forward to seeing you again.
```

### TotalRevenueView
In this view, the user can check the current total revenue, with an option to refresh the total revenue.

**Example:**
```
Admin view (enter 'AdminView')
Cashier view (enter 'CashierView')
Total revenue view (enter 'TotalRevenueView')
Total revenue output view (enter 'TotalRevenueFileOutput')
>> TotalRevenueView

21/10/2024 20:18:46
Total Revenue: 24.0

Options:
1) Enter 'r' to refresh the total revenue.
2) Press any other key + enter to go back to previous menu.
```

### TotalRevenueFileOutput
This view allows the user to output the current total revenue to a text file.

**Example:**
```
Please select view mode:
Admin view (enter 'AdminView')
Cashier view (enter 'CashierView')
Total revenue view (enter 'TotalRevenueView')
Total revenue output view (enter 'TotalRevenueFileOutput')
>> TotalRevenueFileOutput

Do you wish to print the current total revenue as a text file? (y/n)
>> y
```

### AdminView
In the Admin view, users can add or remove "observers" that track and display the total revenue, such as `TotalRevenueView` and `TotalRevenueFileOutput`.

**Example:**
```
Please select view mode:
Admin view (enter 'AdminView')
Cashier view (enter 'CashierView')
Total revenue view (enter 'TotalRevenueView')
Total revenue output view (enter 'TotalRevenueFileOutput')
AdminView
<<Currently added observers>>
se.kth.iv1350.pos.view.TotalRevenueFileOutput
se.kth.iv1350.pos.view.TotalRevenueView

Enter 'add' to add observers, and 'remove' to remove them.
Alternatively, enter 'back' to go back to the previous menu.
```
