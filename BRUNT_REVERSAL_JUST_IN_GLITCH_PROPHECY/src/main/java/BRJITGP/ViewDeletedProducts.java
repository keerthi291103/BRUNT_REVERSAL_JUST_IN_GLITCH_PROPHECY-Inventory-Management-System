package BRJITGP;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ViewDeletedProducts
 */
@WebServlet(name = "ViewDeletedProducts", urlPatterns = "/Dashboard/ViewDeletedProducts")
public class ViewDeletedProducts extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	Connection con;
    public ViewDeletedProducts(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/brunt_reversal_just_in_time_prophecy", "root","Keerthi@2003");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
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
	                        "<title>View Deleted Products - WDMS</title> " +
	                        "<link rel=\"stylesheet\" href=\"../bootstrap-4.6.2/css/bootstrap.css\"> " +
	                        "<link rel=\"stylesheet\" href=\"../css/style.css\"> " +
	                    "</head>");

	        out.println("<body>");

	        // nav bar code - dashboard content
	        if (username != null){
	            out.println("<div class=\"d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3 bg-white border-bottom shadow-sm\"> " +
	                    "<a class=\"navbar-brand my-0 mr-md-auto\" href=\"../Dashboard\"> " +
	                    "<img src=\"../img/logo.svg\" alt=\"logo\" width=\"130\" height=\"30\" alt=\"Logo\" loading=\"lazy\"> " +
	                    "</a> " +
	                    "<button class=\"btn btn-outline logout-btn\" onclick=\"location.href='../Logout'\">Logout ("+username+")</button> " +
	                    "</div>");
	        } else {
	            out.println("<div class=\"d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3 bg-white border-bottom shadow-sm\"> " +
	                    "<a class=\"navbar-brand my-0 mr-md-auto\" href=\"../index.html\"> " +
	                    "<img src=\"../img/logo.svg\" alt=\"logo\" width=\"130\" height=\"30\" alt=\"Logo\" loading=\"lazy\"> " +
	                    "</a> " +
	                    "<button class=\"btn btn-outline logout-btn\" onclick=\"location.href='../login.html'\">Dashboard Login</button> " +
	                    "</div>");
	        }
	        
	        // block content
	        out.println("<div class=\"container h-100\"> " +
	                "<div class=\"row align-items-center h-100\" > " +
	                "<div class=\"col-11 mx-auto\"> " +
	                "<div class=\"mt-4\"> " +
	                "<div class=\"text-center p-1\"> " +
	                // Add Title here
	                "<h3 class=\"theme-color\">View Deleted Products</h3> " +
	                "</div> " +
	                "</div>" );
	        
	        try{
	            Statement stmt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
	            ResultSet rs = stmt.executeQuery("SELECT * FROM deleted_products_table");

	            if (rs.next()){
	                out.println("<table class='table table-striped text-center'>");
	                    out. println("<thead>" +
	                                    "<tr>" +
	                                        "<th scope='col'>Product ID</th> " +
	                                        "<th scope='col'>Product Name</th> " +
	                                        "<th scope='col'>Product Price</th> " +
	                                        "<th scope='col'>Product Quantity</th>" +
	                                        "<th scope='col'>Product QRcode</th>" +
	                                    "</tr>" +
	                                "</thead>");

	                    out.println("<tbody>");
	                    rs.beforeFirst();
	                    while(rs.next()) {
	                    	int product_Id = rs.getInt("productId");
	                    	String product_Name = rs.getString("productName");
	   	                 	double product_Price = rs.getDouble("productPrice");
	   	                 	int product_Quantity = rs.getInt("productQuantity");   	                 		          
	   	                 	byte[] product_qrCodeImage = rs.getBytes("productQR");
	                        out.println("<tr> " +
	                                        "<td>"+product_Id+"</td>" +
	                                        "<td>"+product_Name+"</td>" +
	                                        "<td>"+product_Price+"</td>" +
	                                        "<td>"+product_Quantity+"</td>" +
	                                        "<td>" + "<img src='data:image/png;base64," + convertToBase64(product_qrCodeImage) + "'>" + "</td>" +
	                                    "</tr>");
	                    }
	                    out.println("</tbody>");
	                out.println("</table>");

	            } else {
	                out.println("<h1 class='text-center'>No Products are their to view....</h1>");
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        // Back btn - Dashboard
	        out.println("<button class='btn action-btn btn-block' onclick=\"location.href='../Dashboard'\">Go Back</button>");


	        out.println(        "<br>"+
	                            "</div>" +
	                        "</div>" +
	                    "</div>");

	        out.println("</body>");
	        out.println("</html>");

	}
	private String convertToBase64(byte[] byteArray) {
        return java.util.Base64.getEncoder().encodeToString(byteArray);
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
