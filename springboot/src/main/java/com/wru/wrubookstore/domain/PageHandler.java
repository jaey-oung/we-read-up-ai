package com.wru.wrubookstore.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class PageHandler {
    private int totalCnt;       // 게시물 수
    private int totalPage;      // 총 페이지 수
    private int page;           // 현재 페이지
    private int pageSize;       // 페이지 크기
    private int naviSize = 10;  // 페이지 바 크기
    private int beginPage;      // 첫 페이지
    private int endPage;        // 마지막 페이지
    private boolean showPrev;   // 이전 페이지 있는지
    private boolean showNext;   // 다음 페이지 있는지
    private int offset;

    public PageHandler(){}

    public PageHandler(int totalCnt, int page) {
        this(totalCnt, page, 10);
    }

    public PageHandler(int totalCnt, int page, int pageSize){
        this.totalCnt = totalCnt;
        this.page = page;
        this.pageSize = pageSize;

        totalPage = Math.max((int) Math.ceil((double)totalCnt / pageSize), 1);
        beginPage = (page - 1) / naviSize * naviSize + 1;
        endPage = Math.min(beginPage + naviSize - 1, totalPage);
        showPrev = page != 1;
        showNext = page != totalPage;
        offset = pageSize * (page - 1);
    }
}