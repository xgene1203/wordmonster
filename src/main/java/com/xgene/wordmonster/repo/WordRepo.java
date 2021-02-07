package com.xgene.wordmonster.repo;

import com.xgene.wordmonster.model.Category;
import com.xgene.wordmonster.model.entity.WordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Collection;
import java.util.List;

@RepositoryRestResource
public interface WordRepo extends JpaRepository<WordEntity, Long> {

    @Override
    @RestResource(exported = false)
    void delete(WordEntity w);

    List<WordEntity> findWordEntitiesByCategoryIn(Collection<String> category);

    Collection<WordEntity> findByCategory(@Param("category") Category category);

}
