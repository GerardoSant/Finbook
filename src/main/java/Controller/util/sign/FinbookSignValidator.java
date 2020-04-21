package Controller.util.sign;

public class FinbookSignValidator implements SignValidator {

    @Override
    public String getDecryptedText(byte[] sign) {
        return "";
    }

    @Override
    public String getSignAuthor(byte[] sign) {
        return "E-5756930";
    }
}
