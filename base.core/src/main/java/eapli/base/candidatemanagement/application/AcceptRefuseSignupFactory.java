package eapli.base.candidatemanagement.application;


import eapli.framework.util.Utility;

@Utility
public final class AcceptRefuseSignupFactory {
    private AcceptRefuseSignupFactory() {
        // ensure utility
    }

    public static AcceptRefuseSignupRequestController build() {
        // decide and try

        return new AcceptRefuseSignupRequestControllerTxImpl();
        // return new AcceptRefuseSignupRequestControllerEventfullImpl();
    }
}
