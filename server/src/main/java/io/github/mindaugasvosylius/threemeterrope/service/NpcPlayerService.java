package io.github.mindaugasvosylius.threemeterrope.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.github.mindaugasvosylius.threemeterrope.utils.FileUtils;
import io.github.mindaugasvosylius.threemeterrope.model.NpcPlayer;
import org.springframework.stereotype.Service;

@Service
public class NpcPlayerService {

    public List<NpcPlayer> randomizedList(int amount) {
        final List<String> npcNames = FileUtils.readLines("names.txt");
        Collections.shuffle(npcNames);
        final List<NpcPlayer> npcPlayers = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            npcPlayers.add(NpcPlayer.npcBuilder()
                    .cards(new ArrayList<>())
                    .name(npcNames.get(i))
                    .points(0)
                    .build());
        }
        return npcPlayers;
    }
}
