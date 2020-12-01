<%-- 
    Document   : transactions
    Created on : Dec 2, 2020, 12:25:26 AM
    Author     : tawfe
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*" %>
<%
    String BankAccount = "";
    String id = request.getSession().getAttribute("session_customerID").toString();
    String password = request.getSession().getAttribute("session_customerPassword").toString();
    String name = request.getSession().getAttribute("session_customerName").toString();

    try {

        //Normal case 
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/a2_web?useSSL=false";
        String userDB = "root";
        String passwordDB = "troot";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = DriverManager.getConnection(url, userDB, passwordDB);
        String query = "SELECT * FROM bankaccount";
        statement = connection.createStatement();
        resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            if (resultSet.getString("BACustomerID").toString().equals(id)) {
                //Current customer with a bank account
                BankAccount = resultSet.getString("BankAccountID").toString();
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Transactions</title>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto">
        <link rel="icon" href="bank.png">
    </head>
    <body>
        <h1>Hello <%= name%> , <%= id%> , <%= password%>, <%= BankAccount%></h1>
    </body>
</html>
