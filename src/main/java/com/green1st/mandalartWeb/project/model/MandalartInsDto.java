package com.green1st.mandalartWeb.project.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MandalartInsDto {
    private long projectId;
    private long mandalartId;
    private Long parentId;
    private int depth;
    private int orderId;
}
