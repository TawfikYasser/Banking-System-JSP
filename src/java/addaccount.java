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
import java.util.*;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;

/**
 *
 * @author tawfe
 */
@WebServlet(urlPatterns = {"/addaccount"})
public class addaccount extends HttpServlet {

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
                HttpSession session = request.getSession(true);
                String userID = session.getAttribute("session_customerID").toString();
                Random rand = new Random(); //instance of random class
                int upperbound = 1000;
                int randomBankAccountID = rand.nextInt(upperbound);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                String query = "INSERT INTO bankaccount(BankAccountID, BACreationDate, BACurrentBalance,BACustomerID) VALUES("
                        + "'" + randomBankAccountID + "',"
                        + "'" + formatter.format(date) + "',"
                        + "'" + "1000" + "',"
                        + "'" + userID + "')";
                int affectedRows = statement.executeUpdate(query);
                request.setAttribute("BankAccountID", randomBankAccountID);
                RequestDispatcher dispatcher = request.getRequestDispatcher("customerHome.jsp");
                dispatcher.forward(request, response);
                statement.close();
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
