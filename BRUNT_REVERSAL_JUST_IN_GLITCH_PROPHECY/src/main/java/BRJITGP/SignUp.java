package BRJITGP;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(name = "SignUp", urlPatterns = "/SignUp")
public class SignUp extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        // HttpSession
        HttpSession session = request.getSession();
        String sessionId = session.getId();
        String username = (String) session.getAttribute("username");


        out.println("<!DOCTYPE html> <html lang=\"en\">");
        out.println("<head> " +
                "<meta charset=\"UTF-8\"> " +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"> " +
                // Add Page Title here
                "<title>SignUp - WDMS</title> " +
                "<link rel=\"stylesheet\" href=\"bootstrap-4.6.2/css/bootstrap.css\"> " +
                "<link rel=\"stylesheet\" href=\"css/style.css\">" +
                "<script> " +
                    "function validatePwd() { " +
                        "let pwd1 = document.signUp.password1.value; " +
                        "let pwd2 = document.signUp.password2.value; " +
                        "if (pwd1 !== pwd2) { " +
                            "let err = document.getElementById(\"err\").innerHTML = \"<i>Entered passwords didn't match! Try again....</i>\"; " +
                        "return false; " +
                        "} " +
                    "} " +
                "</script>" +
                "</head>");

        out.println("<body>");

        // nav bar code - dashboard content
        if (username != null){
            out.println("<div class=\"d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3 bg-white border-bottom shadow-sm\"> " +
                    "<a class=\"navbar-brand my-0 mr-md-auto\" href=\"../Dashboard\"> " +
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
                        "<div class=\"row\"> " +
                            "<div class=\"col-12 col-lg-12 \"> " +
                                "<div class=\"row align-items-center h-100\" > " +
                                    "<div class=\"col-7 mx-auto\"> " +
                                        "<div class=\"mt-4\"> " +
                                            "<div class=\"text-center p-1\"> " +
                                                "<h2 class=\"theme-color\">Sign Up</h2> " +
                                            "</div> " +
                                        "</div> " +
                                        "<p class=\"text-center p-3\" id=\"err\" style=\"color: red\"></p> " +
                                        "<form action=\"SignUpResult\" method=\"POST\" name=\"signUp\" onsubmit=\"return validatePwd()\"> " +
                                            "<div class=\"form-group mx-4 mt-4\"> <label for=\"username\">Username:</label> " +
                                                "<input type=\"text\" required name=\"username\" class=\"form-control\" id=\"username\" placeholder=\"Enter Username\"> " +
                                            "</div> " +
                                            "<div class=\"form-group mx-4 mt-4\"> " +
                                                "<label for=\"password1\">Password:</label> " +
                                                "<input type=\"password\" required  name=\"password1\" id=\"password1\"  class=\"form-control\" placeholder=\"Enter Password\"> " +
                                            "</div> " +
                                            "<div class=\"form-group mx-4 mt-4\"> " +
                                                "<label for=\"password2\">Password confirmation:</label> " +
                                                "<input type=\"password\" required  name=\"password2\" id=\"password2\"  class=\"form-control\" placeholder=\"Enter the same password as before, for verification.\"> " +
                                            "</div> " +
                                            "<div class=\"form-group mx-4 mt-4\"> " +
                                                "<button type=\"submit\" class=\"btn login-btn btn-block \">Sign up</button> " +
                                            "</div> " +
                                        "</form> " +
                                        "<p class=\"text-center p-3\">Already have an account? <a href=\"login.html\">Login here</a></p> " +
                                    "</div> " +
                                "</div> " +
                            "</div> " +
                        "</div> " +
                    "</div>");



        out.println("</body>");
        out.println("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}