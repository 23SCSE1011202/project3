To implement a user registration form and profile management system in a healthcare management project using servlets, we need to:

Create a user registration form (where a user can register their personal details).
Create a servlet to handle the registration (process user input and store it).
Create a profile page where users can view or update their details.
This example will show how to set up these features with doGet and doPost methods.

Steps to Implement User Registration and Profile
1. Create User Registration Form (register.jsp)
This page will allow a new user to input their details.

register.jsp - User Registration Form


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User Registration</title>
</head>
<body>
    <h2>User Registration</h2>
    <form action="RegisterUserServlet" method="POST">
        Full Name: <input type="text" name="fullName" required><br>
        Age: <input type="number" name="age" required><br>
        Email: <input type="email" name="email" required><br>
        Gender: 
        <select name="gender">
            <option value="Male">Male</option>
            <option value="Female">Female</option>
            <option value="Other">Other</option>
        </select><br>
        Password: <input type="password" name="password" required><br>
        Confirm Password: <input type="password" name="confirmPassword" required><br>
        <input type="submit" value="Register">
    </form>
</body>
</html>


2. Create the Servlet for Handling Registration (RegisterUserServlet.java)
This servlet will handle the POST request when the registration form is submitted. It will validate the inputs, store them in a database (for simplicity, we simulate storage in this example), and redirect the user to a profile page.

RegisterUserServlet.java


package com.healthcare.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class RegisterUserServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get user inputs from the form
        String fullName = request.getParameter("fullName");
        String age = request.getParameter("age");
        String email = request.getParameter("email");
        String gender = request.getParameter("gender");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        // Basic validation
        if (password.equals(confirmPassword)) {
            // Normally, you would save user information to a database.
            // For simplicity, let's assume registration is successful and store the user data in the session.
            
            // Create a user profile object (you can also use a database for this)
            UserProfile user = new UserProfile(fullName, age, email, gender, password);
            
            // Store the user profile in the session
            HttpSession session = request.getSession();
            session.setAttribute("userProfile", user);
            
            // Redirect to the user profile page
            response.sendRedirect("userProfile.jsp");
        } else {
            // Redirect back to the registration form if passwords don't match
            response.sendRedirect("register.jsp?error=true");
        }
    }
}


3. Create User Profile Page (userProfile.jsp)
Once the user is registered, they will be redirected to their profile page. The profile page will display the user’s details stored in the session.

userProfile.jsp - User Profile


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
        <p><strong>Age:</strong> <%= user.getAge() %></p>
        <p><strong>Email:</strong> <%= user.getEmail() %></p>
        <p><strong>Gender:</strong> <%= user.getGender() %></p>
        <form action="UpdateUserProfileServlet" method="POST">
            <input type="submit" value="Edit Profile">
        </form>
    <%
        } else {
    %>
        <p>You are not logged in. Please register first.</p>
    <%
        }
    %>
</body>
</html>



4. Create a User Profile Object (UserProfile.java)
To keep the user's information, you can create a simple Java class that represents the user profile.

UserProfile.java


package com.healthcare.servlet;

public class UserProfile {
    private String fullName;
    private String age;
    private String email;
    private String gender;
    private String password;

    // Constructor
    public UserProfile(String fullName, String age, String email, String gender, String password) {
        this.fullName = fullName;
        this.age = age;
        this.email = email;
        this.gender = gender;
        this.password = password;
    }

    // Getter methods
    public String getFullName() {
        return fullName;
    }

    public String getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getPassword() {
        return password;
    }

    // Setter methods (optional, in case you want to allow profile updates)
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}



5. Create a Servlet for Updating the Profile (UpdateUserProfileServlet.java)
If you want the user to update their profile, you can create another servlet to handle profile editing.

UpdateUserProfileServlet.java
java
Copy


package com.healthcare.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class UpdateUserProfileServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the updated information from the form
        String fullName = request.getParameter("fullName");
        String age = request.getParameter("age");
        String email = request.getParameter("email");
        String gender = request.getParameter("gender");

        // Retrieve the user profile from the session
        HttpSession session = request.getSession();
        UserProfile user = (UserProfile) session.getAttribute("userProfile");

        if (user != null) {
            // Update the user profile
            user.setFullName(fullName);
            user.setAge(age);
            user.setEmail(email);
            user.setGender(gender);

            // Store the updated profile back in the session
            session.setAttribute("userProfile", user);

            // Redirect to the updated profile page
            response.sendRedirect("userProfile.jsp");
        } else {
            // If no user session exists, redirect to login or registration page
            response.sendRedirect("register.jsp");
        }
    }
}


6. Update Profile Form (updateProfile.jsp)
This page will be used to edit the user profile details.

updateProfile.jsp



<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Update Profile</title>
</head>
<body>
    <h2>Update Profile</h2>
    <form action="UpdateUserProfileServlet" method="POST">
        Full Name: <input type="text" name="fullName" value="<%= user.getFullName() %>" required><br>
        Age: <input type="number" name="age" value="<%= user.getAge() %>" required><br>
        Email: <input type="email" name="email" value="<%= user.getEmail() %>" required><br>
        Gender: 
        <select name="gender">
            <option value="Male" <%= user.getGender().equals("Male") ? "selected" : "" %>>Male</option>
            <option value="Female" <%= user.getGender().equals("Female") ? "selected" : "" %>>Female</option>
            <option value="Other" <%= user.getGender().equals("Other") ? "selected" : "" %>>Other</option>
        </select><br>
        <input type="submit" value="Update Profile">
    </form>
</body>
</html>



Final Setup
Create database integration (optional): This example uses session management to simulate a user's data. For a real-world application, you’d want to store this information in a database.
Servlet Mapping: Ensure that your servlets are mapped in the web.xml or via annotations.
Security: In a real application, ensure that sensitive data like passwords is hashed and securely stored.
With this implementation, users can register, view their profile, and update their details in the healthcare management system.
