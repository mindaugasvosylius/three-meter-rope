package io.github.mindaugasvosylius.threemeterrope.service;

import java.util.ArrayList;
import java.util.List;

import io.github.mindaugasvosylius.threemeterrope.model.BlackCard;
import io.github.mindaugasvosylius.threemeterrope.utils.FileUtils;
import org.springframework.stereotype.Service;

@Service
public class BlackCardService {

    public List<BlackCard> list() {
        return new ArrayList<>(FileUtils.readLines("black_cards.txt").stream()
                .map(line -> BlackCard.builder().text(line).placeholderCount(Math.max(line.split("____").length-1, 1)).build())
                .toList());
    }
}
