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
@WebServlet(urlPatterns = {"/transfer"})
public class transfer extends HttpServlet {

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
            /* TODO output your page here. You may use following sample code. */
            try {

                String Amount = request.getParameter("amount");
                String balance = request.getParameter("myhiddenvalueBalance");

                String userID = request.getParameter("myhiddenvalueID");
                String RecID = request.getParameter("toid");

                if ((Amount.isEmpty() || RecID.isEmpty())) {
                    RequestDispatcher dispatcher = request.getRequestDispatcher("transactions.jsp");
                    dispatcher.include(request, response);
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Empty Account ID or Amount!');");
                    out.println("</script>");
                } else {
                    int iAmount = Integer.valueOf(Amount);
                    int iBalance = Integer.valueOf(balance);
                    if (iBalance <= iAmount) {
                        //Balance not enough
                        RequestDispatcher dispatcher = request.getRequestDispatcher("transactions.jsp");
                        dispatcher.include(request, response);
                        out.println("<script type=\"text/javascript\">");
                        out.println("alert('Amount is greater than your balance: " + iBalance + "!');");
                        out.println("</script>");
                    } else {

                        //Balance enough
                        Class.forName("com.mysql.jdbc.Driver");
                        String url = "jdbc:mysql://localhost:3306/a2_web?useSSL=false";
                        String user = "root";
                        String password = "troot";
                        Connection connection = null;
                        Statement statement = null;
                        connection = DriverManager.getConnection(url, user, password);
                        statement = connection.createStatement();

                        String query2 = "SELECT * FROM bankaccount";
                        Statement s = null;
                        s = connection.createStatement();
                        ResultSet rs = s.executeQuery(query2);

                        Statement updateBalance = null;
                        updateBalance = connection.createStatement();

                        Statement updateRecBalanceS = null;
                        updateRecBalanceS = connection.createStatement();

                        Statement recBalance = null;
                        recBalance = connection.createStatement();

                        int flag = 0;
                        if ((Amount.isEmpty() || RecID.isEmpty())) {
                            RequestDispatcher dispatcher = request.getRequestDispatcher("transactions.jsp");
                            dispatcher.include(request, response);

                        } else {

                            while (rs.next()) {
                                if (rs.getString("BankAccountID").toString().equals(RecID)) {
                                    //Rec ID exists
                                    flag++;
                                }
                            }

                            if (flag != 0) {
                                Random rand = new Random(); //instance of random class
                                int upperbound = 1000;
                                int randomTransactionID = rand.nextInt(upperbound);
                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                Date date = new Date();
                                String query = "INSERT INTO banktransaction(BankTransactionID, BTCreationDate, BTAmount, BTFromAccountID, BTToAccountID) VALUES("
                                        + "'" + randomTransactionID + "',"
                                        + "'" + formatter.format(date) + "',"
                                        + "'" + Amount + "',"
                                        + "'" + userID + "',"
                                        + "'" + RecID + "')";
                                int affectedRows = statement.executeUpdate(query);

                                //Updating sender balance after transaction
                                String updateSenderBalance = "UPDATE bankaccount SET BACurrentBalance = "
                                        + "'" + (iBalance - iAmount) + "'"
                                        + "WHERE (BankAccountID = " + Integer.valueOf(userID) + ");";

                                int updateAffectedS = updateBalance.executeUpdate(updateSenderBalance);

                                //Updating receiver balance after transaction
                                //1. Get receiver balance
                                String gRecBalance = "SELECT * FROM bankaccount WHERE bankaccount.BankAccountID = '" + Integer.valueOf(RecID) + "'";
                                ResultSet resBalance = recBalance.executeQuery(gRecBalance);
                                String recCurrentBalance = "";
                                while (resBalance.next()) {
                                    if (resBalance.getString("BankAccountID").toString().equals(String.valueOf(RecID))) {
                                        recCurrentBalance = resBalance.getString("BACurrentBalance");
                                    }
                                }
                                int iRecBalance = Integer.valueOf(recCurrentBalance);

                                //2. Set the new balance for receiver
                                String updateRecQBalance = "UPDATE bankaccount SET BACurrentBalance = "
                                        + "'" + (iRecBalance + iAmount) + "'"
                                        + "WHERE (BankAccountID = '"
                                        + Integer.valueOf(RecID) + "');";

                                int updatedAffecteR = updateRecBalanceS.executeUpdate(updateRecQBalance);

                                RequestDispatcher dispatcher = request.getRequestDispatcher("transactions.jsp");
                                dispatcher.forward(request, response);
                                statement.close();
                            } else {
                                RequestDispatcher dispatcher = request.getRequestDispatcher("transactions.jsp");
                                dispatcher.include(request, response);
                                out.println("<script type=\"text/javascript\">");
                                out.println("alert('Receiver Account doesn’t found!');");
                                out.println("</script>");
                                statement.close();
                            }

                        }

                    }
                }
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
