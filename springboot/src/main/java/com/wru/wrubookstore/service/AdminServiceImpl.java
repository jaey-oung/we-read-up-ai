package com.wru.wrubookstore.service;

import com.wru.wrubookstore.dto.BookDto;
import com.wru.wrubookstore.dto.PublisherDto;
import com.wru.wrubookstore.dto.WriterBookDto;
import com.wru.wrubookstore.dto.WriterDto;
import com.wru.wrubookstore.dto.response.admin.AdminBookCreateData;
import com.wru.wrubookstore.dto.response.admin.AdminResponse;
import com.wru.wrubookstore.dto.response.book.BookListResponse;
import com.wru.wrubookstore.dto.response.category.CategoryResponse;
import com.wru.wrubookstore.error.exception.*;
import com.wru.wrubookstore.repository.AdminRepository;
import com.wru.wrubookstore.validator.PublisherValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final BookService bookService;
    private final PublisherValidator publisherValidator;
    private final int FAIL = 0;
    private final int ONE = 1;
    // 보여줄 상품 리스트
    private final int ALL_PRODUCT = 1;
    private final int SOLD_OUT = 0;
    private final int FOR_SALE = 2;
    private final String WRT = "wrt_";
    private final String PUB = "pub_";
    private final String WB = "wb_";

    AdminServiceImpl(AdminRepository adminRepository, BookService bookService, PublisherValidator publisherValidator) {
        this.adminRepository = adminRepository;
        this.bookService = bookService;
        this.publisherValidator = publisherValidator;
    }

    // 책 등록
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createBook(AdminResponse adminResponse) throws Exception{
        if(adminResponse.getBookDto() == null || adminResponse.getBookDto().isEmpty()){
            throw new BookInfoMissingException("ERROR: 상품 정보가 없습니다.");
        } else if(adminResponse.getWriterDto() == null || adminResponse.getWriterDto().isEmpty()){
            throw new WriterInfoMissingException("ERROR: 지은이 정보가 없습니다.");
        } else if(adminResponse.getPublisherDto() == null || adminResponse.getPublisherDto().isEmpty()){
            throw new PublisherInfoMissingException("ERROR: 출판사 정보가 없습니다.");
        }

        // 지은이 writerId
        List<String> writerIdList = processWriter(adminResponse.getWriterDto());
        // 출판사 publisherId
        String publisherId = processPublisher(adminResponse.getPublisherDto());
        // 책 bookId
        Integer bookId = processBook(adminResponse.getBookDto(), publisherId);
        // writer_book 등록 로직
        processWriterBook(writerIdList, bookId);

        return "success";
    }



    // 책 리스트 조회
    @Override
    public List<BookDto> getBookList(Integer quantity, Integer offset, Integer limit, String searchWord) throws Exception{
        // 검색 객체가 비어있지 않으면
        if(searchWord != null && !searchWord.isEmpty()){
            return searchBook(searchWord);
        }

        // 상품 정보
        Map<String, Object> map = new HashMap<>();
        map.put("offset", offset);
        map.put("limit", limit);

        return switch(quantity){
            case FOR_SALE -> selectZeroNotQuantityBook(map);
            case SOLD_OUT -> selectZeroQuantityBook(map);
            default -> bookService.selectBook(map);
        };
    }

    // 재고 100개 추가
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String addQuantity(BookListResponse[] bookListResponse) throws Exception{
        if(bookListResponse == null || bookListResponse.length==0){
            return "fail";
        }

        for(BookListResponse book : bookListResponse){
            int result = adminRepository.addQuantity(book);
            if(result == FAIL){
                throw new BookUpdateFailException("ERROR: 책 재고 추가에 실패했습니다.", book.getBookId());
            }
        }

        return "success";
    }

    // 책 삭제
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String deleteMultipleBook(Integer[] bookId) throws Exception{
        if(bookId == null || bookId.length == 0){
            return "fail";
        }

        for(Integer id : bookId){
            int result = adminRepository.deleteMultipleBook(id);
            if(result == FAIL){
                throw new BookDeleteFailException("ERROR: 책 삭제에 실패했습니다.", id);
            }
        }

        return "success";
    }

    // 카테고리 조회
    @Override
    public List<CategoryResponse> selectCategory(CategoryResponse categoryResponse) throws Exception{
        List<CategoryResponse> categoryResponseList = new ArrayList<>();

        // 카테고리 Medium 조회
        if(categoryResponse != null && categoryResponse.getCategoryMediumId() == null){
            categoryResponseList = bookService.selectCategoryMedium(categoryResponse);
        }

        // 카테고리 Small 조회
        if(categoryResponse != null && categoryResponse.getCategoryMediumId() != null){
            categoryResponseList = bookService.selectCategorySmall(categoryResponse);
        }

        if(categoryResponseList.isEmpty()){
            throw new CategoryInfoMissingException("ERROR: 카테고리 정보를 찾을 수 없습니다.");
        }

        return categoryResponseList;
    }
    // 책 등록에 필요한 데이터 조회
    @Override
    public AdminBookCreateData selectBookCreateData() throws Exception{
        AdminBookCreateData adminBookCreateData = new AdminBookCreateData();

        // 카테고리 Large 조회
        adminBookCreateData.setCategoryResponse(bookService.selectCategoryLarge());
        if(adminBookCreateData.getCategoryResponse().isEmpty()){
            throw new CategoryNotFoundException("카테고리 정보를 찾을 수 없습니다.");
        }
        // 모든 지은이 조회
        adminBookCreateData.setWriterDto(adminRepository.selectAllWriter());
        if(adminBookCreateData.getWriterDto().isEmpty()){
            throw new WriterNotFoundException("지은이 정보를 불러오지 못했습니다. 잠시 후 다시 시도해 주세요.");
        }
        // 모든 출판사 조회
        adminBookCreateData.setPublisherDto(adminRepository.selectAllPublisher());
        if(adminBookCreateData.getPublisherDto().isEmpty()){
            throw new PublisherNotFoundException("출판사 정보를 불러오지 못했습니다. 잠시 후 다시 시도해 주세요.");
        }

        return adminBookCreateData;
    }

    // 검색
    @Override
    public List<BookDto> searchBook(String searchWord) throws Exception{
        return adminRepository.searchBook(searchWord);
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
    // 지은이 비교 조회 서비스 로직
    @Override
    public List<String> processWriter(List<WriterDto> writerDto) throws Exception{
        List<String> writerIdList = new ArrayList<>();

        for(WriterDto w : writerDto){
            // Dto가 비었을 경우
            if(w == null){
                throw new WriterInfoMissingException("ERROR: 작가 정보가 없습니다.");
            }

            WriterDto found = adminRepository.selectWriterOne(w);

            // 조회 결과가 없을 경우
            if(found == null){
                // writerId(WRT_0001)에서 WRT_ 뒤의 숫자만 조회
                String code = selectWriterId().substring(WRT.length());
                // 숫자에 +1을해서 다음 번호를 부여함
                int writerNumber = Integer.parseInt(code)+ONE;
                w.setWriterId(WRT+writerNumber);
                // 지은이 등록
                int result = insertWriter(w);
                if(result == FAIL){
                    throw new WriterCreateFailException("ERROR: 작가 등록에 실패했습니다.", w);
                }
                // writerId 추가
                writerIdList.add(selectWriterId());
            } else {
                // 이미 등록된 작가일 경우
                writerIdList.add(found.getWriterId());
            }
        }
        return writerIdList;
    }
    // 지은이 비교 조회 서비스 로직
    @Override
    public String processPublisher(List<PublisherDto> publisherDto) throws Exception{
        String publisherId = null;

        for(PublisherDto p : publisherDto){
            // 출판사 정보가 없을 경우
            if(p == null){
                throw new PublisherInfoMissingException("ERROR: 출판사 정보가 없습니다.");
            }

            // 사업자 번호 000-00-00000 포멧팅
            // 핸드폰번호 010-0000-0000 포멧팅
            p.setBizRegNo(publisherValidator.getFormatBizRegNo(p.getBizRegNo()));
            p.setPhoneNum(publisherValidator.getFormatPhoneNumber(p.getPhoneNum()));

            PublisherDto found = selectPublisherOne(p);

            // 미등록 출판사일 경우 등록
            if(found == null){

                // publisherId(PUB_0001)에서 PUB_ 뒤의 숫자만 조회
                String code = selectPublisherId().substring(PUB.length());
                // 숫자에 +1을해서 다음 번호를 부여함
                int publisherNumber = Integer.parseInt(code)+ONE;
                // publisherId 추가
                publisherId = PUB+publisherNumber;
                p.setPublisherId(publisherId);
                // 출판사 등록
                int result = insertPublisher(p);
                if(result == FAIL){
                    throw new PublisherCreateFailException("ERROR: 출판사 등록에 실패했습니다.", p);
                }
            } else {
                publisherId = found.getPublisherId();
            }
        }

        return publisherId;
    }

    // 책 비교 조회 서비스 로직
    @Override
    public int processBook(List<BookDto> bookDto, String publisherId) throws Exception{
        for(BookDto b : bookDto){
            if(b == null){
                throw new BookInfoMissingException("책 정보가 없습니다.");
            }

            BookDto found = selectBook(b);
            // 등록된 책이 아닐 경우
            if(found == null){
                b.setPublisherId(publisherId);
                b.setDiscountPercent(b.getDiscountPercent().multiply(new BigDecimal("0.01")));

                // 책 등록
                int result = insertBook(b);
                if(result == FAIL){
                    throw new BookCreateFailException("ERROR: 책 등록에 실패했습니다.", b);
                }
                // 방금 등록한 책의 bookId
                return selectBook(b).getBookId();
            } else {
                // 이미 등록된 책일 경우
                throw new BookAlreadyExistsException("ERROR: 이미 등록된 책입니다.", b);
            }
        }
        // 실행 되면 안되는 코드
        throw new IllegalStateException("processBook() 내부 로직이 잘못되어 도달 불가 영역에 도달했습니다.");
    }

    // writer_book 등록 서비스 로직
    public void processWriterBook(List<String> writerIdList, Integer bookId) throws Exception{
        if(bookId == null){
            throw new BookInfoMissingException("ERROR: 책 정보가 없습니다.");
        }

        for(String writerId : writerIdList){
            if(writerId == null){
                throw new WriterInfoMissingException("ERROR: 작가 정보가 없습니다.");
            }
            // writerBookId(WB_0001)에서 WB_ 뒤의 숫자만 조회
            String code = selectWriterBookId().substring(WB.length());
            // 숫자에 +1을해서 다음 번호를 부여함
            int writerBookNumber = Integer.parseInt(code)+ONE;
            // WB_0001 완성
            String writerBookId = WB+writerBookNumber;

            // writerBook 등록
            WriterBookDto writerBookDto = new WriterBookDto(writerBookId, writerId, bookId);
            int result = insertWriterBook(writerBookDto);
            if(result == FAIL){
                throw new WriterBookCreateFailException("ERROR: 책의 작가 등록에 실패했습니다.", bookId);
            }
        }
    }
    // 책 등록용
    @Override
    public int insertBook(BookDto bookDto) throws Exception{
        return adminRepository.insertBook(bookDto);
    }
    // 출판사 등록용
    @Override
    public int insertPublisher(PublisherDto publisherDto) throws Exception{
        return adminRepository.insertPublisher(publisherDto);
    }
    // 지은이 등록용
    @Override
    public int insertWriter(WriterDto writerDto) throws Exception{
        return adminRepository.insertWriter(writerDto);
    }
    // writer_book 등록용
    @Override
    public int insertWriterBook(WriterBookDto writerBookDto) throws Exception{
        return adminRepository.insertWriterBook(writerBookDto);
    }
    // 지은이 검색용
    @Override
    public List<WriterDto> searchWriter(String keyword) throws Exception{
        if(keyword == null || keyword.isEmpty()){
            throw new WriterInfoMissingException("ERROR: 검색어를 입력해주세요!");
        }

        List<WriterDto> writerDtoList = adminRepository.searchWriter(keyword.trim());

        if(writerDtoList == null || writerDtoList.isEmpty()){
            throw new SearchFailException("ERROR: 검색 결과가 없습니다.", keyword);
        }

        return writerDtoList;
    }

    // 출판사 검색용
    @Override
    public List<PublisherDto> searchPublisher(String keyword) throws Exception{
        if(keyword == null || keyword.isEmpty()){
            throw new PublisherInfoMissingException("ERROR: 검색어를 입력해주세요!");
        }

        List<PublisherDto> publisherDto = adminRepository.searchPublisher(keyword.trim());

        if(publisherDto == null || publisherDto.isEmpty()){
            throw new SearchFailException("ERROR: 검색 결과가 없습니다.", keyword);
        }

        return publisherDto;
    }
}
