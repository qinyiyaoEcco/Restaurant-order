package lab28.group4.asm1.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private List<CartItem> items;

    @Column
    private boolean isDelivery;

    @CreationTimestamp
    private Instant createdAt;

    protected Order() {
    }

    public Order(List<CartItem> items, boolean isDelivery) {
        this.items = items;
        this.isDelivery = isDelivery;
    }

    public Long getId() {
        return id;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public double getPrice() {
        return items.stream().mapToDouble(CartItem::getPrice).sum();
    }

    public String getCreatedAt() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());
        return formatter.format(createdAt);
    }
}
