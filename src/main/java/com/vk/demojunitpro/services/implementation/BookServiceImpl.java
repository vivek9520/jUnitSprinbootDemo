package com.vk.demojunitpro.services.implementation;


import com.vk.demojunitpro.enities.Book;
import com.vk.demojunitpro.exceptions.BookNotFoundException;
import com.vk.demojunitpro.repositries.BookRepo;
import com.vk.demojunitpro.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepo bookRepo;
    @Override
    public List<Book> getAllBook() {
        return bookRepo.findAll();
    }
    @Override
    public Book findBookById(Long bookId) {
        Optional<Book> book= bookRepo.findById(bookId);
        if (book.isPresent()){
            return book.get();
        }
        else {
            throw new BookNotFoundException("Book Not Found with ID "+bookId);
        }
    }
    @Override
    public Book CreateBook(Book book){
       return bookRepo.save(book);
    }

    @Override
    public Book UpdateBook(Long bookId,Book book) {
        Book dbBook= bookRepo.findById(bookId).orElseThrow(()->new BookNotFoundException("Book Not Found with ID: "+bookId));
            dbBook.setBookName(book.getBookName());
            dbBook.setRate(book.getRate());

           return bookRepo.save(dbBook);


    }

    @Override
    public void DeleteBookById(Long bookId) {
        Book exisitBook = bookRepo.findById(bookId).orElseThrow(()->new BookNotFoundException("Book not found with ID:"+bookId));

        bookRepo.delete(exisitBook);

    }
}
