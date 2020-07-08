package com.otus.homework.controller;

import com.otus.homework.domain.Author;
import com.otus.homework.domain.Book;
import com.otus.homework.domain.Genre;
import com.otus.homework.dto.BookDto;
import com.otus.homework.repository.AuthorReactiveRepository;
import com.otus.homework.repository.BookReactiveRepository;
import com.otus.homework.repository.CommentReactiveRepository;
import com.otus.homework.repository.GenreReactiveRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class BookController {

    private final AuthorReactiveRepository authorReactiveRepository;
    private final BookReactiveRepository bookReactiveRepository;
    private final GenreReactiveRepository genreReactiveRepository;
    private final CommentReactiveRepository commentReactiveRepository;

    public BookController(AuthorReactiveRepository authorReactiveRepository,
                          BookReactiveRepository bookReactiveRepository,
                          GenreReactiveRepository genreReactiveRepository,
                          CommentReactiveRepository commentReactiveRepository) {
        this.authorReactiveRepository = authorReactiveRepository;
        this.bookReactiveRepository = bookReactiveRepository;
        this.genreReactiveRepository = genreReactiveRepository;
        this.commentReactiveRepository = commentReactiveRepository;
    }

    @ModelAttribute("genres")
    public Flux<Genre> getAllGenres() {
        return genreReactiveRepository.findAll();
    }

    @ModelAttribute("authors")
    public Flux<Author> getAllAuthors() {
        return authorReactiveRepository.findAll();
    }

    @RequestMapping("/book/list-by-author/{id}")
    public Mono<String> listByAuthorPage(@PathVariable String id,
                                         @ModelAttribute Author author,
                                         @ModelAttribute Book book,
                                         final BindingResult bindingResult,
                                         final ServerWebExchange exchange,
                                         Model model) {
        return exchange.getFormData().flatMap(
                formData -> {
                    if (formData.containsKey("save")) {
                        return saveBook(book, bindingResult, model);
                    }
                    if (formData.containsKey("delete")) {
                        return deleteBook(book, bindingResult, model);
                    }
                    return showBooks(id, bindingResult, model);
                });
    }

    @RequestMapping("book/edit/{id}")
    public Mono<String> editPage(@PathVariable String id,
                                 @ModelAttribute Book editedBook,
                                 final BindingResult bindingResult,
                                 final ServerWebExchange exchange,
                                 Model model) {
        return exchange.getFormData().flatMap(
                formData -> {
                    if (formData.containsKey("save")) {
                        return saveBook(editedBook, bindingResult, model);
                    }
                    return editBook(id, bindingResult, model);
                });
    }

    private Mono<String> showBooks(final String authorId, final BindingResult bindingResult, Model model) {
        Mono<Author> authorMono = authorReactiveRepository.findById(authorId);
        model.addAttribute("author", authorMono);

        Flux<BookDto> bookFlux = bookReactiveRepository.findAllByAuthorId(authorId)
                .flatMap(book -> {
                    return Flux.zip(authorReactiveRepository.findById(book.getAuthorId()),
                            genreReactiveRepository.findById(book.getGenreId()))
                            .map(objects -> {
                                BookDto bookDto = new BookDto();
                                bookDto.setId(book.getId());
                                bookDto.setTitle(book.getTitle());
                                bookDto.setAuthor(objects.getT1());
                                bookDto.setGenre(objects.getT2());
                                return bookDto;
                            });
                });

        model.addAttribute("books", bookFlux);
        return Mono.just("book/list-by-author");
    }

    private Mono<String> saveBook(final Book book, final BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return Mono.just("book/list-by-author");
        }
        return this.bookReactiveRepository.save(book)
                .then(Mono.just("redirect:/book/list-by-author/" + book.getAuthorId()));
    }

    private Mono<String> deleteBook(final Book book, final BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return Mono.just("book/list-by-author");
        }

        return commentReactiveRepository.deleteAllByBookId(book.getId())
                .then(bookReactiveRepository.deleteById(book.getId()))
                .then(showBooks(book.getAuthorId(), bindingResult, model));
    }

    private Mono<String> editBook(final String bookId, final BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return Mono.just("book/list-by-author");
        }
        Mono<Book> bookMono = bookReactiveRepository.findById(bookId);
        model.addAttribute("book", bookMono);
        return Mono.just("book/edit");
    }
}
