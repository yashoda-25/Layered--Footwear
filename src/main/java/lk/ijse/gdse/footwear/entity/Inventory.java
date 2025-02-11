package lk.ijse.gdse.footwear.entity;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class Inventory {
   /* public String getNextInventoryId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT inventory_id FROM Inventory ORDER BY inventory_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            System.out.println("Inventory id retrieved:" + lastId);

            String substring =lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i+1;
            System.out.println("New inventory id:" + newIdIndex);

            return String.format("I%03d", newIdIndex);
        }
        System.out.println("No existing Inventory IDs, returning I001");
        return "I001";

    }

    public boolean saveInventory(InventoryDTO inventoryDTO) {
        try {
            String query = "INSERT INTO Inventory (inventory_id, description) VALUES (?, ?)";
            return CrudUtil.execute(query, inventoryDTO.getInventoryId(), inventoryDTO.getInventoryDescription());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateInventory(InventoryDTO inventoryDTO) {
        try {
            String query = "UPDATE Inventory SET description = ? WHERE inventory_id = ?";
            return CrudUtil.execute(query, inventoryDTO.getInventoryDescription(),inventoryDTO.getInventoryId());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }*/
    private String inventoryId;
    private String InventoryDescription;
}
