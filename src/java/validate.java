/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.Date;
import javax.servlet.http.Cookie;

/**
 *
 * @author tawfe
 */
@WebServlet(urlPatterns = {"/validate"})
public class validate extends HttpServlet {

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
        HttpSession session = request.getSession(true);
        try (PrintWriter out = response.getWriter()) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                String url = "jdbc:mysql://localhost:3306/a2_web?useSSL=false";
                String user = "root";
                String password = "troot";
                Connection connection = null;
                Statement statement = null;
                connection = DriverManager.getConnection(url, user, password);
                String query = "SELECT * FROM customer";

                statement = connection.createStatement();

                String userID = request.getParameter("id");
                String userPassword = request.getParameter("password");

                //First: Checking for Empty fields
                if (userID.isEmpty() || userPassword.isEmpty()) {
                    //Go back to HTML page to relogin

                    RequestDispatcher dispatcher = request.getRequestDispatcher("index.html");
                    dispatcher.include(request, response);
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Empty ID or Password');");
                    out.println("</script>");

                } else {
                    /*
                    * Check if entered data exists in customer table
                     */
                    int flag = 0;
                    String correctID = "", correctPassword = "", customerName = "";
                    ResultSet resultSet = statement.executeQuery(query);

                    for (int i = 0; resultSet.next(); i++) {

                        if (resultSet.getString("customerID").equals(userID) && resultSet.getString("customerPassword").equals(userPassword)) {
                            flag++;
                            correctID = resultSet.getString("customerID");
                            correctPassword = resultSet.getString("customerPassword");
                            customerName = resultSet.getString("customerName");
                        }

                    }
                    statement.close();
                    if (flag != 0) {

                        // First initializing a new session for the customer
                        /*if (session.isNew() == false) {
                            session.invalidate();
                            session = request.getSession(true);
                        }*/
                        session.setAttribute("session_customerID", correctID);
                        session.setAttribute("session_customerPassword", correctPassword);
                        session.setAttribute("session_customerName", customerName);

                        //Second initializing a new cookie for the customer
                        //Date now = new Date();
                        //String timestamp = now.toString();
                        Cookie cookie_ID = new Cookie("cookie_customerID", correctID);
                        cookie_ID.setMaxAge(365 * 24 * 60 * 60);
                        cookie_ID.setPath("/");
                        response.addCookie(cookie_ID);

                        //Third Go to Customer Home page
                        request.setAttribute("BankAccountID", "v");
                        RequestDispatcher dispatcher = request.getRequestDispatcher("customerHome.jsp");
                        dispatcher.forward(request, response);
                    } else {
                        //Customer does not found!

                        RequestDispatcher dispatcher = request.getRequestDispatcher("index.html");
                        dispatcher.include(request, response);
                        out.println("<script type=\"text/javascript\">");
                        out.println("alert('Customer doesn’t found!');");
                        out.println("</script>");
                    }

                }

            } catch (Exception e) {
                System.err.println("Got an exception! ");
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
