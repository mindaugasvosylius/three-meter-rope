package io.github.mindaugasvosylius.threemeterrope.service;

import java.util.ArrayList;
import java.util.List;

import io.github.mindaugasvosylius.threemeterrope.utils.FileUtils;
import io.github.mindaugasvosylius.threemeterrope.model.WhiteCard;
import org.springframework.stereotype.Service;

@Service
public class WhiteCardService {

    public List<WhiteCard> list() {
        return new ArrayList<>(FileUtils.readLines("white_cards.txt").stream()
                .map(line -> WhiteCard.builder().text(line).build())
                .toList());
    }
}
