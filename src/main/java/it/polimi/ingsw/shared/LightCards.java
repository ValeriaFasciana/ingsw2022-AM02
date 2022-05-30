package it.polimi.ingsw.shared;

import java.io.Serializable;

 public abstract class LightCards implements Serializable {
        //Class to build light version of {@link it.polimi.ingsw.model.cards.AssistanCard} to send through messages to the client/server
        private int id;
        private int value;
        private int movement;
        private boolean used;

     public LightCards(int id, int value, int movement, boolean used) {
         this.id = id;
         this.value = value;
         this.movement = movement;
         this.used = used;
     }

     public int getId() {
         return id;
     }

     public int getValue() {
         return value;
     }

     public int getMovement() {
         return movement;
     }

     public boolean isUsed() {
         return used;
     }

     @Override
     public String toString() {
         return "LightCard{" +
                 "ID=" + id +
                 '}';
     }


 }


