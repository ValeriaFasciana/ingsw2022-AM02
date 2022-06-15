package it.polimi.ingsw.server.model.cards.characters;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import it.polimi.ingsw.network.data.CharacterCardData;
import it.polimi.ingsw.server.model.StudentContainer;


@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class CharacterCard extends StudentContainer {
    private final int id;
    private int price;
    private int banCounter;
    private int studentsCapacity;
    private boolean excludeTowersFromInfluence;
    private int addedInfluencePoints;
    private int motherNatureAdditionalMovements;
    private boolean affectProfessorAssignment;
    private RuleSet ruleSet;
    private boolean alreadyPlayed = false;
    private String description;
    private CharacterEffect effect;


    @JsonCreator
    public CharacterCard(@JsonProperty("id") int id,
                         @JsonProperty("price") int price,
                         @JsonProperty("banCounter") int banCounter,
                         @JsonProperty("studentsCapacity") int studentsCapacity,
                         @JsonProperty("excludeTowersFromInfluence") boolean excludeTowersFromInfluence,
                         @JsonProperty("addedInfluencePoints") int addedInfluencePoints,
                         @JsonProperty("motherNatureAdditionalMovements") int motherNatureAdditionalMovements,
                         @JsonProperty("affectProfessorAssignment") boolean affectProfessorAssignment,
                         @JsonProperty("effect")CharacterEffect effect,
                         @JsonProperty("description")String description) {
        super(studentsCapacity);
        this.id = id;
        this.price = price;
        this.banCounter = banCounter;
        this.studentsCapacity = studentsCapacity;
        this.excludeTowersFromInfluence = excludeTowersFromInfluence;
        this.addedInfluencePoints = addedInfluencePoints;
        this.motherNatureAdditionalMovements = motherNatureAdditionalMovements;
        this.affectProfessorAssignment = affectProfessorAssignment;
        this.ruleSet = new RulesAffector(DefaultRuleSet.getInstance(),excludeTowersFromInfluence,addedInfluencePoints,motherNatureAdditionalMovements,affectProfessorAssignment);
        this.effect = effect;
        this.description = description;
    }


    public int getId() {
        return id;
    }

    public RuleSet getRuleSet() {
        return ruleSet;
    }

    public void increasePrice() {
        if(alreadyPlayed)return;
        price++;
        alreadyPlayed = true;
    }

    public int getPrice() {
        return price;
    }


    public CharacterCardData getData() {
        return new CharacterCardData(id, price,getStudentCountMap(),description);
    }

    public CharacterEffect getEffect(){
        return effect;
    }

    public int getStudentsCapacity() {
        return studentsCapacity;
    }
}
