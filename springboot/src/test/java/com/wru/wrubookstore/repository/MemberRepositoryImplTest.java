/*
package com.wru.wrubookstore.repository;

import com.wru.wrubookstore.dto.MemberDto;
import com.wru.wrubookstore.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberRepositoryImplTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MemberRepository memberRepository;

    UserDto userDto;
    LocalDate birthdate;

    MemberDto memberDto;

    @BeforeEach
    public void init() throws Exception {
        userDto = new UserDto("tester@gmail.com", "pwd", "tester");

        birthdate = LocalDate.of(2000, 3, 20);

        memberDto = new MemberDto("tester@gmail.com", "pwd", "tester", "tn",
                "01012345678", birthdate, 'M', "https://cdn-icons-png.flaticon.com/128/1077/1077063.png");
    }

    // 테스트 완료
    // userRepository - deleteAll(), selectAll(), count()
    // memberRepository - deleteAllMembers(), deleteAllUsers(), selectAll(), count(), insertUser(), insertMember()
    // memberRepository - countMembers()
    @Test
    @Transactional
    public void insertAndSelectTest() throws Exception {
        // 모든 비회원 삭제
        userRepository.deleteAll();
        assertEquals(0, userRepository.selectAll().size());

        // 모든 회원 삭제
        memberRepository.deleteAllMembers();
        memberRepository.deleteAllUsers();
        assertEquals(0, memberRepository.selectAll().size());
        assertEquals(0, memberRepository.count());

        // 새로운 회원 추가
        assertEquals(1, memberRepository.insertUser(userDto));
        assertEquals(1, memberRepository.insertMember(memberDto));
        assertEquals(1, memberRepository.selectAll().size());
        assertEquals(1, memberRepository.count());

        // 동일한 이메일을 갖는 회원 추가 시 예외 발생하는지 확인
        assertThrows(Exception.class, () -> memberRepository.insertUser(userDto));
        assertThrows(Exception.class, () -> memberRepository.insertMember(memberDto));

        // 전체 비회원 수 조회
        assertEquals(0, userRepository.count());

        // 전체 회원 수 조회
        assertEquals(1, memberRepository.countMembers());

        // 데이터가 정상적으로 조회되는지 확인
        List<MemberDto> members = memberRepository.selectAll();
        assertNotNull(members);

        String email = members.getFirst().getEmail();
        MemberDto selectedMember = memberRepository.select(email);

        // 저장된 데이터와 일치하는지 확인
        assertEquals(memberDto, selectedMember);
    }

    // 테스트 완료
    // userRepository - deleteAll(), selectAll(), count()
    // memberRepository - deleteAllMembers(), deleteAllUsers(), selectAll(), count(), insertUser(), insertMember()
    // memberRepository - countMembers(), updateMember()
    @Test
    @Transactional
    public void updateTest() throws Exception {
        // 모든 비회원 삭제
        userRepository.deleteAll();
        assertEquals(0, userRepository.selectAll().size());
        assertEquals(0, userRepository.count());

        // 모든 회원 삭제
        memberRepository.deleteAllMembers();
        memberRepository.deleteAllUsers();
        assertEquals(0, memberRepository.selectAll().size());
        assertEquals(0, memberRepository.count());

        // 새로운 회원 추가
        assertEquals(1, memberRepository.insertUser(userDto));
        assertEquals(1, memberRepository.insertMember(memberDto));
        assertEquals(1, memberRepository.selectAll().size());
        assertEquals(1, memberRepository.count());
        assertEquals(1, memberRepository.countMembers());

        // 데이터가 정상적으로 조회되는지 확인
        List<MemberDto> members = memberRepository.selectAll();
        assertNotNull(members);

        String email = members.getFirst().getEmail();
        MemberDto selectedMember = memberRepository.select(email);

        // 해당 회원 정보 수정
        selectedMember.setNickname(selectedMember.getNickname() + "1");
        selectedMember.setPhoneNum(selectedMember.getPhoneNum() + "1");
        selectedMember.setBirthdate(selectedMember.getBirthdate().plusDays(1));
        selectedMember.setGender('F');
        selectedMember.setImage(selectedMember.getImage() + "1");

        assertEquals(1, memberRepository.updateMember(selectedMember));

        // 수정 사항 반영되었는지 확인
        MemberDto updatedMember = memberRepository.select(selectedMember.getEmail());

        assertNotNull(updatedMember);

        assertEquals(selectedMember, updatedMember);
    }

     // 테스트 완료
     // userRepository - deleteAll(), selectAll(), count()
     // memberRepository - deleteAllMembers(), deleteAllUsers(), selectAll(), count(), insertUser(), insertMember()
     // memberRepository - countMembers(), deleteMember(), deleteUser()
    @Test
    @Transactional
    public void deleteTest() throws Exception {
        // 모든 비회원 삭제
        userRepository.deleteAll();
        assertEquals(0, userRepository.selectAll().size());
        assertEquals(0, userRepository.count());

        // 모든 회원 삭제
        memberRepository.deleteAllMembers();
        memberRepository.deleteAllUsers();
        assertEquals(0, memberRepository.selectAll().size());
        assertEquals(0, memberRepository.count());

        // 새로운 회원 추가
        assertEquals(1, memberRepository.insertUser(userDto));
        assertEquals(1, memberRepository.insertMember(memberDto));
        assertEquals(1, memberRepository.selectAll().size());
        assertEquals(1, memberRepository.count());
        assertEquals(1, memberRepository.countMembers());

        // 데이터가 정상적으로 조회되는지 확인
        List<MemberDto> members = memberRepository.selectAll();
        assertNotNull(members);

        String email = members.getFirst().getEmail();

        // 해당 회원 삭제
        assertEquals(1, memberRepository.deleteMember(email));
        assertEquals(1, memberRepository.deleteUser(email));

        // 삭제 결과 확인
        assertNull(memberRepository.select(email));
        assertEquals(0, memberRepository.count());
        assertEquals(0, memberRepository.countMembers());
    }

    @Test
    @Transactional
    public void updateMileageTest() throws Exception {
        int mileageDiscount = 2000;
        int actualPrice = 10000;
        Integer userId = 3;
        Map<String, Integer> map = new HashMap<>();
        map.put("mileageDiscount", mileageDiscount);
        map.put("actualPrice", actualPrice);
        map.put("userId", userId);

        int updateCnt = memberRepository.updateMileage(map);
        memberDto = memberRepository.selectMember(userId);

        assertEquals(updateCnt, 1);
        assertEquals(memberDto.getMileage(), 1190);
    }

    @Test
    @Transactional
    public void updateLastMonthAmountTest() throws Exception {
        Integer userId = 3;
        int totalPrice = 10000;
        Map<String, Integer> map = new HashMap<>();
        map.put("totalPrice", totalPrice);
        map.put("userId", userId);

        int updateCnt = memberRepository.updateLastMonthAmount(map);
        MemberDto memberDto = memberRepository.selectMember(userId);

        assertEquals(updateCnt, 1);
        assertEquals(memberDto.getLastMonthAmount(), 10000);
    }

    @Test
    public void selectByNameAndPhoneNumTest() throws Exception {
        String name = "김유리";
        String phoneNum = "010-3456-7890";

        memberDto = new MemberDto();
        memberDto.setName(name);
        memberDto.setPhoneNum(phoneNum);

        MemberDto selectMemberDto = memberRepository.selectByNameAndPhoneNum(memberDto);

        assertEquals(selectMemberDto.getName(), name);
        assertEquals(selectMemberDto.getPhoneNum(), phoneNum);
        assertEquals(selectMemberDto.getEmail(), "yuri@gmail.com");
    }

    @Test
    public void selectByEmailAndNameAndPhoneNumTest() throws Exception {
        String email = "yuri@gmail.com";
        String name = "김유리";
        String phoneNum = "010-3456-7890";

        memberDto = new MemberDto();
        memberDto.setEmail(email);
        memberDto.setName(name);
        memberDto.setPhoneNum(phoneNum);

        MemberDto selectMemberDto = memberRepository.selectByEmailAndNameAndPhoneNum(memberDto);

        assertEquals(selectMemberDto.getName(), name);
        assertEquals(selectMemberDto.getPhoneNum(), phoneNum);
        assertEquals(selectMemberDto.getEmail(), "yuri@gmail.com");
        assertEquals(selectMemberDto.getPassword(), "asdf");
    }
}
*/
