package com.green1st.mandalartWeb.project.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ProjectResultDto {
    private long projectId;
    private String userId;
    private String title;
    private String pic;
    private Date createDate;
    private Date updateDate;
}
