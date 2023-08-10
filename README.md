# Taboola-Take-Home-Assignment
Take-home assignment for an internship role at Taboola.
Consist of 3 problems solved in Java and MySQL languages.

## Problem 1: Implementation of JSON parser in Java.
This Java program provides a simple JSON parsing utility to convert a JSON string into a structured Java data structure.
The parser supports parsing JSON objects, arrays, strings, numbers, and boolean values.

### Introduction
The JSONParser class is designed to parse JSON strings and convert them into a hierarchical Java data structure consisting of Map<String, Object> for objects and List<String> for arrays. 
The parser reads through the JSON string character by character and constructs the corresponding Java data structure.

### Parsing JSON
The parsing process is achieved through several private methods in the JSONParser class:

parseObject: Parses a JSON object, converting it into a Map<String, Object> data structure.
parseValue: Parses JSON values, including objects, arrays, strings, numbers, and boolean values.
parseStringArray: Parses a JSON array of strings.
parseString: Parses a JSON string enclosed in double quotes.
parseNumber: Parses a JSON number, including integers and floating-point numbers.
customTrim: Trims whitespace characters from the input character array.
validateColon: Validates the presence of a colon (:) symbol in the JSON.

## Problem 2: Serializing and deserializing a binary tree
This Java program illustrates a preorder traversal method for serializing and deserializing a binary tree. 
In order to guarantee that the tree is cycle-free, it also has a cycle detection method.

### Introduction
The code defines a Node class representing a binary tree node. Each node has a value and references to its left and right child nodes. 
The TreeSerializer class contains methods for serializing and deserializing a binary tree and a cycle detection mechanism.

### Serialization
A binary tree is transformed into a string representation during serialization. 
Preorder traversal of the tree is used to achieve this. The tree's structure is kept intact by using the string "null" to represent null nodes. 
A Node is fed into the serialize method, which outputs a serialized string.

### Deserialization
A serialized string is transformed back into a binary tree during the deserialization process, which is the opposite of serialization. A serialized string is provided
to the deSerialize method, which returns the root Node of the rebuilt tree. Reconstructing the tree from the serialized string is done using the deSerializeHelper method,
which is a recursive helper function.

### Cycle Detection
The code has a cycle detection algorithm to make sure the input tree is acyclic. Utilizing a collection of visited nodes, the "checkCycle" technique searches for cycles. 
Recursively navigating the tree, the checkCycleHelper method looks for any cycles. A runtime exception is thrown if a cycle is found.

## Problem 3: Creating multiple tables using MYSQL
The database schema for maintaining product information, such as specifics about products, their prices, and price changes, is defined by this collection of MySQL scripts. 
The product, product_price, and product_price_change_log tables make up the schema.

### Introduction
The provided MySQL scripts create a database schema for managing product-related information. This includes details about products, their prices, and a log of price changes. 
The schema is designed to provide a comprehensive solution for tracking products and their pricing history.

### Tables:

### product
Basic information about products, such as their names, categories, creation dates/timestamps, and the user who added the product, is stored in the product table.

### product_price
The product_price table stores the current pricing information for each product. It includes the product price, discount percentage, update timestamps, and the user who modified the price. 
This table is linked to the "product" table through the "product_id" foreign key.

### product_price_change_log
A log of product price changes is stored in the product_price_change_log table. It logs the operation's nature, modification timestamps, old and new prices, discount percentages, and the user who carried out the operation.
This table is also linked to the "product" table through the "product_id" foreign key.

### Query to Join product and product_price tables
To access details about products, including their current prices and update information, a query is given. Based on the product_id field, this query executes an inner join between the product and product_price tables.
