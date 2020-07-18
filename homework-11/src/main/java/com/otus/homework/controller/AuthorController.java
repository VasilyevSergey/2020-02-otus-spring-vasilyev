package com.otus.homework.controller;

import com.otus.homework.domain.Author;
import com.otus.homework.repository.AuthorReactiveRepository;
import com.otus.homework.repository.BookReactiveRepository;
import com.otus.homework.repository.CommentReactiveRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class AuthorController {

    private final AuthorReactiveRepository authorReactiveRepository;
    private final BookReactiveRepository bookReactiveRepository;
    private final CommentReactiveRepository commentReactiveRepository;

    public AuthorController(AuthorReactiveRepository authorReactiveRepository,
                            BookReactiveRepository bookReactiveRepository,
                            CommentReactiveRepository commentReactiveRepository) {
        this.authorReactiveRepository = authorReactiveRepository;
        this.bookReactiveRepository = bookReactiveRepository;
        this.commentReactiveRepository = commentReactiveRepository;
    }

    @ModelAttribute("authors")
    public Flux<Author> getAllAuthors() {
        return authorReactiveRepository.findAll();
    }

    @GetMapping("/")
    public Mono<String> listPage(@ModelAttribute Author author, final ServerWebExchange exchange) {
        return exchange.getFormData().flatMap(
                formData -> showAuthors());
    }

    @PostMapping("/author/save")
    public Mono<String> saveAuthor(@ModelAttribute Author author, final BindingResult bindingResult, final ServerWebExchange exchange) {
        return exchange.getFormData().flatMap(
                formData -> saveAuthor(author, bindingResult));
    }

    @PostMapping("/author/delete")
    public Mono<String> deleteAuthor(@ModelAttribute Author author, final BindingResult bindingResult, final ServerWebExchange exchange) {
        return exchange.getFormData().flatMap(
                formData -> deleteAuthor(author, bindingResult));
    }

    @GetMapping("author/edit/{id}")
    public Mono<String> editPage(@PathVariable String id,
                                 @ModelAttribute Author editedAuthor,
                                 final BindingResult bindingResult,
                                 final ServerWebExchange exchange,
                                 Model model) {
        return exchange.getFormData().flatMap(
                formData -> editAuthor(id, bindingResult, model));
    }

    private Mono<String> showAuthors() {
        return Mono.just("author/list");
    }

    private Mono<String> saveAuthor(final Author author, final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Mono.just("author/list");
        }
        return this.authorReactiveRepository.save(author).then(Mono.just("redirect:/"));
    }

    private Mono<String> deleteAuthor(final Author author, final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Mono.just("author/list");
        }

        return bookReactiveRepository.findAllByAuthorId(author.getId())
                .flatMap(book -> {
                    return commentReactiveRepository.deleteAllByBookId(book.getId())
                            .then(bookReactiveRepository.deleteById(book.getId()));
                })
                .then(authorReactiveRepository.deleteById(author.getId()))
                .then(Mono.just("redirect:/"));
    }

    private Mono<String> editAuthor(final String authorId, final BindingResult bindingResult, Model model) {
        Mono<Author> authorMono = authorReactiveRepository.findById(authorId);
        model.addAttribute("author", authorMono);
        return Mono.just("author/edit");
    }
}
