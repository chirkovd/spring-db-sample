package com.dipegroup.db.sample.repositories;

import com.dipegroup.db.sample.models.BookEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Project: spring-db-sample
 * Description:
 * Date: 7/24/2017
 *
 * @author Dmitriy_Chirkov
 * @since 1.8
 */
@Repository
public interface BooksRepository extends CrudRepository<BookEntity, Long> {

    @Transactional(readOnly = true)
    List<BookEntity> findByAppTrue();

    @Transactional(readOnly = true)
    List<BookEntity> findByAppFalseAndOwner(String owner);

    @Transactional(readOnly = true)
    Long countByAppTrue();
}
