package com.example.demo.controllers;

import com.example.demo.model.Product;
import com.example.demo.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;


    @GetMapping("/list")
    public ModelAndView showAllProduct() {
        ModelAndView modelAndView = new ModelAndView("/shop");
       Iterable<Product> product = productService.findAll();
        modelAndView.addObject("products", product);
        return modelAndView;
    }


    @PostMapping("/save")
    public String save(@RequestParam("id") Product product) {
        productService.save(product);
        return "redirect:/shop";
    }


    @GetMapping("/findOne")
    @ResponseBody
    public Product findOne(@RequestParam("id") Product product) {
        return productService.finById(product.getId());
    }







}
