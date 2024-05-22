package ru.kpfu.itis.belskaya.converters;

import lombok.AllArgsConstructor;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import ru.kpfu.itis.belskaya.models.Gender;
import ru.kpfu.itis.belskaya.models.Order;
import ru.kpfu.itis.belskaya.models.Skill;
import ru.kpfu.itis.belskaya.models.User;
import ru.kpfu.itis.belskaya.models.dto.OrderDto;
import ru.kpfu.itis.belskaya.models.forms.OrderForm;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Elizaveta Belskaya
 */

@AllArgsConstructor
public class OrderConverter implements GenericConverter {

    private User author;

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        HashSet<ConvertiblePair> pairSet = new HashSet<>();
        pairSet.add(new ConvertiblePair(OrderForm.class, Order.class));
        pairSet.add(new ConvertiblePair(Order.class, OrderDto.class));
        return pairSet;
    }

    @Override
    public Object convert(Object o, TypeDescriptor typeDescriptor, TypeDescriptor typeDescriptor1) {
        if (OrderForm.class.equals(typeDescriptor.getType()) && Order.class.equals(typeDescriptor1.getType())) {
            OrderForm src = (OrderForm) o;
            return Order.builder()
                    .description(src.getDescription())
                    .skill(src.getSkill())
                    .minRating(src.getRating()? 4.0f:0.0f)
                    .recipientGender(Enum.valueOf(Gender.class, src.getGender().toUpperCase()))
                    .author(author)
                    .build();
        } else if (Order.class.equals(typeDescriptor.getType()) && OrderDto.class.equals(typeDescriptor1.getType())) {
            Order order = (Order) o;
            return OrderDto.builder().id(order.getId())
                    .creationDate(order.getCreationDate())
                    .candidates(order.getCandidates().stream().mapToLong(User::getId).boxed().collect(Collectors.toList()))
                    .authorId(order.getAuthor().getId())
                    .authorSkills(order.getAuthor().getSkillList().stream().map(Skill::getTitle).collect(Collectors.toList()))
                    .recipientId(order.getRecipient() == null ? null : order.getRecipient().getId())
                    .description(order.getDescription())
                    .minRating(order.getMinRating())
                    .recipientGender(order.getRecipientGender().toString())
                    .skill(order.getSkill()).build();
        } else if (OrderDto.class.equals(typeDescriptor.getType()) && Order.class.equals(typeDescriptor1.getType())) {
            OrderDto orderDto = (OrderDto) o;
            return Order.builder().author(author)
                    .description(orderDto.getDescription())
                    .minRating(orderDto.getMinRating())
                    .recipientGender(Gender.valueOf(orderDto.getRecipientGender()))
                    .skill(orderDto.getSkill()).build();
        }
        throw new IllegalArgumentException();
    }


}
