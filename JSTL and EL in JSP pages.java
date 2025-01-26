Using JSTL (JavaServer Pages Standard Tag Library) and Expression Language (EL) in JSP pages is an effective way to display dynamic data while separating the Java code (business logic) from the view. This is especially useful in applications like a Healthcare Management System, where you need to display user data such as profile information, medical history, and appointments.

Let's modify the previously created JSP pages to utilize JSTL and EL.



1. Add JSTL Dependency
Before using JSTL in your project, ensure that you have the JSTL library included in your WEB-INF/lib folder. If you're using Maven, you can add this dependency to your pom.xml:


<dependency>
    <groupId>javax.servlet.jsp.jstl</groupId>
    <artifactId>javax.servlet.jsp.jstl-api</artifactId>
    <version>1.2.1</version>
</dependency>
<dependency>
    <groupId>org.glassfish</groupId>
    <artifactId>jstl</artifactId>
    <version>1.2.0</version>
</dependency>


2. JSP Page to Display User Data Using JSTL and EL
We will now modify the userProfile.jsp page to use JSTL for displaying user data. The main goal is to avoid using Java scriptlets (<%= %>) and instead use EL for accessing session attributes, which makes the code more readable and maintainable.

userProfile.jsp (Using JSTL and EL)
This page will display user data, medical history, and appointments using EL and JSTL tags.


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User Profile</title>
</head>
<body>
    <h2>User Profile</h2>
    
    <c:choose>
        <c:when test="${not empty userProfile}">
            <p><strong>Full Name:</strong> ${userProfile.fullName}</p>
            <p><strong>Email:</strong> ${userProfile.email}</p>
            <p><strong>Age:</strong> ${userProfile.age}</p>
            <p><strong>Gender:</strong> ${userProfile.gender}</p>
        
            <hr>
            <h3>Medical History</h3>
            <ul>
                <c:forEach var="record" items="${sessionScope.medicalHistory}">
                    <li>${record}</li>
                </c:forEach>
            </ul>

            <hr>
            <h3>Upcoming Appointments</h3>
            <ul>
                <c:forEach var="appointment" items="${sessionScope.appointments}">
                    <li>${appointment}</li>
                </c:forEach>
            </ul>
        
        </c:when>
        <c:otherwise>
            <p>No user profile found. Please login first.</p>
        </c:otherwise>
    </c:choose>
</body>
</html>



Breakdown of Code:
JSTL Tags:

<c:choose>: This tag is similar to an if-else block. It allows conditional rendering based on the presence of a user profile.
<c:when>: If the userProfile exists, it displays the user's details.
<c:otherwise>: If userProfile is null, it prompts the user to log in.
<c:forEach>: This tag loops over collections, such as medicalHistory and appointments, which are stored in the session.
EL (Expression Language):

${userProfile.fullName}: This accesses the fullName property of the userProfile object from the session.
${sessionScope.medicalHistory}: This accesses the medicalHistory attribute stored in the session.
${appointment}: This will display each appointment from the appointments collection.



3. Using LoginServlet to Set Session Attributes
In the LoginServlet, after the user logs in, we’ll set the user profile, medical history, and appointments in the session.

Here’s how the LoginServlet can be modified:


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

            // Set up mock medical history and appointments in session
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



4. Login Page (login.jsp)
This is a simple login page where the user can input their email and password. Upon submission, the data is sent to the LoginServlet.


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

    <c:if test="${param.error != null}">
        <p style="color: red;">Invalid login. Please try again.</p>
    </c:if>
</body>
</html>



In this login page:

We use JSTL’s <c:if> to show an error message if the error parameter is passed in the URL (which happens when the login fails).
The form submits to the LoginServlet which performs authentication and redirects to the userProfile.jsp upon success.
5. Session Management in the Servlet
The servlet code provided above stores the user profile and additional attributes (medicalHistory and appointments) in the session. These attributes will be accessible across multiple requests until the session expires or the user logs out.



6. Conclusion
By using JSTL and EL, we can effectively:

Separate business logic from presentation logic.
Enhance maintainability and readability of our JSP pages.
Make dynamic content (like user profile, medical history, and appointments) easy to manage and display.
This method also keeps the Java code out of the JSP pages, making it more modular and cleaner. You can expand this further by adding more features such as editing user profiles, appointment booking, or integrating with a database to store and retrieve real data.




