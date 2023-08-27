package ra.project_md4_shopnoithat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ra.project_md4_shopnoithat.dto.request.FormLoginDto;
import ra.project_md4_shopnoithat.dto.request.FormRegisterDto;
import ra.project_md4_shopnoithat.dto.request.ProductDto;
import ra.project_md4_shopnoithat.model.Product;
import ra.project_md4_shopnoithat.model.User;
import ra.project_md4_shopnoithat.service.impl.ProductService;
import ra.project_md4_shopnoithat.service.impl.UserService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping({"/"})
public class HomeController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ModelAndView displayProduct() {
        List<Product> product = productService.findAll();
        ModelAndView modelAndView = new ModelAndView("products", "products", product);
        return modelAndView;
    }

    @GetMapping({"/index", "/", "/h"})
    public String index() {
        return "/index";
    }

    @GetMapping("/singleproduct")
    public String singleproduct() {
        return "/singleproduct";
    }

    @GetMapping("/store")
    public String store() {
        return "/store";
    }

    //    ADmin
    @GetMapping("/user")
    public String user() {
        return "/user";
    }

    @GetMapping("/product")
    public String product(Model model) {
        List<Product> product = productService.findAll();
        model.addAttribute("product", product);
        return "/product";
    }

    //    ------------ order ------------
    @GetMapping("/order")
    public String order() {
        return "/order";
    }


    @GetMapping("/admin")
    public String admin() {
        return "/admin";
    }
//  ---------  đăng kí------------
//    @GetMapping("/register")
//    public String register() {
//        return "/register";
//    }

    //    Add sản phẩm mới
    @GetMapping("/newProduct")
    public ModelAndView getProductList() {
        return new ModelAndView("newProduct", "product", new Product());
    }

    //------------Đăng kí Đăng nhập---------------
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login", "login_form", new FormLoginDto());
    }

    @PostMapping("/handle-login")
    public String handleLogin(HttpSession session, @ModelAttribute("login_form") FormLoginDto formLoginDto) {
        // checkk validate
        // tao mois user
        User user = userService.login(formLoginDto);
        if (user == null) {
            return "redirect:/login";
        }
        session.setAttribute("userlogin", user);
        if (user.getRoleId() == 1) {
            System.out.println("Welcome to the admin page");
            return "redirect:/views";
        }
        session.setAttribute("store", new ArrayList<>());
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("userlogin");
        session.setAttribute("cart", null);
        return "redirect:/";
    }

    @GetMapping("/register")
    public ModelAndView register() {
        return new ModelAndView("register", "register_form", new FormRegisterDto());
    }

    @PostMapping("/handle-register")
    public String handleRegister(HttpSession session, @ModelAttribute("register") FormRegisterDto formRegisterDto) {
        // checkk validate
        // tao mois user

        User user = userService.register(formRegisterDto);
        if (user == null) {
            userService.save(formRegisterDto);
            System.out.println("You have successfully created an account");
            return "redirect:/login";
        }
        System.err.println("Account already exists");
        // lỗi
        return "redirect:/register";
    }
    //   ---------  catalog------------

    @GetMapping("/catalog")
    public String category() {
        return "/catalog";
    }

    @GetMapping("/addCatalog")
    public String addCatalog() {
        return "/addCatalog";
    }

    @GetMapping("/editCatalog")
    public String editCategory() {
        return "/editCatalog";
    }

}
