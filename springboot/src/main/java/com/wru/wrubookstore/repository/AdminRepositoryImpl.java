package com.wru.wrubookstore.repository;

import com.wru.wrubookstore.dto.BookDto;
import com.wru.wrubookstore.dto.PublisherDto;
import com.wru.wrubookstore.dto.WriterBookDto;
import com.wru.wrubookstore.dto.WriterDto;
import com.wru.wrubookstore.dto.response.admin.AdminResponse;
import com.wru.wrubookstore.dto.response.book.BookListResponse;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class AdminRepositoryImpl implements AdminRepository {
    private final SqlSessionTemplate session;

    AdminRepositoryImpl(SqlSessionTemplate session) {
        this.session = session;
    }

    private final String namespace = "com.wru.wrubookstore.mapper.AdminMapper.";

    // 재고 100개 추가
    @Override
    public int addQuantity(BookListResponse bookListResponse) throws Exception{
        return session.update(namespace + "addQuantity", bookListResponse);
    }

    // 책 한권 삭제
    @Override
    public int deleteMultipleBook(Integer bookId) throws Exception{
        return session.delete(namespace + "deleteMultipleBook", bookId);
    }

    // 검색
    @Override
    public List<BookDto> searchBook(String searchWord) throws Exception{
        return session.selectList(namespace + "searchBook", searchWord);
    }
    // 재고 0이아닌 상품 전부 조회
    @Override
    public List<BookDto> selectZeroNotQuantityBook(Map map) throws Exception{
        return session.selectList(namespace + "selectZeroNotQuantityBook", map);
    }

    // 재고0인 상품 전부 조회
    @Override
    public List<BookDto> selectZeroQuantityBook(Map map) throws Exception{
        return session.selectList(namespace + "selectZeroQuantityBook", map);
    }
    // writer-book 마지막 코드 조회용
    @Override
    public String selectWriterBookId() throws Exception{
        return session.selectOne(namespace + "selectWriterBookId");
    }
    // 책 한권 조회용(isbn)
    @Override
    public BookDto selectBook(BookDto bookDto) throws Exception{
        return session.selectOne(namespace + "selectBook", bookDto);
    }
    // 출판사 마지막 코드 조회용
    @Override
    public String selectPublisherId() throws Exception{
        return session.selectOne(namespace + "selectPublisherId");
    }
    // 출판사 모두 조회용
    @Override
    public List<PublisherDto> selectAllPublisher() throws Exception{
        return session.selectList(namespace + "selectAllPublisher");
    }
    // 출판사 한개 조회용
    @Override
    public PublisherDto selectPublisherOne(PublisherDto publisherDto) throws Exception{
        return session.selectOne(namespace + "selectPublisherOne", publisherDto);
    }
    // 지은이 모두 조회용
    @Override
    public List<WriterDto> selectAllWriter() throws Exception{
        return session.selectList(namespace + "selectAllWriter");
    }
    @Override
    public String selectWriterId() throws Exception{
        return session.selectOne(namespace + "selectWriterId");
    }

    @Override
    public WriterDto selectWriterOne(WriterDto writerDto) throws Exception {
        return session.selectOne(namespace + "selectWriterOne", writerDto);
    }

    @Override
    public int insertBook(BookDto bookDto) throws Exception {
        return session.insert(namespace + "insertBook", bookDto);
    }

    @Override
    public int insertPublisher(PublisherDto publisherDto) throws Exception {
        return session.insert(namespace + "insertPublisher", publisherDto);
    }

    @Override
    public int insertWriter(WriterDto writerDto) throws Exception {
        return session.insert(namespace + "insertWriter", writerDto);
    }

    @Override
    public int insertWriterBook(WriterBookDto writerBookDto) throws Exception {
        return session.insert(namespace + "insertWriterBook", writerBookDto);
    }

    // 지은이 검색용
    @Override
    public List<WriterDto> searchWriter(String keyword) throws Exception{
        return session.selectList(namespace + "searchWriter", keyword);
    }

    // 출판사 검색용
    @Override
    public List<PublisherDto> searchPublisher(String keyword) throws Exception{
        return session.selectList(namespace + "searchPublisher", keyword);
    }


}
