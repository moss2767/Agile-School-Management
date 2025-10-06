import java.util.LinkedHashMap;
import java.util.Scanner;

public class MenuRunner {
//    Scanner scanner = new Scanner(System.in);
    static MenuRunner singleObject;

    public void run() {
        InputManagementHandler.runMenuUntilQuit(new LinkedHashMap<>() {{
            System.out.println("Welcome to school");
            put("Show dummy student", () -> System.out.println("This is a student"));
            put("Submenu", () -> InputManagementHandler.runMenuUntilQuit(new LinkedHashMap<>() {{
                put("Option 1", ()-> System.out.println("Option 1"));
                put("Option 2", ()-> System.out.println("Option 2"));
            }}));
        }});
    }

    public static MenuRunner getInstance(){
        if(singleObject == null){
            singleObject = new MenuRunner();
        }
        return singleObject;
    }
}