package com.example.youngdetective;

import java.io.Serializable;

public class adapter implements Serializable {
   private int baly;
   private Boolean status;
   private String user;

   public adapter(){

   }
   public adapter(int baly, Boolean status, String user){
       this.baly=baly;
       this.status=status;
       this.user=user;
   }

    public int getBaly() {
        return baly;
    }

    public void setBaly(int baly) {
        this.baly = baly;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
