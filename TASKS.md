# Task for mentoring

## Junits + Tests

- Fix all junits present on the project;
- Add enough tests to cover at least 80%;
  - Use integration tests;
  - You can start the  test from "service", not from controller;
- Add testscontainer for the database
    - In case you need to use any "free" docker-desktop, you can use Rancher desktop
- For any API consumer use webmock for it
  - In the future we can switch it to Spring cloud contracts
- Create API contract using SpringCloud Contracts (Optional for now - but good to have)
- Create at least one saving verification using argument captor. Captor on repository.save
- Create at least one saving verification checking from the DB.

## File processing 

**Goals:** Practice parallel process, concurrency, race condition and transactions.

**Description:** For this task we will update our stock via file and in batch. The idea is that we will receive a huge file that will contain multiple lines where each line will be a combination of productId and quantity which we should update our database. For the sake of performance we would like to process this file in parallel, so it means that each line will be process in different thread. Also, another requirement will be that at same file we will be able to create new stock for the product, and we can have more than one line for the same product with different quantity.

**Requirements:**

- Create a file (stockUpdate.csv) that will contain 2 columns:
  - **productId:** Product ID that we would like to update the stock
  - **quantity:** Quantity that we want to add/remove from stock, it can be a positive number (add) or a negative number (remove)
- Read the file and process each line in parallel - you can limit to 10 thread for example
- Each line will have they own transaction
- Take care that at we can have multiple lines for the same product being processed at the same time, it can cause lock on DB OR wrong final result

**File Example:** stockUpdate.csv
``` 
1,5
2,3
3,7
2,-3
6,20
2,-3
2,-4
2,10
3,6
```