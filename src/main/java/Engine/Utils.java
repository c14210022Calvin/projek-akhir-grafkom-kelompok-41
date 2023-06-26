package Engine;
import org.joml.Vector3f;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Utils {

    public static String readFile(String filePath) {
        String str;
        try {
            str = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException excp) {
            throw new RuntimeException("Error reading file [" + filePath + "]", excp);
        }
        return str;
    }

    public static float[] listoFloat(List<Vector3f> arraylist){
        float[] arr = new float[arraylist.size()*3];
        int index = 0;
        for(int i = 0;i<arraylist.size();i++){
            arr[index++] = arraylist.get(i).x;
            arr[index++] = arraylist.get(i).y;
            arr[index++] = arraylist.get(i).z;
        }
        return arr;
    }

    public static int[] listoInt(List<Integer> arraylist){
        int[] arr = new int[arraylist.size()];
        for(int i = 0;i<arraylist.size();i++){
            arr[i] = arraylist.get(i);
        }
        return arr;
    }

    public static String loadResource(String fileName) throws Exception {
        String result;
        System.out.println(fileName);
        try (InputStream in = Utils.class.getClass().getResourceAsStream(fileName);
             Scanner scanner = new Scanner(in, "UTF-8")) {
            result = scanner.useDelimiter("\\A").next();
        }
        return result;
    }

    public static List<String> readAllLines(String fileName) {
        List<String> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(Class.forName(Utils.class.getName()).getResourceAsStream(fileName)))) {
            String line;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }


}
