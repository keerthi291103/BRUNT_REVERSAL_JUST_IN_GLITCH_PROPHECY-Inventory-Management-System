package BRJITGP;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "DeleteAvailableProductsResult", urlPatterns = "/Dashboard/DeleteAvailableProductsResult")
public class DeleteAvailableProductsResult extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Connection con;
    public DeleteAvailableProductsResult(){
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        // HttpSession
        HttpSession session = request.getSession();
        String sessionId = session.getId();
        String username = (String) session.getAttribute("username");

        // getParameter values from HTML form
        int sellProductId = Integer.parseInt(request.getParameter("product_id"));

        out.println("<!DOCTYPE html> <html lang=\"en\">");
        out.println("<head> " +
                "<meta charset=\"UTF-8\"> " +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"> " +
                // Add Page Title here
                "<title>Result - Delete Available Products - WDMS</title> " +
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
                "<h3 class=\"theme-color\">Result - Delete Available Products</h3> " +
                "</div> " +
                "</div>" );

        // dashboard content
        try{
            PreparedStatement ps1 = con.prepareStatement("SELECT * FROM available_products_table WHERE productId=?");
            ps1.setInt(1,sellProductId);

            ResultSet rs1 = ps1.executeQuery();
            ps1.clearParameters();

            if(rs1.next()){
                int rs1ProductId = rs1.getInt(1);
                String rs1ProductName = rs1.getString(2);
                double rs1ProductPrice = rs1.getDouble(3);
                int rs1ProductQuantity =  rs1.getInt(4);
                byte[] rs1ProductQRcode = rs1.getBytes(5);

                PreparedStatement ps2 = con.prepareStatement("DELETE FROM available_products_table  WHERE productId=?");
                ps2.setInt(1,sellProductId);

                
                int i2 = ps2.executeUpdate();

                if(i2>0){
//                    out.println(i2+" successfully deleted product from available_products_table");
                    PreparedStatement ps3 = con.prepareStatement("INSERT INTO deleted_products_table VALUES (?,?,?,?,?)");
                    ps3.setInt(1,rs1ProductId);
                    ps3.setString(2,rs1ProductName);
                    ps3.setDouble(3,rs1ProductPrice);
                    ps3.setInt(4,rs1ProductQuantity);
                    ps3.setBytes(5, rs1ProductQRcode);

                    int i3 = ps3.executeUpdate();

                    if (i3>0){
//                        out.println(i3+"Deleted Product added to sold_products_table...!!!");
                        out.println("<h1 class='text-center m-4'>Product Deleted successfully!</h1>");

//                        Deleted product info in table
                        out.println("<table class='table table-striped text-center'>" +
                                "<thead>" +
                                "<tr>" +
                                "<th scope='col'>Product ID</th>" +
                                "<th scope='col'>Product Name</th>" +
                                "<th scope='col'>Product Price</th>" +
                                "<th scope='col'>Product Quantity</th>" +
                                "<th scope='col'>Product QRcode</th>" +
                                "</tr>" +
                                "</thead>" +
                                "<tbody>" +
                                "<tr>" +
                                "<td>" + rs1ProductId + "</th> " +
                                "<td>" + rs1ProductName + "</th> " +
                                "<td>" + rs1ProductPrice + "</th> " +
                                "<td>" + rs1ProductQuantity + "</th> " +
                                "<td>" + "<img src='data:image/png;base64," + convertToBase64(rs1ProductQRcode) + "'>" + "</td>" +
                                "</tr>" +
                                "</tbody>" +
                                "</table>");

                        // Back btn - Dashboard
                        out.println("<button class='btn action-btn btn-block' onclick=\"location.href='../Dashboard'\">Go Back</button>");


                    } else {
//                        out.println(" ERRR on ps3 - Adding product to deleted_products_table ");
                        out.println("<h1 class='text-center'>Something went wrong! </h1>");
                        // Back btn - Dashboard/SellAvailableProducts
                        out.println("<button class='btn action-btn btn-block' onclick=\"location.href='../Dashboard/DeleteAvailableProducts'\">Go Back</button>");
                    }
                    ps3.clearParameters();



                } else {
                    out.println("<h1 class='text-center'>Something went wrong! </h1>");
                    // Back btn - Dashboard/SellAvailableProducts
                    out.println("<button class='btn action-btn btn-block' onclick=\"location.href='../Dashboard/DeleteAvailableProducts'\">Go Back</button>");
                }

                ps2.clearParameters();


            } else {
//                out.println("Errr on ps1 - getting data from available_products_table");
                out.println("<h3 class='text-center m-4'>Entered Quantity is greater than available stock <br><u>or</u><br> Product is Out of Stock!</h3>");
                // Back btn - Dashboard/SellAvailableProducts
                out.println("<button class='btn action-btn btn-block' onclick=\"location.href='../Dashboard/DeleteAvailableProducts'\">Go Back</button>");

            }


        } catch (SQLException e) {
            out.println("<h1 class='text-center'>Something went wrong! </h1>");
            // Back btn - Dashboard/SellAvailableProducts
            out.println("<button class='btn action-btn btn-block' onclick=\"location.href='../Dashboard/DeleteAvailableProducts'\">Go Back</button>");

            out.print(e);
        }


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
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}