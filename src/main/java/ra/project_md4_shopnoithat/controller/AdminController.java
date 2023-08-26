package ra.project_md4_shopnoithat.controller;

//import com.sun.org.apache.xml.internal.resolver.Catalog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;
import ra.project_md4_shopnoithat.dto.request.ProductDto;
import ra.project_md4_shopnoithat.model.Catalog;
import ra.project_md4_shopnoithat.model.Product;
import ra.project_md4_shopnoithat.model.User;
import ra.project_md4_shopnoithat.service.impl.AccountService;
//import ra.project_md4_shopnoithat.service.impl.CatalogService;
import ra.project_md4_shopnoithat.service.impl.CatalogService;
import ra.project_md4_shopnoithat.service.impl.ProductService;


import java.io.File;
import java.io.IOException;
import java.util.List;


@Controller
@RequestMapping("/views")
@PropertySource("classpath:update.properties")
public class AdminController {
    @Autowired
    private ProductService productService;
    @Value("${upload-path}")
    private String pathUpload;

    @GetMapping()
    public ModelAndView listProduct() {
        List<Product> product = productService.findAll();
        ModelAndView modelAndView = new ModelAndView("product", "product", product);
        return modelAndView ;
    }


    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") int id) {
        // Xóa sản phẩm dựa trên ID
        productService.delete(id);
        return "redirect:/views";
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") int id) {
        Product product = productService.findById(id);
        ModelAndView modelAndView = new ModelAndView("editProduct", "product", product);
        return modelAndView;
    }

    @PostMapping("/editProduct")
    public String update(@ModelAttribute("product") ProductDto productDto) {
        File file = new File(pathUpload);
        if (!file.exists()) {
            // chưa tồn tại folder , khởi tạo 1 folder mới
            file.mkdirs();
        }
        String fileName = productDto.getImg_url().getOriginalFilename();
        try {
            FileCopyUtils.copy(productDto.getImg_url().getBytes(), new File(pathUpload + fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Product newProduct = new Product();
        newProduct.setId(productDto.getId());
        newProduct.setImg_url(fileName);
        newProduct.setName(productDto.getName());
        newProduct.setCatalog_id(productDto.getCatalog_id());
        newProduct.setPrice(productDto.getPrice());
        newProduct.setStock(productDto.getStock());
        newProduct.setDescription(productDto.getDescription());
        newProduct.setStatus(productDto.isStatus());
        productService.save(newProduct);
        return "redirect:/product";
    }


    @GetMapping("/addProduct")
    public ModelAndView upload() {
        return new ModelAndView("Product", "product", new ProductDto());
    }

    @PostMapping("/addProduct")
    public String doUpload(@ModelAttribute ProductDto productDto) {
        // upload file
        File file = new File(pathUpload);
        if (!file.exists()) {
            // chưa tồn tại folder , khởi tạo 1 folder mới
            file.mkdirs();
        }
        String fileName = productDto.getImg_url().getOriginalFilename();
        try {
            FileCopyUtils.copy(productDto.getImg_url().getBytes(), new File(pathUpload + fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Product newProduct = new Product();
        newProduct.setId(productDto.getId());
        newProduct.setImg_url(fileName);
        newProduct.setName(productDto.getName());
        newProduct.setCatalog_id(productDto.getCatalog_id());
        newProduct.setPrice(productDto.getPrice());
        newProduct.setStock(productDto.getStock());
        newProduct.setDescription(productDto.getDescription());
        newProduct.setStatus(productDto.isStatus());
        productService.save(newProduct);
        return "redirect:/product";
    }


    //    ********************  Phần quản lý tài khoản  ******************
    @Autowired
    private AccountService accountService;
    @GetMapping("/account")
    public ModelAndView listAcc() {
        List<User> users = accountService.findAll();
        ModelAndView modelAndView = new ModelAndView("admin/acc/account", "account", users);
        return modelAndView;
    }

    @GetMapping("/unlock_acc/{id}")
    public String unlockAcc(@PathVariable("id") Integer id) {
        accountService.updateStatusAcc(id, true);
        return "redirect:/admin/account";
    }

    @GetMapping("/block_acc/{id}")
    public String blockAcc(@PathVariable("id") Integer id) {
        accountService.updateStatusAcc(id, false);
        return "redirect:/admin/account";
    }


    //    ********************  Phần quản lý danh mục  ******************
    @Autowired
    private CatalogService catalogService;

    @GetMapping("/catalog")
    public ModelAndView listCatalog() {
        List<Catalog> catalogs = catalogService.findAll();
        ModelAndView modelAndView = new ModelAndView("/category", "catalogs", catalogs);
        return modelAndView;
    }

    @GetMapping("/addCatalog")
    public ModelAndView add() {
        // Tạo ModelAndView để hiển thị form thêm mới trong view "/views/addCatalog"
        ModelAndView modelAndView = new ModelAndView("/addCategory", "catalog", new Catalog());
        return modelAndView;
    }

    @PostMapping("/addCatalog")
    public String add(@ModelAttribute("catalog") Catalog catalog) {
        // Lưu thông tin công việc mới
        catalogService.save(catalog);
        // Chuyển hướng về trang danh sách công việc
        return "redirect:/category";
    }

    @GetMapping("/editCatalog/{id}")
    public ModelAndView edit_catalog(@PathVariable("id") int id) {
        // Lấy công việc cần chỉnh sửa dựa trên ID
        Catalog catalogEdit = catalogService.findById(id);
        // Tạo ModelAndView để hiển thị form chỉnh sửa trong view "/admin/ctl/add_catalog"
        ModelAndView modelAndView = new ModelAndView("/editCategory", "catalog", catalogEdit);
        return modelAndView;
    }

    @PostMapping("/editCatalog")
    public String update_catalog(@ModelAttribute("catalog") Catalog catalog) {
        // Lưu thông tin công việc đã cập nhật
        catalogService.save(catalog);
        // Chuyển hướng về trang danh sách công việc
        return "redirect:/category";
    }
}