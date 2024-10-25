package BRJITGP;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "SignUpResult", urlPatterns = "/SignUpResult")
public class SignUpResult extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Connection con;
    public SignUpResult(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/brunt_reversal_just_in_time_prophecy", "root","Keerthi@2003");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        // HttpSession
        HttpSession session = request.getSession();
        String sessionId = session.getId();
        String username = (String) session.getAttribute("username");

        // getParameter values from HTML form
        String signUpUsername = request.getParameter("username");
        String signUpPassword = request.getParameter("password1");


        out.println("<!DOCTYPE html> <html lang=\"en\">");
        out.println("<head> " +
                "<meta charset=\"UTF-8\"> " +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"> " +
                // Add Page Title here
                "<title>Result - SignUp - WDMS</title> " +
                "<link rel=\"stylesheet\" href=\"bootstrap-4.6.2/css/bootstrap.css\"> " +
                "<link rel=\"stylesheet\" href=\"css/style.css\"> " +
                "</head>");

        out.println("<body>");

        // nav bar code - dashboard content
        if (username != null){
            out.println("<div class=\"d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3 bg-white border-bottom shadow-sm\"> " +
                    "<a class=\"navbar-brand my-0 mr-md-auto\" href=\"Dashboard\"> " +
                    "<img src=\"img/logo.svg\" alt=\"logo\" width=\"130\" height=\"30\" alt=\"Logo\" loading=\"lazy\"> " +
                    "</a> " +
                    "<button class=\"btn btn-outline logout-btn\" onclick=\"location.href='Logout'\">Logout ("+username+")</button> " +
                    "</div>");
        } else {
            out.println("<div class=\"d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3 bg-white border-bottom shadow-sm\"> " +
                    "<a class=\"navbar-brand my-0 mr-md-auto\" href=\"index.html\"> " +
                    "<img src=\"img/logo.svg\" alt=\"logo\" width=\"130\" height=\"30\" alt=\"Logo\" loading=\"lazy\"> " +
                    "</a> " +
                    "<button class=\"btn btn-outline logout-btn\" onclick=\"location.href='login.html'\">Dashboard Login</button> " +
                    "</div>");
        }

        // block content
        out.println("<div class=\"container h-100\"> " +
                "<div class=\"row align-items-center h-100\" > " +
                "<div class=\"col-11 mx-auto\"> " +
                "<div class=\"mt-4\"> " +
                "<div class=\"text-center p-1\"> " +
                // Add Title here
                "<h3 class=\"theme-color\">Result - SignUp</h3> " +
                "</div> " +
                "</div>" );

        // dashboard content
        try{
            PreparedStatement ps = con.prepareStatement("INSERT INTO users_table VALUES (?,?)", ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ps.setString(1,signUpUsername);
            ps.setString(2,signUpPassword);

            try {
                int i = ps.executeUpdate();

                if (i > 0) {
                    out.println("<h1 class='text-center'>New User Added successfully!</h1>");
                    out.println("<h2 class='text-center'><u>"+signUpUsername+"</u></h2>");
                    out.println("<button class='btn action-btn btn-block' onclick=\"location.href='index.html'\">Go Back</button>");
                }

            } catch (SQLIntegrityConstraintViolationException e){
                out.println("<h1 class='text-center'>Username Already Taken!!!</h1>");
                out.println("<h2 class='text-center'><u>"+signUpUsername+"</u></h2>");
                out.println("<button class='btn action-btn btn-block' onclick=\"location.href='SignUp'\">Go Back</button>");
            }

            ps.clearParameters();

        } catch (SQLException e) {
            out.println("<h1 class='text-center'>Something went wrong! </h1>");
            out.println("<button class='btn action-btn btn-block' onclick=\"location.href='SignUp'\">Go Back</button>");
            e.printStackTrace();
        }

        out.println(        "<br>"+
                "</div>" +
                "</div>" +
                "</div>");

        out.println("</body>");
        out.println("</html>");
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}