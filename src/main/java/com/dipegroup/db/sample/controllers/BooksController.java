package com.dipegroup.db.sample.controllers;

import com.dipegroup.db.sample.models.BookBean;
import com.dipegroup.db.sample.models.BookEntity;
import com.dipegroup.db.sample.services.BooksService;
import com.dipegroup.exceptions.models.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Project: spring-db-sample
 * Description:
 * Date: 7/24/2017
 *
 * @author Dmitriy_Chirkov
 * @since 1.8
 */
@RestController
public class BooksController {

    @Autowired
    private BooksService booksService;

    @RequestMapping(value = "/books/u/{owner}", method = RequestMethod.GET)
    public List<BookEntity> findUserBooks(@PathVariable String owner) throws AppException {
        return booksService.findUserBooks(owner);
    }

    @RequestMapping(value = "/books", method = RequestMethod.GET)
    public List<BookEntity> findAppBooks() throws AppException {
        return booksService.findAppBooks();
    }

    @RequestMapping(value = "/book/{id}", method = RequestMethod.GET)
    public BookEntity findOne(@PathVariable Long id) throws AppException {
        return booksService.findOne(id);
    }

    @RequestMapping(value = "/book/u/{owner}/{id}", method = RequestMethod.GET)
    public void removeOne(@PathVariable String owner, @PathVariable Long id) throws AppException {
        booksService.removeOne(owner, id);
    }

    @RequestMapping(value = "/book/u/{owner}", method = RequestMethod.POST)
    public BookEntity saveOne(@PathVariable String owner, @Valid @RequestBody BookBean bookBean) throws AppException {
        return booksService.saveOne(owner, bookBean);
    }

    @RequestMapping(value = "/book/u/{owner}", method = RequestMethod.PUT)
    public BookEntity updateOne(@PathVariable String owner, @Valid @RequestBody BookBean bookBean) throws AppException {
        return booksService.updateOne(owner, bookBean);
    }

}
