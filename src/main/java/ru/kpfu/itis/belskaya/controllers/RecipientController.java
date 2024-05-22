package ru.kpfu.itis.belskaya.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import ru.kpfu.itis.belskaya.models.Account;
import ru.kpfu.itis.belskaya.models.Order;
import ru.kpfu.itis.belskaya.models.Review;
import ru.kpfu.itis.belskaya.models.User;
import ru.kpfu.itis.belskaya.models.dto.OrderDto;
import ru.kpfu.itis.belskaya.services.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * @author Elizaveta Belskaya
 */
@Controller
@RequestMapping("/recipient")
@RequiredArgsConstructor
public class RecipientController {

    private final UserService userService;

    private final AuthorService authorService;

    private final OrderService orderService;

    private final AccountService accountService;

    private final RecipientService recipientService;

    @PreAuthorize("hasAuthority('USER')")
    @RequestMapping(value = "/my_authors", method = RequestMethod.GET)
    public String getMyAuthors(ModelMap map,  @AuthenticationPrincipal Account account) {
        User user = userService.findByAccountId(account.getId());
        Optional<List<Order>> recipientOrders = orderService.getOrdersByRecipient(user);
        if (recipientOrders.isPresent()) {
            List<User> authors = recipientOrders.get().stream().map(Order::getAuthor).collect(Collectors.toList());
            List<Account> accounts = authors.stream().map(User::getAccount).collect(Collectors.toList());
            map.put("orders", recipientOrders.get());
            map.put("authors", authors);
            map.put("accounts", accounts);
        } else {
            map.put("orders", null);
        }

        List<Order> uncompletedOrders = orderService.getUncompletedOrdersByAuthor(user);
        List<List<User>> candidatesLists = new ArrayList<>();
        for (Order order : uncompletedOrders) {
            candidatesLists.add(order.getCandidates());
        }
        List<User> approvedUsers = orderService.getRecipientsOfAuthor(user);
        map.put("uncompletedOrders", uncompletedOrders);
        map.put("candidatesLists", candidatesLists);
        map.put("approvedRecipients", approvedUsers);
        return "/views/authorsPage";
    }

    @PreAuthorize("hasAuthority('USER')")
    @RequestMapping(value = "/my_authors", method = RequestMethod.POST)
    public String getAuthorOfRecipientPost(@RequestParam("reject") Long orderId, @AuthenticationPrincipal Account account) {
        User user = userService.findByAccountId(account.getId());
        orderService.cancelRecipient(orderId, user);
        return "redirect:" + MvcUriComponentsBuilder.fromMappingName("RC#getMyAuthors").build() + "?rejected=true";
    }

    @PreAuthorize("hasAuthority('USER')")
    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public String getOrders(ModelMap map,  @AuthenticationPrincipal Account account) {
        User user = userService.findByAccountId(account.getId());
        Optional<List<OrderDto>> orders = orderService.getSuitableOrders(user);
        if (orders.isPresent()) {
            map.put("orders", orders.get());
        } else {
            map.put("orders", null);
        }
        return "/views/newOrdersPage";
    }

    @PreAuthorize("hasAuthority('USER')")
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String getProfile(ModelMap map,  @AuthenticationPrincipal Account account) {
        User user = userService.findByAccountId(account.getId());
        Optional<List<Review>> reviewsList = recipientService.getRatesOfRecipient(user);
        map.put("account", account);
        map.put("user", user);
        map.put("buddiesCount", authorService.getAuthorsCountByRecipient(user));
        map.put("mapSkillToAmount", recipientService.getMapSkillToAuthorsAmount(user));
        if (reviewsList.isPresent()) {
            map.put("reviewsList", reviewsList.get());
        } else {
            map.put("reviewsList", null);
        }
        return "/views/profilePage";
    }

    @PreAuthorize("hasAuthority('USER')")
    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public String addDescriptionToProfile(@RequestParam("description") String description, @AuthenticationPrincipal Account account) {
        User user = userService.findByAccountId(account.getId());
        recipientService.changeDescription(user, description);
        return "redirect:" + MvcUriComponentsBuilder.fromMappingName("RC#getProfile").build();
    }


    @RequestMapping(value = "/profile/delete", method = RequestMethod.POST)
    public String deleteProfile( @AuthenticationPrincipal Account account) {
        accountService.deleteAccount(account);
        return "redirect:" + MvcUriComponentsBuilder.fromMappingName("MC#login").build();
    }


}
