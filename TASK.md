# Task for mentoring

## Spring batch

**Goals:** Practice and create a spring batch job from the scratch

**Description:** Create a spring batch job that will sync the stock based on the temp table. This temp table will be populated by file or manually.
On it there will be quantity that should be added/decreased from the stock.

**Requirements:**

- Create a table that will contain 3 columns:
    - **productId:** Product ID that we would like to update the stock
    - **quantity:** Quantity that we want to add/remove from stock, it can be a positive number (add) or a negative number (remove)
    - **createdAt** dateTime when this entry was created
- Create a spring batch job that will read from this file as a writer
- As second step we should group all the products with quantity calculated, for instance: product 1 has 3 entries with quantities: 5, 3, -4. So total for prod 1 will be 4
- As a writer save this amount on DB, dont forget to calculate it with the currenct value on stock.