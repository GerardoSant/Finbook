package Controller.util;

import mx.bigdata.sat.cfdi.CFDv33;

import javax.servlet.http.Part;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class CFDIVerifier {
    public static boolean verify(Part file) throws Exception{
        Files.copy(file.getInputStream(), Paths.get("src/main/resources/upload/tempFile"), StandardCopyOption.REPLACE_EXISTING);
        CFDv33 cfd = new CFDv33(new FileInputStream(new File("src/main/resources/upload/tempFile")));
        try {
            //cfd.validar();
            cfd.verificar();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
