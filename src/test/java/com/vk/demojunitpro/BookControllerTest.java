package com.vk.demojunitpro;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.vk.demojunitpro.controllers.BookController;
import com.vk.demojunitpro.enities.Book;
import com.vk.demojunitpro.exceptions.BookNotFoundException;
import com.vk.demojunitpro.services.BookService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class BookControllerTest {

    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter =objectMapper.writer();
    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;




    Book book1 = new Book(9L,"testbook",78);
    Book book2 = new Book(2L,"vivek2",67);
    Book book3 = new Book(3L,"vivek3",23);
    List<Book> records = new ArrayList<>(Arrays.asList(book1,book2,book3));

    @Before
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    public void getAllBooks_success()throws Exception{


        Mockito.when(bookService.getAllBook()).thenReturn(records);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/book")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$",hasSize(3)))
                .andExpect(jsonPath("$[2].bookName",is("vivek3")));

    }

    @Test
    public void getBookByid_success() throws Exception {
        Mockito.when(bookService.findBookById(9L)).thenReturn(book1);

        mockMvc.perform(MockMvcRequestBuilders.get("/book/9").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",notNullValue()))
                .andExpect(jsonPath("$.bookName",is("testbook")));
    }

    @Test
    public void getBookByid_id_not_found_failure() throws Exception {
        Mockito.doThrow(new BookNotFoundException("Book Not found")).when(bookService).findBookById(100L);

        mockMvc.perform(MockMvcRequestBuilders.get("/book/100").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$",is("Book Not found")));


    }

    @Test
    public void create_book_sucess() throws Exception {
        Book record = Book.builder().bookId(4L).bookName("Kamal").rate(67).build();

        Mockito.when(bookService.CreateBook(record)).thenReturn(record);

        String content = objectWriter.writeValueAsString(record);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/book")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$",notNullValue()))
                .andExpect(jsonPath("$.bookName",is("Kamal")));
    }

    @Test
    public void delete_book_by_id_success() throws Exception {

        Mockito.doNothing().when(bookService).DeleteBookById((9L));

        mockMvc.perform(MockMvcRequestBuilders.delete("/book/9L").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Mockito.verify(bookService,Mockito.times(1)).DeleteBookById(9L);

    }

}
