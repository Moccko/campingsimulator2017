package fr.iut.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by roman on 10/02/17.
 */
@Entity
@Table(name = "Client", schema = "CampingSimulator", catalog = "")
public class ClientEntity {
    private int idClient;
    private String lastName;
    private String firstName;
    private String phone;
    private String email;
    private Collection<ClientHasProblemEntity> clientHasProblemsByIdClient;
    private Collection<PurchaseEntity> purchasesByIdClient;
    private Collection<ReservationEntity> reservationsByIdClient;

    @Id
    @Column(name = "idClient", nullable = false)
    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    @Basic
    @Column(name = "last_name", nullable = false, length = 45)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "first_name", nullable = false, length = 45)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientEntity that = (ClientEntity) o;

        if (idClient != that.idClient) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idClient;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "clientByIdClient")
    public Collection<ClientHasProblemEntity> getClientHasProblemsByIdClient() {
        return clientHasProblemsByIdClient;
    }

    public void setClientHasProblemsByIdClient(Collection<ClientHasProblemEntity> clientHasProblemsByIdClient) {
        this.clientHasProblemsByIdClient = clientHasProblemsByIdClient;
    }

    @OneToMany(mappedBy = "clientByIdClient")
    public Collection<PurchaseEntity> getPurchasesByIdClient() {
        return purchasesByIdClient;
    }

    public void setPurchasesByIdClient(Collection<PurchaseEntity> purchasesByIdClient) {
        this.purchasesByIdClient = purchasesByIdClient;
    }

    @OneToMany(mappedBy = "clientByIdClient")
    public Collection<ReservationEntity> getReservationsByIdClient() {
        return reservationsByIdClient;
    }

    public void setReservationsByIdClient(Collection<ReservationEntity> reservationsByIdClient) {
        this.reservationsByIdClient = reservationsByIdClient;
    }
}
