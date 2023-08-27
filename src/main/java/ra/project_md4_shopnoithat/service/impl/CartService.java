package ra.project_md4_shopnoithat.service.impl;



import org.springframework.stereotype.Service;
import ra.project_md4_shopnoithat.model.CartItem;
import ra.project_md4_shopnoithat.service.IGenericService;
import java.util.List;
@Service
public class CartService implements IGenericService<CartItem,Integer> {
    private List<CartItem> cart;

    public void setCart(List<CartItem> cart) {
        this.cart = cart;
    }

    public CartService(List<CartItem> cart) {
        this.cart = cart;
    }

    @Override
    public List<CartItem> findAll() {
        return cart;
    }

    @Override
    public CartItem findById(Integer id) {
        for (CartItem c: cart) {
            if(c.getId()==id){
                return c;
            }
        }
        return null;
    }

    @Override
    public long save(CartItem cartItem) {
        if(findById(cartItem.getId())==null){
            // thêm mới
            cart.add(cartItem);
        }else {
            // cập nhật
            cart.set(cart.indexOf(findById(cartItem.getId())),cartItem);
        }
        return 0;
    }

    @Override
    public void delete(Integer id) {
        cart.remove(findById(id));
    }
    public CartItem findByProductId(int id){
        for (CartItem c: cart) {
            if(c.getProduct().getId()==id){
                return c;
            }
        }
        return null;
    }
    public int getNewId(){
        int max = 0;
        for (CartItem ci:cart
        ) {
            if(ci.getId()>max){
                max= ci.getId();
            }
        }
        return max+1;
    }
}