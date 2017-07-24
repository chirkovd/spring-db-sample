package com.dipegroup.db.sample.dao;

import com.dipegroup.db.sample.models.BookEntity;
import com.dipegroup.db.sample.repositories.BooksRepository;
import com.dipegroup.exceptions.models.AppErrorImp;
import com.dipegroup.exceptions.models.AppException;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class JpaBooksDao implements BooksDao {

    private static final Logger logger = LoggerFactory.getLogger(JpaBooksDao.class);

    @Autowired
    private BooksRepository repository;

    @Override
    public List<BookEntity> findUserBooks(String owner) throws AppException {
        try {
            return repository.findByAppFalseAndOwner(owner);
        } catch (HibernateException e) {
            logger.debug(e.getMessage());
            throw new AppException(AppErrorImp.DATABASE_ERROR);
        }
    }

    @Override
    public BookEntity findOne(Long id) throws AppException {
        try {
            return repository.findOne(id);
        } catch (HibernateException e) {
            logger.debug(e.getMessage());
            throw new AppException(AppErrorImp.DATABASE_ERROR);
        }
    }

    @Override
    public BookEntity saveOne(BookEntity book) throws AppException {
        try {
            return repository.save(book);
        } catch (HibernateException e) {
            logger.debug(e.getMessage());
            throw new AppException(AppErrorImp.DATABASE_ERROR);
        }
    }

    @Override
    public List<BookEntity> save(Collection<BookEntity> books) throws AppException {
        try {
            return (List<BookEntity>) repository.save(books);
        } catch (HibernateException e) {
            logger.debug(e.getMessage());
            throw new AppException(AppErrorImp.DATABASE_ERROR);
        }
    }

    @Override
    public void removeOne(Long id) throws AppException {
        try {
            repository.delete(id);
        } catch (HibernateException e) {
            logger.debug(e.getMessage());
            throw new AppException(AppErrorImp.DATABASE_ERROR);
        }
    }

    @Override
    public List<BookEntity> findAppBooks() throws AppException {
        try {
            return repository.findByAppTrue();
        } catch (HibernateException e) {
            logger.debug(e.getMessage());
            throw new AppException(AppErrorImp.DATABASE_ERROR);
        }
    }

    @Override
    public void removeAppBooks(Collection<BookEntity> books) throws AppException {
        try {
            repository.delete(books);
        } catch (HibernateException e) {
            logger.debug(e.getMessage());
            throw new AppException(AppErrorImp.DATABASE_ERROR);
        }
    }

    @Override
    public Long countAppBooks() throws AppException {
        try {
            return repository.countByAppTrue();
        } catch (HibernateException e) {
            logger.debug(e.getMessage());
            throw new AppException(AppErrorImp.DATABASE_ERROR);
        }
    }
}
