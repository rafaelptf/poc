package br.com.manager.address.service;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by rpeixoto on 07/08/15.
 */
@Component
public interface Converter<E, D> {

    <R extends D> R convertEntityToDomain(E entity);

    List<D> convertEntityToDomain(List<E> entities);

    <P extends D> E convertDomainToEntity(P domainObject);

}


