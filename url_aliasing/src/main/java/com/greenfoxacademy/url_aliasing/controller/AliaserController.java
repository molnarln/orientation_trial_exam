package com.greenfoxacademy.url_aliasing.controller;

import com.greenfoxacademy.url_aliasing.model.Alias;
import com.greenfoxacademy.url_aliasing.model.AliasToDelet;
import com.greenfoxacademy.url_aliasing.service.AliasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.NullLiteral;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class AliaserController {

    private AliasService aliasService;

    @Autowired
    public AliaserController(AliasService aliasService) {
        this.aliasService = aliasService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showDefaultMainPage(Model model) {
        model.addAttribute("alias", new Alias());
        return "main";
    }

    @RequestMapping(value = "/save-link", method = RequestMethod.POST)
    public String saveLink(@ModelAttribute("alias") Alias alias, Model model) {
        if (!aliasService.isTheAliasInUse(alias.getAliasName())) {
            aliasService.saveAlias(alias);
            model.addAttribute("success", true);
            model.addAttribute("aliasname", alias.getAliasName());
            model.addAttribute("aliascode", alias.getCode());
            model.addAttribute("alias", new Alias());

            return "main";
        }
        model.addAttribute("error", true);
        model.addAttribute("alias", alias);
        return "main";
    }

    @RequestMapping(value = "/a/{alias}", method = RequestMethod.GET)
    public Object redirectToLink(@PathVariable("alias") String alias) {
        if (aliasService.findLinkByAliasName(alias).equalsIgnoreCase("error")) {
            ResponseEntity responseEntity = new ResponseEntity("Error 404 :(", null, HttpStatus.NOT_FOUND);
            return responseEntity;
        } else {
            Alias aliasToIncrement = aliasService.findAliasByAliasName(alias);
            aliasToIncrement.incrementHitcount();
            aliasService.saveAlias(aliasToIncrement);
            return "redirect:http://www." + aliasService.findLinkByAliasName(alias);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/api/links", method = RequestMethod.GET)
    public Object sendAllEntries() {
        return aliasService.findAll();
    }

    @ResponseBody
    @RequestMapping(value = "/api/links/{id}", method = RequestMethod.DELETE)
    public Object deleteAlias(@PathVariable("id") Long id, @RequestBody AliasToDelet secretCode) {

        if (!aliasService.getAliasRepository().existsById(id)) {
            ResponseEntity responseEntity = new ResponseEntity("404", null, HttpStatus.NOT_FOUND);
            return responseEntity;

        } else if (secretCode.getSecretCode().equals(aliasService.getAliasRepository().findById(id).get().getCode())) {
            Alias aliasToDelete = aliasService.getAliasRepository().findById(id).get();
            aliasService.getAliasRepository().deleteById(aliasToDelete.getId());
            ResponseEntity responseEntity = new ResponseEntity("202", null, HttpStatus.ACCEPTED);
            return responseEntity;

        } else {
            ResponseEntity responseEntity = new ResponseEntity("403", null, HttpStatus.FORBIDDEN);
            return responseEntity;
        }
    }
}
