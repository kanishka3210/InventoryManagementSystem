

\# Inventory Management System



A console-based Inventory Management System developed using Java.



\## Features



\- Add new products

\- Update product quantities

\- View all products

\- Search for products

\- Low stock alerts

\- Transaction history

\- Inventory statistics

\- Undo the last update

\- Sort products

\- Delete products

\- Process low stock alerts



\## Technologies Used



\- Java

\- Java Collections Framework

\- Object-Oriented Programming

\- Git and GitHub



\## Project Structure



```text

InventoryManagementSystem/

├── src/

│   └── com/

│       └── inventory/

│           ├── Main.java

│           ├── InventoryManagementSystem.java

│           ├── model/

│           │   └── Product.java

│           └── comparators/

│               ├── NameComparator.java

│               ├── PriceComparator.java

│               └── ValueComparator.java

├── .gitignore

└── README.md

```



\## How to Run



Compile the project:



```bash

javac -d out src/com/inventory/model/Product.java src/com/inventory/comparators/PriceComparator.java src/com/inventory/comparators/ValueComparator.java src/com/inventory/comparators/NameComparator.java src/com/inventory/InventoryManagementSystem.java src/com/inventory/Main.java

```



Run the application:



```bash

java -cp out com.inventory.Main

```



\## Author



Kanishka M



