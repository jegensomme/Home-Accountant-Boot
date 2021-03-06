package ru.jegensomme.homeaccountant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import ru.jegensomme.homeaccountant.util.ValidationUtil;

// https://stackoverflow.com/questions/42781264/multiple-base-repositories-in-spring-data-jpa
@NoRepositoryBean
public interface BaseRepository<T> extends JpaRepository<T, Integer> {
    default T getExisted(int id) {
        return ValidationUtil.checkNotFoundWithId(findById(id), id);
    }
}