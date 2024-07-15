package eapli.base.candidatemanagement.domain;

import jakarta.persistence.Embeddable;

import eapli.framework.domain.model.ValueObject;
import lombok.EqualsAndHashCode;
@Embeddable
@EqualsAndHashCode
public class  AppStatus implements ValueObject, Comparable<AppStatus> {
    private static final long serialVersionUID = 1L;

    private String appStatus;

    private AppStatus(String appStatus) {

        this.appStatus = appStatus;
    }

    @Override
    public int compareTo(AppStatus other) {
        return this.appStatus.compareTo(other.appStatus);
    }
    protected AppStatus() {
        // for ORM only
    }

    public static AppStatus valueOf(String appStatus) {
        return new AppStatus(appStatus);
    }

    @Override
    public String toString() {

        return appStatus;
    }
}
