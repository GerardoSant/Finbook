package Controller.util.sign;

public interface SignValidator {

    public String getDecryptedText(byte[] sign);

    public String getSignAuthor(byte[] sign);

}
