package edu.eci.arsw.blueprints.persistence.impl;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.eci.arsw.blueprints.model.*;


@Service("FilterRedundancias")
public class FilterRedundancias implements BluePrintFilters {
    @Override
    public Blueprint filtro(Blueprint blue) {
        List<Point> point = blue.getPoints();
        List<Point> arraypoints = new ArrayList<Point>();
        for (Point p: point) {
            boolean tiene = false;
            for(Point pp: arraypoints ) {
                if (p.equals(pp)) {tiene=true;break;}
            }
            if(!tiene)arraypoints.add(p);
        }
        return new Blueprint(blue.getAuthor(),blue.getName(),arraypoints);
    }

}