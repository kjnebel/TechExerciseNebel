
/**
 * @file SimpleFormInsert.java
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import datamodel.ShoppingCart;
import util.UtilDBNebel;

@WebServlet("/Order")
public class Order extends HttpServlet {
   private static final long serialVersionUID = 1L;
   private String items = "";
   private int price = 0;

   public Order() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String name = request.getParameter("name");
      String email = request.getParameter("email");
      String address = request.getParameter("address");
      int i = 0;
      this.price = 0;
      List<ShoppingCart> listItems = UtilDBNebel.listItems();
      for (ShoppingCart list : listItems) {
    	  System.out.println("Test: " + list.getItem());
    	  if (i == 0) {
    		  this.items = list.getItem();
    	  } else {    		  
    		  this.items += ", " + list.getItem();
    	  }
    	  this.price += list.getPrice();
    	  System.out.println("Items: " + this.items);
    	  i++;
      }

      Connection connection = null;
      String insertSql = " INSERT INTO OrdersDB (id, NAME, ITEMS, ADDRESS, EMAIL, TOTAL_PRICE) values (default, ?, ?, ?, ?, ?)";
      try {
         DBConnection.getDBConnection();
         connection = DBConnection.connection;
         PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
         preparedStmt.setString(1, name);
         preparedStmt.setString(2, this.items);
         preparedStmt.setString(3, address);
         preparedStmt.setString(4, email);
         preparedStmt.setInt(5, this.price);
         preparedStmt.execute();
         connection.close();
      } catch (Exception e) {
         e.printStackTrace();
      }
      connection = null;
      String deleteSql = " DELETE FROM shoppingCart;";
      try {
          DBConnection.getDBConnection();
          connection = DBConnection.connection;
          PreparedStatement preparedStmt = connection.prepareStatement(deleteSql);
          preparedStmt.execute();
          connection.close();
       } catch (Exception e) {
          e.printStackTrace();
       }

      // Set response content type
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Order Complete";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title>"
            + "<style>"
            + "	a {"
            + "		color: white;"
            + "		float: right;"
            + "	    border-radius: 10px;"
            + "	    border: none;"
            + "	    cursor: pointer;"
            + "	    width: fit-content;"
            + "	    height: 30px;"
            + "	    text-decoration: none;"
            + "		padding: 8px;"
            + "		z-index: 5;"
            + " 	position: fixed; padding-bottom: 0;"
            + "	} a { background-color: transparent; bottom: 20; right: 34; border: 2px solid rgb(0, 82, 204); border-radius: 10px; color: rgb(0, 82, 204);} "
            + "	a:hover {"
            + "		background-color: rgb(140, 240, 251);"
            + "	} div {width: 100%} html {height: auto !important; padding-bottom: 80px !important;}"
            + "li {display: grid; grid-row-template: 1fr 1fr; margin: 8px; font-size: 18px; border: 1px solid black; border-radius: 10px; height: fit-content; min-height: 60px; width: 500px; padding: 8px;}"
            + "body { display: flex;"
            + "		justify-content: center;"
            + "		align-items: center;}</style></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h2 align=\"center\">" + title + "</h2>\n" + //
            "<ul>\n" + //

            "  <li><div><b>Your Name</b>: </div><div>" + name + "</div>\n" + //
            "  <li><div><b>Items Ordered</b>: </div><div>" + this.items + "</div>\n" + //
            "  <li><div><b>Email</b>: </div><div>" + email + "</div>\n" + //
            "  <li><div><b>Address</b>: </div><div>" + address + "</div>\n" + //
            "  <li><div><b>Total Price</b>: </div><div>$" + this.price + ".00</div>\n" + //

            "</ul>\n");

      out.println("<a href=/webproject-te-Nebel/ToyStoreNebel>Order Again</a> <br>");
      out.println("</body></html>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
