package com.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface IBaseService<T, M, K, D> {
    List<D> findAll();

    Page<D> findAll(Pageable page);

    List<D> findAll(Specification<T> specs);

    D findOne(Specification<T> specs);

    Page<T> findAll(Pageable page, Specification<T> specs);

    D findById(K id);

    D add(M model);

    List<D> adds(List<M> model);

    D update(M model);

    boolean deleteById(K id);

    boolean deleteByIds(List<K> ids);
}
