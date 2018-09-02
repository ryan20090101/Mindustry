package io.anuke.mindustry.net;

/** Represents a cancelled event */
public class Cancelled extends RuntimeException {
    public String reason;

    public Cancelled(String reason) {
        this.reason = reason;
    }
}
