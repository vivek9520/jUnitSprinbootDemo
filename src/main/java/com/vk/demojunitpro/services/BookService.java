package com.vk.demojunitpro.services;

import com.vk.demojunitpro.enities.Book;

import java.util.List;


public interface BookService {
    public List<Book> getAllBook();
    public Book findBookById(Long bookId);
    public Book CreateBook(Book book);

    public Book UpdateBook(Long bookId,Book book);
    public void DeleteBookById(Long bookId);

}
