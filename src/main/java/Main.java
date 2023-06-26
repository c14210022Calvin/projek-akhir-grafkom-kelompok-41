import Engine.*;
import Engine.Object;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL30.*;

public class Main {
    private Window window =
            new Window
//    (800,800,"Hello World");
    (1820,980,"Project Akhir");
//    (1820,980,"Project Akhir");
    private ArrayList<Object> objects
            = new ArrayList<>();

    private MouseInput mouseInput;
    Projection projection = new Projection(window.getWidth(),window.getHeight());
    Camera camera = new Camera();
    Model model;
    int[] mode_light;
    int delayCounter = 0;

    boolean delay = false;

    boolean malam = true;

    public void init(){
        window.init();
        GL.createCapabilities();
        mouseInput = window.getMouseInput();
        camera.setPosition(0,1f,1.0f);
        camera.setRotation((float)Math.toRadians(0.0f),(float)Math.toRadians(0.0f));
        mode_light = new int[]{1};

        objects.add(new Model(
                Arrays.asList(
                        //shaderFile lokasi menyesuaikan objectnya
                        new ShaderProgram.ShaderModuleData
                                ("resources/shaders/scene.vert"
                                        , GL_VERTEX_SHADER),
                        new ShaderProgram.ShaderModuleData
                                ("resources/shaders/scene.frag"
                                        , GL_FRAGMENT_SHADER)
                ),
                new ArrayList<>(),
//                new Vector4f(0.8f, 0.4f, 0.9f, 0f), "resources/Models/Map/csgo_mirage.obj"
                new Vector4f(0.8f, 0.8f, 0.8f, 0f), "resources/Models/Map/street_house.obj"
        ));
        objects.add(new Model(
                Arrays.asList(
                        //shaderFile lokasi menyesuaikan objectnya
                        new ShaderProgram.ShaderModuleData
                                ("resources/shaders/scene.vert"
                                        , GL_VERTEX_SHADER),
                        new ShaderProgram.ShaderModuleData
                                ("resources/shaders/scene.frag"
                                        , GL_FRAGMENT_SHADER)
                ),
                new ArrayList<>(),
//                new Vector4f(0.8f, 0.4f, 0.9f, 0f), "resources/Models/Map/csgo_mirage.obj"
                new Vector4f(1f, 1f, 1f, 1f), "resources/Models/Ball/ball.obj"
        ));
        objects.get(1).scaleObject(0.3f,0.3f,0.3f);

    }

    public void input(){
        float move = 0.05f;

        /*if (window.isKeyPressed(GLFW_KEY_W)) {
            camera.moveForward(move);
        }
        if (window.isKeyPressed(GLFW_KEY_S)) {
            camera.moveBackwards(move);
        }
        if (window.isKeyPressed(GLFW_KEY_A)) {
            camera.moveLeft(move);
        }
        if (window.isKeyPressed(GLFW_KEY_D)) {
            camera.moveRight(move);
        }*/
        if (window.isKeyPressed(GLFW_KEY_L)) {
            malam = true;
            for (Object object: objects){
                object.setScene(malam);
            }
        }/*
        if (window.isKeyPressed(GLFW_KEY_7)) {
            for (Object object: objects){
                object.setX(-28f);
                object.setY(3f);
                object.setZ(-20.5f);
                object.setScene(malam);
            }
        }
        if (window.isKeyPressed(GLFW_KEY_6)) {
            for (Object object: objects){
                object.setX(0f);
                object.setY(3f);
                object.setZ(0.0f);
                object.setScene(malam);
            }
        }*/

        if (window.isKeyPressed(GLFW_KEY_K)) {
            malam = false;
            for (Object object: objects){
                object.setScene(malam);
            }
        }
        /*
        if (window.isKeyPressed(GLFW_KEY_W)) {
            camera.moveForward(move);
        }
        if (window.isKeyPressed(GLFW_KEY_S)) {
            camera.moveBackwards(move);
        }
        if (window.isKeyPressed(GLFW_KEY_A)) {
            camera.moveLeft(move);
        }
        if (window.isKeyPressed(GLFW_KEY_D)) {
            camera.moveRight(move);
        }

        if (window.isKeyPressed(GLFW_KEY_Q)) {
            camera.moveLeft(move/6);
            camera.addRotation(0, move/6);
        }

        if (window.isKeyPressed(GLFW_KEY_E)) {
            camera.setPosition(0,0.25f,0.5f);
            camera.setRotation((float)Math.toRadians(0.0f),(float)Math.toRadians(0.0f));
        }

        if (window.isKeyPressed(GLFW_KEY_SPACE)) {
            camera.moveUp(move);
            camera.addRotation(move/4, 0);

        }*/


/*
// Untuk street_house
        if (camera.getPosition().z > -2 && camera.getPosition().z < 2) {

            */
        /*if (camera.getPosition().x > -0.3 && camera.getPosition().x < 0.3 && camera.getPosition().z < -1.5) {
                System.out.println("Aman");
            }*//*
            if (camera.getPosition().x > -2 && camera.getPosition().x < 2) {
                if (window.isKeyPressed(GLFW_KEY_T)) {
                    camera.moveForward(move);
                }
                if (window.isKeyPressed(GLFW_KEY_G)) {
                    camera.moveBackwards(move);
                }
                if (window.isKeyPressed(GLFW_KEY_F)) {
                    camera.addRotation(0, -move * 2f);
                }
                if (window.isKeyPressed(GLFW_KEY_H)) {
                    camera.addRotation(0, move * 2f);
                }
            } else {

                if(camera.getDirection().x < 0) {
                    camera.moveBackwards(0.02f);
                } else if(camera.getDirection().x > 0 && camera.getDirection().z > -0.1  && camera.getDirection().z < 0.1) {
                    camera.moveBackwards(0.02f);
                } else {
                    camera.moveForward(0.02f);
                }
            }
        } else {
            if(camera.getDirection().x < 1 && camera.getDirection().z < 0) {
                camera.moveBackwards(0.02f);
            } else if(camera.getDirection().x > 0) {
                camera.moveForward(0.02f);
            }
        }*/
        if (window.isKeyPressed(GLFW_KEY_T)) {
            camera.moveForward(move);
        }
        if (window.isKeyPressed(GLFW_KEY_G)) {
            camera.moveBackwards(move);
        }
        if (window.isKeyPressed(GLFW_KEY_F)) {
            camera.addRotation(0, -move/2);
        }
        if (window.isKeyPressed(GLFW_KEY_H)) {
            camera.addRotation(0, move/2);
        }

        if (window.isKeyPressed(GLFW_KEY_W)) {
            camera.moveForward(move);
            objects.get(1).translateObject(0f,0f, -move);
        }
        if (window.isKeyPressed(GLFW_KEY_S)) {
            camera.moveBackwards(move);
            objects.get(1).translateObject(0f,0f, move);
        }
        if (window.isKeyPressed(GLFW_KEY_A)) {
            camera.moveLeft(move);
            objects.get(1).translateObject(-move,0f, 0f);
        }
        if (window.isKeyPressed(GLFW_KEY_D)) {
            camera.moveRight(move);
            objects.get(1).translateObject(move,0f, 0f);
        }

        if (window.isKeyPressed(GLFW_KEY_Q)) {
            camera.moveLeft(move/6);
            camera.addRotation(0, move/6);
        }

        if (window.isKeyPressed(GLFW_KEY_E)) {
            camera.setPosition(0,1f,0.5f);
            camera.setRotation((float)Math.toRadians(0.0f),(float)Math.toRadians(0.0f));
        }

        if (window.isKeyPressed(GLFW_KEY_SPACE)) {
            camera.moveUp(move/1.5f);
            camera.addRotation(move/32, 0);
        }

//        System.out.println(camera.getPosition().x + "     " + camera.getPosition().y + "      " + camera.getPosition().z);
        System.out.println(camera.getDirection().x + "      " + camera.getDirection().z);

        if (camera.getPosition().z > -19 && camera.getPosition().z < 19) {

        } else {
            if(camera.getDirection().x < 0.05 && camera.getDirection().z < 0) {
                camera.moveBackwards(0.2f);
            } else if(camera.getDirection().z > 0) {
                camera.moveForward(0.2f);
            }
        }

        if (camera.getPosition().x > -26 && camera.getPosition().x < 26) {

        } else {
            if(camera.getDirection().x < 0.05 && camera.getDirection().z < 0.04) {
                camera.moveBackwards(0.2f);
            } else if(camera.getDirection().z > 0) {
                camera.moveForward(0.2f);
            }
        }
/*
// Untuk street_house
        if (camera.getPosition().z > -20.5 && camera.getPosition().z < 20.5) {

            if (camera.getPosition().x > -0.3 && camera.getPosition().x < 0.3 && camera.getPosition().z < -1.5) {
                System.out.println("Aman");
            }
            if (camera.getPosition().x > -2 && camera.getPosition().x < 2) {
                if (window.isKeyPressed(GLFW_KEY_T)) {
                    camera.moveForward(move);
                }
                if (window.isKeyPressed(GLFW_KEY_G)) {
                    camera.moveBackwards(move);
                }
                if (window.isKeyPressed(GLFW_KEY_F)) {
                    camera.addRotation(0, -move * 2f);
                }
                if (window.isKeyPressed(GLFW_KEY_H)) {
                    camera.addRotation(0, move * 2f);
                }
            } else {

                if(camera.getDirection().x < 0) {
                    camera.moveBackwards(0.02f);
                } else if(camera.getDirection().x > 0 && camera.getDirection().z > -0.1  && camera.getDirection().z < 0.1) {
                    camera.moveBackwards(0.02f);
                } else {
                    camera.moveForward(0.02f);
                }
            }
        } else {
            if(camera.getDirection().x < 1 && camera.getDirection().z < 0) {
                camera.moveBackwards(0.02f);
            } else if(camera.getDirection().x > 0) {
                camera.moveForward(0.02f);
            }
        }*/
        //light mode
        if (window.isKeyPressed(GLFW_KEY_1) && !delay){
            if(mode_light[0] == 0){
                mode_light[0] = 1;
            } else{
                mode_light[0] = 0;
            }
            delay = true;
        }

        if (window.getMouseInput().isLeftButtonPressed()) {
            Vector2f displayVec = window.getMouseInput().getDisplVec();
//            System.out.println(displayVec);
            camera.addRotation((float)Math.toRadians(displayVec.x * 0.3f), (float) Math.toRadians(displayVec.y * 0.3f));
        }

        if(window.getMouseInput().getScroll().y != 0){
            projection.setFOV(projection.getFOV()- (window.getMouseInput().getScroll().y*0.01f));
            window.getMouseInput().setScroll(new Vector2f());
        }
    }


    public void loop(){
        while (window.isOpen()) {
            window.update();
            if(malam){
                glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
            } else {
                glClearColor(0.0f, 0.67f, 1.0f, 1.0f);
            }
            if (delay){
                delayCounter++;
            }

            if (delayCounter > 30){
                delayCounter = 0;
                delay = false;
            }
//            glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
            GL.createCapabilities();
            input();

            //code
            for(Object object: objects){
                object.draw(camera,projection, mode_light);
            }

            // Restore state
            glDisableVertexAttribArray(0);

            // Poll for window events.
            // The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }
    public void run() {

        init();
        loop();

        // Terminate GLFW and
        // free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
    public static void main(String[] args) {
        new Main().run();
    }
}