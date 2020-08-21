package Controller.util.sign;

import io.finbook.Verifier;

public class FinbookSignValidator implements SignValidator {

    @Override
    public String getDecryptedText(byte[] sign) {
        return "";
    }

    @Override
    public String getSignAuthor(byte[] sign) {
        new Verifier(sign).validateSign();
        return "W-5725886";
    }
}
