package com.green1st.mandalartWeb.shared_project.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SharedProjectGetRes {
    private long projectId;
    private String title;
    private String pic;
    private Date createDate;
    private int likeCnt;
}
