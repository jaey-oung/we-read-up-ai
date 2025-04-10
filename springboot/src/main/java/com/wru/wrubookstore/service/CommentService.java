package com.wru.wrubookstore.service;

import com.wru.wrubookstore.repository.CommentRepositoryImpl;
import com.wru.wrubookstore.dto.CommentDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepositoryImpl commentRepositoryImpl;

    public CommentService(CommentRepositoryImpl commentRepositoryImpl) {
        this.commentRepositoryImpl = commentRepositoryImpl;
    }

    public int getCount(Integer noticeId) throws Exception {
        return commentRepositoryImpl.count(noticeId);
    }

    @Transactional(rollbackFor = Exception.class)
    public int remove(Integer commentId, Integer noticeId, Integer memberId) throws Exception {
//        int rowCnt = noticeDao.updateCommentCnt(noticeId, -1);
//        System.out.println("updateCommentCnt - rowCnt = " + rowCnt);
////        throw new Exception("test");
        int rowCnt = commentRepositoryImpl.delete(commentId, memberId);
        System.out.println("rowCnt = " + rowCnt);
        return rowCnt;
    }

    @Transactional(rollbackFor = Exception.class)
    public int write(CommentDto commentDto) throws Exception {
//        noticeDao.updateCommentCnt(commentDto.getBno(), 1);
//                throw new Exception("test");
        return commentRepositoryImpl.insert(commentDto);
    }

    public List<CommentDto> getList(Integer noticeId) throws Exception {
//        throw new Exception("test");
        return commentRepositoryImpl.selectAll(noticeId);
    }

    public CommentDto read(Integer commentId) throws Exception {
        return commentRepositoryImpl.select(commentId);
    }

    public int modify(CommentDto commentDto) throws Exception {
        return commentRepositoryImpl.update(commentDto);
    }
}
