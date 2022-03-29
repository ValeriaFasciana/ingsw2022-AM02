package it.polimi.ingsw.server.model;

public class RuleSet {
    void planningPhase(){

    }
    void calculateActionOrder(){

    }
    void assignProfessor(){

    }
    void getMotherNatureAvailableMoves(){

    }
    void moveMotherNature(){

    }
    void mergeIsle(){

    }
    void calculateInfluence(){

    }
    void checkWinningConditions(){

    }
    void declareWinner(){

    }
    void checkAvailableClouds(){

    }
    void checkStudentsEntrance(){

    }
    void playerHasNoTowers(){

    }
    void playerHasNoCards(){

    }
    void lessThanThreeIsleGroups(){

    }
    void bagIsEmpty(){

    }
    void generateCharacter(CharacterContext character1,CharacterContext character2,CharacterContext character3){
        int min = 1;
        int max = 12;
        int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
        character1 = new CharacterContext(random_int);
        random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
        character2 = new CharacterContext(random_int);
        random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
        character3 = new CharacterContext(random_int);


    }

}
