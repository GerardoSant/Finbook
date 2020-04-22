package Controller.Web.commands;

import Controller.Web.FrontCommand;
import spark.Request;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collection;

import static Controller.util.CFDIVerifier.verify;

public class UploadBillsCommand extends FrontCommand {

    private static String[] resBody={""};

    @Override
    public String execute() throws Exception {
        resBody[0]="";
        request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
        try (InputStream is = request.raw().getPart("sampleFile").getInputStream()) {
            initDirectories();
            processFiles(request);
        } catch( Exception e){
            resBody[0]="No files attached";
        }
        response.body(resBody[0].substring(1));
        return response.body();
    }

    private static void processFiles(Request request) throws Exception {
        Collection<Part> files = request.raw().getParts();
        for (Part file: files){
            processFile(file);
        }
    }

    private static void processFile(Part file) throws Exception {
        String bill = getBillString(file);
        File dateDir = new File("src/main/resources/upload/billsDate/"+getBillDate(bill));
        File UUIDList = new File("src/main/resources/upload/billsDate/"+getBillDate(bill)+"/UUIDList.txt");
        if (dateDir.mkdir()){
            UUIDList.createNewFile();
            if (verify(file)){
                appendTextToFile(UUIDList.getPath(), getBillUIID(bill));
                appendBilltoMainFile(bill);
                resBody[0]=resBody[0]+"|"+file.getSubmittedFileName()+":"+"Yes";
            } else{
                resBody[0]=resBody[0]+"|"+file.getSubmittedFileName()+":"+"Invalid";
            }

        } else{
            if (!billUUIDIsAlready(bill)) {
                if(verify(file)){
                    appendTextToFile(UUIDList.getPath(), getBillUIID(bill));
                    appendBilltoMainFile(bill);
                    resBody[0]=resBody[0]+"|"+file.getSubmittedFileName()+":"+"Yes";
                } else{
                    resBody[0]=resBody[0]+"|"+file.getSubmittedFileName()+":"+"Invalid";
                }
            } else {
                resBody[0]=resBody[0]+"|"+file.getSubmittedFileName()+":"+"Already";
            }
        }
    }

    private static boolean billUUIDIsAlready(String bill) throws IOException {
        BufferedReader br  = new BufferedReader(new FileReader("src/main/resources/upload/billsDate/"+getBillDate(bill)+"/UUIDList.txt"));
        String line;
        boolean isIn=false;
        while ((line = br.readLine()) !=null){
            if(line.indexOf(getBillUIID(bill))!=-1){
                isIn= true;
                break;
            }
        }
        return isIn;
    }

    private static String getBillString(Part file) throws IOException {
        return getFittedXML(file);
    }

    private static String getBillUIID(String bill) {
        return bill.substring(bill.indexOf("UUID")+6, bill.indexOf("\"",bill.indexOf("UUID")+6)).toUpperCase();
    }

    private static String getBillDate(String bill) {
        return bill.substring(bill.indexOf("Fecha=") +7, bill.indexOf("Fecha")+17);
    }


    private static void initDirectories() throws IOException {
        File uploadDir = new File("src/main/resources/upload/xmlList.txt");
        uploadDir.createNewFile(); // Crea xmlList si no existe.
        File billsDateDir = new File("src/main/resources/upload/billsDate");
        billsDateDir.mkdir(); // Crea el directorio billsdate si no existe
    }

    private static void printFileInfo(Part file) {
        System.out.println(file.getSubmittedFileName());
        System.out.println(file.getName());
        System.out.println(file.getSize());
        System.out.println(file.getContentType());
    }



    private static void appendBilltoMainFile(String bill) {
        try {
            Files.write(Paths.get("src/main/resources/upload/xmlList.txt"), (bill+"\n").getBytes(), StandardOpenOption.APPEND);
        }catch (IOException e) {
            //exception handling left as an exercise for the reader
        }
    }

    private static void appendTextToFile(String filePath, String text){
        try {
            Files.write(Paths.get(filePath), (text+"\n").getBytes(), StandardOpenOption.APPEND);
        }catch (IOException e) {
            //exception handling left as an exercise for the reader
        }
    }

    private static String getFittedXML(Part file) throws IOException {
        BufferedReader br  = new BufferedReader(new InputStreamReader(file.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line= br.readLine())!=null){
            sb.append(line.replace("\r","").replace("\n",""));
        }
        return sb.toString();
    }
}
