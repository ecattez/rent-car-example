package dev.ecattez.rentme.rent.features.stage;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

public class StageClock extends Clock {

    private final StageContext context;
    private final ZoneId zoneId;

    public StageClock(StageContext context, ZoneId zoneId) {
        this.context = context;
        this.zoneId = zoneId;
    }

    @Override
    public ZoneId getZone() {
        return zoneId;
    }

    @Override
    public Clock withZone(ZoneId zone) {
        return new StageClock(context, zoneId);
    }

    @Override
    public Instant instant() {
        return context.today().atStartOfDay(zoneId).toInstant();
    }
}
