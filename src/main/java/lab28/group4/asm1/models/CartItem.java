package lab28.group4.asm1.models;

import jakarta.persistence.*;


@Entity
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn
    @ManyToOne
    private MenuItem menuItem;

    @Column
    private int quantity;

    @JoinColumn(name = "order_id")
    @ManyToOne
    private Order order;

    public CartItem() {
    }

    public CartItem(MenuItem menuItem) {
        this.menuItem = menuItem;
        this.quantity = 0;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }

    public double getPrice() {
        return menuItem.getPrice() * quantity;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
