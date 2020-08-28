package com.greenfoxacademy.url_aliasing.repository;

import com.greenfoxacademy.url_aliasing.model.Alias;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AliasRepository extends CrudRepository<Alias, Long> {
    Alias findAliasByAliasName(String alias);


}
