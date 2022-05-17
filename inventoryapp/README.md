# Shopify Inventory Manager Design Document

# Inventory Manager
Inventory is an effective way to manage all your inventory and shipping. It helps to keep track of all the active and inactive products.
It also helps to create shipment orders and keep track of all shipments at one place.


### Live Project Preview:
###### Deployed on AWS Amplify
[Inventory Manager Application - Website](https://main.d2tsc03xo6nb5v.amplifyapp.com/)

###### Deployed on Replit
[Inventory Manager Application - Replit](https://inventorymanager-frontend.poojaleo.repl.co/)

#### For Testing, You can use dummy account to login
    Username: Pepsico
    Password: Pepsi@12345

Note: There is a 2-3 seconds latency to establish a connection.

### Project Walkthrough
[Video link](https://www.loom.com/share/3fd04979bcce4ae8a5df0f73e11ad606)

### Technologies Used :
* Java, DynamoDB, AWS S3, AWS Lambda, AWS API Gateway, React, Bootstrap, AWS Amplify (for hosting)

## API Documentation
[InventoryManager API Documentation](http://invmanager-swagger.s3-website-us-west-2.amazonaws.com/#post-/company)

<img src="inventory-manager/src/resources/images/Swagger_Screenshot.png" width="350">

## 1. Problem Statement

Managing inventory is one of the core requirements for a logistic business. Inventory Manager is application management service for
companies such as logistics company to create products and keep track of all active and inactive products with quantity and cost.
With Inventory Manager, you can also create shipments and easily monitor the state of all shipments at a glance.

## 2. Features Included
1. Create product (inventory) items
2. Update product
3. Delete product with delete comments
4. View list of active products
5. View List of inactive products
6. Undelete product
7. Create shipment (adjust inventory)
8. Update Shipment
9. Get Shipment
10. Get list of shipments
11. Signup with company name, email address and password
12. Signin/Signout using company name and password

## 3. Use Cases

U1. *As a InventoryManager customer, I want to create my own account*

U2. *As a InventoryManager customer, I want to create new product*

U3. *As a InventoryManager customer, I want to update existing product (description, quantity, cost, name) *

U4. *As a InventoryManager customer, I want to delete a product with delete comments*

U5. *As a InventoryManager customer, I want to view all my inactive products*

U6. *As a InventoryManager customer, I want to undelete a product*

U7. *As a InventoryManager customer, I want to create shipment and my inventory should automatically be reduced*

U8. *As a InventoryManager customer, I want to update shipment (tracking number, address, status)*

U8. *As a InventoryManager customer, I want to update status of shipment using dropdown menu*


# 4. Architecture Overview

Deployed a serverless app using DynamoDB, AWS Lambda & API Gateway with 13 endpoints to handle CRUD requests.
•	Utilized Lambda Logger to maintain logs and to debug.


![Architecture Overview](/Users/pooja/lambda/inventory-manager/src/resources/images/AWSArchitecture.png)

13 endpoints are
( `CreateCompany`, `GetCompany`,  `CreateProduct`, `UpdateProduct`, `GetProduct`,
`GetAllActiveProduct`, `GetAllInactiveProduct`,  `DeleteProduct`, `UndeleteProduct`,
`CreateShipment`, `UpdateShipment`, `GetShipment`, `GetAllShipment`) that will handle the
creation, updating, and retrieval of companies products and shipments.

We will store Companies (users), Products, Shipments in DynamoDB database. 

# 5 Design Document

![Inventory Manager Design Document](https://github.com/poojaleo/inventory-manager#readme)

