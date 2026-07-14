package tourAgency.tour_agency.exception.user;

public class CannotChangeOwnRoleException extends RuntimeException {

    public CannotChangeOwnRoleException(String message) {
        super(message);
    }
}