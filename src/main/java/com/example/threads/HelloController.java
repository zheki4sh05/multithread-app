package com.example.threads;

import javafx.event.*;
import javafx.fxml.*;

import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;


import java.net.*;
import java.util.*;


public class HelloController implements Initializable {


    @FXML
    private Button startAnimation;

    @FXML
    private Pane lightWindow1;
    @FXML
    private Pane lightWindow2;
    @FXML
    private ImageView smokeAnimation;

    private static volatile Boolean btnState = false;

   private SmokeThread animation1;
   private LightThread1 animation2;

    private LightThread2 animation3;

   private Boolean started = false;

    @FXML
    public void startAnimationHandler(ActionEvent actionEvent){
       if(btnStateHandler()){
           if(!started){
               animation1.start();
               animation2.start();
               animation3.start();
               started = true;
           }else{
               notifyAnimation();
           }


       }else{
           resetAnimation();
       }
    }

    private void notifyAnimation() {
        animation1.notif();
        animation2.notif();
        animation3.notif();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //animation1 = new Thread(new SmokeThread());

        animation1 = new SmokeThread();

        animation2  = new LightThread1();

        animation3  = new LightThread2();

    }


        private synchronized void resetAnimation () {
            smokeAnimation.setOpacity(0.0);
            lightWindow1.setOpacity(0.0);
            lightWindow2.setOpacity(0.0);


        }

        private Boolean btnStateHandler() {

            if(btnState){
                btnState = false;

            }else{
                btnState = true;
            }

            startAnimation.setText(btnState ? "Стоп" : "Старт");

            return btnState;

        }


        private class SmokeThread extends Thread {


            Double startOpacityValue=0.0;
            final Double opacityChangeStep = 0.1;

            synchronized void notif(){
                startOpacityValue = 0.0;
                this.notify();

            }

            @Override
            public synchronized void run() {

                try {



                    while (true) {

                            Thread.sleep(400);


                        if (smokeAnimation.getOpacity() <= 1) {

                            startOpacityValue += opacityChangeStep;

                            smokeAnimation.setOpacity(startOpacityValue);

                        }
                            if(!btnState){
                                smokeAnimation.setOpacity(0.0);
                                wait();
                            }
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }


            }
        }

        private class LightThread1 extends Thread {


            Double startOpacityValue = 0.0;
            final Double opacityChangeStep = 0.2;

            synchronized void notif(){
                startOpacityValue = 0.0;
                this.notify();
            }

            @Override
            public synchronized void run() {



                while (true) {

                    try {
                        Thread.sleep(200);

                        if(lightWindow1.getOpacity()<=0.6) {
                        startOpacityValue+=opacityChangeStep;
                            lightWindow1.setOpacity(startOpacityValue);
                    }

                        if(!btnState){
                            lightWindow1.setOpacity(0.0);
                            this.wait();
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }


            }


        }

    private class LightThread2 extends Thread {

        Double startOpacityValue =0.0;
        final Double opacityChangeStep = 0.2;
        synchronized void notif(){
            startOpacityValue = 0.0;

            this.notify();
        }

        @Override
        public synchronized void run() {



            while (true) {

                try {
                    Thread.sleep(200);


                    if(lightWindow2.getOpacity()<=0.6) {
                        startOpacityValue+=opacityChangeStep;
                        lightWindow2.setOpacity(startOpacityValue);
                    }

                    if(!btnState){
                        lightWindow2.setOpacity(0.0);
                        this.wait();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }


        }


    }

    }