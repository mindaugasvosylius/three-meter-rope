package io.github.mindaugasvosylius.threemeterrope.model;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlayerMove {
    private Player player;
    private List<WhiteCard> cards;
}