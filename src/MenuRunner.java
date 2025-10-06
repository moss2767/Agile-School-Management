import java.util.LinkedHashMap;
import java.util.Scanner;

public class MenuRunner {
    Scanner scanner = new Scanner(System.in);
    static MenuRunner singleObject;

    public void run() {
        InputManagementHandler.runMenuUntilQuit(new LinkedHashMap<>() {{
            System.out.println("Welcome to school");
            put("Show dummy student", () -> System.out.println("This is a student"));
        }});
    }

    public static MenuRunner getInstance(){
        if(singleObject == null){
            singleObject = new MenuRunner();
        }
        return singleObject;
    }
}