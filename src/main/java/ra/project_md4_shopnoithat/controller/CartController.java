package ra.project_md4_shopnoithat.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ra.project_md4_shopnoithat.model.CartItem;
import ra.project_md4_shopnoithat.model.Product;
import ra.project_md4_shopnoithat.service.impl.CartService;
import ra.project_md4_shopnoithat.service.impl.ProductService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private ProductService productService;
    private CartService cartService = new CartService(new ArrayList<>());

    @RequestMapping
    public String getCart(HttpSession session, Model model) {

        // đã có người đăng nhập rồi

//        tính tổng tiền
        double total = cartService.findAll().stream().map(ci -> ci.getQuantity() * ci.getProduct().getPrice()).reduce((double) 0, Double::sum);
        model.addAttribute("total", total);
        return "store";
    }

    @GetMapping("/addToCart/{idPro}")
    public String addToCart(@PathVariable("idPro") int idPro, HttpSession session) {
        List<Product> products = productService.findAll();
        Product p = findProductById(idPro, products);

        // kiểm tra sản phẩm dã có trong giỏ hàng chưa
        CartItem cartItem = cartService.findByProductId(idPro);

        if (cartItem == null) {
            // tạo mới item
            cartItem = new CartItem(cartService.getNewId(), p, 1);
        } else {
            // cập nhật số lượng thêm 1 đơn vị
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        }
        cartService.save(cartItem);
        // lưu vào session
        session.setAttribute("cart", cartService.findAll());
        return "redirect:/store";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id, HttpSession session) {
        cartService.delete(id);
        session.setAttribute("cart", cartService.findAll());
        return "redirect:/cart";
    }

    @PostMapping("/update/{id}")
    public String handleUpdate(HttpSession session, @PathVariable("id") int id, @RequestParam("quantity") int quantity) {
        CartItem cartItem = cartService.findById(id);
        cartItem.setQuantity(quantity);
        cartService.save(cartItem);
        // lưu vào session
        session.setAttribute("store", cartService.findAll());
        return "redirect:/cart";
    }

    public Product findProductById(int id, List<Product> list) {
        for (Product p : list) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }
}