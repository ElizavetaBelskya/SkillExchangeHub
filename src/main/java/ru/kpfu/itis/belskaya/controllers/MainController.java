package ru.kpfu.itis.belskaya.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.kpfu.itis.belskaya.converters.RecipientFormToAccountAndRecipientConverter;
import ru.kpfu.itis.belskaya.exceptions.NotFoundException;
import ru.kpfu.itis.belskaya.models.Account;
import ru.kpfu.itis.belskaya.models.Review;
import ru.kpfu.itis.belskaya.models.User;
import ru.kpfu.itis.belskaya.models.forms.LoginForm;
import ru.kpfu.itis.belskaya.models.forms.RegistrationForm;
import ru.kpfu.itis.belskaya.services.AccountService;
import ru.kpfu.itis.belskaya.services.AuthorService;
import ru.kpfu.itis.belskaya.services.RecipientService;
import ru.kpfu.itis.belskaya.services.SkillService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


/**
 * @author Elizaveta Belskaya
 */
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class MainController {


    private final RecipientService recipientService;
    private final AuthorService authorService;
    private final AccountService accountService;
    private final SkillService skillService;

    @RequestMapping(value = "/main")
    public String login(@RequestParam(required = false) String status,
                        @Valid @ModelAttribute("loginForm") LoginForm loginForm,
                        BindingResult result, ModelMap map) {
        if (status != null && status.equals("error")) {
            map.put("error", "User not found");
        } else {
            map.put("error", null);
        }
        return "/views/mainPage";
    }



    @RequestMapping(value = "/profile/{id}", method = RequestMethod.GET)
    public String getRecipientProfile(@PathVariable("id") Long id, ModelMap map) {
        Optional<User> recipient = recipientService.getRecipientById(id);
        if (recipient.isPresent()) {
            Optional<List<Review>> reviewsList = recipientService.getRatesOfRecipient(recipient.get());
            map.put("account", recipient.get().getAccount());
            map.put("user", recipient.get());
            map.put("buddiesCount", authorService.getAuthorsCountByRecipient(recipient.get()));
            map.put("mapSkillToAmount", recipientService.getMapSkillToAuthorsAmount(recipient.get()));
            if (reviewsList.isPresent()) {
                map.put("reviewsList", reviewsList.get());
            } else {
                map.put("reviewsList", null);
            }
            return "/views/profileForAll";
        } else {
            throw new NotFoundException();
        }
    }

    @GetMapping(value = "/forbidden")
    public String forbidden(ModelMap map) {
        map.put("status", HttpStatus.FORBIDDEN.value());
        map.put("alert", "403: you are not allowed to access this page");
        return "/views/errorPage";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerPost(RedirectAttributes redirectAttributes,
                               @ModelAttribute("userForm") @Valid RegistrationForm authorForm,
                               BindingResult result, ModelMap map) {
        if (!result.hasErrors()) {
            RecipientFormToAccountAndRecipientConverter converter = new RecipientFormToAccountAndRecipientConverter();
            Account account = (Account) converter.convert(authorForm, TypeDescriptor.valueOf(RegistrationForm.class), TypeDescriptor.valueOf(Account.class));
            User author = (User) converter.convert(authorForm, TypeDescriptor.valueOf(RegistrationForm.class), TypeDescriptor.valueOf(User.class));
            try {
                assert author != null;
                boolean registered = accountService.registerUser(account, author);
                if (registered) {
                    redirectAttributes.addFlashAttribute("message", "Successfully registered");
                    return "redirect:"+ MvcUriComponentsBuilder.fromMappingName("MC#register").build() + "?status=success";
                } else  {
                    redirectAttributes.addFlashAttribute("message", "Problems are occurred");
                    return "redirect:"+ MvcUriComponentsBuilder.fromMappingName("MC#register").build() + "?status=failed";
                }
            } catch (Exception ex) {
                redirectAttributes.addFlashAttribute("message", ex.getMessage());
                return "redirect:"+ MvcUriComponentsBuilder.fromMappingName("MC#register").build() + "?status=failed";
            }
        }
        map.put("skills", skillService.findAll());
        return "/views/registrationPage";
    }


    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(ModelMap map) {
        map.put("userForm", new RegistrationForm());
        map.put("skills", skillService.findAll());
        return "/views/registrationPage";
    }


}
