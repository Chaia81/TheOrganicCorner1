package dk.kea.dat19c.java.webshop.controllers;

import dk.kea.dat19c.java.webshop.models.ProductDTO;
import dk.kea.dat19c.java.webshop.repositories.IProductRepository;
import dk.kea.dat19c.java.webshop.repositories.ProductRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductController {

    private IProductRepository productRepository;

    @Autowired
    public ProductController(){
        productRepository = new ProductRepositoryImpl();
    }

    /*------------------------------ Product Administration ----------------------------------------------*/

    @GetMapping("/productAdministration")
    public String productAdministration(Model model){
        model.addAttribute("products", productRepository.readAll());
        return "product/productAdministration";
    }

    /*------------------------------------- Create Product ----------------------------------------------*/

    @GetMapping("/createProduct")
    public String createProduct(Model model){
        model.addAttribute("product", new ProductDTO());
        return "product/createProduct";
    }

    @PostMapping("/createProduct")
    public String saveProduct(@ModelAttribute ProductDTO productDTO){
        productRepository.create(productDTO);
        return "redirect:/productAdministration";
    }

    /*---------------------------------------- Edit Product ----------------------------------------------*/

    @GetMapping("/editProduct")
    public String editProduct(Model model, @RequestParam int productId){
        ProductDTO prod = productRepository.read(productId);
        model.addAttribute("product", prod);
        return "product/editProduct";
    }

    @PostMapping("/editProduct")
    public String updateProduct(@ModelAttribute ProductDTO product){
        productRepository.edit(product);
        return "redirect:/productAdministration";
    }

    /*------------------------------------- Delete Product ----------------------------------------------*/

    @GetMapping("/deleteProduct")
    public String deleteProduct(Model model, @RequestParam int productId){
        ProductDTO prod = productRepository.read(productId);
        model.addAttribute("product", prod);
        return "product/deleteProduct";
    }

    @PostMapping("/deleteProduct")
    public String deleteForGood(@RequestParam int productId){
        productRepository.delete(productId);
        return "redirect:/productAdministration";
    }
}
