package com.lec.spring.repository;

import com.lec.spring.domain.Book;
import com.lec.spring.domain.Publisher;
import com.lec.spring.domain.Review;
import com.lec.spring.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void bookTest() {
        System.out.println("\n-- TEST#bookTest() ---------------------------------------------");
        Book book = new Book();
        book.setName("JPA 스터디");
        book.setAuthorId(1L);
//        book.setPublisherId(1L);
        bookRepository.save(book);  // INSERT

        System.out.println(bookRepository.findAll());
        System.out.println("\n------------------------------------------------------------\n");
    }


    @Test
    @Transactional
    void bookRelationTest() {
        System.out.println("\n-- TEST#bookRelationTest() ---------------------------------------------");

        // 테스트용 데이터 입력
        givenBookAndReview();

        // 특정 User 가져오기
        User user = userRepository.findByEmail("martin@redknight.com");

        // 특정 User가 남긴 review 정보들 가져오기
        System.out.println("Review : " + user.getReviews());

        // 특정 User 가 남긴 review 중에 첫 번째 revicw 의 book 정보 가져오기
        System.out.println("Book : " + user.getReviews().get(0).getBook());

        // 특정 User 가 남긴 review 중에 첫 번째 revicw 의 book publicher 정보 가져오기
        System.out.println("Publisher : " + user.getReviews().get(0).getBook().getPublisher());


        System.out.println("\n------------------------------------------------------------\n");
    }


    private Book givenBook(Publisher publisher) {
        Book book = new Book();
        book.setName("JPA 완전정복");
        book.setPublisher(publisher);

        return bookRepository.save(book);
    }

    private Publisher givenPublisher() {
        Publisher publisher = new Publisher();
        publisher.setName("K-출판사");

        return publisherRepository.save(publisher);
    }

    private void givenReview(User user, Book book) {
        Review review = new Review();
        review.setTitle("내 인생을 바꾼 책");
        review.setContent("너무너무 재미있고 즐거운 책이었어요");
        review.setScore(5.0f);
        review.setUser(user);   // FK
        review.setBook(book);   // FK

        reviewRepository.save(review);
    }

    private User givenUser() {
        // 1번 User값 리턴
        return userRepository.findByEmail("martin@redknight.com");
    }

    private void givenBookAndReview() {
        // 리뷰 저장
        givenReview(givenUser(), givenBook(givenPublisher()));
    }

    @Test
    void queryTest1(){
        System.out.println("findByCategoryIsNullAndNameEqualsAndCreatedAtGreaterThanEqualAndUpdatedAtGreaterThanEqual() : ");
        System.out.println(bookRepository.findByCategoryIsNullAndNameEqualsAndCreatedAtGreaterThanEqualAndUpdatedAtGreaterThanEqual(
                "JPA 완전정복",
                LocalDateTime.now().minusDays(1L),
                LocalDateTime.now().minusDays(1L)
        ));
    }

    @Test
    void queryTest2(){
        System.out.println("findByNameRecently" +
                bookRepository.findByNameRecently(
                        "JPA 완전정복",
                        LocalDateTime.now().minusDays(1L),
                        LocalDateTime.now().minusDays(1L)));
    }

    @Test
    void queryTest3(){
        System.out.println("findByNameRecently" +
                bookRepository.findByNameRecently2(
                        "JPA 완전정복",
                        LocalDateTime.now().minusDays(1L),
                        LocalDateTime.now().minusDays(1L)));
    }

    @Test
    void queryTest4(){
        bookRepository.findBookNameAndCategory1().forEach(tuple -> {
            System.out.println(tuple.get(0) + " : " + tuple.get(1));
        });
    }

    @Test
    void queryTest5(){
        bookRepository.findBookNameAndCategory2().forEach(b -> {
            System.out.println(b.getName() + " : " + b.getCategory());
        });
    }

    @Test
    void queryTest6(){
        bookRepository.findBookNameAndCategory3().forEach(b -> {
            System.out.println(b.getName() + " : " + b.getCategory());
        });
    }

    @Test
    void queryTest7(){
        bookRepository.findBookNameAndCategory4(PageRequest.of(0,1)).forEach(b -> {
            System.out.println(b.getName() + " : " + b.getCategory());
        });
    }

    @Test
    void nativeQueryTest1(){
        System.out.println("🐹".repeat(30));
        bookRepository.findAll().forEach(System.out::println);
        System.out.println("🐹".repeat(30));
        bookRepository.findAllCustom1().forEach(System.out::println);
    }

    @Test
    void nativeQueryTest2(){
        List<Book> books = bookRepository.findAll();

        for(Book book : books){
            book.setCategory("IT전문서");
        }
        System.out.println("💀".repeat(30));
        bookRepository.findAll().forEach(System.out::println);
    }

    @Test
    void nativeQueryTest3(){
        System.out.println("affected rows:" + bookRepository.updateCategories());
        System.out.println("😒".repeat(30));
        System.out.println(bookRepository.findAllCustom1());
    }

    @Test
    void nativeQueryTest4(){
        System.out.println(bookRepository.showTables());
    }

    //------------------------------------------------------------------
    // Cnverter
    @Test
    void converterTest1(){
        bookRepository.findAll().forEach(System.out::println);
    }
}