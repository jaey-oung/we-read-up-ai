package com.wru.wrubookstore.dto;

import java.util.Date;
import java.util.Objects;

public class NoticeDto {

    private Integer noticeId;
    private String employeeId;
    private String title;
    private String content;
    private Date regDate;
    private Date modDate;
    private int viewCnt;
    private String employeeName;

    public NoticeDto() {    }
    public NoticeDto(Integer noticeId, String employeeId, String title) {
        this.noticeId = noticeId;
        this.employeeId = employeeId;
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof NoticeDto noticeDto)) return false;
        return noticeId == noticeDto.noticeId && Objects.equals(employeeId, noticeDto.employeeId) && Objects.equals(title, noticeDto.title) && Objects.equals(content, noticeDto.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(noticeId, employeeId, title, content);
    }

    @Override
    public String toString() {
        return "NoticeDto{" +
                "noticeId=" + noticeId +
                ", employeeId='" + employeeId + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", regDate=" + regDate +
                ", modDate=" + modDate +
                ", viewCnt=" + viewCnt +
                ", employeeName='" + employeeName + '\'' +
                '}';
    }

    public Integer getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Integer noticeId) {
        this.noticeId = noticeId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public Date getModDate() {
        return modDate;
    }

    public void setModDate(Date modDate) {
        this.modDate = modDate;
    }

    public int getViewCnt() {
        return viewCnt;
    }

    public void setViewCnt(int viewCnt) {
        this.viewCnt = viewCnt;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
}
