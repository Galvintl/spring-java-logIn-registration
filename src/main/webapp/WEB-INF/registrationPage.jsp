<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>    
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Registration Page</title>
    <link rel="stylesheet" href="/webjars/bootstrap/4.5.0/css/bootstrap.min.css" />
    <script src="/webjars/jquery/3.5.1/jquery.min.js"></script>
    <script src="/webjars/bootstrap/4.5.0/js/bootstrap.min.js"></script>
</head>
<body>
    <h1>Register!</h1>
    
    <p class="text-danger"><form:errors path="user.*"/></p>  
    <!-- show all the errors from form -->
    
    <form:form method="POST" action="/registration" modelAttribute="user">
        <p>
            <form:label path="firstName">First Name:</form:label>
            <form:input type="firstName" path="firstName"/>
        </p>
        <p>
            <form:label path="lastName">Last Name:</form:label>
            <form:input type="lastName" path="lastName"/>
        </p>
        <p>
            <form:label path="email">Email:</form:label>
            <form:input type="email" path="email"/>
        </p>
        <p>
            <form:label path="password">Password:</form:label>
            <form:password path="password"/>
        </p>
        <p>
            <form:label path="passwordConfirmation">Password Confirmation:</form:label>
            <form:password path="passwordConfirmation"/>
        </p>
        <input type="submit" value="Register!"/>
    </form:form>
<br>
    <br>

    <!-- to collect information from a regular form (not form:form) - use RequestParam -->
    <h1>Login</h1>
    <p><c:out value="${error}" /></p>
    <!-- error matches error in the flashAttributes on controller page -->
    <form method="post" action="/login">
        <p>
            <label for="email">Email</label>
            <input type="text" id="email" name="email"/>
        </p>
        <p>
            <label for="password">Password</label>
            <input type="password" id="password" name="password"/>
        </p>
        <input type="submit" value="Login!"/>
    </form> 
</body>
</html>
