package br.com.manager.address.repository;

import br.com.manager.address.domain.AddressEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by rpeixoto on 05/08/15.
 */
@Repository
public interface AddressRepository extends CrudRepository<AddressEntity, Long> {

    @Query("Select address From Address address")
    List<AddressEntity> findAll(Pageable pageable);
}
