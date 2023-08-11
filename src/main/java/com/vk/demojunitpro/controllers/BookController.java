package com.vk.demojunitpro.controllers;

import com.vk.demojunitpro.enities.Book;
import com.vk.demojunitpro.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/book")
public class BookController {

    @Autowired
    BookService bookService;
    @GetMapping()
    public List<Book> getAllBook(){

        return bookService.getAllBook();
    }

    @GetMapping(value = "/{bookId}")
    public ResponseEntity getBookById(@PathVariable Long bookId){
        try{
            return new ResponseEntity<>(bookService.findBookById(bookId),HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping()
    public ResponseEntity<Object> CreateBook(@RequestBody Book book){

        return new ResponseEntity<>( bookService.CreateBook(book),HttpStatus.CREATED);
    }

    @PutMapping(value ="/{bookId}" )
    public ResponseEntity<Object> UpdateBook(@PathVariable Long bookId, @RequestBody Book book){
        try {
            return  new ResponseEntity(bookService.UpdateBook(bookId,book),HttpStatus.OK);
        }catch (Exception e){
            return  new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping(value = "/{bookId}")
    public ResponseEntity<Object> DeleteBookById(@PathVariable Long bookId){
        try {
            bookService.DeleteBookById(bookId);
            return  new ResponseEntity(HttpStatus.OK);
        }catch (Exception e){
            return  new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

}
