## Introduction🖖
The solution to [Musala soft] (https://www.musala.com/) interview assessment.

This is a simple Drone based service api capable of delivering small loads such as medication. It facilitates the registration of new drones, checking of available drones for loading, checking of drone battery level, Loading drones with medication, and checking of loaded medication for a given drone.

### Step One - Tools and Technologies used 🎼

- Spring Boot(Lastest Version)
- Spring Data JPA
- Lombok Library
- JDK 18
- Embedded Tomcat
- Mysql Database(Mysql Workbench)
- Maven
- Java IDE (Eclipse)
- Postman Client

### Step Two - Steps to Run the project Locally ⚙️

[MySQL Workbench] (https://www.mysql.com/products/workbench/) was used to run the database locally. Navigate to Project application.properties and modify the database credential to match that of your Database server such as `username` and `password`

## Installation

* Clone this repo:

```bash
git clone https://github.com/michaelik/Drone-Baesd-Service-APIs.git
```

* Open cloned project in Eclipse

* Navigate to Maven > Update, the select(check) Force Update then click OK

* Run Project As Spring Boot app

* You can also run test to assert that everything is working

## Usage 🧨

>REST APIs for Drone Service Resource

|HTTP METHOD|ROUTE|STATUS CODE|DESCRIPTION|
|:------------- | :----------: |:------------- | :----------: |
|POST|`/api/drone/register`|201|Create new drone
|POST|`/api/drone/battery`|201|Get drone battery level
|GET|`/api/drone/available`|200|Get all available drones
|POST|`/api/drone/load`|201|Insert medication into drone
|GET|`/api/drone/details/{serialNumber}`|200|Get single medication details by serial number
|POST|`/api/drone/deliver`|201|Get drone delivery status


### APIs Requirement from Client

The API is Authenticated and you will have to specify the `username` and `password` in the postman client header as Basic Auth

>**NOTE**<br>
>The `ContentType` is `application/json` 

Username: **admin**

Password: **password**

### The REST Client should be able to:

**Register New Drone**

`/api/drone/register`

The payload will have the following fields

- `serialNumber` is the unique serial number for the drone being loaded
- `model` is the unique name representing the drone (Lightweight, Middleweight, Cruiserweight, Heavyweight)
- `weightLimit` represent the weight of the drone 
- `battery` is the current battery level of the drone
- `state`  represent the current state of the drone (IDLE, LOADING, LOADED, DELIVERING, DELIVERED, RETURNING)

[![Create-New-Drone.png](https://i.postimg.cc/nrtQM428/Create-New-Drone.png)](https://postimg.cc/G8qp5s6z)
The payload is in `json format` show as the left side of the image

**Check Drone battery level for a given drone**
`/api/drone/battery`

The payload will have the following field

- `serialNumber` is the unique serial number for identifying drone battery level

[![Get-Drone-Battery.png](https://i.postimg.cc/MGNBJ2jp/Get-Drone-Battery.png)](https://postimg.cc/f37yYPKG)
The payload is in `json format` show as the left side of the image

**Checking available drones for loading**

`/api/drone/available`

The payload will not accept any field or URL parameter

[![Get-Available-Drone-For-Loading.png](https://i.postimg.cc/1tFq9Cpc/Get-Available-Drone-For-Loading.png)](https://postimg.cc/hzSjs0Zf)

**Loading a drone with medication items**

`/api/drone/load`

The payload will have the following fields

- `serialNumber` is the unique serial number for the drone being loaded
- `code` is the unique code for the medication item being loaded to the drone
- `source` is the point of loading
- `destination` is the end goal of the load

[![Insert-Medication-Into-Drone.png](https://i.postimg.cc/rpK4HFsH/Insert-Medication-Into-Drone.png)](https://postimg.cc/wtY3yzh5)
The payload is in `json format` show as the left side of the image

**Checking loaded medication items for a given drone**

`/api/drone/details`

- The payload will accept URL parameter request which is basically the specific drone serial number.

[![Get-Single-Drone-Medication-Details.png](https://i.postimg.cc/TY6rP6Ny/Get-Single-Drone-Medication-Details.png)](https://postimg.cc/fk83H6NZ)

**Delivery of medication iitem**

`/api/drone/deliver`

The payload will have the following field

- `serialNumber` is the unique serial number for identifying drone delivery status

[![Get-Drone-Delivery-Status.png](https://i.postimg.cc/MpZVyYSq/Get-Drone-Delivery-Status.png)](https://postimg.cc/JGgs8Z02)
The payload is in `json format` show as the left side of the image