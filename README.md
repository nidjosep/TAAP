TAAP - Teaching Assistant Application
=============================================

TAAP is a web application that assists teaching assistants and professors in evaluating students' performance in lab sessions.

Requirements
------------

*   The system should allow the creation of new lab sessions and generate tokens for teaching assistants and students to join the active lab session.
*   The system should allow students to raise a review request to the teaching assistant when they finish their lab exercise.
*   The teaching assistant should be able to see the number of students in the queue, and the system should assign review requests to teaching assistants on a first-come, first-serve basis.
*   The system supports multiple lab sessions and multiple teaching assistants to be registered with a lab session so that the review requests will be split based on availability.
*   Once the teaching assistant taps on the "ready to accept request" button, a request will be assigned to the teaching assistant from the queue.
*   The system should allow the teaching assistant to grade the students and submit the evaluation.
*   While in the queue, the students should be able to see their queue status on the page.
*   The lab session should have a set time duration, and once the session expires, the evaluation results should become available in the dashboard for download for the next 24 hours.

Technologies Used
-----------------

*   Java Spring Boot
*   Server-Sent Events (SSE)
*   Thymeleaf
*   HTML
*   CSS
*   Bootstrap
*   JavaScript
*   jQuery

How to Use
----------

1.  Clone the repository.
2.  Build the project with Gradle.
3.  Run the project using Spring Boot.
4.  Access the application on a web browser.
5.  Create a new lab session and share the token with students and teaching assistants.
6.  Students can join the active lab session and complete their lab exercise.
7.  Students can raise a review request to the teaching assistant when they finish their lab exercise.
8.  Teaching assistants can see the number of students in the queue and accept review requests.
9.  Teaching assistants can grade the students and submit the evaluation.
10.  Once the lab session expires, the evaluation results will become available in the dashboard for download for the next 24 hours.

License
-------

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.

Authors
-------

*   Ashwanth Manikoth
*   Joel Cherian Jacob
*   Nidhin Joseph
*   Soorya Sahar Puducode Narayanan