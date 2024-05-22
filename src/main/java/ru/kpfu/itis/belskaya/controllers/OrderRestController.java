package ru.kpfu.itis.belskaya.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.kpfu.itis.belskaya.controllers.api.OrderApi;
import ru.kpfu.itis.belskaya.converters.OrderConverter;
import ru.kpfu.itis.belskaya.models.Account;
import ru.kpfu.itis.belskaya.models.Order;
import ru.kpfu.itis.belskaya.models.Skill;
import ru.kpfu.itis.belskaya.models.User;
import ru.kpfu.itis.belskaya.models.dto.OrderDto;
import ru.kpfu.itis.belskaya.services.OrderService;
import ru.kpfu.itis.belskaya.services.SkillService;
import ru.kpfu.itis.belskaya.services.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderRestController implements OrderApi {


    private final OrderService orderService;
    private final UserService authorService;
    private final UserService recipientService;
    private final SkillService skillService;

    public ResponseEntity<OrderDto> getOrder(Long id) {
        Order order = orderService.findOrderById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Order not found"
        ));

        OrderConverter orderConverter = new OrderConverter(null);
        OrderDto orderDto = (OrderDto) orderConverter.convert(order, TypeDescriptor.valueOf(Order.class), TypeDescriptor.valueOf(OrderDto.class));
        return ResponseEntity.ok(orderDto);
    }

    @Override
    public ResponseEntity<String> addOrder(Account account, OrderDto orderDto) {
        User author = authorService.findByAccountId(account.getId());
        log.info(orderDto.toString());
        OrderConverter converter = new OrderConverter(author);
        Order updatedOrder = (Order) converter.convert(orderDto, TypeDescriptor.valueOf(OrderDto.class), TypeDescriptor.valueOf(Order.class));
        assert updatedOrder != null;
        updatedOrder.setRecipient(null);
        log.info(updatedOrder.toString());
        orderService.updateOrder(updatedOrder);
        return ResponseEntity.accepted().build();
    }

    @Override
    public ResponseEntity<String> deleteOrder(Account account, Long id) {
        Order order = orderService.findOrderById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Order not found"
        ));
        if (order.getAuthor().getAccount().equals(account)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You have no rights to delete this order");
        }
        orderService.deleteOrder(order);
        return ResponseEntity.ok("Deleted");
    }

    @Override
    public ResponseEntity<String> updateOrder(Account account, Long id, @Valid OrderDto orderDto) {
        User author = authorService.findByAccountId(account.getId());
        OrderConverter converter = new OrderConverter(author);
        Order updatedOrder = (Order) converter.convert(orderDto, TypeDescriptor.valueOf(OrderDto.class), TypeDescriptor.valueOf(Order.class));
        Order orderById = orderService.findOrderById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Order not found"
        ));
        if (orderById.getAuthor().getAccount().equals(account)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You have no rights to delete this order");
        }
        updatedOrder.setId(orderById.getId());
        updatedOrder.setCreationDate(orderById.getCreationDate());
        updatedOrder.setCandidates(orderById.getCandidates());
        updatedOrder.setRecipient(orderById.getRecipient());
        updatedOrder.setState(orderById.getState());
        updatedOrder.setAuthor(orderById.getAuthor());
        orderService.updateOrder(updatedOrder);
        return ResponseEntity.ok("Updated");
    }

    @Override
    public ResponseEntity<String> addRecipientToOrder(Long id, Account account) {
        User user = recipientService.findByAccountId(account.getId());
        Order order = orderService.findOrderById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Order not found"
        ));
        if (!order.getCandidates().contains(user)) {
            order.getCandidates().add(user);
            orderService.updateOrder(order);
        }
        return ResponseEntity.ok("Updated");
    }

    @Override
    public ResponseEntity<List<OrderDto>> getAllOrders(Account account) {
        User author = authorService.findByAccountId(account.getId());
        OrderConverter orderConverter = new OrderConverter(author);
        List<Order> orders = orderService.getOrdersByAuthor(author).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Orders not found"
        ));
        List<OrderDto> orderDtoList = orders.stream()
                .map(o -> (OrderDto) orderConverter.convert(o, TypeDescriptor.valueOf(Order.class), TypeDescriptor.valueOf(OrderDto.class))).collect(Collectors.toList());
        return ResponseEntity.ok(orderDtoList);
    }

    @Override
    public ResponseEntity<List<Skill>> getAllSkills() {
        List<Skill> skills = skillService.findAll();
        return ResponseEntity.ok(skills);
    }


}
