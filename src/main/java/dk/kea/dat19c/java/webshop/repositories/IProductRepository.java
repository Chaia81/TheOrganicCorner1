package dk.kea.dat19c.java.webshop.repositories;

import dk.kea.dat19c.java.webshop.models.ProductDTO;

import java.util.List;

public interface IProductRepository {

    /*---------------------------------------- CRUD methods to be implemented ---------------------------------------*/

    public void create(ProductDTO productDTO);

    public List<ProductDTO> readAll();

    public ProductDTO read(int productId);

    public void edit(ProductDTO product);

    public void delete(int productId);

    public ProductDTO find(String productName);

}