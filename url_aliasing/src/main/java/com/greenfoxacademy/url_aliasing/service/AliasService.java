package com.greenfoxacademy.url_aliasing.service;

import com.greenfoxacademy.url_aliasing.model.Alias;
import com.greenfoxacademy.url_aliasing.repository.AliasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AliasService {

    private AliasRepository aliasRepository;

    @Autowired
    public AliasService(AliasRepository aliasRepository) {
        this.aliasRepository = aliasRepository;
    }

    public Boolean isTheAliasInUse(String name) {
        if (aliasRepository.findAliasByAliasName(name) != null) {
            return true;
        } else {
            return false;
        }
    }

    public void saveAlias(Alias alias) {
        aliasRepository.save(alias);
    }

    public String findLinkByAliasName (String alias){
        if (aliasRepository.findAliasByAliasName(alias)!=null){
            return aliasRepository.findAliasByAliasName(alias).getUrl();
        } else {
            return "error";
        }
    }

    public Alias findAliasByAliasName(String aliasName){
        return aliasRepository.findAliasByAliasName(aliasName);
    }

    public List<Alias> findAll(){
        List<Alias> aliasList = new ArrayList<>();
        aliasRepository.findAll().forEach(aliasList::add);
        return aliasList;
    }

    public AliasRepository getAliasRepository() {
        return aliasRepository;
    }
}
