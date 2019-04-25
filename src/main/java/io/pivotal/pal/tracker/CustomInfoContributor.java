package io.pivotal.pal.tracker;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;


public class CustomInfoContributor implements InfoContributor {
    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("test", "camarillo city ").build();

    }

    public static class TimeEntryHealthIndicator {
    }
}
