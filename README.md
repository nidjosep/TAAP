TAAP - Teaching Assistant Application

TAAP - Teaching Assistant Application
=====================================

Table of Contents
-----------------

* [Requirements](#requirements)
* [Technologies Used](#technologies-used)
* [How to Use](#how-to-use)
* [Project Structure](#project-structure)
* [Contributing](#contributing)
* [License](#license)
* [Authors](#authors)

Requirements
------------

* The system should allow users to create a new lab session, the lab owner will get a TA token
* The lab owner should be able to login using the TA token or share it with other teaching assistants
* The system should allow multiple teaching assistants to join a lab session with the same token
* The system should also provide a way for logged-in teaching assistants to copy student's token
* The students should be able to join the lab session using the student's token
* The system should associate both TA and student users logged-in with the TA/Student token pair with the same lab session.
* The system should allow students to raise a review request to the teaching assistant when they finish their lab exercise.
* The system should allow students to revert their submission request if they are not at queue position 0
* The teaching assistant should be able to see the number of students in the queue, and the system should assign review requests to teaching assistants on a first-come, first-serve basis.
* The system supports multiple lab sessions and multiple teaching assistants to be registered with a lab session so that the review requests will be split based on availability.
* Once the teaching assistant taps on the "ready to accept request" button, a request will be assigned to the teaching assistant from the queue.
* The system should allow the teaching assistant to grade the students and submit the evaluation.
* While in the queue, the students should be able to see their queue status on the page.
* The lab session should be time limited, and TA will have the option to end a lab session.
* Students should be able to view their grade report in the report tab.
* The teaching assistants should be able to view the grade report of all students in the report tab.

Technologies Used
-----------------

* Java Spring Boot
* Thymeleaf
* HTML
* CSS
* Bootstrap
* JavaScript
* JQuery
* Server-Sent Events (SSE)

How to Use
----------

* Clone the repository.
* Build the code using `gradlew build` command.
* Run the project using Spring Boot by executing `java -jar build/libs/taap-0.0.1-SNAPSHOT.jar`.
* Access the application on a web browser at `http://localhost:8080/taap`.
* Create a new lab session and share the token with students and teaching assistants.
* Students can join the active lab session and complete their lab exercise.
* Students can raise a review request to the teaching assistant when they finish their lab exercise.
* Teaching assistants can see the number of students in the queue and accept review requests.
* Teaching assistants can grade the students and submit the evaluation.
* Once the lab session expires, the evaluation results will become available in the dashboard for download for the next 24 hours.

Project Structure
-----------------

The TAAP application has the following project structure:

* `src/main/java`: Contains the Java source code for the application.
* `src/main/resources`: Contains the static resources for the application, such as files iles.
* `src/main/templates`: Contains the Thymeleaf templates for the application.
* `src/test/java`: Contains the Java test code for the application.

Contributing
------------

If you would like to contribute to the TAAP application, please open an issue or submit a pull request on the GitHub repository.

License
-------

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.

Authors
-------

* Ashwanth Manikoth
* Joel Cherian Jacob
* Nidhin Joseph
* Soorya Sahar Puducode Narayanan