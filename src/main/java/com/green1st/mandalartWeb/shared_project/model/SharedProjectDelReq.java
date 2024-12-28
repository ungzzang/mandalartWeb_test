package com.green1st.mandalartWeb.shared_project.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SharedProjectDelReq {
    private long projectId;
    private String userId;
}
