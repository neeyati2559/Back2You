package back2you;

import java.time.LocalDate;

public class Validator {

    public static String requireNonEmpty(String value, String fieldName) throws InvalidInputException {
        if (value == null || value.isBlank()) {
            throw new InvalidInputException(fieldName + " cannot be empty.");
        }
        return value.trim();
    }

    public static String requireValidContact(String contact) throws InvalidInputException {
        String c = (contact == null) ? "" : contact.trim();
        boolean isPhone = c.matches("\\d{10}");
        boolean isEmail = c.matches("[\\w.+-]+@[\\w-]+\\.[\\w.]+");
        if (!isPhone && !isEmail) {
            throw new InvalidInputException("Contact must be a 10-digit phone number or a valid email.");
        }
        return c;
    }

    public static LocalDate requireNotFuture(LocalDate date) throws InvalidInputException {
        if (date.isAfter(LocalDate.now())) {
            throw new InvalidInputException("Date cannot be in the future.");
        }
        return date;
    }
}
