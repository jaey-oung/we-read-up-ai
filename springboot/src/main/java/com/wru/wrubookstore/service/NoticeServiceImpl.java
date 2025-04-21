package com.wru.wrubookstore.service;

import com.wru.wrubookstore.repository.NoticeRepositoryImpl;
import com.wru.wrubookstore.dto.NoticeDto;
import com.wru.wrubookstore.domain.SearchCondition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepositoryImpl noticeRepositoryImpl;

    public NoticeServiceImpl(NoticeRepositoryImpl noticeRepositoryImpl) {
        this.noticeRepositoryImpl = noticeRepositoryImpl;
    }

    @Override
    public int getCount() throws Exception {
        return noticeRepositoryImpl.count();
    }

    @Transactional
    @Override
    public int remove(Integer noticeId, String employeeId) throws Exception {
        // 댓글 먼저 삭제
        noticeRepositoryImpl.deleteCommentsByNoticeId(noticeId);
        // 게시물 삭제
        return noticeRepositoryImpl.delete(noticeId, employeeId);
    }

    @Override
    public int write(NoticeDto noticeDto) throws Exception {
//        throw new Exception("test");      // 쓰기 실패 테스트를 위한 예외 던지기
        return noticeRepositoryImpl.insert(noticeDto);
    }

    @Override
    public List<NoticeDto> getList() throws Exception {
        return noticeRepositoryImpl.selectAll();
    }

    @Override
    public NoticeDto read(Integer noticeId) throws Exception {
        NoticeDto noticeDto = noticeRepositoryImpl.select(noticeId);
        noticeRepositoryImpl.increaseViewCnt(noticeId);

        return noticeDto;
    }

    @Override
    public List<NoticeDto> getPage(Map map) throws Exception {
        return noticeRepositoryImpl.selectPage(map);
    }

    @Override
    public int modify(NoticeDto noticeDto) throws Exception {
        return noticeRepositoryImpl.update(noticeDto);
    }

    @Override
    public int getSearchResultCnt(SearchCondition sc) throws Exception {
        return noticeRepositoryImpl.searchResultCnt(sc);
    }

    @Override
    public List<NoticeDto> getSearchResultPage(SearchCondition sc) throws Exception {
        return noticeRepositoryImpl.searchSelectPage(sc);
    }


}
