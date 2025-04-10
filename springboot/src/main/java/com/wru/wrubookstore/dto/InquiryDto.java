package com.wru.wrubookstore.dto;

import java.util.Date;
import java.util.Objects;

public class InquiryDto {

    private Integer inquiryId;
    private Integer memberId;
    private String inquiryStatusId;
    private String employeeId;
    private String title;
    private String content;
    private String replyContent;
    private Date regDate;
    private Date modDate;
    private String inquiryStatusName;
    private String memberNickname;
    private String employeeName;

    public InquiryDto() {}

    public InquiryDto(Integer inquiryId, Integer memberId, String inquiryStatusId, String employeeId, String title, String content, String replyContent) {
        this.inquiryId = inquiryId;
        this.memberId = memberId;
        this.inquiryStatusId = inquiryStatusId;
        this.employeeId = employeeId;
        this.title = title;
        this.content = content;
        this.replyContent = replyContent;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof InquiryDto that)) return false;
        return Objects.equals(inquiryId, that.inquiryId) && Objects.equals(memberId, that.memberId) && Objects.equals(inquiryStatusId, that.inquiryStatusId) && Objects.equals(employeeId, that.employeeId) && Objects.equals(title, that.title) && Objects.equals(content, that.content) && Objects.equals(replyContent, that.replyContent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inquiryId, memberId, inquiryStatusId, employeeId, title, content, replyContent);
    }

    @Override
    public String toString() {
        return "InquiryDto{" +
                "inquiryId=" + inquiryId +
                ", memberId=" + memberId +
                ", inquiryStatusId='" + inquiryStatusId + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", replyContent='" + replyContent + '\'' +
                ", regDate=" + regDate +
                ", modDate=" + modDate +
                ", inquiryStatusName='" + inquiryStatusName + '\'' +
                ", memberNickname='" + memberNickname + '\'' +
                ", employeeName='" + employeeName + '\'' +
                '}';
    }

    public Integer getInquiryId() {
        return inquiryId;
    }

    public void setInquiryId(Integer inquiryId) {
        this.inquiryId = inquiryId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getInquiryStatusId() {
        return inquiryStatusId;
    }

    public void setInquiryStatusId(String inquiryStatusId) {
        this.inquiryStatusId = inquiryStatusId;
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

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
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

    public String getInquiryStatusName() {
        return inquiryStatusName;
    }

    public void setInquiryStatusName(String inquiryStatusName) {
        this.inquiryStatusName = inquiryStatusName;
    }

    public String getMemberNickname() {
        return memberNickname;
    }

    public void setMemberNickname(String memberNickname) {
        this.memberNickname = memberNickname;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
}
