package com.renaissance.core.jpa;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.jpa.repository.support.QuerydslJpaRepository;
import org.springframework.data.querydsl.EntityPathResolver;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.data.repository.support.PageableExecutionUtils;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

/**
 * @author Wilson
 *
 * @param <T>
 * @param <ID>
 */
@SuppressWarnings("deprecation")
public class ExtenedRepositoryImpl<T, ID extends Serializable>
        extends QuerydslJpaRepository<T, ID> implements BaseRepository<T, ID> {

    private static final EntityPathResolver DEFAULT_ENTITY_PATH_RESOLVER = SimpleEntityPathResolver.INSTANCE;

    private final EntityPath<T> path;
    private final PathBuilder<T> builder;
    private final Querydsl querydsl;

    public ExtenedRepositoryImpl(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
        this(entityInformation, entityManager, DEFAULT_ENTITY_PATH_RESOLVER);
    }

    public ExtenedRepositoryImpl(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager, EntityPathResolver resolver) {

        super(entityInformation, entityManager);
        this.path = resolver.createPath(entityInformation.getJavaType());
        this.builder = new PathBuilder(this.path.getType(), this.path.getMetadata());
        this.querydsl = new Querydsl(entityManager, this.builder);
    }

    @Override
    public List<T> findAll(Predicate predicate, Expression<?> groupBy) {
        return createQuery(predicate).select(path).groupBy(groupBy).fetch();
    }

    @Override
    public List<T> findAll(Predicate predicate, Expression<?> groupBy, Sort sort) {
        return querydsl.applySorting(sort, createQuery(predicate).select(path).groupBy(groupBy)).fetch();
    }

    @Override
    public Page<T> findAll(Predicate predicate, Expression<?> groupBy, Pageable pageable) {

        JPQLQuery countQuery = createCountQuery(predicate).select(path).groupBy(groupBy);

        JPQLQuery query = querydsl.applyPagination(pageable, createQuery(predicate).select(path).groupBy(groupBy));

        return PageableExecutionUtils.getPage(query.fetch(), pageable, countQuery::fetchCount);
    }

}