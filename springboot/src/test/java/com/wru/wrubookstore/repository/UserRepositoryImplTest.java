/*
package com.wru.wrubookstore.repository;

import com.wru.wrubookstore.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryImplTest {

    @Autowired
    UserRepository userRepository;

    UserDto userDto;

    @BeforeEach
    public void init() throws Exception {
        userDto = new UserDto("tester@gmail.com", "pwd", "tester");
    }

    // 테스트 완료
    // deleteAll(), selectAll(), count(), insert(), select()
    @Test
    @Transactional
    public void insertAndSelectTest() throws Exception {
        // 모든 비회원 삭제
        userRepository.deleteAll();
        assertEquals(0, userRepository.selectAll().size());
        assertEquals(0, userRepository.count());

        // 새로운 비회원 추가
        assertEquals(1, userRepository.insert(userDto));
        assertEquals(1, userRepository.selectAll().size());
        assertEquals(1, userRepository.count());

        // 동일한 이메일을 갖는 비회원 추가 시 예외 발생하는지 확인
        assertThrows(Exception.class, () -> userRepository.insert(userDto));

        // 데이터가 정상적으로 조회되는지 확인
        List<UserDto> users = userRepository.selectAll();
        assertNotNull(users);

        String email = users.getFirst().getEmail();
        UserDto selectedUser = userRepository.select(email);

        // 저장된 데이터와 일치하는지 확인
        assertEquals(userDto.getEmail(), selectedUser.getEmail());
        assertEquals(userDto.getPassword(), selectedUser.getPassword());
        assertEquals(userDto.getName(), selectedUser.getName());
    }

    // 테스트 완료
    // deleteAll(), selectAll(), count(), insert(), select(), update()
    @Test
    @Transactional
    public void updateTest() throws Exception {
        // 모든 비회원 삭제
        userRepository.deleteAll();
        assertEquals(0, userRepository.selectAll().size());
        assertEquals(0, userRepository.count());

        // 새로운 비회원 추가
        assertEquals(1, userRepository.insert(userDto));
        assertEquals(1, userRepository.selectAll().size());
        assertEquals(1, userRepository.count());

        // 데이터가 정상적으로 조회되는지 확인
        List<UserDto> users = userRepository.selectAll();
        assertNotNull(users);

        String email = users.getFirst().getEmail();
        UserDto selectedUser = userRepository.select(email);

        // 해당 비회원 정보 수정
        userDto.setPassword(selectedUser.getPassword() + "1");

        assertEquals(1, userRepository.update(userDto));

        // 수정 사항 반영되었는지 확인
        UserDto updatedUser = userRepository.select(userDto.getEmail());

        assertNotNull(updatedUser);

        assertEquals(userDto, updatedUser);
    }

    // 테스트 완료
    // deleteAll(), selectAll(), count(), insert(), select(), delete()
    @Test
    @Transactional
    public void deleteTest() throws Exception {
        // 모든 비회원 삭제
        userRepository.deleteAll();
        assertEquals(0, userRepository.selectAll().size());
        assertEquals(0, userRepository.count());

        // 새로운 비회원 추가
        assertEquals(1, userRepository.insert(userDto));
        assertEquals(1, userRepository.selectAll().size());
        assertEquals(1, userRepository.count());

        // 데이터가 정상적으로 조회되는지 확인
        List<UserDto> users = userRepository.selectAll();
        assertNotNull(users);

        String email = users.getFirst().getEmail();

        // 해당 비회원 삭제
        assertEquals(1, userRepository.delete(email));

        // 삭제 결과 확인
        assertNull(userRepository.select(email));
        assertEquals(0, userRepository.count());
    }

}
*/
