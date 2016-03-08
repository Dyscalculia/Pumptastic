package ux;

import Backend.DBConnect;


public class IndexController  {

    private final DBConnect dbConnect;

    public IndexController(DBConnect dbConnect){
        this.dbConnect =dbConnect;
    }
}
