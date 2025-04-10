package com.wru.wrubookstore.service;

import com.wru.wrubookstore.dto.BookDto;
import com.wru.wrubookstore.dto.PublisherDto;
import com.wru.wrubookstore.dto.WriterBookDto;
import com.wru.wrubookstore.dto.WriterDto;
import com.wru.wrubookstore.dto.response.admin.AdminResponse;
import com.wru.wrubookstore.repository.AdminRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;

    AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    // 검색
    @Override
    public List<BookDto> searchBook(String name) throws Exception{
        return adminRepository.searchBook(name);
    }
    // 재고 0이아닌 상품 전부 조회
    @Override
    public List<BookDto> selectZeroNotQuantityBook(Map map) throws Exception{
        return adminRepository.selectZeroNotQuantityBook(map);
    }

    // 재고0인 상품 전부 조회
    @Override
    public List<BookDto> selectZeroQuantityBook(Map map) throws Exception{
        return adminRepository.selectZeroQuantityBook(map);
    }
    // writer-book 마지막 코드 조회용
    @Override
    public String selectWriterBookId() throws Exception{
        return adminRepository.selectWriterBookId();
    }
    // 책 한권 조회용(isbn)
    @Override
    public BookDto selectBook(BookDto bookDto) throws Exception{
        return adminRepository.selectBook(bookDto);
    }
    // 출판사 마지막 코드 조회용
    @Override
    public String selectPublisherId() throws Exception{
        return adminRepository.selectPublisherId();
    }
    // 출판사 모두 조회용
    @Override
    public List<PublisherDto> selectAllPublisher() throws Exception{
        return adminRepository.selectAllPublisher();
    }
    // 출판사 한개 조회용
    @Override
    public PublisherDto selectPublisherOne(PublisherDto publisherDto) throws Exception{
        return adminRepository.selectPublisherOne(publisherDto);
    }

    // 지은이 모두 조회용
    @Override
    public List<WriterDto> selectAllWriter() throws Exception{
        return adminRepository.selectAllWriter();
    }
    // 마지막 지은이 코드 조회용
    @Override
    public String selectWriterId() throws Exception{
        return adminRepository.selectWriterId();
    }
    // 지은이 한명 조회용
    @Override
    public WriterDto selectWriterOne(WriterDto writerDto) throws Exception{
        return adminRepository.selectWriterOne(writerDto);
    }
    // 책 등록용
    @Override
    public void insertBook(BookDto bookDto) throws Exception{
        adminRepository.insertBook(bookDto);
    }
    // 출판사 등록용
    @Override
    public void insertPublisher(PublisherDto publisherDto) throws Exception{
        adminRepository.insertPublisher(publisherDto);
    }
    // 지은이 등록용
    @Override
    public void insertWriter(WriterDto writerDto) throws Exception{
        adminRepository.insertWriter(writerDto);
    }
    // writer_book 등록용
    @Override
    public void insertWriterBook(WriterBookDto writerBookDto) throws Exception{
        adminRepository.insertWriterBook(writerBookDto);
    }
}
