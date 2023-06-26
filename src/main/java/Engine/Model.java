package Engine;


import java.io.File;
import java.io.IOException;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class Model extends Object {

    List<Vector3f> normal = new ArrayList<>();
    List<Vector2f> textures = new ArrayList<Vector2f>();


    ArrayList<ArrayList<Vector3f>> faces = new ArrayList<>(Arrays.asList(
            //v, vt, vn
            new ArrayList<>(), new ArrayList<>(), new ArrayList<>()
    ));
    ArrayList<Vector3f> tempVertices = new ArrayList<>();
    List<Vector2f> tempTexture = new ArrayList<>();
    ArrayList<Vector3f> tempNormal = new ArrayList<>();

    int nbo;
    String filePath;

    public Model(List<ShaderModuleData> shaderModuleDataList, List<Vector3f> vertices, Vector4f color, String filePath) {
        super(shaderModuleDataList, vertices, color);
        this.filePath = filePath;
        createModel();
        setupVAOVBO();
    }

    public void createModel() {
        readModel();
        makeModel();
    }


    public void readModel() {
        File myObj = new File(filePath);
        if (myObj.exists()) {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(filePath));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("v ")) {
                        float x = Float.parseFloat(line.split(" ")[1]);
                        float y = Float.parseFloat(line.split(" ")[2]);
                        float z = Float.parseFloat(line.split(" ")[3]);
                        tempVertices.add(new Vector3f(x, y, z));
                    }
                    else if (line.startsWith("vt ")) {
                        float x = Float.parseFloat(line.split(" ")[1]);
                        float y = Float.parseFloat(line.split(" ")[2]);
                        tempTexture.add(new Vector2f(x, y));
                    }else if (line.startsWith("vn ")) {
                        float x = Float.parseFloat(line.split(" ")[1]);
                        float y = Float.parseFloat(line.split(" ")[2]);
                        float z = Float.parseFloat(line.split(" ")[3]);
                        tempNormal.add(new Vector3f(x, y, z));
                    }else if (line.startsWith("f ")) {
                        Vector3f v = new Vector3f(Float.parseFloat(line.split(" ")[1].split("/")[0]),
                                Float.parseFloat(line.split(" ")[2].split("/")[0]),
                                Float.parseFloat(line.split(" ")[3].split("/")[0]));
                        Vector3f vt = new Vector3f(Float.parseFloat(line.split(" ")[1].split("/")[1]),
                                Float.parseFloat(line.split(" ")[2].split("/")[1]),
                                Float.parseFloat(line.split(" ")[3].split("/")[1]));
                        Vector3f vn = new Vector3f(Float.parseFloat(line.split(" ")[1].split("/")[2]),
                                Float.parseFloat(line.split(" ")[2].split("/")[2]),
                                Float.parseFloat(line.split(" ")[3].split("/")[2]));
//                        System.out.println(vt);
                        faces.get(0).add(v);
                        faces.get(1).add(vt);
                        faces.get(2).add(vn);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void makeModel() {
        for (int i = 0; i < faces.get(0).size(); i++) {
            float vA = faces.get(0).get(i).x - 1;
            float vB = faces.get(0).get(i).y - 1;
            float vC = faces.get(0).get(i).z - 1;

            vertices.add(tempVertices.get((int) vA));
            vertices.add(tempVertices.get((int) vB));
            vertices.add(tempVertices.get((int) vC));

            float vtA = faces.get(1).get(i).x - 1;
            float vtB = faces.get(1).get(i).y - 1;
            textures.add(tempTexture.get((int) vtA));
            textures.add(tempTexture.get((int) vtB));

            float vnA = faces.get(2).get(i).x - 1;
            float vnB = faces.get(2).get(i).y - 1;
            float vnC = faces.get(2).get(i).z - 1;

            normal.add(tempNormal.get((int) vnA));
            normal.add(tempNormal.get((int) vnB));
            normal.add(tempNormal.get((int) vnC));
        }

    }

    public void setupVAOVBO() {
        super.setupVAOVBO();
        //nbo
        //set nbo
        nbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, nbo);
        //mengirim vertices ke shader
        glBufferData(GL_ARRAY_BUFFER,
                Utils.listoFloat(normal),
                GL_STATIC_DRAW);

    }

    @Override
    public void drawSetup(Camera camera, Projection projection, int[] mode_light) {
        super.drawSetup(camera, projection, mode_light);

        //bind nbo
        glEnableVertexAttribArray(1);
        glBindBuffer(GL_ARRAY_BUFFER, nbo);
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
    }
}
