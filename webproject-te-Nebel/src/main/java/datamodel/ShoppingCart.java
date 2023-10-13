package datamodel;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @since J2SE-1.8
 CREATE TABLE employee (
  id INT NOT NULL AUTO_INCREMENT,    
  item VARCHAR(30) NOT NULL,   
  price INT NOT NULL,    
  PRIMARY KEY (id));
 */
@Entity
@Table(name = "shoppingCart")
public class ShoppingCart{

   @Id  // primary key
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id") // specify the column item. Without it, it will use method item
   private Integer id;

   @Column(name = "item")
   private String item;

   @Column(name = "price")
   private Integer price;

   public ShoppingCart() {
   }

   public ShoppingCart(Integer id, String item, Integer price) {
      this.id = id;
      this.item = item;
      this.price = price;
   }

   public ShoppingCart(String item, int price) {
      this.item = item;
      this.price = price;
   }

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public String getItem() {
      return item;
   }

   public void setItem(String item) {
      this.item = item;
   }

   public Integer getPrice() {
      return price;
   }

   public void setPrice(Integer price) {
      this.price = price;
   }

   @Override
   public String toString() {
      return "ShoppingCart: " + this.id + ", " + this.item + ", " + this.price;
   }
}