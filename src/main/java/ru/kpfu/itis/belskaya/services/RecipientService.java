package ru.kpfu.itis.belskaya.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.belskaya.models.Review;
import ru.kpfu.itis.belskaya.models.Skill;
import ru.kpfu.itis.belskaya.models.User;
import ru.kpfu.itis.belskaya.models.forms.RateDto;
import ru.kpfu.itis.belskaya.repositories.AuthorRepository;
import ru.kpfu.itis.belskaya.repositories.ReviewRepository;
import ru.kpfu.itis.belskaya.repositories.RecipientRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipientService {

    private final RecipientRepository recipientRepository;
    private final ReviewRepository reviewRepository;
    private final AuthorRepository authorRepository;


    public void changeRating(RateDto rateDto) {
        User user = recipientRepository.findById(rateDto.getRecipientId()).get();
        Optional<Review> rate = reviewRepository.findByRecipientAndAuthor(user, rateDto.getAuthor());
        if (rate.isPresent()) {
            rate.get().setRating(rateDto.getRating());
            rate.get().setComment(rateDto.getComment());
            reviewRepository.save(rate.get());
        } else {
            Review newReview = Review.builder()
                    .author(rateDto.getAuthor())
                    .recipient(user)
                    .rating(rateDto.getRating())
                    .comment(rateDto.getComment()).build();
            reviewRepository.save(newReview);
        }
        user.setRating(reviewRepository.changeRating(rateDto.getRecipientId()));
        recipientRepository.save(user);
    }

    public void changeDescription(User user, String description) {
        user.setDescription(description);
        recipientRepository.save(user);
    }

    public Optional<List<Review>> getRatesOfRecipient(User user) {
        return reviewRepository.findAllByRecipient(user);
    }

    public Optional<User> getRecipientById(Long id) {
        return recipientRepository.findById(id);
    }

    public Map<String, Integer> getMapSkillToAuthorsAmount(User user) {
        List<Skill> skillList = user.getSkillList();
        Map<String, Integer> mapSkillToAuthorAmount = new HashMap<>();
        for (Skill skill : skillList) {
            mapSkillToAuthorAmount.put(skill.getTitle(),
                    authorRepository.findBuddyCountBySkill(user.getId(), skill.getTitle()));
        }
        return mapSkillToAuthorAmount;
    }


}

