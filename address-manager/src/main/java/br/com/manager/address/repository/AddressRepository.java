package br.com.manager.address.repository;

import br.com.manager.address.domain.AddressEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by rpeixoto on 05/08/15.
 */
@Repository
public interface AddressRepository extends CrudRepository<AddressEntity, Long> {

    @Query("Select address " +
            "From Address address " +
            "Where address.active = true")
    Slice<AddressEntity> findAllActive(Pageable pageable);

    @Query("Select address " +
            "From Address address " +
            "Where address.id = (?1) " +
            "and address.active = true ")
    AddressEntity findActive(Long addressId);
}
