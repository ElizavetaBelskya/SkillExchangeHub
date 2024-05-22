package ru.kpfu.itis.belskaya.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.belskaya.converters.OrderConverter;
import ru.kpfu.itis.belskaya.models.Order;
import ru.kpfu.itis.belskaya.models.User;
import ru.kpfu.itis.belskaya.models.dto.OrderDto;
import ru.kpfu.itis.belskaya.repositories.OrderRepository;
import ru.kpfu.itis.belskaya.repositories.RecipientRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final int PAGE_SIZE = 3;

    private final OrderRepository orderRepository;
    private final RecipientRepository recipientRepository;

    public Optional<List<Order>> getOrdersByAuthor(User author) {
        return orderRepository.findAllByAuthor(author);
    }

    public void createOrder(Order order) {
        orderRepository.save(order);
    }

    public Optional<List<Order>> getOrdersByPage(int page) {
        return orderRepository.getOrdersByPageNumber((page-1)*PAGE_SIZE, PAGE_SIZE);
    }

    public long getCountOfPages() {
        return orderRepository.count()/PAGE_SIZE + orderRepository.count()%PAGE_SIZE;
    }

    public Optional<Order> findOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public void deleteOrder(Order order) {
        order.setState(Order.State.DELETED);
        orderRepository.save(order);
    }

    public void updateOrder(Order updatedOrder) {
        orderRepository.save(updatedOrder);
    }


    public Optional<List<OrderDto>> getSuitableOrders(User user) {
        OrderConverter orderConverter = new OrderConverter(user);
        Optional<List<Order>> list = orderRepository.findSuitableOrderForRecipient(user.getId());

        return list.map(orders -> orders.stream()
                .map(x -> (OrderDto) orderConverter.convert(x,
                        TypeDescriptor.valueOf(Order.class),
                        TypeDescriptor.valueOf(OrderDto.class)))
                .collect(Collectors.toList()));
    }

    public Optional<List<Order>> getOrdersByRecipient(User user) {
        return orderRepository.findOrdersByRecipient(user);
    }

    public void cancelRecipient(Long orderId, User user) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            order.get().setRecipient(null);
            order.get().getCandidates().remove(user);
            orderRepository.save(order.get());
        }
    }

    public List<Order> getUncompletedOrdersByAuthor(User author) {
        return orderRepository.findOrdersByAuthorWithoutRecipient(author.getId());
    }


    public List<User> getRecipientsOfAuthor(User author) {
        return orderRepository.findRecipientsOfAuthor(author.getId());
    }

    public void setRecipientToOrder(Long orderId, Long recipientId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            Optional<User> recipient = recipientRepository.findById(recipientId);
            if (recipient.isPresent()) {
                order.get().setRecipient(recipient.get());
                orderRepository.save(order.get());
            }
        }
    }

    public void rejectRecipientFromAuthorOrders(User author, Long rejectId) {
        orderRepository.rejectRecipientFromAuthorOrders(author.getId(), rejectId);
    }

}
