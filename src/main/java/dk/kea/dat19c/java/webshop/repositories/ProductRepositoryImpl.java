package dk.kea.dat19c.java.webshop.repositories;

import dk.kea.dat19c.java.webshop.models.ProductDTO;
import dk.kea.dat19c.java.webshop.util.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryImpl implements IProductRepository {

    /*-------------------------------------- Collection for handling test data --------------------------------------*/

    //private List<Product> products = new ArrayList<Product>();

    /*-------------------------------------- Connection to DB with SQL queries --------------------------------------*/

    private final Connection conn;
    private static final String CREATE_PRODUCT_SQL = "INSERT INTO product" + "(product_id, product_name," +
            " product_price, product_description) VALUES" + "(?,?,?,?);";
    private static final String EDIT_PRODUCT_SQL = "UPDATE product SET product_name =?, product_price =?," +
            " product_description =? WHERE product_id=?;";
    private static final String DELETE_PRODUCT_SQL = "DELETE FROM product WHERE product_id =?";


    /*------------------------------ Constructor with test data rewritten to link to DB -----------------------------*/

    public ProductRepositoryImpl(){
        //fill in test data
        //this.products.add(new Product(1, "Organic Coffee", "Organic Nescafe Gold"));
        //this.products.add(new Product(2, "Organic Tea", "Organic Clipper Earl Grey"));
        //this.products.add(new Product(3, "Is", "Magnum white Chocolate"));
        //this.products.add(new Product(4, "Soda", "Coca Cola Life"));
        this.conn = DatabaseConnectionManager.getDatabaseConnection();

    }

    /*------------------------------------------- Overridden CRUD methods -------------------------------------------*/

    /*
    +----------------------------------------+
    |           Create new product           |
    +----------------------------------------+
    */

    @Override
    public void create(ProductDTO product){
        try {
            PreparedStatement prep = conn.prepareStatement(CREATE_PRODUCT_SQL);
            {
                prep.setLong(1, product.getId());
                prep.setString(2, product.getName());
                prep.setDouble(3, product.getPrice());
                prep.setString(4, product.getDescription());
                prep.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    +----------------------------------------+
    |              Find product              |
    +----------------------------------------+
    */

    @Override
    public ProductDTO read(int productId) {
        ProductDTO productToReturn = new ProductDTO();
        try {
            PreparedStatement getSingleCustomer = conn.prepareStatement("SELECT * FROM product WHERE product_id=?");
            getSingleCustomer.setInt(1, productId);
            ResultSet rs = getSingleCustomer.executeQuery();
            while (rs.next()) {
                productToReturn = new ProductDTO();
                productToReturn.setId(rs.getLong("product_id"));
                productToReturn.setName(rs.getString(2));
                productToReturn.setPrice(rs.getDouble(3));
                productToReturn.setDescription(rs.getString(4));
            }
        } catch (SQLException s) {
            s.printStackTrace();
        }
        return productToReturn;
    }

    /*
    +----------------------------------------+
    |        Create a list of products       |
    +----------------------------------------+
    */

    @Override
    public List<ProductDTO> readAll() {
        List<ProductDTO> allProducts = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM product");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                ProductDTO tempProduct = new ProductDTO();
                tempProduct.setId(rs.getLong("product_id"));
                tempProduct.setName(rs.getString(2));
                tempProduct.setPrice(rs.getDouble(3));
                tempProduct.setDescription(rs.getString(4));
                allProducts.add(tempProduct);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allProducts;
    }

    /*
    +----------------------------------------+
    |             Update product             |
    +----------------------------------------+
    */

    @Override
    public void edit(ProductDTO productDTO) {
        try {
            PreparedStatement prep = conn.prepareStatement(EDIT_PRODUCT_SQL);

            prep.setString(1, productDTO.getName());
            prep.setDouble(2, productDTO.getPrice());
            prep.setString(3, productDTO.getDescription());
            prep.setLong(4, productDTO.getId());

            prep.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
    +----------------------------------------+
    |             Delete product             |
    +----------------------------------------+
    */

    @Override
    public void delete(int productId) {
        try {
            PreparedStatement prep = conn.prepareStatement(DELETE_PRODUCT_SQL);
            prep.setLong(1, productId);
            prep.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    //metoder der looper igennem product table og gemmer alle items den finder i List<ProductDTO> productDTOList
    public ProductDTO find(String productName){
        List<ProductDTO> productDTOList = readAll();
        for (ProductDTO product : productDTOList) {
            if(product.getName().compareToIgnoreCase(productName) == 0)
                return product;
        }
        return null; //returnerer null object hvis den ik finder noget (object fordi den returnerer ProductDTO)
    }

}
