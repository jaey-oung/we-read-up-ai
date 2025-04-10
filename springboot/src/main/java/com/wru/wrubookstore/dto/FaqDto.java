package com.wru.wrubookstore.dto;

import java.util.Date;
import java.util.Objects;

public class FaqDto {

    private Integer faqId;
    private String employeeId;
    private String title;
    private String content;
    private Date regDate;
    private Date modDate;
    private String name;

    public FaqDto() {}

    public FaqDto(Integer faqId, String employeeId, String title, String content) {
        this.faqId = faqId;
        this.employeeId = employeeId;
        this.title = title;
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof FaqDto faqDto)) return false;
        return Objects.equals(faqId, faqDto.faqId) && Objects.equals(employeeId, faqDto.employeeId) && Objects.equals(title, faqDto.title) && Objects.equals(content, faqDto.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(faqId, employeeId, title, content);
    }

    @Override
    public String toString() {
        return "FaqDto{" +
                "faqId=" + faqId +
                ", employeeId='" + employeeId + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", regDate=" + regDate +
                ", modDate=" + modDate +
                ", name='" + name + '\'' +
                '}';
    }

    public Integer getFaqId() {
        return faqId;
    }

    public void setFaqId(Integer faqId) {
        this.faqId = faqId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
