//package project.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import ra.project_md4_shopnoithat.model.CartItem;
//import ra.project_md4_shopnoithat.model.Order;
//import ra.project_md4_shopnoithat.model.User;
//import ra.project_md4_shopnoithat.service.impl.CartService;
//import ra.project_md4_shopnoithat.service.impl.OrderService;
//
//import javax.servlet.http.HttpSession;
//import java.util.Date;
//import java.util.List;
//
//
//@Controller
//@RequestMapping("/order")
//public class OrderController {
//    @Autowired
//    private CartService cartService;
//
//    @Autowired
//    private OrderService orderService ;
//    @GetMapping("/history")
//    public  String displayOrder(Model model ,HttpSession session){
//        User userLogin = (User) session.getAttribute("userlogin");
//        List<Order> orders = orderService.findByUserId(userLogin.getId().intValue());
//        model.addAttribute("orders", orders);
//        return "/history";
//    }
//    @PostMapping("/create")
//    public String createOrder(@RequestParam("phone") String phone,
//                              @RequestParam("address") String address,
//                              HttpSession session) {
//        // Lấy thông tin người dùng đăng nhập từ session
//        User userLogin = (User) session.getAttribute("userlogin");
//
//        // Tính toán tổng giá trị của đơn đặt hàng
//        List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cart");
//        double total = 0;
//        for (CartItem cartItem : cartItems) {
//            total += cartItem.getQuantity() * cartItem.getProduct().getPrice();
//        }
//
//        Order newOrder = new Order();
//        newOrder.setUser_id(userLogin.getId().intValue());
//        newOrder.setPhoneNumber(phone);
//        newOrder.setAddress(address);
//        newOrder.setTotal_price(total);
//        newOrder.setStatus(userLogin.isStatus()); // Hoặc bạn có thể đặt giá trị khác tùy theo logic của bạn
//        newOrder.setBuyDate(new Date());
//        orderService.save(newOrder);
//        return "redirect:order/history";
//    }
//}
