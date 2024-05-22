package ru.kpfu.itis.belskaya.converters;


import lombok.AllArgsConstructor;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import ru.kpfu.itis.belskaya.models.Account;
import ru.kpfu.itis.belskaya.models.Gender;
import ru.kpfu.itis.belskaya.models.User;
import ru.kpfu.itis.belskaya.models.forms.RegistrationForm;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
public class RecipientFormToAccountAndRecipientConverter implements GenericConverter {

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        HashSet<ConvertiblePair> pairSet = new HashSet<>();
        pairSet.add(new ConvertiblePair(RegistrationForm.class, Account.class));
        pairSet.add(new ConvertiblePair(RegistrationForm.class, User.class));
        return pairSet;
    }

    @Override
    public Object convert(Object o, TypeDescriptor typeDescriptor, TypeDescriptor typeDescriptor1) {
        if (RegistrationForm.class.equals(typeDescriptor.getType()) && Account.class.equals(typeDescriptor1.getType())) {
            RegistrationForm registrationForm = (RegistrationForm) o;
            Account account = Account.builder()
                    .email(registrationForm.getEmail())
                    .name(registrationForm.getName())
                    .passwordHash(registrationForm.getPassword())
                    .role(Account.Role.USER)
                    .state(Account.State.ACTIVE)
                    .build();
            return account;
        } else if (RegistrationForm.class.equals(typeDescriptor.getType()) && User.class.equals(typeDescriptor1.getType())) {
            RegistrationForm registrationForm = (RegistrationForm) o;
            User user = User.builder()
                    .email(registrationForm.getEmail())
                    .phone(registrationForm.getPhone())
                    .gender(registrationForm.isGender()? Gender.MALE : Gender.FEMALE)
                    .skillList(registrationForm.getSkills())
                    .build();
            return user;
        }
        throw new IllegalArgumentException();
    }


}
