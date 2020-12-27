<%-- 
    Document   : transactions
    Created on : Dec 2, 2020, 12:25:26 AM
    Author     : tawfe
--%>

<%@page import="java.util.concurrent.TimeUnit"%>
<%@page import="java.util.Locale"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*" %>
<%@page import="java.util.Date" %>
<%
    String BankAccount = "";
    String balance = "";
    String id = request.getSession().getAttribute("session_customerID").toString();
    String password = request.getSession().getAttribute("session_customerPassword").toString();
    String name = request.getSession().getAttribute("session_customerName").toString();
    ResultSet tResultSet = null;
    ResultSet resultSet = null;

    try {

        //Normal case 
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/a2_web?useSSL=false";
        String userDB = "root";
        String passwordDB = "troot";
        Connection connection = null;
        Statement statement = null;
        Statement s = null;
        connection = DriverManager.getConnection(url, userDB, passwordDB);
        String query = "SELECT * FROM bankaccount";
        String tQuery = "SELECT * FROM banktransaction";
        statement = connection.createStatement();
        s = connection.createStatement();
        resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            if (resultSet.getString("BACustomerID").toString().equals(id)) {
                //Current customer with a bank account

                BankAccount = resultSet.getString("BankAccountID").toString();
                balance = resultSet.getString("BACurrentBalance").toString();

            }
        }

        tResultSet = s.executeQuery(tQuery);
%>


<%
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
        <style>

            body{
                margin: 0;
                padding: 20px;

            }
            *{
                box-sizing: border-box;
            }
            h1{
                text-align: center;
                margin: 50px;
                font-family: "Roboto";
            }
            h3{
                text-align: center;
                margin: 50px;
                font-family: "Roboto";
            }
            table{
                width: 100%;
                margin-left: 50%;
                margin-top: 20%;
                transform: translate(-50%,-50%);
                border-collapse: collapse;
                border-spacing: 0;
                box-shadow: 0 2px 15px rgba(64,64,64,.7);
                border-radius: 12px 12px 0 0;
                overflow: hidden;

            }
            td , th{
                padding: 5px 10px;
                text-align: center;
            }
            th{
                background-color: #27a327;
                color: #fafafa;
                font-family: "Roboto";
                font-weight: 200;
                text-transform: uppercase;


            }
            tr{
                width: 100%;
                background-color: #fafafa;
                font-family: "Roboto";
            }
            tr:nth-child(even){
                background-color: #eeeeee;
            }
            .createTransaction{
                margin-left: 50%;
                transform: translateX(-50%);
                height: 50px;
                border: none;
                outline: none;
                background: #27a327;
                cursor: pointer;
                font-size: 16px;
                text-transform: uppercase;
                color: white;
                border-radius: 4px;
                transition: .3s;
                font-family: "Roboto";
            }
            .cancelTransaction{
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
                border-radius: 4px;
                transition: .3s;
                font-family: "Roboto";
            }
            .cancelTransaction:hover{
                opacity: .7;
            }
            .createTransaction:hover{
                opacity: .7;
            }
            .cancelTransactionDisabled{
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
                border-radius: 4px;
                transition: .3s;
                font-family: "Roboto";
            }
            .form-control{
                text-align: center;
                margin-left: 50%;
                margin-bottom: 1%;
                border-radius: 4px;
                transform: translateX(-50%);
                height: 50px;
            }
        </style>
    </head>
    <body>
        <h1>Hello <%=name%></h1>
        <h3 style="color: #27a327">Your Transactions Page</h3>
        <h3 style="color: #5144F3">Your balance: <%=balance%></h3>

        <form action="transfer" method="Post">
            <input type="text" class="form-control" name="toid" placeholder="Account ID">
            <input type="text" class="form-control" name="amount" placeholder="Amount">
            <input type="hidden" name="myhiddenvalueID" value=<%=BankAccount%> />
            <input type="hidden" name="myhiddenvalueBalance" value=<%=balance%> />
            <input  type="submit" class="createTransaction" value="Create Transaction">
        </form>
        <table border="1">
            <tr> 
                <th><b>Transaction ID</b></th>
                <th><b>Transaction Date</b></th>
                <th><b>Transaction Amount</b></th>
                <th><b>Transaction Account Sender</b></th>
                <th><b>Transaction Account Receiver</b></th>
                <th><b>Action</b></th>
            </tr>
            <%
                while (tResultSet.next()) {%>

            <%if (tResultSet.getString("BTFromAccountID").equals(BankAccount)) {%>
            <tr> 
                <td><%=tResultSet.getString("BankTransactionID")%></td>
                <td><%=tResultSet.getString("BTCreationDate")%></td>
                <td><%=tResultSet.getString("BTAmount")%></td>
                <td><%=tResultSet.getString("BTFromAccountID")%></td>
                <td><%=tResultSet.getString("BTToAccountID")%></td>
                <td>
                    <%
                        //Getting current date

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                        Date firstDate = sdf.parse(tResultSet.getString("BTCreationDate"));
                        Date date = new Date();
                        Date secondDate = sdf.parse(sdf.format(date));
                        long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
                        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                        if (diff > 1) {
                    %>
                    <form>
                        <input type="submit" class="cancelTransactionDisabled" value="Cancel Transaction" disabled>
                    </form>
                    <%} else {%>
                    <form action="cancelTransaction" method="Post">
                        <input type="hidden" name="myhiddenvalueID" value=<%=BankAccount%> />
                        <input type="hidden" name="toid" value=<%=tResultSet.getString("BTToAccountID")%> />
                        <input type="hidden" name="amountC" value=<%=tResultSet.getString("BTAmount")%> />
                        <input type="hidden" name="senderBalance" value=<%=balance%> />
                        <input type="hidden" name="transactionID" value=<%=tResultSet.getString("BankTransactionID")%> />
                        <input type="submit" class="cancelTransaction" value="Cancel Transaction">
                    </form>
                    <%}%>
                </td>
            </tr>
            <%}%>

            <%}%>

        </table>
        <br/>

    </body>
</html>
