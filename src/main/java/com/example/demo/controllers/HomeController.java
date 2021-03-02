package com.example.demo.controllers;
import com.example.demo.model.Product;
import com.example.demo.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.service.role.IRoleService;
import com.example.demo.service.user.IUserService;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.HashSet;
import java.util.Set;


@Controller
public class HomeController {

//    @Value("${upload.path}")
//    private String fileUpload;


    @Autowired
    IUserService userService;

    @Autowired
    IRoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String homepage() {
        return "index";
    }

    @GetMapping("/account")
    public String accountPage() {
        return "account";
    }

    @GetMapping("/cart")
    public String cartPage() {
        return "cart";
    }

    @GetMapping("/products")
    public String productsPage() {
        return "products";
    }

    @GetMapping("/products_detal")
    public String products_detalPage() {
        return "products_detal";
    }


    @PostMapping("/save")
    public String save(@ModelAttribute("user") User user) {
        List<Role> roles = (List<Role>) roleService.findAll();

        Role roleUser = new Role();
        roleUser.setName("ROLE_USER");
        roleService.save(roleUser);


        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));

        Set<Role> roles2 = new HashSet<>();
        roles2.add(roleUser);
        user.setRoles(roles2);
        userService.save(user);

        return "index";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }


    @GetMapping("/shop")
    public ModelAndView showCreateProduct() {
        ModelAndView modelAndView = new ModelAndView("/shop");
        modelAndView.addObject("products", productService.findAll());
        return modelAndView;
    }



    @GetMapping("/delete")
    public String delete(@RequestParam Long id) {
        if (id == null) {
            return "redirect:/shop";
        }
        productService.delete(id);
        return "redirect:/shop";
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteProduct(@PathVariable Long id) {
        Product product = productService.finById(id);
        productService.delete(product.getId());
        Iterable<Product> products = productService.findAll();
        ModelAndView modelAndView = new ModelAndView("shop");
        modelAndView.addObject("products", products);
        return modelAndView;
    }




    @GetMapping("/create")
    public ModelAndView getCreate() {
        ModelAndView modelAndView = new ModelAndView("create");
        modelAndView.addObject("products",new Product());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView postCreate(@ModelAttribute("products") Product product) {
//        MultipartFile multipartFile = product.getImage();
//        String fileName = multipartFile.getOriginalFilename();
//
////        try {
////            FileCopyUtils.copy(product.getImage().getBytes(), new File(fileUpload + fileName));
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
//        product.setImgSrc(fileName);
        productService.save(product);
        ModelAndView modelAndView = new ModelAndView("/create");
        modelAndView.addObject("products", new Product());
        modelAndView.addObject("message", "New Product Created Successfully");
        return modelAndView;
    }



}
