In a Healthcare Management System project, you would often need to handle both doGet and doPost methods in your servlets. These methods are part of the HTTP request-response lifecycle in Java web development, where:

doGet is typically used to handle HTTP GET requests (i.e., when retrieving or displaying data).
doPost is used to handle HTTP POST requests (i.e., when submitting or sending data, such as form submissions).
Example 1: Login Servlet Implementation with doGet and doPost Methods
In a healthcare management system, a login page could involve both the retrieval of login page (GET) and the handling of the login credentials (POST).

LoginServlet.java

package com.healthcare.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class LoginServlet extends HttpServlet {

    // Handle GET requests (display login form)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Just forward the request to the login page (JSP)
        RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
        dispatcher.forward(request, response);
    }

    // Handle POST requests (process login)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Simple validation (in a real application, validate against a database)
        if ("admin".equals(username) && "password".equals(password)) {
            // Redirect to a dashboard or homepage if login is successful
            response.sendRedirect("dashboard.jsp");
        } else {
            // Send back an error if the login fails
            response.sendRedirect("login.jsp?error=true");
        }
    }
}

Explanation:
doGet: It is used to display the login form when the user accesses the login page. The request is forwarded to login.jsp.
doPost: It handles the form submission, validating the user credentials and performing an action (such as redirecting to a dashboard or showing an error).


Example 2: Register Patient Servlet with doGet and doPost Methods
The Register Patient functionality typically involves two parts: displaying a registration form (doGet) and processing form submissions (doPost).

RegisterPatientServlet.java


package com.healthcare.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class RegisterPatientServlet extends HttpServlet {

    // Handle GET requests (display registration form)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Forward the request to the patient registration form
        RequestDispatcher dispatcher = request.getRequestDispatcher("register.jsp");
        dispatcher.forward(request, response);
    }

    // Handle POST requests (process the registration data)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String age = request.getParameter("age");
        String disease = request.getParameter("disease");

        // In a real app, you'd store the patient info in a database
        // For now, print it to the console (as an example)
        System.out.println("Patient Registered: Name = " + name + ", Age = " + age + ", Disease = " + disease);

        // After processing, redirect to a patient list or success page
        response.sendRedirect("patientList.jsp");
    }
}

Explanation:
doGet: When a user visits the registration page, the servlet forwards the request to the register.jsp page where the user can enter patient details.
doPost: When the form is submitted, the servlet retrieves the data from the form, processes it (e.g., saving it to a database), and then redirects the user to another page (e.g., patientList.jsp).



Example 3: View Patient Details Servlet with doGet Method
Sometimes, you may want to display the details of a patient by fetching data from a database or data source. This can be handled via a doGet request.

ViewPatientServlet.java


package com.healthcare.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class ViewPatientServlet extends HttpServlet {

    // Handle GET requests (view patient details)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the patient ID from the request
        String patientId = request.getParameter("patientId");

        // Fetch the patient details based on the ID (For now, assume static details)
        String patientName = "John Doe"; // In a real application, this would come from a database
        String patientAge = "45";
        String patientDisease = "Hypertension";

        // Set patient data as request attributes
        request.setAttribute("patientName", patientName);
        request.setAttribute("patientAge", patientAge);
        request.setAttribute("patientDisease", patientDisease);

        // Forward the request to a JSP page to display the patient details
        RequestDispatcher dispatcher = request.getRequestDispatcher("viewPatient.jsp");
        dispatcher.forward(request, response);
    }
}


Explanation:
doGet: The servlet processes GET requests, retrieves patient details (either from the request or a database), and forwards the request to a JSP page (viewPatient.jsp) to display the details.



Example 4: Update Patient Details Servlet with doGet and doPost Methods
If you want to allow updating patient details, you can use both doGet (to show the edit form) and doPost (to process the updated data).

UpdatePatientServlet.java


package com.healthcare.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class UpdatePatientServlet extends HttpServlet {

    // Handle GET requests (show the edit form)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get patient ID from request (for editing a specific patient)
        String patientId = request.getParameter("patientId");

        // Retrieve patient details from the database (for now, just mock data)
        String patientName = "John Doe";  // In reality, this would come from a database
        String patientAge = "45";
        String patientDisease = "Hypertension";

        // Set the patient data as attributes to be used in the JSP form
        request.setAttribute("patientId", patientId);
        request.setAttribute("patientName", patientName);
        request.setAttribute("patientAge", patientAge);
        request.setAttribute("patientDisease", patientDisease);

        // Forward to the update patient form
        RequestDispatcher dispatcher = request.getRequestDispatcher("updatePatient.jsp");
        dispatcher.forward(request, response);
    }

    // Handle POST requests (process the updated data)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String patientId = request.getParameter("patientId");
        String name = request.getParameter("name");
        String age = request.getParameter("age");
        String disease = request.getParameter("disease");

        // Update patient details in the database (or process the data)
        System.out.println("Updated Patient: ID = " + patientId + ", Name = " + name + ", Age = " + age + ", Disease = " + disease);

        // Redirect to the patient list page after successful update
        response.sendRedirect("patientList.jsp");
    }
}


Explanation:
doGet: When the user wants to edit a patient's details, this method retrieves the existing patient details and forwards the request to the edit form (updatePatient.jsp).
doPost: When the user submits the form with the updated data, the servlet processes the form and updates the patient information (usually in a database). After that, it redirects to another page (e.g., patient list).



Summary of doGet vs. doPost:
doGet: This method is used to retrieve data and display it to the user. It’s typically used for showing forms, retrieving information from a database, or navigating through the application.
doPost: This method is used to handle user input, form submissions, and actions that involve sending data to the server. It’s generally used for operations like logging in, registering a patient, or updating records.
Both methods are important for building interactive web applications like a healthcare management system.
