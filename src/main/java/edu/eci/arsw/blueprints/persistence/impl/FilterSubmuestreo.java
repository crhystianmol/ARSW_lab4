package edu.eci.arsw.blueprints.persistence.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;

@Service("FilterSubmuestreo")
public class FilterSubmuestreo implements BluePrintFilters {
    @Override
    public Blueprint filtro(Blueprint blue) {
        List<Point> point = blue.getPoints();
        List<Point> arraypoints = new ArrayList<Point>();
        boolean paso=true;
        for(Point p : point){
            if(paso) {
                arraypoints.add(p);
                paso=!paso;
            }
        }
        return new Blueprint(blue.getAuthor(),blue.getName(),arraypoints);
    }


}