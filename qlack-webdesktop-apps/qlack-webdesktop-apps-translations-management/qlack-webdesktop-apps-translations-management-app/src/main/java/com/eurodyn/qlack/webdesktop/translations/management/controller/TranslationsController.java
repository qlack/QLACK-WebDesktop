package com.eurodyn.qlack.webdesktop.translations.management.controller;

import com.eurodyn.qlack.fuse.aaa.dto.UserAttributeDTO;
import com.eurodyn.qlack.fuse.lexicon.dto.LanguageDTO;
import com.eurodyn.qlack.webdesktop.translations.management.dto.TmKeyDTO;
import com.eurodyn.qlack.webdesktop.translations.management.service.TranslationsService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequestMapping("/api")
@RestController
public class TranslationsController {

  private TranslationsService translationsService;

  @Autowired
  public TranslationsController(
      TranslationsService translationsService) {
    this.translationsService = translationsService;
  }

  @GetMapping("/key")
  public Page<TmKeyDTO> getPagesForAllKeys(@RequestParam String page,
      @RequestParam String size,
      @RequestParam List<String> sort) {
    return translationsService.findPagesForAllKeys(page, size, sort);
  }

  @PostMapping("/key/update")
  public void updateTranslationsForKey(@RequestBody TmKeyDTO tmKeyDto) {
    translationsService
        .updateTranslationsForKey(tmKeyDto.getId(), tmKeyDto.getTranslations());
  }

  @GetMapping("/languages/{includeInactive}")
  public List<LanguageDTO> getLanguages(@PathVariable boolean includeInactive) {
    return translationsService.findLanguages(includeInactive);
  }

  @PostMapping("/languages/update")
  public void updateLanguages(@RequestBody List<LanguageDTO> languages) {
    translationsService.updateLanguages(languages);
  }

  @PostMapping("/languages/create")
  public void createLanguage(@ModelAttribute LanguageDTO language) {
    translationsService.createLanguage(language);
  }

  @GetMapping("/translations")
  public Map<String, Map<String, String>> getTranslations(
      @RequestParam String lang) {
    return translationsService.findTranslationsForLocale(lang);
  }

  @GetMapping("/user/attributes/{attributeName}")
  public UserAttributeDTO getUserAttributeByName(@PathVariable String attributeName) {
    return translationsService.findUserAttributeByName(attributeName);
  }

  @GetMapping("/user/logout")
  public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    authentication.setAuthenticated(false);
    new SecurityContextLogoutHandler().logout(request,response,authentication);
    SecurityContextHolder.clearContext();
    request.logout();
    request.getSession().invalidate();
  }


}
