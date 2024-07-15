package eapli.base.jobapplicationmanagement.domain;

public enum ScreeningResult {

    ACCEPTED,
    REJECTED,
    WAITING;

    @Override
    public String toString() {
        return switch (this) {
            case ACCEPTED -> "Accepted";
            case REJECTED -> "Rejected";
            case WAITING -> "Waiting";
        };
    }


    public ScreeningResult getResultOf(String value) {
        return switch (value) {
            case "accepted", "Accepted", "ACCEPTED" -> ACCEPTED;
            case "rejected", "Rejected", "REJECTED" -> REJECTED;
            case "waiting", "Waiting", "WAITING" -> WAITING;
            default -> throw new IllegalArgumentException();
        };
    }

}
