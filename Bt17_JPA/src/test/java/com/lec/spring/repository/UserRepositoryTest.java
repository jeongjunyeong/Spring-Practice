package com.lec.spring.repository;

import com.lec.spring.domain.Gender;
import com.lec.spring.domain.User;
import com.lec.spring.domain.UserHistory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.hibernate.internal.util.collections.ArrayHelper.forEach;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.contains;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.endsWith;


@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
            private UserHistoryRepository userHistoryRepository;

//    @Test
    void crud(){
        System.out.println("\n-- TEST#crud() ---------------------------------------------");

//        userRepository.findAll().forEach(System.out::println); // select 전체 조회 > select * from T_USER;
//
//        User user = new User();
//        System.out.println("user : " + user); // Java 객체
//
//        userRepository.save(user); // INSERT, 저장하기 -> 영속화된 객체!!!
//        userRepository.findAll().forEach(System.out::println);
//        System.out.println(userRepository.findAll());
//        System.out.println("user : " + user);


        // 테스트에 사용할 변수들
        List<User> users = null;
        User user1 = null, user2 = null, user3 = null, user4 = null, user5 = null;
        List<Long> ids = null;


        // #002 findAll()
        users = userRepository.findAll();
        users.forEach(System.out::println);

        // #003 findXX() + Sort.by() 사용
        users = userRepository.findAll(Sort.by(Sort.Direction.DESC, "name"));
        users.forEach(System.out::println);

        // #004 find XX by XX (iterable)
        ids = List.of(1L, 3L, 5L);
        users = userRepository.findAllById(ids);
        users.forEach(System.out::println);

        // #005 svae(entity) 저장하기
        user1 = new User("Jack", "jack@rednight.com");
        userRepository.save(user1);

        userRepository.findAll().forEach(System.out::println);

        // #006 saveAll(Iterable)
        user1 = new User("jack", "jack@redknight.com");
        user2 = new User("steve", "steve@redknight.com");
        userRepository.saveAll(List.of(user1,user2));
        // 결과를 출력하면 갯수만큼 insert가 발생하게 된다.
        userRepository.findAll().forEach(System.out::println);


        // #008 findById(id)
        System.out.println();
        System.out.println("#008 findById(id)");
        System.out.println();
        // 리턴타입은 Optional<Entity>
        Optional<User> user;
        user = userRepository.findById(1L);
        System.out.println(user); // Optional
        System.out.println(user.get());

        user = userRepository.findById(10L); // 없다면?
        System.out.println(user); // Optional.empty

        user1 = userRepository.findById(10L).orElse(null);
        // 10번 유저를 찾는데 없으면? null 리턴
        System.out.println(user1);


        // #009 flush()

        // flush() 는 SQL쿼리의 변화를 주는게 아니라 DB 반영 시점을 조정한다. 로그 출력으로는 변화를 확인하기 힘들다
        userRepository.save(new User("new martin", "newmartin@redknight.com"));
        userRepository.flush();

        // saveANdFlush() = save() + flush()
        userRepository.saveAndFlush(new User("베리베리", "베리@berry.com"));
        userRepository.findAll().forEach(System.out::println);

        // #010 count()
        Long count = userRepository.count();
        System.out.println(count);

        // #011 existsById()
        // 특정 데이터가 있는지 보는 boolean type에 대한 이야기
        boolean exists = userRepository.existsById(1L);
        System.out.println(exists);

        // #012 delete(entity)
        System.out.println();
        System.out.println("#012 delete(entity)");
        System.out.println();
        // 삭제하기
        // delete 는 해당 객체가 있는지 select를 한다.
        // 총 4번의 쿼리문이 나오게 된다.

//        userRepository.delete(userRepository.findById(1L).orElse(null));
        userRepository.findAll().forEach(System.out::println);

        // delete 메소드는 null 값이 전달되지 않는다.
//        userRepository.delete(userRepository.findById(1L).orElse(null));
        // 차라리 예외 발생하고 처리하도록 하는게 낫다.
//        userRepository.delete(userRepository.findById(1L).orElseThrow(RuntimeException::new));


        // #013 deleteById(id)
        // 특정 id에 대한 delete
//        userRepository.deleteById(1L); // 없는 경우에는 select 만 수행하고 끝난다.
//        userRepository.deleteById(2L);


        // #014 deleteAll()
        System.out.println(new String("베리베리").repeat(10));
//        userRepository.deleteAll(); // select 1회 + delete x n

        // #015 deleteAll (Iterable)
//        userRepository.deleteAll(userRepository.findAllById(List.of(1L, 3L)));
        // select n회 + delete n회
        // deleteAll() 은 성능이슈 발생!!


        // #016 deleteInBatch()
//        userRepository.deleteInBatch(userRepository.findAllById(List.of(3L,4L, 5L)));


        // #017
//        userRepository.deleteAllInBatch();
        // Batch 가 없는 delete -> Delete x n 회
        // Batch 가 있는 delete -> 한방에 delete

        // #018 findXXX(Pageable)  페이징
        // PageRequest 는 Pageable 의 구현체  org.springframework.data.domain.PageRequest
        // 리턴값은 Page<T>  org.springframework.data.domain.Page
        // 주의: page 값은 0-base 다
        Page<User> page = userRepository.findAll(PageRequest.of(1,3)); //page1 (두번째 페이지), size3
        System.out.println("page: " + page);

        System.out.println("totalElements: " + page.getTotalElements());
        System.out.println("totalPages: " + page.getTotalPages());
        System.out.println("numberOfElements: " + page.getNumberOfElements());
        System.out.println("sort: " + page.getSort());
        System.out.println("size: " + page.getSize());  // 페이징 할때 나누는 size

        // 리스트로 나온다.
        page.getContent().forEach(System.out::println); // 해당 페이지 내부의 데이터 (들)


        // #019 QueryByExample 사용
        ExampleMatcher matcher = ExampleMatcher.matching()              // 검색 조건
                .withIgnorePaths("name")                  // name 칼럼은 매칭하지 않음.
                .withMatcher("email", endsWith());          // email 칼럼은 뒷 부분으로 매칭하여 검색

        //Example.of(probe, ExampleMatcher)  <-- 여기서 probe 란 실제 Entity 는 아니란 말입니다.  match 를 위해서 쓰인 객체

        Example<User> example = Example.of(new User("ma","knight.com"),matcher); // probe

        userRepository.findAll(example).forEach(System.out::println);




        // #020
        user1 = new User();
        user1.setEmail("blue");

        matcher = ExampleMatcher.matching().withMatcher("email", contains());
        example = Example.of(user1,matcher);
        userRepository.findAll(example).forEach(System.out::println);





        // UPDATE !!
        // save() 는 INSERT 뿐 아니라 UPDATE도 가능

        userRepository.save(new User("최영재", "Yeong-jea@gmail.com")); // INSERT

        user1 = userRepository.findById(1L).orElseThrow(RuntimeException::new); // -> 영속성 객체
        user1.setEmail("마우스가@움직여요.com");
        userRepository.save(user1);   // UPDATE

        userRepository.findAll().forEach(System.out::println);

        System.out.println("------------------------------------------------------------\n");
    }



    // 테스트에 사용할 변수들
    List<User> users;
    User user, user1, user2, user3, user4, user5;
    List<Long> ids;
    Optional<User> optUser;


    // @BeforeEach : 매 테스트 메소드가 실행되기 직전에 실행
    // @BeforeEach 메소드의 매개변수에 TestInfo 객체를 지정하면
    // JUnit Jupiter 에선 '현재 실행할 test' 의 정보가 담긴 TestInfo 객체를 주입해준다

    @BeforeEach
    void beforeEach(TestInfo testInfo) {

        System.out.println("─".repeat(40));

        users = null;
        user = user1 = user2 = user3 = user4 = user5 = null;
        ids = null;
        optUser = null;
    }

    @AfterEach
    void afterEach(){
        System.out.println("-".repeat(40) + "\n");
    }

    /***********************************************************************
     * Query Method
     */

    // 다양한 Query Return Types
    //   https://docs.spring.io/spring-data/jpa/reference/repositories/query-return-types-reference.html
    //   => void, Primitive, Wrapper, T, Iterator<T>, Collection<T>, List<T>, Optional<T>, Option<T>, Stream<T> ...
    // 쿼리 메소드의 리턴타입은 SELECT 결과가  '1개인지' 혹은 '복수개인지' 에 따라, 위에서 가용한 범위내에서 설정해서 선언

    @Test
    void qryMethod01() {
//        System.out.println(userRepository.findByName("dennis"));
        // 리턴 타입이 User 이면 에러다. <- 여러개를 리턴하는 경우.
//        System.out.println(userRepository.findByName("martin"));
//        userRepository.findByName("martin").forEach(System.out::println);
    }


    // 쿼리 메소드의 naming
    //  https://docs.spring.io/spring-data/jpa/reference/repositories/query-keywords-reference.html
    //     find…By, read…By, get…By, query…By, search…By, stream…By
    @Test
    void qryMethod002() {
        String email = "martin@redknight.com";
        System.out.println("findByEmail : " + userRepository.findByEmail(email));System.out.println("getByEmail : " + userRepository.getByEmail(email));
        System.out.println("readByEmail : " + userRepository.readByEmail(email));
        System.out.println("queryByEmail : " + userRepository.queryByEmail(email));
        System.out.println("searchByEmail : " + userRepository.searchByEmail(email));
        System.out.println("streamByEmail : " + userRepository.streamByEmail(email));
        System.out.println("findUserByEmail : " + userRepository.findUsersByEmail(email));
    }

    @Test
    void qryMethod03(){
        System.out.println(userRepository.findJJYDByEmail("martin@redknight.com"));
    }


    // First, Top
    // First 와 Top 은 둘다 동일 (가독성 차원에서 제공되는 것임)
    @Test
    void qryMethod005() {
        String name = "martin";
        System.out.println("findTop1ByName : " + userRepository.findFirst1ByName(name));
        System.out.println("findTop2ByName : " + userRepository.findTop2ByName(name));
        System.out.println("findFirst1ByName : " + userRepository.findFirst1ByName(name));
        System.out.println("findFirst2ByName : " + userRepository.findFirst2ByName(name));
    }


    // And, Or
    @Test
    void qryMethod007() {
        String email = "martin@redknight.com";
        System.out.println("findByEmailAndName : " + userRepository.findByEmailAndName("martin@redknight.com", "martin"));
        System.out.println("findByEmailAndName : " + userRepository.findByEmailAndName("martin@redknight.com", "dennis"));
        System.out.println("findByEmailOrName : " + userRepository.findByEmailOrName("martin@redknight.com", "dennis"));
    }

    // After, Before
    // After, Before 는 주로 '시간'에 대해서 쓰이는 조건절에 쓰인다.  (가독성 측면)
    // 그러나, 꼭 '시간'만을 위해서 쓰이지는 않는다 .   '숫자', '문자열' 등에서도 쓰일수 있다.
    @Test
    void qryMethod008() {
        System.out.println("findByCreatedAtAfter : " + userRepository.findByCreatedAtAfter(
                LocalDateTime.now().minusDays(1L)
        ));

        System.out.println("findByIdAfter : " + userRepository.findByIdAfter(4L));
        System.out.println("findByNameBefore : " + userRepository.findByNameBefore("martin"));
    }


    // GreaterThan, GreaterThanEqual, LessThan, LessThanEqual
    @Test
    void qryMethod009() {
        System.out.println("findByCreatedAtGreaterThan : " + userRepository.findByCreatedAtGreaterThan(LocalDateTime.now().minusDays(1L)));
        System.out.println("findByNameGreaterThanEqual : " + userRepository.findByNameGreaterThanEqual("martin"));
    }


    // Between
    @Test
    void qryMethod010() {
        System.out.println("findByCreatedAtBetween : "
                + userRepository.findByCreatedAtBetween(LocalDateTime.now().minusDays(1L),LocalDateTime.now().plusDays(1L)));
        System.out.println("findByIdBetween : "
                + userRepository.findByIdBetween(1L,3L));
        System.out.println("findByIdGreaterThanEqualAndIdLessThanEqual : "
                + userRepository.findByIdGreaterThanEqualAndIdLessThanEqual(1L,3L));
    }


    // Empty 와 Null
    //   - IsEmpty, Empty
    //   - IsNotEmpty, NotEmpty,
    //   - NotNull, IsNotNull
    //   - Null, IsNull
    @Test
    void qryMethod011() {
        System.out.println("findByIdIsNotNull : " + userRepository.findByIdIsNotNull()); // WHERE id IS


//        System.out.println("findByIdIsNotEmpty : " + userRepository);

//        System.out.println("findByAddressIsNotEmpty : "
//              + userRepository.findByAddressIsNotEmpty());
    }


    // In, NotIn
    @Test
    void qryMethod012() {
        System.out.println("findByNameIn : "
                + userRepository.findByNameIn(List.of("martin","dennis")));
        // where name IN (?,?)
    }


    // StartingWith, EndingWith, Contains
    // 문자열에 대한 검색쿼리, LIKE 사용
    @Test
    void qryMethod013() {
        System.out.println("findByNameStartingWith : " + userRepository.findByNameStartingWith("mar"));
        System.out.println("findByNameEndingWith : " + userRepository.findByNameEndingWith("s"));
        System.out.println("findByEmailContains : " + userRepository.findByEmailContains("red"));
    }


    // Like
    // 사실 위의 키워드는 Like 를 한번 더 wrapping 한거다.
    //  Like 검색시 %, _ 와 같은 와일드 카드 사용 가능
    @Test
    void qryMethod014() {
        System.out.println("findByEmailLike : " + userRepository.findByEmailLike("%" + "dragon" + "%"));
    }


    // Is, Equals
    // 특별한 역할은 하지 않는다. 생략가능. 가독성 차원에서 남겨진 키워드입니다.
    @Test
    void qryMethod015() {
        System.out.println(userRepository);
    }


    /***********************************************************************
     * Query Method - Sorting & Paging
     */


    // Naming 기반 정렬
    // Query method 에 OrderBy 를 붙임
    @Test
    void qryMethod016() {
        System.out.println("findTop1ByName : " + userRepository.findTop1ByName("martin"));
        System.out.println("findLast1ByName : " + userRepository.findLast1ByName("martin"));


        System.out.println("findTopByNameOrderByIdDesc : " + userRepository.findTopByNameOrderByIdDesc("martin"));
    }


    // 정렬기준 추가
    // 네이밍 기반으로 한 정렬
    @Test
    void qryMethod017() {
        System.out.println("findFirstByNameOrderByIdDesc : "
                + userRepository.findFirstByNameOrderByIdDesc("martin"));
        System.out.println("findFirstByNameOrderByIdDescEmailDesc : "
                + userRepository.findFirstByNameOrderByIdDescEmailDesc("martin"));
    }


    // 매개변수(Sort) 기반 정렬
    // Query method 에 Sort 매개변수로 정렬옵션 제공
    @Test
    void qryMethod018() {
        System.out.println("findFirstByName + Sort : "
                + userRepository.findFirstByName("martin", Sort.by(Sort.Order.desc("id"))));
        System.out.println("findFirstByName + Sort : "
                + userRepository.findFirstByName("martin", Sort.by(Sort.Order.desc("id"),Sort.Order.asc("email"))));
//        System.out.println("findFirstByName + Sort : " + userRepository);
    }

    // ↑ 무엇이 더 좋을까?
    //   Naming 기반 정렬 vs. 매개변수(Sort) 기반 정렬
    //   'Naming 기반 정렬' 은 유연성이 떨어지고..
    //      정렬 조건이 많아지면 길어지면 메소드도 많이 생성해야 하고 메소드 이름이 길어지니까 가독성이 안좋다.
    //   '매개변수 기반 정렬' 은 메소드 하나로 여러 정렬 조건을 다룰수 있다.
    //     메소드 하나만 정의해놓고, 사용하는 쪽에서 정렬조건을 제공할수 있다
    //     유연성, 자유도, 가독성 좋다.


    @Test
    void qryMethod018_2() {
        System.out.println("findFirstByName + Sort : "
                + userRepository);
        System.out.println("findFirstByName + Sort : "
                + userRepository);
        System.out.println("findFirstByName + Sort : "
                + userRepository);
        System.out.println("findFirstByName + Sort : "
                + userRepository);
    }


    private Sort getSort(){
        return Sort.by(
                Sort.Order.desc("id"),
                Sort.Order.asc("email").ignoreCase(),
                Sort.Order.desc("createdAt"),
                Sort.Order.asc("updatedAt")
        );
    }

    @Test
    void qryMethod018_3() {
        System.out.println("findFirstByName + Sort : "
                + userRepository.findFirstByName("martin", getSort()));
    }


    // TODO


    // 19 Paging + Sort
    // PageRequest.of(page, size, Sort) page는 0-base
    // PageRequest 는 Pageable의 구현체
    @Test
    void qryMethod019() {
        Page<User> userPage = userRepository.findByName("martin", PageRequest.of(1,3));

        System.out.println("page: " + userPage); // Page 를 함 찍어보자
        System.out.println("totalElements: " + userPage.getTotalElements());
        System.out.println("totalPages: " + userPage.getTotalPages());
        System.out.println("numberOfElements: " + userPage.getNumberOfElements());
        System.out.println("sort: " + userPage.getSort());
        System.out.println("size: " + userPage.getSize()); // 페이징 할때 나누는 size
        userPage.getContent().forEach(System.out::println);  // 페이지 내의 데이터 List<>


    }

    @Test
    void insertAndUpdateTest() {
        System.out.println("\n-- TEST#insertAndUpdateTest() ---------------------------------------------");

        user = User.builder()
                        .name("martin")
                                .email("martin2@blutknight.com")
                                        .build();

        userRepository.save(user);

        user2 = userRepository.findById(1L).orElseThrow(RuntimeException::new);
        user2.setName("U E NA");
        userRepository.save(user2); // UPDATE

        System.out.println("\n------------------------------------------------------------\n");
    }

    @Test
    void enumTest() {
        System.out.println("\n-- TEST#enumTest() ---------------------------------------------");
        User user = userRepository.findById(1L).orElseThrow(RuntimeException::new);
        user.setGender(Gender.MALE);
        userRepository.save(user);  // UPDATE
        userRepository.findAll().forEach(System.out::println);

        // enum 타입이 실제 어떤 값으로 DB에 저장되었는지 확인

        System.out.println(userRepository.findRowRecord().get("gender"));

        System.out.println("\n------------------------------------------------------------\n");
    }

    @Test
    void listenerTest() {

        user = new User();
        user.setEmail("berry@mail.com");
        user.setName("U IN A");

        userRepository.save(user);  // INSERT

        // SELECT
        User user2 = userRepository.findById(1L).orElseThrow(RuntimeException::new);

        user2.setName("marrrrrtin");
        userRepository.save(user2);  // SELECT, UPDATE

        userRepository.deleteById(4L);  // SELECT, DELETE

        System.out.println("\n------------------------------------------------------------\n");
    }


    @Test
    void prePersistTest() {
        System.out.println("\n-- TEST#prePersistTest() ---------------------------------------------");
        User user = new User();
        user.setEmail("martin2@redknight.com");
        user.setName("martin2");

//        user.setCreatedAt(LocalDateTime.now());
//        user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user); //INSERT

        System.out.println(userRepository.findByEmail("martin2@redknight.com"));

        System.out.println("\n------------------------------------------------------------\n");
    }

    @Test
    void preUpdateTest() throws InterruptedException {
        Thread.sleep(1000); // 1초 뒤에 업데이트 시도
        System.out.println("\n-- TEST#preUpdateTest() ---------------------------------------------");

        User user = userRepository.findById(1L).orElseThrow(RuntimeException::new);
        System.out.println("as-is : " + user);   // 수정전
        user.setName("martin2");

        userRepository.save(user);
        System.out.println("to-be : " + userRepository.findAll().get(0));


        System.out.println("\n------------------------------------------------------------\n");
    }

    @Test
    void userHistoryTest() {
        System.out.println("\n-- TEST#userHistoryTest() ---------------------------------------------");

        User user = new User();
        user.setEmail("martin-new@greendragon.com");
        user.setName("martin-new");

        userRepository.save(user); // insert 양속성 객체

        user.setName("U E NA");
        userRepository.save(user);  // UPdate 유저 업데이트 전에 userHistory 에 insert 발생

        userRepository.findAll().forEach(System.out::println);


        System.out.println("\n------------------------------------------------------------\n");
    }


    @Test
    void userRelationTest() {
        System.out.println("\n-- TEST#userRelationTest() ---------------------------------------------");

        User user = new User();
        user.setName("David");
        user.setEmail("david@reddragon.com");
        user.setGender(Gender.MALE);

        userRepository.save(user); // USer에 Insert, UserHistory에 Insert

        user.setName("berry");
        userRepository.save(user);  // User에 UPDATE, UserHistory에 INSERT

        System.out.println("\uD83D\uDE00".repeat(30));

        user.setEmail("Berry@mail.com");
        userRepository.save(user);  // User에 UPDATE, UserHistory에 INSERT

        userHistoryRepository.findAll().forEach(System.out::println);

        System.out.println("❤\uFE0F".repeat(30));
        // 특정 userId 로 UserHistory 조회
//        Long userId = userRepository.findByEmail("Berry@mail.com").getId();
//        List<UserHistory> result = userHistoryRepository.findByUserId(userId);
//        result.forEach(System.out::println);

        List<UserHistory> result = userRepository.findByEmail("Berry@mail.com").getUserHistories();

        result.forEach(System.out::println); //  LazyInitializationException 발생!!

        //LazyInitializationException이 발생하는 주요 원인은 JPA에서 관리하는 세션이 종료 된 후(정확하게는 persistence context가 종료 된 후) 관계가 설정된 엔티티를 참조하려고 할 때 발생한다.
        //이것에서 착안해 DAO레이어(Spring data에서 Repository) 상위에서 세션을 시작해 DAO 계층 밖에서도 세션이 종료되지 않도록 트랜젝션을 거는 방법이다.

        System.out.println("\uD83D\uDC69".repeat(30));
        System.out.println(userHistoryRepository.findAll().get(0).getUser());

        System.out.println("\n------------------------------------------------------------\n");
    }

}



