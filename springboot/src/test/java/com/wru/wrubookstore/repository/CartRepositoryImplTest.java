//    package com.wru.wrubookstore.repository;
//
//    import com.wru.wrubookstore.dto.BookDto;
//    import com.wru.wrubookstore.dto.CartDto;
//    import com.wru.wrubookstore.dto.MemberDto;
//    import com.wru.wrubookstore.dto.UserDto;
//    import org.junit.jupiter.api.DisplayName;
//    import org.junit.jupiter.api.Test;
//    import org.junit.jupiter.api.BeforeEach;
//    import org.springframework.beans.factory.annotation.Autowired;
//    import org.springframework.boot.test.context.SpringBootTest;
//    import org.springframework.transaction.annotation.Transactional;
//
//    import java.math.BigDecimal;
//    import java.time.LocalDate;
//    import java.util.Calendar;
//    import java.util.Date;
//    import java.util.List;
//
//    import static org.junit.jupiter.api.Assertions.*;
//
//    @SpringBootTest
//    class CartRepositoryImplTest {
//
//        @Autowired
//        UserRepository userRepository;
//
//        @Autowired
//        MemberRepository memberRepository;
//
//        @Autowired
//        BookRepository bookRepository;
//
//        @Autowired
//        CartRepository cartRepository;
//
//        UserDto userDto1;
//        UserDto userDto2;
//        MemberDto memberDto;
//        BookDto bookDto;
//
//        // 테스트용 비회원, 회원, 도서 데이터 정의
//        @BeforeEach
//        public void init() throws Exception {
//            userDto1 = new UserDto("tester1@gmail.com", "pwd1", "tester1");
//            userDto2 = new UserDto("tester2@gmail.com", "pwd2", "tester2");
//
//            memberDto = new MemberDto(
//                    userDto2.getEmail(),
//                    userDto2.getPassword(),
//                    userDto2.getName(),
//                    "tn",
//                    "01012345678",
//                    LocalDate.of(2000, 3, 20),
//                    'M',
//                    "https://cdn-icons-png.flaticon.com/128/1077/1077063.png"
//            );
//
//            Calendar releaseDate = Calendar.getInstance();
//            releaseDate.set(2023, Calendar.DECEMBER, 1);
//
//            bookDto = new BookDto(
//                    "pub_1",
//                    "cs_2",
//                    "인생은 순간이다",
//                    "홍길동",
//                    18000,
//                    new BigDecimal("0.01"),
//                    180,
//                    17820,
//                    releaseDate.getTime(),
//                    new Date(),
//                    "목차1",
//                    "야구에 관한 내용",
//                    9791130648385L,
//                    10000,
//                    "133*200",
//                    426,
//                    328,
//                    "https://image.aladin.co.kr/product/32875/63/cover500/k562936112_2.jpg"
//            );
//        }
//
//        @DisplayName("장바구니에 상품 추가 후 정상적으로 조회되는지 확인")
//        @Test
//        @Transactional
//        public void insertAndSelectTest() throws Exception {
//            // 모든 비회원 삭제
//            userRepository.deleteAll();
//
//            // 모든 회원 삭제
//            memberRepository.deleteAllMembers();
//            memberRepository.deleteAllUsers();
//
//            // 모든 도서 삭제
//            bookRepository.deleteAllByAdmin();
//
//            // 모든 장바구니 삭제
//            cartRepository.deleteAllByAdmin();
//            assertEquals(0, cartRepository.selectAllByAdmin().size());
//            assertEquals(0, cartRepository.countAllByAdmin());
//
//            // 새로운 비회원 추가
//            assertEquals(1, userRepository.insert(userDto1));
//
//            // 새로운 회원 추가
//            assertEquals(1, memberRepository.insertUser(userDto2));
//            assertEquals(1, memberRepository.insertMember(memberDto));
//
//            // 새로운 도서 추가
//            assertEquals(1, bookRepository.insert(bookDto));
//
//            // 사용자 및 도서 userId 조회
//            int userId = userRepository.selectAll().getFirst().getUserId();
//            int memberId = memberRepository.selectAll().getFirst().getUserId();
//            BookDto selectedBook = bookRepository.selectAllByAdmin().getFirst();
//            int bookId = selectedBook.getBookId();
//            int bookSalePrice = selectedBook.getSalePrice();
//
//            // 장바구니에 도서 추가
//            CartDto cartDto1 = new CartDto(bookId, userId, 1, bookSalePrice);
//            CartDto cartDto2 = new CartDto(bookId, memberId, 5, bookSalePrice);
//            assertEquals(1, cartRepository.insert(cartDto1));
//            assertEquals(1, cartRepository.insert(cartDto2));
//
//            // 전체 장바구니 수 조회
//            assertEquals(2, cartRepository.countAllByAdmin());
//            assertEquals(2, cartRepository.selectAllByAdmin().size());
//
//            // 장바구니 리스트가 정상적으로 조회되는지 확인
//            List<CartDto> carts = cartRepository.selectAllByAdmin();
//            assertNotNull(carts);
//
//            CartDto selectedCart1 = cartRepository.selectByUserIdAndBookId(userId, bookId);
//            CartDto selectedCart2 = cartRepository.selectByUserIdAndBookId(memberId, bookId);
//
//            // 저장된 데이터와 일치하는지 확인
//            assertEquals(cartDto1, selectedCart1);
//            assertEquals(cartDto2, selectedCart2);
//        }
//
//        @DisplayName("장바구니에 상품 수정 후 정상적으로 조회되는지 확인 - bookId 와 userId 동일할 때만 같은 객체")
//        @Test
//        @Transactional
//        public void updateTest() throws Exception {
//            // 모든 비회원 삭제
//            userRepository.deleteAll();
//
//            // 모든 회원 삭제
//            memberRepository.deleteAllMembers();
//            memberRepository.deleteAllUsers();
//
//            // 모든 도서 삭제
//            bookRepository.deleteAllByAdmin();
//
//            // 모든 장바구니 삭제
//            cartRepository.deleteAllByAdmin();
//            assertEquals(0, cartRepository.selectAllByAdmin().size());
//            assertEquals(0, cartRepository.countAllByAdmin());
//
//            // 새로운 비회원 추가
//            assertEquals(1, userRepository.insert(userDto1));
//
//            // 새로운 회원 추가
//            assertEquals(1, memberRepository.insertUser(userDto2));
//            assertEquals(1, memberRepository.insertMember(memberDto));
//
//            // 새로운 도서 추가
//            assertEquals(1, bookRepository.insert(bookDto));
//
//            // 사용자의 userId 및 도서의 bookId 와 salePrice 조회
//            int userId = userRepository.selectAll().getFirst().getUserId();
//            BookDto selectedBook = bookRepository.selectAllByAdmin().getFirst();
//            int bookId = selectedBook.getBookId();
//            int bookSalePrice = selectedBook.getSalePrice();
//
//            // 장바구니에 도서 추가
//            CartDto cartDto = new CartDto(bookId, userId, 1, bookSalePrice);
//            assertEquals(1, cartRepository.insert(cartDto));
//
//            // 전체 장바구니 수 조회
//            assertEquals(1, cartRepository.countAllByAdmin());
//            assertEquals(1, cartRepository.selectAllByAdmin().size());
//
//            // 장바구니 리스트가 정상적으로 조회되는지 확인
//            List<CartDto> carts = cartRepository.selectAllByAdmin();
//            assertNotNull(carts);
//
//            CartDto selectedCart1 = cartRepository.selectByUserIdAndBookId(userId, bookId);
//
//            // 해당 장바구니 정보 수정 (userId와 bookId)
//            selectedCart1.setUserId(userId + 1);
//            selectedCart1.setBookId(bookId + 1);
//
//            assertEquals(1, cartRepository.update(selectedCart1));
//
//            // 수정 사항 반영되었는지 확인
//            CartDto updatedCart1 = cartRepository.selectByUserIdAndBookId(selectedCart1.getUserId(), selectedCart1.getBookId());
//            assertNull(updatedCart1);
//            assertNotEquals(selectedCart1, updatedCart1);
//
//            CartDto selectedCart2 = cartRepository.selectByUserIdAndBookId(userId, bookId);
//            int cartQuantity = selectedCart2.getQuantity();
//            int cartPrice =  selectedCart2.getPrice();
//
//            // 해당 장바구니 정보 수정 (quantity 와 price)
//            selectedCart2.setQuantity(cartQuantity + 1);
//            selectedCart2.setPrice(cartPrice + 1);
//
//            assertEquals(1, cartRepository.update(selectedCart2));
//
//            // 수정 사항 반영되었는지 확인
//            CartDto updatedCart2 = cartRepository.selectByUserIdAndBookId(selectedCart2.getUserId(), selectedCart2.getBookId());
//            assertNotNull(updatedCart2);
//            assertEquals(selectedCart2, updatedCart2);
//        }
//
//        @DisplayName("장바구니 상품을 삭제했을 때 정상적으로 제거되는지 확인")
//        @Test
//        @Transactional
//        public void deleteTest() throws Exception {
//            // 모든 비회원 삭제
//            userRepository.deleteAll();
//
//            // 모든 회원 삭제
//            memberRepository.deleteAllMembers();
//            memberRepository.deleteAllUsers();
//
//            // 모든 도서 삭제
//            bookRepository.deleteAllByAdmin();
//
//            // 모든 장바구니 삭제
//            cartRepository.deleteAllByAdmin();
//            assertEquals(0, cartRepository.selectAllByAdmin().size());
//            assertEquals(0, cartRepository.countAllByAdmin());
//
//            // 새로운 비회원 추가
//            assertEquals(1, userRepository.insert(userDto1));
//
//            // 새로운 회원 추가
//            assertEquals(1, memberRepository.insertUser(userDto2));
//            assertEquals(1, memberRepository.insertMember(memberDto));
//
//            // 새로운 도서 추가
//            assertEquals(1, bookRepository.insert(bookDto));
//
//            // 사용자의 userId 및 도서의 bookId 와 salePrice 조회
//            int userId = userRepository.selectAll().getFirst().getUserId();
//            BookDto selectedBook = bookRepository.selectAllByAdmin().getFirst();
//            int bookId = selectedBook.getBookId();
//            int bookSalePrice = selectedBook.getSalePrice();
//
//            // 장바구니에 도서 추가
//            CartDto cartDto = new CartDto(bookId, userId, 1, bookSalePrice);
//            assertEquals(1, cartRepository.insert(cartDto));
//
//            // 전체 장바구니 수 조회
//            assertEquals(1, cartRepository.countAllByAdmin());
//            assertEquals(1, cartRepository.selectAllByAdmin().size());
//
//            // 장바구니 리스트가 정상적으로 조회되는지 확인
//            CartDto selectedCart  = cartRepository.selectByUserIdAndBookId(userId, bookId);
//            assertNotNull(selectedCart );
//
//            Integer cartId = selectedCart.getCartId();
//
//            // 해당 회원 삭제
//            assertEquals(1, cartRepository.delete(cartId));
//
//            // 삭제 결과 확인
//            assertNull(cartRepository.selectByUserIdAndBookId(userId, bookId));
//            assertEquals(0, cartRepository.countAllByAdmin());
//        }
//
//    }