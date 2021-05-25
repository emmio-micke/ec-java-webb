package com.example.demo.controllers;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import com.example.demo.entities.Product;
import com.example.demo.repositories.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    /*
     * MAPPINGS
     */

    @GetMapping("/")
    public String viewHomePage(Model model) {
        List<Product> products = productRepository.findTop3ByOrderByNameAsc();
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable(name = "id") long id) {
        productRepository.deleteById(id);
        return "redirect:/";
    }

    @PostMapping("/edit")
    public ModelAndView editProduct(@Valid @ModelAttribute("product") Product product, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();

        if (product.getId() == null || productRepository.findById(product.getId()).isEmpty()) {
            throw new NoSuchElementException("Product with id: " + product.getId() + " does not exist.");
        }

        if (result.hasErrors()) {
            modelAndView.setViewName("edit-product");
            return modelAndView;
        }

        modelAndView.setViewName("redirect:/");
        productRepository.save(product);
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditProductPage(@PathVariable(name = "id") long id) {
        ModelAndView modelAndView = new ModelAndView("edit-product");
        Product product = productRepository.findById(id).get();
        modelAndView.addObject("product", product);
        return modelAndView;
    }

    @GetMapping("/new")
    public String showNewProductPage(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "new-product";
    }

    @PostMapping(value = "/save")
    public String saveProduct(@ModelAttribute("product") Product product) {
        productRepository.save(product);
        return "redirect:/";
    }

    /*
     * EXCEPTION HANDLERS
     */

    @ExceptionHandler({ Exception.class })
    public ModelAndView handleException(Exception ex) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("exception", ex.getMessage());
        return modelAndView;
    }
}
