import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import datamodel.ShoppingCart;
import util.UtilDBNebel;

@WebServlet("/SelectItems")
public class SelectItems extends HttpServlet {
   private static final long serialVersionUID = 1L;
   static String url = "jdbc:mysql://ec2-18-191-215-100.us-east-2.compute.amazonaws.com:3306/StoreDBNebel";
   static String user = "kjnebel_remote";
   static String password = "csci4830";
   static Connection connection = null;
   public static Item[] items = new Item[10];
   private int totalPrice = 0;

   public SelectItems() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   connection = null;
	   PrintWriter out = response.getWriter();
	   try {
	       connection = DriverManager.getConnection(url, user, password);
	    } catch (SQLException e) {
	       System.out.println("Connection Failed! Check output console");
	       out.println("<h1>Error Loading! Please Go Back and Try Again!<h1><br>");
	       out.println("<div><a href=/webproject-te-Nebel/index.html>Go Back</a></div> <br>");
	       e.printStackTrace();
	       return;
	    }
	    if (connection != null) {
	    } else {
	       System.out.println("Failed to make connection!");
	    }
	   try {
	    	 int i = 0;
	         String selectSQL = "SELECT * FROM AvailableItems";
	         PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
	         //preparedStatement.setString(1, theUserName);
	         ResultSet rs = preparedStatement.executeQuery();
	         while (rs.next()) {
	            int id = rs.getInt("ID");
	            String item = rs.getString("ITEM");
	            int price = rs.getInt("PRICE");
	            items[i] = new Item(id, item, price);
	            //response.getWriter().append(items[i].toString());
	            i++;
	         }
	      } catch (SQLException e) {
	         e.printStackTrace();
	      }
		String item = "";
		int price = 0;
		
		System.out.println("Test: " + request.getParameter("addItem"));
		if (items.length > 0) {			
			if ("ball".equals(request.getParameter("addItem"))) {
				item = items[0].getItemName();
				price = items[0].getPrice();
			} else if ("cards".equals(request.getParameter("addItem"))) {
				item = items[1].getItemName();
				price = items[1].getPrice();
			} else if ("pogo".equals(request.getParameter("addItem"))) {
				item = items[2].getItemName();
				price = items[2].getPrice();
			} else if ("teddy".equals(request.getParameter("addItem"))) {
				item = items[3].getItemName();
				price = items[3].getPrice();
			} else if ("car".equals(request.getParameter("addItem"))) {
				item = items[4].getItemName();
				price = items[4].getPrice();
			} else if ("nerf".equals(request.getParameter("addItem"))) {
				item = items[5].getItemName();
				price = items[5].getPrice();
			} else if ("lego".equals(request.getParameter("addItem"))) {
				item = items[6].getItemName();
				price = items[6].getPrice();
			} else if ("slinky".equals(request.getParameter("addItem"))) {
				item = items[7].getItemName();
				price = items[7].getPrice();
			}
		}
      
		addItem(item, price, response);
   }

   void addItem(String item, int price, HttpServletResponse response) throws IOException {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Shopping Cart";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
            "transitional//en\">\n"; //
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + 
            "</title><style>a, .button {"
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
            + "	.button { background-color: rgb(0, 82, 204); padding-right: 16px; padding-left: 16px; padding-top: 10px; padding-bottom: 2px; bottom: 20; right: 180; margin-right: 16px;}"
            + "	a:hover, .button:hover {"
            + "		background-color: rgb(140, 240, 251);"
            + "	} div {width: 100%} html {height: auto !important; padding-bottom: 80px !important;}"
            + "li {display: grid; grid-column-template: 1fr 1fr; margin: 8px; font-size: 18px; border: 1px solid black; border-radius: 10px; height: 60px; width: fit-content;"
            + "float: left; padding: 8px;}"
            + ".price {position: fixed; bottom: 25; left: 34; font-size: 24px; z-index: 5;}"
            + ".bottom {width: 140%; height: 80px; position: fixed; bottom: 0; left: -5; background-color: lightgrey; z-index: 4; box-shadow: 4px 4px 4px 1px rgba(0,0,0,0.5);}"
            + "body {margin-bottom: 80px;}</style>" + "</head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h1 align=\"center\">" + title + "</h1>\n");
      
      if (item != "") {    	  
    	  UtilDBNebel.addToCart(item, price);
      }
      
      this.totalPrice = 0;
      
      List<ShoppingCart> listItems = UtilDBNebel.listItems();
      System.out.println("ListItems: " + listItems.toString());
      for (ShoppingCart list : listItems) {
    	  out.println("<li><div>Item: " + list.getItem() + "</div>" //
    			  + "<div>Price: $" + list.getPrice() + ".00</div></li>");
    	  this.totalPrice += list.getPrice();
      }
      out.println("<div style='height: 100px'> </div>");
      out.println("<div class='bottom'></div>");
      out.println("<div class='price'>Total Price: $" + this.totalPrice + ".00</div>");
      out.println("<div><a class='button' href=/webproject-te-Nebel/order.html>Proceed to Checkout</a></div>");
      out.println("<div><a href=/webproject-te-Nebel/index.html>Continue Shopping</a></div> <br>");
      out.println("</body></html>");
   }
   
   public void order() {
	   System.out.println("Ordering...");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
