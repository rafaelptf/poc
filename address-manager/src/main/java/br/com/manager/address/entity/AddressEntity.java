package br.com.manager.address.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * Created by rpeixoto on 05/08/15.
 */
@Entity(name = "Address")
@Table(name = "address")
public class AddressEntity {

    private Long id;
    private String cep;
    private String street;
    private Integer number;
    private String complement;
    private String district;
    private String city;
    private String state;
    private boolean active;

    @Id
    @GeneratedValue
    @Column(name = "idt_address")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "num_cep", nullable = false)
    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    @Column(name = "des_street", nullable = false)
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Column(name = "num_street_number", nullable = false)
    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Column(name = "des_complement")
    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    @Column(name = "des_district")
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Column(name = "des_city", nullable = false)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "des_state", nullable = false)
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Column(name = "flg_active", nullable = false)
    public boolean isActive() {
        return active;
    }

    public AddressEntity setActive(boolean active) {
        this.active = active;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("cep", cep)
                .append("street", street)
                .append("number", number)
                .append("complement", complement)
                .append("district", district)
                .append("city", city)
                .append("state", state)
                .append("active", active)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        AddressEntity that = (AddressEntity) o;

        return new EqualsBuilder()
                .append(active, that.active)
                .append(id, that.id)
                .append(cep, that.cep)
                .append(street, that.street)
                .append(number, that.number)
                .append(complement, that.complement)
                .append(district, that.district)
                .append(city, that.city)
                .append(state, that.state)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(cep)
                .append(street)
                .append(number)
                .append(complement)
                .append(district)
                .append(city)
                .append(state)
                .append(active)
                .toHashCode();
    }
}
