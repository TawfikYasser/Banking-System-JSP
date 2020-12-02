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

                String userID = request.getParameter("myhiddenvalueID");
                String RecID = request.getParameter("toid");
                String Amount = request.getParameter("amount");
                String balance = request.getParameter("myhiddenvalueBalance");
                int iAmount = Integer.valueOf(Amount);
                int iBalance = Integer.valueOf(balance);
                int flag = 0;
                if ((Amount.isEmpty() || RecID.isEmpty()) && (iBalance <= iAmount)) {
                    out.println("Invalid data");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("transactions.jsp");
                    dispatcher.forward(request, response);
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
                        RequestDispatcher dispatcher = request.getRequestDispatcher("transactions.jsp");
                        dispatcher.forward(request, response);
                        statement.close();
                    } else {
                        RequestDispatcher dispatcher = request.getRequestDispatcher("transactions.jsp");
                        dispatcher.forward(request, response);
                        statement.close();
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
