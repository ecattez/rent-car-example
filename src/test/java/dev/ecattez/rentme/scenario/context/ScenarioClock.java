package dev.ecattez.rentme.scenario.context;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class ScenarioClock extends Clock {

    private final ZoneId zoneId;
    private LocalDate fixedDate;

    public ScenarioClock() {
        this(LocalDate.now(), ZoneId.of("UTC"));
    }

    public ScenarioClock(LocalDate fixedDate, ZoneId zoneId) {
        this.fixedDate = fixedDate;
        this.zoneId = zoneId;
    }

    @Override
    public ZoneId getZone() {
        return zoneId;
    }

    @Override
    public Clock withZone(ZoneId zone) {
        return new ScenarioClock(fixedDate, zone);
    }

    public Clock fixDateTo(LocalDate fixedDate) {
        this.fixedDate = fixedDate;
        return this;
    }

    @Override
    public Instant instant() {
        return fixedDate.atStartOfDay(zoneId).toInstant();
    }
}
