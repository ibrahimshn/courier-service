<br />
<p align="center">
  
<h1 align="center">Courier Service</h1>

  <p align="center">
    This project aims to process the data of the couriers by checking their location relative to the stores and to calculate the total distance they travel.  
  </p>
</p>

## Tech Stack
    • Java 17
    • Spring Boot 2.7.5
    • H2 Database
    • Lombok
    • Swagger
    • Docker
    
 **Additional Information:**
 
* []() Provides swagger api docs. You can reach it after successfully run the application.
`The URL`: http://localhost:8080/swagger-ui/index.html

<!-- GETTING STARTED -->
## Getting Started

To get a local copy up and running follow these simple steps.

### Installation

1. Clone the project
   ```sh
   git clone https://github.com/ibrahimshn/courier-service
   ```
2. Execute the following docker command:
   ```sh
   docker build -t courier-service . 
   docker run -p 8080:8080 courier-service
   ```

<!-- USAGE EXAMPLES -->
## Usage

You can make request by import postman collections where is `courier-service.postman_collection.json`
