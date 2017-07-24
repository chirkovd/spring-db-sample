package com.dipegroup.db.sample.services;

import com.dipegroup.db.sample.dao.BooksDao;
import com.dipegroup.db.sample.models.BookBean;
import com.dipegroup.db.sample.models.BookEntity;
import com.dipegroup.exceptions.models.AppException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Project: spring-db-sample
 * Description:
 * Date: 7/24/2017
 *
 * @author Dmitriy_Chirkov
 * @since 1.8
 */
@Service
public class BooksService {

    @Autowired
    private BooksDao booksDao;

    @Autowired
    public void init(PlatformTransactionManager tm, ObjectMapper objectMapper,
                     @Value("classpath:books/library.json") Resource library) throws AppException {
        TypeReference<List<BookBean>> typeReference = new TypeReference<List<BookBean>>() {
        };
        List<BookBean> books;
        try {
            books = objectMapper.readValue(library.getInputStream(), typeReference);
        } catch (IOException e) {
            throw new AppException("error.app.library.required");
        }

        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = tm.getTransaction(def);
        try {
            if (booksOutdated(true, books)) {
                saveBooks(books);
            }
            tm.commit(status);
        } catch (AppException | TransactionException e) {
            if (!status.isCompleted()) {
                tm.rollback(status);
                throw new AppException("error.app.library.init.abort");
            } else {
                if (booksOutdated(false, books)) {
                    throw new AppException("error.app.library.init.retry");
                }
            }
        }
    }

    private boolean booksOutdated(boolean clean, List<BookBean> books) throws AppException {
        boolean result = true;
        if (booksDao.countAppBooks() == 0) {
            List<BookEntity> appBooks = booksDao.findAppBooks();
            if (appBooks.size() == books.size()) {
                Map<String, BookEntity> library = appBooks.stream()
                        .collect(Collectors.toMap(BookEntity::getIsbn, Function.identity()));
                result = books.stream().filter(book -> {
                    BookEntity bookEntity = library.get(book.getIsbn());
                    return bookEntity == null || !(Objects.equals(book.getName(), bookEntity.getName())
                            && Objects.equals(book.getAuthor(), bookEntity.getAuthor()));
                }).count() > 0;
            }
            if (result && clean) {
                booksDao.removeAppBooks(appBooks);
            }
        }
        return result;
    }

    private void saveBooks(List<BookBean> books) throws AppException {
        booksDao.save(books.stream().map(bean -> {
            BookEntity book = new BookEntity();
            book.setAuthor(bean.getAuthor());
            book.setIsbn(bean.getIsbn());
            book.setName(bean.getName());
            book.setApp(true);
            book.setOwner("application");
            return book;
        }).collect(Collectors.toList()));
    }

    public List<BookEntity> findUserBooks(String owner) throws AppException {
        return booksDao.findUserBooks(owner);
    }

    public BookEntity findOne(Long id) throws AppException {
        return booksDao.findOne(id);
    }

    public BookEntity saveOne(String owner, BookBean bookBean) throws AppException {
        BookEntity book = new BookEntity();
        book.setAuthor(bookBean.getAuthor());
        book.setIsbn(bookBean.getIsbn());
        book.setName(bookBean.getName());
        book.setApp(false);
        book.setOwner(owner);
        return booksDao.saveOne(book);
    }

    public BookEntity updateOne(String owner, BookBean bookBean) throws AppException {
        BookEntity entity = booksDao.findOne(bookBean.getId());
        if (entity == null) {
            throw new AppException("error.cannot.find.book");
        }
        if (entity.isApp() || !Objects.equals(entity.getOwner(), owner)) {
            throw new AppException("error.cannot.modify.book");
        }
        entity.setAuthor(bookBean.getAuthor());
        entity.setIsbn(bookBean.getIsbn());
        entity.setName(bookBean.getName());
        return booksDao.saveOne(entity);
    }

    public void removeOne(String owner, Long id) throws AppException {
        BookEntity entity = booksDao.findOne(id);
        if (entity == null) {
            throw new AppException("error.cannot.find.book");
        }
        if (entity.isApp() || !Objects.equals(entity.getOwner(), owner)) {
            throw new AppException("error.cannot.delete.book");
        }
        booksDao.removeOne(id);
    }

    public List<BookEntity> findAppBooks() throws AppException {
        return booksDao.findAppBooks();
    }
}
