package com.example.demo.controllers;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import com.example.demo.entities.Product;
import com.example.demo.entities.User;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.service.SecurityService;
import com.example.demo.service.UserService;

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

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

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

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (securityService.isAuthenticated()) {
            return "redirect:/";
        }

        if (error != null) {
            model.addAttribute("error", "Your username or password is invalid.");
        }

        if (logout != null) {
            model.addAttribute("message", "you have been logged out successfully.");
        }

        return "login";
    }

    @GetMapping("/new")
    public String showNewProductPage(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "new-product";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        if (securityService.isAuthenticated()) {
            return "redirect:/";
        }

        model.addAttribute("userForm", new User());

        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
        User userExists = userService.findByUsername(userForm.getUsername());

        if (userExists != null) {
            bindingResult.rejectValue("username", "error.user",
                    "There already is a user registered with that username.");
        }

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.save(userForm);
        securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/";
    }

    @PostMapping("/save")
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
