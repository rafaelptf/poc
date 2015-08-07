package br.com.manager.address.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by rpeixoto on 02/08/15.
 */
@JsonIgnoreProperties
public class CompleteAddress extends Address {

    private Long id;

    public CompleteAddress() {
    }

    public CompleteAddress(Address address, Long id) {
        super(address);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public CompleteAddress setId(Long id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .toString();
    }
}
