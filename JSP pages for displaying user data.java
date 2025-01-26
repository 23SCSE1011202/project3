To implement JSP pages for displaying user data in a He
althcare Management System project, we'll follow a structure that ensures separation of concerns between the business logic (handled by servlets) and the presentation layer (handled by JSP). The servlets will collect data (e.g., user profile, medical records) and forward it to the corresponding JSP pages for display.

Here's how to implement JSP pages that display user data like profile details, medical history, and appointments.



1. Model Class for User (UserProfile.java)
We assume that the UserProfile model class stores information about a user, such as their personal details. Here's the class:


package com.healthcare.model;

public class UserProfile {
    private String fullName;
    private String email;
    private String gender;
    private String age;

    // Constructor
    public UserProfile(String fullName, String email, String gender, String age) {
        this.fullName = fullName;
        this.email = email;
        this.gender = gender;
        this.age = age;
    }

    // Getter and Setter methods
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}


2. Servlet to Handle User Data (DisplayUserProfileServlet.java)
This servlet will collect the user profile information (which could be from a session or a database) and forward it to a JSP page to display the data.


package com.healthcare.servlet;

import com.healthcare.model.UserProfile;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class DisplayUserProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Simulating fetching user data from a database or session
        HttpSession session = request.getSession();
        
        // Check if the user is logged in (or has a session)
        UserProfile user = (UserProfile) session.getAttribute("userProfile");

        if (user != null) {
            // Forward the request to the JSP page to display the profile data
            RequestDispatcher dispatcher = request.getRequestDispatcher("userProfile.jsp");
            dispatcher.forward(request, response);
        } else {
            // If the user is not logged in, redirect them to the login page
            response.sendRedirect("login.jsp");
        }
    }
}



3. JSP Page to Display User Data (userProfile.jsp)
This page will display the details of the user that have been forwarded from the servlet (via the session or request).


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User Profile</title>
</head>
<body>
    <h2>User Profile</h2>
    
    <%-- Get the user profile from the session --%>
    <%
        UserProfile user = (UserProfile) session.getAttribute("userProfile");
        if (user != null) {
    %>
        <p><strong>Full Name:</strong> <%= user.getFullName() %></p>
        <p><strong>Email:</strong> <%= user.getEmail() %></p>
        <p><strong>Age:</strong> <%= user.getAge() %></p>
        <p><strong>Gender:</strong> <%= user.getGender() %></p>
        
        <hr>
        <h3>Medical History</h3>
        <ul>
            <%-- Assuming medical history is an array or list, which is set in session --%>
            <%
                String[] medicalHistory = (String[]) session.getAttribute("medicalHistory");
                if (medicalHistory != null && medicalHistory.length > 0) {
                    for (String record : medicalHistory) {
            %>
                        <li><%= record %></li>
            <%
                    }
                } else {
            %>
                    <p>No medical history available.</p>
            <%
                }
            %>
        </ul>

        <hr>
        <h3>Upcoming Appointments</h3>
        <ul>
            <%-- Assuming appointments are stored as a list of strings --%>
            <%
                String[] appointments = (String[]) session.getAttribute("appointments");
                if (appointments != null && appointments.length > 0) {
                    for (String appointment : appointments) {
            %>
                        <li><%= appointment %></li>
            <%
                    }
                } else {
            %>
                    <p>No upcoming appointments.</p>
            <%
                }
            %>
        </ul>
        
    <%
        } else {
    %>
        <p>No user profile found. Please login first.</p>
    <%
        }
    %>

</body>
</html>



4. Login Page (login.jsp)
A simple login page to simulate user login. This is where the user will input their credentials.


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body>
    <h2>Login</h2>
    <form action="LoginServlet" method="POST">
        <label for="email">Email:</label><br>
        <input type="email" name="email" required><br><br>
        <label for="password">Password:</label><br>
        <input type="password" name="password" required><br><br>
        <input type="submit" value="Login">
    </form>
</body>
</html>



5. Servlet for Handling Login (LoginServlet.java)
This servlet handles the login form submission. If the login is successful, it stores user data in the session.


package com.healthcare.servlet;

import com.healthcare.model.UserProfile;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get email and password from form
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // For simplicity, let's assume "user@example.com" and "password123" is valid
        if ("user@example.com".equals(email) && "password123".equals(password)) {
            // Create a UserProfile object with dummy data
            UserProfile user = new UserProfile("John Doe", email, "Male", "30");

            // Save the UserProfile object in the session
            HttpSession session = request.getSession();
            session.setAttribute("userProfile", user);

            // Set up mock medical history and appointments
            session.setAttribute("medicalHistory", new String[] {"High blood pressure", "Asthma"});
            session.setAttribute("appointments", new String[] {"2025-02-10 - Dr. Smith", "2025-03-15 - Dr. Lee"});

            // Redirect to the user profile page
            response.sendRedirect("userProfile.jsp");
        } else {
            // Invalid login, redirect back to the login page
            response.sendRedirect("login.jsp?error=true");
        }
    }
}


6. Mapping the Servlets in web.xml
In your WEB-INF/web.xml, you can define the servlet mappings for LoginServlet and DisplayUserProfileServlet.


<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee
             http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">

    <servlet>
        <servlet-name>LoginServlet</servlet-name>
        <servlet-class>com.healthcare.servlet.LoginServlet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>LoginServlet</servlet-name>
        <url-pattern>/LoginServlet</url-pattern>
    </servlet-mapping>

    <servlet>
        <servlet-name>DisplayUserProfileServlet</servlet-name>
        <servlet-class>com.healthcare.servlet.DisplayUserProfileServlet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>DisplayUserProfileServlet</servlet-name>
        <url-pattern>/DisplayUserProfileServlet</url-pattern>
    </servlet-mapping>

</web-app>



7. Testing the Application
Access the Login Page: Go to http://localhost:8080/YourApp/login.jsp.
Submit Login Form: Use the login credentials:
Email: user@example.com
Password: password123
View User Profile: After successful login, the user will be redirected to the userProfile.jsp, displaying their profile, medical history, and appointments.



Summary
In this example, we’ve implemented the following:

User login using LoginServlet which authenticates the user.
Displaying user data (profile, medical history, appointments) on userProfile.jsp.
Session management to store user data like UserProfile, medicalHistory, and appointments.
This structure can be expanded by integrating a database to store the data persistently, along with more sophisticated user management features like password encryption, user roles, etc.
