package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.*;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

/**
 *
 * @author hcadavid
 */
@Service("Memory")
public class InMemoryBlueprintPersistence implements BlueprintsPersistence{

    private final ConcurrentHashMap<Tuple<String,String>,Blueprint> blueprints=new ConcurrentHashMap<>();

    public InMemoryBlueprintPersistence() {
        //load stub data
        Point[] pts=new Point[]{new Point(140, 140),new Point(115, 115)};
        Blueprint bp=new Blueprint("_authorname_", "_bpname_ ",pts);
        Blueprint bp1=new Blueprint("Nicolas", "paint1",pts);
        Blueprint bp2=new Blueprint("Daniel", "paint2",pts);
        Blueprint bp3=new Blueprint("Santiago", "paint3",pts);

        blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        blueprints.put(new Tuple<>(bp1.getAuthor(),bp1.getName()), bp1);
        blueprints.put(new Tuple<>(bp2.getAuthor(),bp2.getName()), bp2);
        blueprints.put(new Tuple<>(bp3.getAuthor(),bp3.getName()), bp3);
    }


    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        if (blueprints.containsKey(new Tuple<>(bp.getAuthor(),bp.getName()))){
            throw new BlueprintPersistenceException("The given blueprint already exists: "+bp);
        }
        else{
            blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        }
    }

    @Override
    public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException {
        return blueprints.get(new Tuple<>(author, bprintname));
    }

    @Override
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException{
        HashSet<Blueprint> auuthor = new HashSet();
        Set<Entry<Tuple<String,String>,Blueprint>> blueprintcopy= blueprints.entrySet();
        for (Entry<Tuple<String, String>, Blueprint> a: blueprintcopy) {
            if (a.getKey().o1.equals(author)) {
                auuthor.add(a.getValue());
            }
        }

        return auuthor;


    }

    @Override
    public Set<Blueprint> getAllBlueprints(){
        HashSet<Blueprint> auuthor = new HashSet();
        Set<Entry<Tuple<String,String>,Blueprint>> blueprintcopy= blueprints.entrySet();
        for (Entry<Tuple<String, String>, Blueprint> a: blueprintcopy) {
            auuthor.add(a.getValue());
        }
        return auuthor;

    }

    @Override
    public void setModif(String author, String bpname, Blueprint bp) throws BlueprintNotFoundException {
        Blueprint blueprint = getBlueprint(author,bpname);
        blueprint.setPuntos(bp.getPoints());
    }
}
