package Controller.Web.commands;

import Controller.Web.FrontCommand;
import Controller.util.bill.BillXMLVerifier;
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

    private static String resBody;
    private final String billsDatePath="src/main/resources/upload/billsDate/";
    private final String xmlListPath="src/main/resources/upload/xmlList.txt";

    @Override
    public String execute() throws Exception {
        resBody="";
        request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
        try (InputStream is = request.raw().getPart("sampleFile").getInputStream()) {
            initDirectories();
            processFiles(request);
        } catch( Exception e){
            System.out.println(e);
            resBody="No files attached";
        }
        response.body(resBody);
        return response.body();
    }

    private void processFiles(Request request) throws Exception {
        Collection<Part> files = request.raw().getParts();
        for (Part file: files){
            try{
                processFile(file);
            } catch(Exception e){
                resBody = resBody + "|" + file.getSubmittedFileName() + ":" + "Invalid";
            }

        }
    }

    private void processFile(Part file) throws Exception {
        String bill = getBillString(file);
        File dateDir = new File(billsDatePath+getBillDate(bill));
        File UUIDList = new File(billsDatePath+getBillDate(bill)+"/UUIDList.txt");
        if (dateDir.mkdir()){
            UUIDList.createNewFile();
            addBill(file, bill, UUIDList);
        } else{
            if (!billUUIDIsAlready(bill)) {
                addBill(file, bill, UUIDList);
            } else {
                resBody=resBody+"|"+file.getSubmittedFileName()+":"+"Already";
            }
        }
    }

    private void addBill(Part file, String bill, File UUIDList) throws Exception {
        try{
            if (new BillXMLVerifier().verify(bill)) {
                appendTextToFile(UUIDList.getPath(), getBillUIID(bill));
                appendBilltoMainFile(bill);
                resBody = resBody + "|" + file.getSubmittedFileName() + ":" + "Yes";
            } else {
                resBody = resBody + "|" + file.getSubmittedFileName() + ":" + "Invalid";
            }
        } catch (Exception e){
            resBody = resBody + "|" + file.getSubmittedFileName() + ":" + "Invalid";
        }

    }

    private boolean billUUIDIsAlready(String bill) throws IOException {
        BufferedReader br  = new BufferedReader(new FileReader(billsDatePath+getBillDate(bill)+"/UUIDList.txt"));
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

    private String getBillString(Part file) throws IOException {
        return getFittedXML(file);
    }



    private String getBillUIID(String bill) {
        return bill.substring(bill.indexOf("UUID=\"")+6, bill.indexOf("\"",bill.indexOf("UUID")+6)).toUpperCase();
    }

    private String getBillDate(String bill) {
        return bill.substring(bill.indexOf("Date=") +6, bill.indexOf("Date=")+16);
    }


    private void initDirectories() throws IOException {
        File uploadDir = new File(xmlListPath);
        uploadDir.createNewFile(); // Crea xmlList si no existe.
        File billsDateDir = new File(billsDatePath);
        billsDateDir.mkdir(); // Crea el directorio billsdate si no existe
    }

    private void printFileInfo(Part file) {
        System.out.println(file.getSubmittedFileName());
        System.out.println(file.getName());
        System.out.println(file.getSize());
        System.out.println(file.getContentType());
    }



    private void appendBilltoMainFile(String bill) {
        try {
            Files.write(Paths.get(xmlListPath), (bill+"\n").getBytes(), StandardOpenOption.APPEND);
        }catch (IOException e) {
            //exception handling left as an exercise for the reader
        }
    }

    private void appendTextToFile(String filePath, String text){
        try {
            Files.write(Paths.get(filePath), (text+"\n").getBytes(), StandardOpenOption.APPEND);
        }catch (IOException e) {
            //exception handling left as an exercise for the reader
        }
    }

    private String getFittedXML(Part file) throws IOException {
        BufferedReader br  = new BufferedReader(new InputStreamReader(file.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line= br.readLine())!=null){
            sb.append(line.replace("\r","").replace("\n",""));
        }
        return sb.toString();
    }
}
