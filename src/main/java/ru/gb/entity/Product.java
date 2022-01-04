package ru.gb.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import ru.gb.entity.enums.Status;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "product")
public class Product {
    @Id@GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "cost")
    private BigDecimal cost;
    @Column(name = "manufacture_date")
    private LocalDate manufactureDate;
    @ManyToOne()
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;

    @Version
    @Column(name = "version")
    private int version;
    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;
    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createtedDate;
    @LastModifiedBy
    @Column(name = "last_modified_By")
    private String lastModifiedBy;
    @LastModifiedDate
    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "cart_product",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "cart_id"))
    private Set<Cart> carts;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", cost=" + cost +
                ", manufactureDate=" + manufactureDate +
                ", manufacturer=" + manufacturer.getName() +
                "}\n";
    }
}
