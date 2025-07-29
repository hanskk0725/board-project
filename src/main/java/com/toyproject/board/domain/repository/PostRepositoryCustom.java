package com.toyproject.board.domain.repository;

import com.toyproject.board.domain.entity.Post;
import com.toyproject.board.domain.query.PostSearchCond;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {

    Page<Post> search(PostSearchCond cond, Pageable pageable);


}
