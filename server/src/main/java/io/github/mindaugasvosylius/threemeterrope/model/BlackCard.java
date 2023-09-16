package io.github.mindaugasvosylius.threemeterrope.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BlackCard {
    private String text;
    private int placeholderCount;
}