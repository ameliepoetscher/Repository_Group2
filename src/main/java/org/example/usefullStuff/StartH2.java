package org.example.usefullStuff;

import org.h2.tools.Server;

public class StartH2 {
        public static void main(String[] args) throws Exception {
            Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082").start();

            // go to console and run:
            // http://localhost:8082
            // sa as username and password is empty




        }

}
