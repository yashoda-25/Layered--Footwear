package lk.ijse.gdse.footwear.dto.tm;

import javafx.scene.control.Button;
import lombok.*;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class CartTM {
   private String phoneNo;
   private String productId;
   private String productDesc;
   private int cartQty;
   private double unitPrice;
   private double amount;
   private double discount;
   private String paymentMethod;
   private double total;
   private Button removeBtn;

}
