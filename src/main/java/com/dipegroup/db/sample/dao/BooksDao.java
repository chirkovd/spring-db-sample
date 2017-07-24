package com.dipegroup.db.sample.dao;

import com.dipegroup.db.sample.models.BookEntity;
import com.dipegroup.exceptions.models.AppException;
import org.springframework.stereotype.Repository;

import java.util.Collection;
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
public interface BooksDao {

    List<BookEntity> findUserBooks(String owner) throws AppException;

    BookEntity findOne(Long id) throws AppException;

    BookEntity saveOne(BookEntity book) throws AppException;

    List<BookEntity> save(Collection<BookEntity> books) throws AppException;

    void removeOne(Long id) throws AppException;

    List<BookEntity> findAppBooks() throws AppException;

    void removeAppBooks(Collection<BookEntity> books) throws AppException;

    Long countAppBooks() throws AppException;

}
