package BRJITGP;

import javax.servlet.*;
import javax.servlet.http.*;

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

import javax.servlet.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "UpdateAvailableProductsResult", urlPatterns = "/Dashboard/UpdateAvailableProductsResult")

public class UpdateAvailableProductsResult extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Connection con;
    public UpdateAvailableProductsResult(){
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
        int product_Id = Integer.parseInt(request.getParameter("productid"));
        String product_Name = request.getParameter("productname");
        double product_Price = Double.parseDouble(request.getParameter("productprice"));
        int product_Qty = Integer.parseInt(request.getParameter("productqty"));
        byte[] product_qrCodeImage = generateQRCode(product_Id, product_Name, product_Price, product_Qty);

        out.println("<!DOCTYPE html> <html lang=\"en\">");
        out.println("<head> " +
                "<meta charset=\"UTF-8\"> " +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"> " +
                // Add Page Title here
                "<title>Result - Update Products - WDMS</title> " +
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
                    "<h3 class=\"theme-color\">Result - Update Products</h3> " +
                    "</div> " +
                "</div>" );

        // dashboard content
        try{
        	String sql = "UPDATE available_products_table SET productName = ?, productPrice = ?, productQuantity = ?, productQR = ? WHERE productId = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,product_Name);
            ps.setDouble(2,product_Price);
            ps.setInt(3,product_Qty);
            ps.setBytes(4, product_qrCodeImage);
            ps.setInt(5,product_Id);
           

                int i = ps.executeUpdate();

                if (i > 0) {
                    out.println("<h1 class='text-center'>Product Updated successfully</h1><br><br>");

                    out.println("<table class='table table-striped text-center'>" +
                            "<thead>" +
                            "<tr>" +
                            "<th scope='col'>Product ID</th>" +
                            "<th scope='col'>Product Name</th>" +
                            "<th scope='col'>Product Price</th>" +
                            "<th scope='col'>Product Quantity</th>" +
                            "<th scope='col'>Product QRCode</th>" +
                            "</tr>" +
                            "</thead>" +
                            "<tbody>" +
                            "<tr>" +
                            "<td>" + product_Id + "</th> " +
                            "<td>" + product_Name + "</th> " +
                            "<td>" + product_Price + "</th> " +
                            "<td>" + product_Qty + "</th> " +
                            "<td>" + "<img src='data:image/png;base64," + convertToBase64(product_qrCodeImage) + "'>" + "</td>" +
                            "</tr>" +
                            "</tbody>" +
                            "</table>");

                    // Back btn - Dashboard
                    out.println("<button class='btn action-btn btn-block' onclick=\"location.href='../Dashboard'\">Go Back</button>");

                }
                else
                {
                	out.println("<h1 class='text-center'>Product is Not Updated successfully</h1><br><br>");
                }

            ps.clearParameters();

        } catch (Exception e) {
            out.println("<h1 class='text-center'>Something went wrong! </h1>");
            // Back btn - Dashboard/AddProducts
            out.println("<button class='btn action-btn btn-block' onclick=\"location.href='../Dashboard/UpdateAvailableProducts'\">Go Back</button>");
            out.print(e);
        }

        out.println(        "<br>"+
                        "</div>" +
                    "</div>" +
                "</div>");

        out.println("</body>");
        out.println("</html>");
    }
    private byte[] generateQRCode(int productId, String productName, double productPrice, int productQuantity) {
        String qrData = "Product ID: " + productId + "\n"
                        + "Product Name: " + productName + "\n"
                        + "Product Price: " + productPrice + "\n"
                        + "Product Quantity: " + productQuantity;

        ByteArrayOutputStream out = QRCode.from(qrData)
                .to(ImageType.PNG)
                .stream();
        return out.toByteArray();
    }
    private String convertToBase64(byte[] byteArray) {
        return java.util.Base64.getEncoder().encodeToString(byteArray);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}