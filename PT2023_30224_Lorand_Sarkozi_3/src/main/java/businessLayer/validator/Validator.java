package businessLayer.validator;

/**
 * The {@code Validator} interface defines a contract for validating objects of type {@code T}.
 *
 * @param <T> the type of object to validate
 *
 * @Author Sarkozi Lorand
 */
public interface Validator<T> {

    /**
     * Validates the given object.
     *
     * @param object the object to validate
     * @throws IllegalArgumentException if the object fails validation
     */
    void validate(T object);
}
