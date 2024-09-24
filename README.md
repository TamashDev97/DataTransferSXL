
# Excel to PostgreSQL Data Transfer
This project is a Java-based script that reads data from an Excel file (SXL format) and transfers it into a PostgreSQL database. The script uses Apache POI for handling Excel files and JDBC for connecting and transferring data to the PostgreSQL database. It ensures efficient data transfer by batching SQL inserts, handling missing or empty cells, and providing error handling for invalid data.


## Features
- Excel File Parsing: Reads data from an Excel file (localidades.xlsx).
- PostgreSQL Integration: Inserts the parsed data into a PostgreSQL database.
- Batch Processing: Adds data in batches to improve performance.
- Data Validation: Skips rows with empty or invalid data.
- Error Handling: Includes error handling for file reading, database connection, and data type conversion.


## Technologies
- Java: The programming language used for writing the script.
- Apache POI: A library for reading Excel files (XLSX format).
- PostgreSQL JDBC: The Java Database Connectivity (JDBC) driver for connecting to PostgreSQL.
- Maven: Dependency management and build tool.



## Dependencies
- Java 17: Ensure you have JDK 17 installed.
- PostgreSQL: Set up a PostgreSQL database.
- Maven: Install Maven for building the project and managing dependencies.

## Installation
- 1. Clone the Repository:
    - ```git clone https://github.com/your-username/exceltopostgres.git```
    - ```cd exceltopostgres```
- 2. Configure the PostgreSQL Database:
    - Ensure that PostgreSQL is running and a database named practicapostgres is available.
    - The default database connection details are:
        - JDBC URL: jdbc:postgresql://localhost:5432/practicapostgres
        - Username: postgres
        - Password: campus2023
Remember that you can change these details in the Main.java file as necessary.
- 3. Install Dependencies and Build the Project:
    - Use Maven to build the project and download the required dependencies:
        - ```mvn clean install```

