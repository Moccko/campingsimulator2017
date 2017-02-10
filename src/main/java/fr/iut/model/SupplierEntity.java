package fr.iut.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by roman on 10/02/17.
 */
@Entity
@Table(name = "Supplier", schema = "CampingSimulator", catalog = "")
public class SupplierEntity {
    private int idSupplier;
    private String name;
    private String phone;
    private String email;
    private String website;
    private Collection<RestockingEntity> restockingsByIdSupplier;
    private Collection<SupplierHasProductEntity> supplierHasProductsByIdSupplier;

    @Id
    @Column(name = "idSupplier", nullable = false)
    public int getIdSupplier() {
        return idSupplier;
    }

    public void setIdSupplier(int idSupplier) {
        this.idSupplier = idSupplier;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 45)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "phone", nullable = true, length = 11)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "email", nullable = true, length = 45)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "website", nullable = true, length = 45)
    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SupplierEntity that = (SupplierEntity) o;

        if (idSupplier != that.idSupplier) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (website != null ? !website.equals(that.website) : that.website != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idSupplier;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (website != null ? website.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "supplierByIdSupplier")
    public Collection<RestockingEntity> getRestockingsByIdSupplier() {
        return restockingsByIdSupplier;
    }

    public void setRestockingsByIdSupplier(Collection<RestockingEntity> restockingsByIdSupplier) {
        this.restockingsByIdSupplier = restockingsByIdSupplier;
    }

    @OneToMany(mappedBy = "supplierByIdSupplier")
    public Collection<SupplierHasProductEntity> getSupplierHasProductsByIdSupplier() {
        return supplierHasProductsByIdSupplier;
    }

    public void setSupplierHasProductsByIdSupplier(Collection<SupplierHasProductEntity> supplierHasProductsByIdSupplier) {
        this.supplierHasProductsByIdSupplier = supplierHasProductsByIdSupplier;
    }
}