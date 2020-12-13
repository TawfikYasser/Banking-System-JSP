/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.text.SimpleDateFormat;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.Date;
import javax.servlet.http.Cookie;

/**
 *
 * @author tawfe
 */
@WebServlet(urlPatterns = {"/cancelTransaction"})
public class cancelTransaction extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            try {

                String userID = request.getParameter("myhiddenvalueID");
                String RecID = request.getParameter("toid");
                String amount = request.getParameter("amountC");
                String senderBalance = request.getParameter("senderBalance");
                String transactionID = request.getParameter("transactionID");
                int iAmount = Integer.valueOf(amount);
                int iSenderBalance = Integer.valueOf(senderBalance);
                Class.forName("com.mysql.jdbc.Driver");
                String url = "jdbc:mysql://localhost:3306/a2_web?useSSL=false";
                String user = "root";
                String password = "troot";
                Connection connection = null;
                connection = DriverManager.getConnection(url, user, password);
                Statement statement1 = null;
                statement1 = connection.createStatement();
                Statement statement2 = null;
                statement2 = connection.createStatement();
                Statement statement3 = null;
                statement3 = connection.createStatement();
                Statement statement4 = null;
                statement4 = connection.createStatement();

                //Delete the transaction
                String deleteTransaction = "DELETE FROM `a2_web`.`banktransaction` WHERE (`BankTransactionID` = '" + Integer.valueOf(transactionID) + "')";
                int affectedRow1 = statement1.executeUpdate(deleteTransaction);

                //Update Sender Balance 
                //Updating sender balance after transaction
                String updateSenderBalance = "UPDATE bankaccount SET BACurrentBalance = "
                        + "'" + (iSenderBalance + iAmount) + "'"
                        + "WHERE (BankAccountID = " + Integer.valueOf(userID) + ");";

                int updateAffectedS = statement2.executeUpdate(updateSenderBalance);

                //Update Receiver Balance
                //1. Get receiver balance
                String gRecBalance = "SELECT * FROM bankaccount WHERE bankaccount.BankAccountID = '" + Integer.valueOf(RecID) + "'";
                ResultSet resBalance = statement3.executeQuery(gRecBalance);
                String recCurrentBalance = "";
                while (resBalance.next()) {
                    if (resBalance.getString("BankAccountID").toString().equals(String.valueOf(RecID))) {
                        recCurrentBalance = resBalance.getString("BACurrentBalance");
                    }
                }
                int iRecBalance = Integer.valueOf(recCurrentBalance);

                //2. Set the new balance for receiver
                String updateRecQBalance = "UPDATE bankaccount SET BACurrentBalance = "
                        + "'" + (iRecBalance - iAmount) + "'"
                        + "WHERE (BankAccountID = '"
                        + Integer.valueOf(RecID) + "');";

                int updatedAffecteR = statement4.executeUpdate(updateRecQBalance);

                RequestDispatcher dispatcher = request.getRequestDispatcher("transactions.jsp");
                dispatcher.forward(request, response);
                statement1.close();
                statement2.close();
                statement3.close();
                statement4.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
