import com.sun.istack.internal.NotNull;
import java.util.*;

public class Quest{
    private String name;
    private HashMap<String, Boolean> reqs;
    Quest(){
        name = null;
        reqs = new HashMap<>();
    }
    Quest(String name, @NotNull ArrayList<String> requirements){
        this.name = name;
        reqs = new HashMap<>();
        for (String requirement : requirements) {
            reqs.put(requirement, false);
        }
    }
    String getQuestName(){
        return name;
    }
    void setQuestName(String name){
        this.name = name;
    }
    void setReqs(ArrayList<String> requirements){
        reqs.clear();
        for (String requirement : requirements) {
            reqs.put(requirement, false);
        }
    }
    boolean getReqAtPos(int position){
        Set<String> set = reqs.keySet();
        List<String> list = new ArrayList<>(set);
        for(int i = 0; i<list.size(); i++){
            if(position==i){
                return reqs.get(list.get(i));
            }
        }
        return false;
    }
    void completeReqAtPos(int position){
        List<String> list = new ArrayList<>(reqs.keySet());
        for(int i = 0; i<list.size(); i++){
            if(position==i){
                reqs.replace(list.get(i),true);
            }
        }
    }
    boolean questComplete(){
        for(int i = 0; i<reqs.size(); i++){
            if(!getReqAtPos(i)){
                return false;
            }
        }
        return true;
    }
    void addReq(@NotNull String requirement){
        reqs.put(requirement, false);
    }
    String getReqNameAtPos(int position){
        List<String> list = new ArrayList<>(reqs.keySet());
        for(int i = 0; i<list.size(); i++){
            if(position==i){
                return list.get(i);
            }
        }
        return null;
    }
    List<String> getReqs(){
        return new ArrayList<>(reqs.keySet());
    }
}