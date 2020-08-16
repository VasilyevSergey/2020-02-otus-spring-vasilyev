package com.otus.homework.service;

import com.otus.homework.domain.BookClub;
import com.otus.homework.domain.User;
import com.otus.homework.exception.DataLoadingException;
import com.otus.homework.repository.BookClubRepository;
import com.otus.homework.repository.UserRepository;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.otus.homework.security.AuthoritiesConstants.USER;

@Service
public class BookClubServiceImpl implements BookClubService {
    private static final String ERROR_INSERT = "При создании книжного клуба '%s' произошла ошибка";
    private static final String BOOK_CLUB_NOT_FOUND = "Книгжный клуб с id '%s' не найдена";

    private final BookClubRepository bookClubRepository;
    private final UserRepository userRepository;

    public BookClubServiceImpl(BookClubRepository bookClubRepository,
                               UserRepository userRepository) {
        this.bookClubRepository = bookClubRepository;
        this.userRepository = userRepository;
    }

//    @Secured("ROLE_ADMIN")
//    @Override
//    public long count() {
//        return bookRepository.count();
//    }

//    @Secured("ROLE_ADMIN")

    @Override
    public BookClub create(String name,
                           String mainTheme) throws DataLoadingException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        User bookClubCreator = userRepository.findByLogin(userDetails.getUsername());
        BookClub bookClub = new BookClub(name, bookClubCreator, mainTheme, List.of(bookClubCreator));

        try {
            return bookClubRepository.save(bookClub);
        } catch (Exception e) {
            throw new DataLoadingException(String.format(ERROR_INSERT, name), e.getCause());
        }
    }

    @Secured(USER)
    @Override
    public BookClub getById(String id) throws DataLoadingException {
        Optional<BookClub> book = bookClubRepository.findById(id);
        if (book.isPresent()) {
            return book.get();
        } else {
            throw new DataLoadingException(String.format(BOOK_CLUB_NOT_FOUND, id));
        }
    }

    @Secured(USER)
    @Override
    public void deleteById(String id) throws DataLoadingException {
        if (!bookClubRepository.existsById(id)) {
            throw new DataLoadingException(String.format(BOOK_CLUB_NOT_FOUND, id));
        }
        bookClubRepository.deleteById(id);
    }

    @Secured(USER)
    @Override
    public List<BookClub> getAll() {
        return bookClubRepository.findAll();
    }

    @Secured(USER)
    @Override
    public List<BookClub> findAllByName(String name) {
        return bookClubRepository.findAllByName(name);
    }

    @Secured(USER)
    @Override
    public BookClub updateById(String id, String name, String mainTheme) throws DataLoadingException {
        BookClub bookClub = getById(id);
        bookClub.setName(name);
        bookClub.setName(mainTheme);
        return bookClubRepository.save(bookClub);
    }
}
