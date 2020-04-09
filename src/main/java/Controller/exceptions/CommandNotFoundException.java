package Controller.exceptions;

public class CommandNotFoundException extends Exception {

    @Override
    public String getMessage(){
        return "The command that was tried to execute does not exist.";
    }
}
