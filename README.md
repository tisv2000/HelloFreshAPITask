# HelloFresh REST API automation technical task

###  Prerequisites
- JDK8 or higher
- Maven v3.5.0
- Git v2.14
- Lombok plugin for IDE

### How to run the tests and generate a report
    cd <your_projects_directory>
    git clone https://github.com/tisv2000/HelloFreshAPITask.git HelloFreshAPITask
    cd HelloFreshAPITask
    mvn clean test
    mvn allure:report
    
### Where to find the report, screenshots and logs
- Report can be found in `target/site/allure-maven` (the report file is `index.html`)
- Logging system has 3 appenders:
    - file appender, which logs information in a file in`target/logs`
    - console appender, which prints information to a console
    - allure appender, which captures test log output and store it as attachment in allure report

### Dependencies
- Rest Assured for working with REST API
- TestNG to develop tests
- Allure to generate a report
- Apache commons to generate random strings
- Lombok to avoid boilerplate code
- Logback for logging

### Configuration
- To run tests against a different environment:
    - change `base.url` property in `src/test/resources/config.properties`
    - or use the following command: `mvn clean test -Dbase.url=<url>`

### Personal notes
There are some inconsistencies with documentation and a real API behavior:

1. When sending 'bookingid' field in any kind of request (post booking/get booking) - 'bookingid' field, which is returned in the response, is not the same.
And it looks like it is generated due to the last bookingid in the list of already created bookings.
2. When sending 'email' and 'phone' fields in any kind of request (post booking / get booking) - they are not returned in the response.
3. There are some inconsistencies about status codes and returned body types:
    * For creating booking operation, when getting a successful result there should be 200 (OK) status code and the body type should be ResponseEntity. But in fact, when get a successful result, status code is 201 (Created), and the body type is CreatedBooking.
    * For getting booking operation, when getting a successful result, response body type should be ResponseEntity. But in fact for successful result response body type is CreatedBooking.
     
Also, there are some problems with delete booking and update booking operations on the server-side. There is a 403 (Forbidden) error code. 
So that's why I decided not to include them in the tests (because there were no opportunities for debugging)
