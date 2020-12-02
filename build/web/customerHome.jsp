<%-- 
    Document   : customerHome
    Created on : Dec 1, 2020, 9:15:45 PM
    Author     : tawfe
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*" %>
<!DOCTYPE html>
<%

    String BankAccount = "";
    if (request.getParameter("BankAccountID") == null) {
        BankAccount = "";
    } else {
        BankAccount = request.getParameter("BankAccountID");
    }
    String id = request.getSession().getAttribute("session_customerID").toString();
    String password = request.getSession().getAttribute("session_customerPassword").toString();
    String name = request.getSession().getAttribute("session_customerName").toString();
%>
<html>
    <head>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto">
        <link rel="icon" href="bank.png">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Customer Home</title>
        <style>
            *{
                margin: 0;
                padding: 0;
                box-sizing: border-box
            }
            body{
                min-height: 100vh;
                background: #eee;
                display: flex;
                font-family: "Roboto";
            }
            .container{
                margin-top: 50px;
                margin-bottom: auto;
                margin-left: auto;
                margin-right: auto;
                width: 500px;
                max-width: 90%
            }
            .container h3{
                text-align: center;
                margin: 50px;
                font-family: "Roboto";
            }
            .container form{
                width: 100%;
                height: 100%;
                padding: 20px;
                background: white;
                border-radius: 1px;
                box-shadow: 0 8px 16px rgba(0,0,0,.3);
                padding-top: 50px;
                padding-right: 50px;
                padding-left: 50px;
                padding-bottom: 50px;
            }
            .container form .btnAddAcount{
                margin-left: 50%;
                transform: translateX(-50%);
                width: 100%;
                height: 50px;
                border: none;
                outline: none;
                background: #27a327;
                cursor: pointer;
                font-size: 16px;
                text-transform: uppercase;
                color: white;
                border-radius: 1px;
                transition: .3s;
                font-family: "Roboto";
            }
            .container form .btnAddAcount:hover{
                opacity: .7;
            }

            .container form .btnLogout{
                margin-left: 50%;
                transform: translateX(-50%);
                width: 100%;
                height: 50px;
                border: none;
                outline: none;
                background: #C62E2E;
                cursor: pointer;
                font-size: 16px;
                text-transform: uppercase;
                color: white;
                border-radius: 1px;
                transition: .3s;
                font-family: "Roboto";
            }
            .container form .btnLogout:hover{
                opacity: .7;
            }
            .h3{
                text-align: center;
                margin-bottom: 24px;
                margin: auto;
                color: #222;
                font-family: "Roboto";
            }
            .container .txtform{
                width: 100%;
                height: 100%;
                background: white;
                border-radius: 1px;
                box-shadow: 0 8px 16px rgba(0,0,0,.3);
                padding-top: 1px;
                padding-right: 1px;
                padding-left: 1px;
                padding-bottom: 1px;
            }
            .container form .btnAddAcountDisabled{
                margin-left: 50%;
                transform: translateX(-50%);
                width: 100%;
                height: 50px;
                border: none;
                outline: none;
                background: #D1D1D1;
                cursor: default;
                font-size: 16px;
                text-transform: uppercase;
                color: white;
                border-radius: 1px;
                transition: .3s;
                font-family: "Roboto";
            }
            .container form .btnTransaction{
                margin-left: 50%;
                transform: translateX(-50%);
                width: 100%;
                height: 50px;
                border: none;
                outline: none;
                background: #5144F3;
                cursor: pointer;
                font-size: 16px;
                text-transform: uppercase;
                color: white;
                border-radius: 1px;
                transition: .3s;
                font-family: "Roboto";
            }
            .container form .btnTransaction:hover{
                opacity: .7;
            }
            .container form .btnTransactionDisabled{
                margin-left: 50%;
                transform: translateX(-50%);
                width: 100%;
                height: 50px;
                border: none;
                outline: none;
                background: #D1D1D1;
                cursor: default;
                font-size: 16px;
                text-transform: uppercase;
                color: white;
                border-radius: 1px;
                transition: .3s;
                font-family: "Roboto";
            }
        </style>
    </head>
    <body>
        <div class="container">
            <form class="txtform">
                <h3>Hello <%= name%></h3>
                <h3>Customer ID: <%= id%></h3>
                <%

                    try {

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
                        //Check if the customer has a bank account
                        resultSet = statement.executeQuery(query);
                        while (resultSet.next()) {
                            if (resultSet.getString("BACustomerID").toString().equals(id)) {
                                //Current customer has a bank account

                                BankAccount = resultSet.getString("BankAccountID").toString();
                            }
                        }
                %>

                <% if (!BankAccount.isEmpty()) {%>
                <h3 style="text-align: center;margin: 50px; font-family: Roboto; color: #27a327">Bank Account ID: <%=BankAccount%></h3>
                <% } else {%>
                <h3 style="text-align: center;margin: 50px; font-family: Roboto; color: red">You don’t have a bank account.</h3>
                <%}%>

            </form>

            <%
                } catch (Exception e) {
                    e.printStackTrace();
                }
            %>

            <form action="addaccount" method="Get">
                <% if (!BankAccount.isEmpty()) {%>
                <input  type="submit" class="btnAddAcountDisabled" value="Add Account" disabled > 
                <% } else {%>
                <input  type="submit" class="btnAddAcount" value="Add Account">
                <%}%>
            </form>
            <form action="transactions.jsp" method="Get">
                <% if (!BankAccount.isEmpty()) {%>
                <input  type="submit" class="btnTransaction" value="Transactions">
                <% } else {%>
                <input  type="submit" class="btnTransactionDisabled" value="Transactions" disabled >
                <%}%>
            </form>
            <form action="index.html" method="Get">
                <input  type="submit" class="btnLogout" value="Logout">
            </form>
        </div>

    </body>
</html>
