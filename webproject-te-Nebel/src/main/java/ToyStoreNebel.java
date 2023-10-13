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

@WebServlet("/ToyStoreNebel")
public class ToyStoreNebel extends HttpServlet {
   private static final long serialVersionUID = 1L;
   static Connection connection = null;
   public static Item[] items = new Item[10];

   public ToyStoreNebel() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      response.setContentType("text/html;charset=UTF-8");
      PrintWriter out = response.getWriter();
      String title = "Welcome to the Toy Shop";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
            "transitional//en\">\n"; //
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title><style>a {"
            		+ "		background-color: rgb(0, 82, 204);"
            		+ "		color: white;"
            		+ "	    border-radius: 10px;"
            		+ "	    border: none;"
            		+ "	    cursor: pointer;"
            		+ "	    width: fit-content;"
            		+ "	    height: 30px;"
            		+ "     text-decoration: none;"
            		+ "		padding: 8px;"
            		+ "		padding-bottom: 0px;"	
            		+ "		margin: auto;"
            		+ "	}"
            		+ "	a:hover {"
            		+ "		background-color: rgb(140, 240, 251);"
            		+ "	} div {width: 100%; align-items: center; justify-content: center; display: flex;}"
    		+ "</style>"
            + "</head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h1 align=\"center\">" + title + "</h1>\n" +
            "<div><a href=/webproject-te-Nebel/index.html>Begin Shopping</a></div>");
      try {
         Class.forName("com.mysql.cj.jdbc.Driver"); //old:com.mysql.jdbc.Driver
      } catch (ClassNotFoundException e) {
         System.out.println("Where is your MySQL JDBC Driver?");
         e.printStackTrace();
         return;
      }
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }
}
