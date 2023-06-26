package Engine;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class Object extends ShaderProgram{

    List<Vector3f> vertices;
    int vao;
    int vbo;
    UniformsMap uniformsMap;
    Vector4f color;

    Matrix4f model;

    int vboColor;

    List<Object> childObject;
    List<Float> centerPoint;

    boolean scene = true;
    float x = 0.0f , y = 0.0f, z = 0f;
//    float xs = 0.0f , ys = 1.0f, zs = -3.5f;

    public void setScene(boolean scene) {
        this.scene = scene;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public List<Object> getChildObject() {
        return childObject;
    }

    public void setChildObject(List<Object> childObject) {
        this.childObject = childObject;
    }

    public List<Float> getCenterPoint() {
        updateCenterPoint();
        return centerPoint;
    }

    public void setCenterPoint(List<Float> centerPoint) {
        this.centerPoint = centerPoint;
    }

    List<Vector3f> verticesColor;
    public Object(List<ShaderModuleData> shaderModuleDataList
            , List<Vector3f> vertices
            , Vector4f color) {
        super(shaderModuleDataList);
        this.vertices = vertices;
//        setupVAOVBO();
        uniformsMap = new UniformsMap(getProgramId());
//        uniformsMap.createUniform(
//                "uni_color");
//        uniformsMap.createUniform(
//                "model");
//        uniformsMap.createUniform(
//                "projection");
//        uniformsMap.createUniform(
//                "view");
        uniformsMap.createUniform(
                "uni_color");
        uniformsMap.createUniform(
                "model");
        uniformsMap.createUniform(
                "projection");
        uniformsMap.createUniform(
                "view");
        uniformsMap.createUniform("dirLight.direction");
        uniformsMap.createUniform("dirLight.ambient");
        uniformsMap.createUniform("dirLight.diffuse");
        uniformsMap.createUniform("dirLight.specular");
        for(int i = 0; i < 4; i++){
            uniformsMap.createUniform("pointLights["+i+"].position");
            uniformsMap.createUniform("pointLights["+i+"].ambient");
            uniformsMap.createUniform("pointLights["+i+"].diffuse");
            uniformsMap.createUniform("pointLights["+i+"].specular");
            uniformsMap.createUniform("pointLights["+i+"].constant");
            uniformsMap.createUniform("pointLights["+i+"].linear");
            uniformsMap.createUniform("pointLights["+i+"].quadratic");
        }
        uniformsMap.createUniform("spotLight.position");
        uniformsMap.createUniform("spotLight.direction");
        uniformsMap.createUniform("spotLight.ambient");
        uniformsMap.createUniform("spotLight.diffuse");
        uniformsMap.createUniform("spotLight.specular");
        uniformsMap.createUniform("spotLight.constant");
        uniformsMap.createUniform("spotLight.linear");
        uniformsMap.createUniform("spotLight.quadratic");
        uniformsMap.createUniform("spotLight.cutOff");
        uniformsMap.createUniform("spotLight.outerCutOff");
        uniformsMap.createUniform("viewPos");

        this.color = color;
        model = new Matrix4f().identity();
        childObject = new ArrayList<>();
        centerPoint = Arrays.asList(0f,0f,0f);
//        float x = 0.0f , y = 1.0f, z = -3.5f;
    }
    public Object(List<ShaderModuleData> shaderModuleDataList,
                  List<Vector3f> vertices,
                  List<Vector3f> verticesColor) {
        super(shaderModuleDataList);
        this.vertices = vertices;
        this.verticesColor = verticesColor;
        setupVAOVBOWithVerticesColor();
    }
    public void setupVAOVBO(){
        //set vao
        vao = glGenVertexArrays();
        glBindVertexArray(vao);

        //set vbo
        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER,
                Utils.listoFloat(vertices),
                GL_STATIC_DRAW);
    }
    public void setupVAOVBOWithVerticesColor(){
        //set vao
        vao = glGenVertexArrays();
        glBindVertexArray(vao);

        //set vbo
        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER,
                Utils.listoFloat(vertices),
                GL_STATIC_DRAW);

        //set vboColor
        vboColor = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboColor);
        glBufferData(GL_ARRAY_BUFFER,
                Utils.listoFloat(verticesColor),
                GL_STATIC_DRAW);
    }
    public void drawSetup(Camera camera, Projection projection, int[] mode_light){
        bind();
        uniformsMap.setUniform(
                "uni_color", color);
        uniformsMap.setUniform(
                "model", model);
        uniformsMap.setUniform(
                "view", camera.getViewMatrix());
        uniformsMap.setUniform(
                "projection", projection.getProjMatrix());


        uniformsMap.setUniform("dirLight.direction", new Vector3f(-0.2f, -1.0f, -0.3f));
//        uniformsMap.setUniform("dirLight.ambient", new Vector3f(0.05f, 0.05f, 0.05f));
        if (scene){
            uniformsMap.setUniform("dirLight.ambient", new Vector3f(0.05f, 0.05f, 0.05f));
        } else {
            uniformsMap.setUniform("dirLight.ambient", new Vector3f(0.2f, 0.2f, 0.2f));
        }
        if (scene) {
            uniformsMap.setUniform("dirLight.diffuse", new Vector3f(0.05f, 0.05f, 0.05f));
        } else {
            uniformsMap.setUniform("dirLight.diffuse", new Vector3f(0.6f, 0.6f, 0.6f));
        }
//        uniformsMap.setUniform("dirLight.diffuse", new Vector3f(0.05f, 0.05f, 0.05f));
        uniformsMap.setUniform("dirLight.specular", new Vector3f(0.5f, 0.5f, 0.5f));


        Vector3f[] _pointLightPositions = {
                new Vector3f(0f, 3f, -20.5f),
                new Vector3f(2f, 3f, -12f),
                new Vector3f(2f, 3f, 20.5f),
                new Vector3f(-28f, 3f , -20.5f)
        };

        for(int i = 0; i < _pointLightPositions.length; i++){
            uniformsMap.setUniform("pointLights["+i+"].position", _pointLightPositions[i]);
            uniformsMap.setUniform("pointLights["+i+"].ambient", new Vector3f(0.01f, 0.01f, 0.01f));
            if(scene){
                uniformsMap.setUniform("pointLights["+i+"].diffuse", new Vector3f(0.2f, 0.2f, 0.2f));
            }else{
                uniformsMap.setUniform("pointLights["+i+"].diffuse", new Vector3f(0.4f, 0.4f, 0.4f));
            }
            uniformsMap.setUniform("pointLights["+i+"].specular", new Vector3f(0.5f, 0.5f, 0.5f));
      /*      uniformsMap.setUniform("pointLights["+i+"].constant", 1.0f);
            uniformsMap.setUniform("pointLights["+i+"].linear", 0.1f);
            uniformsMap.setUniform("pointLights["+i+"].quadratic", 0.1f);*/
            if (mode_light[0] == 1) {
                uniformsMap.setUniform("pointLights[" + i + "].constant", 1.0f);
                uniformsMap.setUniform("pointLights[" + i + "].linear", 0.1f);
                uniformsMap.setUniform("pointLights[" + i + "].quadratic", 0.007f);
            } else {
                uniformsMap.setUniform("pointLights[" + i + "].constant", 1.0f);
                uniformsMap.setUniform("pointLights[" + i + "].linear", 0.1f);
                uniformsMap.setUniform("pointLights[" + i + "].quadratic", 100f);
            }
        }

        // spotLight
        uniformsMap.setUniform("spotLight.position", camera.getPosition());
        uniformsMap.setUniform("spotLight.direction", camera.getDirection());
        uniformsMap.setUniform("spotLight.ambient", new Vector3f(0.0f, 0.0f ,0.0f));
        uniformsMap.setUniform("spotLight.diffuse", new Vector3f(1.0f, 1.0f, 1.0f));
        uniformsMap.setUniform("spotLight.specular", new Vector3f(1.0f, 1.0f, 1.0f));
        uniformsMap.setUniform("spotLight.constant", 1.0f);
        uniformsMap.setUniform("spotLight.linear", 0.09f);
        uniformsMap.setUniform("spotLight.quadratic", 0.032f);
        uniformsMap.setUniform("spotLight.cutOff", 0f);
        uniformsMap.setUniform("spotLight.outerCutOff", 0f);

        uniformsMap.setUniform("viewPos", camera.getPosition());

        // Bind VBO
        glEnableVertexAttribArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glVertexAttribPointer(0, 3,
                GL_FLOAT,
                false,
                0, 0);

    }
    public void drawSetupWithVerticesColor(){
        bind();
        // Bind VBO
        glEnableVertexAttribArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glVertexAttribPointer(0, 3,
                GL_FLOAT,
                false,
                0, 0);

        // Bind VBOColor
        glEnableVertexAttribArray(1);
        glBindBuffer(GL_ARRAY_BUFFER, vboColor);
        glVertexAttribPointer(1, 3,
                GL_FLOAT,
                false,
                0, 0);
    }
    public void draw(Camera camera, Projection projection,int[] mode_light){
        drawSetup(camera, projection, mode_light);
        // Draw the vertices
        //optional
        glLineWidth(10); //ketebalan garis
        glPointSize(10); //besar kecil vertex
        //wajib
        //GL_LINES
        //GL_LINE_STRIP
        //GL_LINE_LOOP
        //GL_TRIANGLES
        //GL_TRIANGLE_FAN
        //GL_POINT
        glDrawArrays(GL_TRIANGLES,
                0,
                vertices.size());
        for(Object child:childObject){
            child.draw(camera,projection, mode_light);
        }
    }
    public void drawWithVerticesColor(){
        drawSetupWithVerticesColor();
        // Draw the vertices
        //optional
        glLineWidth(10); //ketebalan garis
        glPointSize(10); //besar kecil vertex
        //wajib
        //GL_LINES
        //GL_LINE_STRIP
        //GL_LINE_LOOP
        //GL_TRIANGLES
        //GL_TRIANGLE_FAN
        //GL_POINT
        glDrawArrays(GL_TRIANGLES,
                0,
                vertices.size());
    }
//    public void drawLine(){
//        drawSetup();
//        // Draw the vertices
//        //optional
//        glLineWidth(1); //ketebalan garis
//        glPointSize(1); //besar kecil vertex
//        glDrawArrays(GL_LINE_STRIP,
//                0,
//                vertices.size());
//    }
    public void addVertices(Vector3f newVertices){
        vertices.add(newVertices);
        setupVAOVBO();
    }
    public void translateObject(Float offsetX,Float offsetY,Float offsetZ){
        model = new Matrix4f().translate(offsetX,offsetY,offsetZ).mul(new Matrix4f(model));
        updateCenterPoint();
        for(Object child:childObject){
            child.translateObject(offsetX,offsetY,offsetZ);
        }
    }
    public void rotateObject(Float degree, Float x,Float y,Float z){
        model = new Matrix4f().rotate(degree,x,y,z).mul(new Matrix4f(model));
        updateCenterPoint();
        for(Object child:childObject){
            child.rotateObject(degree,x,y,z);
        }

    }
    public void updateCenterPoint(){
        Vector3f destTemp = new Vector3f();
        model.transformPosition(0.0f,0.0f,0.0f,destTemp);
        centerPoint.set(0,destTemp.x);
        centerPoint.set(1,destTemp.y);
        centerPoint.set(2,destTemp.z);
        System.out.println(centerPoint.get(0) + " " + centerPoint.get(1));
    }
    public void scaleObject(Float scaleX,Float scaleY,Float scaleZ){
        model = new Matrix4f().scale(scaleX,scaleY,scaleZ).mul(new Matrix4f(model));
        for(Object child:childObject){
            child.translateObject(scaleX,scaleY,scaleZ);
        }
    }

}
