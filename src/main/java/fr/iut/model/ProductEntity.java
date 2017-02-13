package fr.iut.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Sydpy on 2/13/17.
 */
@Entity
@Table(name = "Product", schema = "CampingSimulator", catalog = "")
public class ProductEntity {
    private int stock;
    private double sellPrice;
    private String label;
    private int id;
    private Collection<PurchaseEntity> purchases;
    private Collection<RestockingEntity> restockings;
    private Collection<SupplierHasProductEntity> supplierHasProducts;

    @Basic
    @Column(name = "stock", nullable = false)
    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Basic
    @Column(name = "sell_price", nullable = false, precision = 0)
    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    @Basic
    @Column(name = "label", nullable = false, length = 45)
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductEntity that = (ProductEntity) o;

        if (stock != that.stock) return false;
        if (Double.compare(that.sellPrice, sellPrice) != 0) return false;
        if (id != that.id) return false;
        if (label != null ? !label.equals(that.label) : that.label != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = stock;
        temp = Double.doubleToLongBits(sellPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (label != null ? label.hashCode() : 0);
        result = 31 * result + id;
        return result;
    }

    @OneToMany(mappedBy = "product")
    public Collection<PurchaseEntity> getPurchases() {
        return purchases;
    }

    public void setPurchases(Collection<PurchaseEntity> purchases) {
        this.purchases = purchases;
    }

    @OneToMany(mappedBy = "product")
    public Collection<RestockingEntity> getRestockings() {
        return restockings;
    }

    public void setRestockings(Collection<RestockingEntity> restockings) {
        this.restockings = restockings;
    }

    @OneToMany(mappedBy = "product")
    public Collection<SupplierHasProductEntity> getSupplierHasProducts() {
        return supplierHasProducts;
    }

    public void setSupplierHasProducts(Collection<SupplierHasProductEntity> supplierHasProducts) {
        this.supplierHasProducts = supplierHasProducts;
    }
}
