package com.toyproject.board.domain.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toyproject.board.domain.entity.Post;
import com.toyproject.board.domain.entity.QPost;
import com.toyproject.board.domain.entity.QUser;
import com.toyproject.board.domain.query.PostSearchCond;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;


public class PostRepositoryImpl implements PostRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public PostRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Post> search(PostSearchCond cond, Pageable pageable) {
        QPost post = QPost.post;
        QUser user = QUser.user;

        List<Post> content = queryFactory
                .selectFrom(post)
                .leftJoin(post.user, user).fetchJoin()
                .where(
                        titleContains(cond.getTitle()),
                        contentContains(cond.getContent())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(post.count())
                .from(post)
                .where(
                        titleContains(cond.getTitle()),
                        contentContains(cond.getContent())
                )
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression titleContains(String title) {
        return (title != null && !title.isBlank()) ? QPost.post.title.contains(title) : null;
    }

    private BooleanExpression contentContains(String content) {
        return (content != null && !content.isBlank()) ? QPost.post.content.contains(content) : null;
    }
}




