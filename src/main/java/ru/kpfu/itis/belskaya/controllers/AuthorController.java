package ru.kpfu.itis.belskaya.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.kpfu.itis.belskaya.converters.OrderConverter;
import ru.kpfu.itis.belskaya.models.Account;
import ru.kpfu.itis.belskaya.models.Order;
import ru.kpfu.itis.belskaya.models.User;
import ru.kpfu.itis.belskaya.models.forms.OrderForm;
import ru.kpfu.itis.belskaya.models.forms.RateDto;
import ru.kpfu.itis.belskaya.services.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Elizaveta Belskaya
 */
@Controller
@ComponentScan({"ru.kpfu.itis.belskaya.converters",
        "ru.kpfu.itis.belskaya.repositories",
        "ru.kpfu.itis.belskaya.validators",
        "ru.kpfu.itis.belskaya.services"})
@RequestMapping("/author")
@RequiredArgsConstructor
public class AuthorController {


    private final OrderService orderService;

    private final RecipientService recipientService;

    private final SkillService skillService;

    private final UserService userService;

    private final AccountService accountService;

    @PreAuthorize("hasAuthority('USER')")
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String getProfile(ModelMap map, @AuthenticationPrincipal Account account) {
        User author = userService.findByAccountId(account.getId());
        map.put("account", account);
        map.put("author", author);
        return "/views/authorProfilePage";
    }

    @PreAuthorize("hasAuthority('USER')")
    @RequestMapping(value = "/my_recipients", method = RequestMethod.POST, params = "action=choose")
    public String chooseRecipient(@RequestParam("chooseOrderId") Long chooseOrderId,
                                  @RequestParam("chooseRecipientId") Long chooseRecipientId) {
        orderService.setRecipientToOrder(chooseOrderId, chooseRecipientId);
        return "redirect:" + MvcUriComponentsBuilder.fromMappingName("RC#getMyAuthors").build();
    }

    @PreAuthorize("hasAuthority('USER')")
    @RequestMapping(value = "/my_recipients", method = RequestMethod.POST, params = "action=reject")
    public String rejectRecipient(@RequestParam("rejectId") Long rejectId, @AuthenticationPrincipal Account account) {
        User author = userService.findByAccountId(account.getId());
        orderService.rejectRecipientFromAuthorOrders(author, rejectId);
        return "redirect:" + MvcUriComponentsBuilder.fromMappingName("RC#getMyAuthors").build();
    }

    @PreAuthorize("hasAuthority('USER')")
    @RequestMapping(value = "/my_recipients", method = RequestMethod.POST, params = "action=rated")
    public String rateRecipient(@RequestParam("idRated") Long id, @RequestParam("starCount") int starCount,
                                @RequestParam("comment") String comment,
                                @AuthenticationPrincipal Account account) {
        User author = userService.findByAccountId(account.getId());
        RateDto rateDto = RateDto.builder().recipientId(id).author(author).rating(starCount).comment(comment).build();
        recipientService.changeRating(rateDto);
        return "redirect:" + MvcUriComponentsBuilder.fromMappingName("RC#getMyAuthors").build();
    }

    @PreAuthorize("hasAuthority('USER')")
    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public String deleteProfile(@AuthenticationPrincipal Account account) {
        accountService.deleteAccount(account);
        return "redirect:" + MvcUriComponentsBuilder.fromMappingName("MC#login").build();
    }

    @PreAuthorize("hasAuthority('USER')")
    @RequestMapping(value = "/my_orders", method = RequestMethod.GET)
    public String getAuthorOrders(ModelMap map, @AuthenticationPrincipal Account account) {
        User author = userService.findByAccountId(account.getId());

        Optional<List<Order>> orders = orderService.getOrdersByAuthor(author);
        if (orders.isPresent()) {
            map.put("orders", orders.get());
        } else {
            map.put("orders", null);
        }
        return "/views/ordersPage";
    }

    @PreAuthorize("hasAuthority('USER')")
    @RequestMapping(value = "/new_order", method = RequestMethod.GET)
    public String addOrderGet(ModelMap map) {
        map.put("skills", skillService.findAllTitles());
        map.put("order", new OrderForm());
        return "/views/orderCreationPage";
    }

    @PreAuthorize("hasAuthority('USER')")
    @RequestMapping(value = "/new_order", method = RequestMethod.POST)
    public String addOrder(
            RedirectAttributes redirectAttributes,
            @ModelAttribute("order") @Valid OrderForm orderForm,
            BindingResult result,
            ModelMap map, @AuthenticationPrincipal Account account
    ) {
        map.put("skills", skillService.findAllTitles());
        if (result.hasErrors()) {
            return "/views/orderCreationPage";
        } else {
            if (orderForm != null) {
                User author = userService.findByAccountId(account.getId());
                OrderConverter orderConverter = new OrderConverter(author);
                Order order = (Order) orderConverter.convert(orderForm, TypeDescriptor.valueOf(OrderForm.class), TypeDescriptor.valueOf(Order.class));
                orderService.createOrder(order);
                redirectAttributes.addFlashAttribute("answer", "Successfully created");
            }
            return "redirect:"+ MvcUriComponentsBuilder.fromMappingName("AC#addOrderGet").build();
        }
    }



}
