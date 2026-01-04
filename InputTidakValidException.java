public class InputTidakValidException extends AppException {
    public InputTidakValidException(String field) {
        super("Input " + field + " tidak valid. Coba lagi ya.");
    }
}
