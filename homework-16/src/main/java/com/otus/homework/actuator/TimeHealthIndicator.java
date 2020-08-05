package com.otus.homework.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Component
public class TimeHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        boolean serverIsDown = (LocalDateTime.now().getMinute() % 2) == 0;
        String currentTime = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).format(LocalDateTime.now());

        if (serverIsDown) {
            return Health.down()
                    .status(Status.DOWN)
                    .withDetail("message", String.format("Текущее время %s. Четная минута -> Статус = DOWN", currentTime))
                    .build();
        } else {
            return Health.up()
                    .withDetail("message", String.format("Текущее время %s. Нечетная минута -> Статус = UP", currentTime))
                    .build();
        }
    }
}
