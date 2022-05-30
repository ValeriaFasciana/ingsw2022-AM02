package it.polimi.ingsw.shared.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Resource {
    COIN(0, "©"),TOWER(1, "♙");

    private int value;
    public String symbol;
    private static Map<Integer, Resource> map= new HashMap<>();
    private static Map<Resource, String> mapSymbol = new HashMap<>();
    Resource(int value, String symbol){
        this.value=value;
        this.symbol = symbol;
    }

    static {
        for(Resource resource : Resource.values()){
            map.put(resource.value, resource);
        }
    }
    public static Resource valueOf(int resource){
        return map.get(resource);
    }

    public int getValue(){
        return value;
    }

    public static List<Resource> realValues(){
        List<Resource> values = new ArrayList<>();
        for (int i=0; i<4; i++){
            values.add(valueOf(i));
        }
        return values;
    }
}
