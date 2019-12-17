package com.olbimacoojam.heaven.yutnori;

import com.olbimacoojam.heaven.domain.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class YutnoriParticipant {
    private final User participant;
    private final Color color;

    public YutnoriParticipant(User participant, Color color) {
        this.participant = participant;
        this.color = color;
    }

    public boolean isRightThrower(User thrower) {
        return participant.equals(thrower);
    }
}