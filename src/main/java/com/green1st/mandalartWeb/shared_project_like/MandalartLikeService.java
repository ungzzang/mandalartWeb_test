package com.green1st.mandalartWeb.shared_project_like;

import com.green1st.mandalartWeb.shared_project_like.model.ProjectLikeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MandalartLikeService {
    private final MandalartLikeMapper mandalartLikeMapper;

    public int insMandalratLike(ProjectLikeDto dto) {
        return mandalartLikeMapper.insSharedProjectLike(dto);
    }

    public int delMandalratLike(ProjectLikeDto dto) {
        return mandalartLikeMapper.delSharedProjectLike(dto);
    }
}
