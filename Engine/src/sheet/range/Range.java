package sheet.range;

import expression.systemicExpression.REF;
import sheet.coordinate.Coordinate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Range implements Serializable {

    Set<REF> refs;
    String rangeName;
    Set<Coordinate> cellsThatThisRangeAffects = new HashSet<>();

    public Range(Set<REF> rangeOfCoordinates, String rangeName) {
        this.refs = rangeOfCoordinates;
        this.rangeName = rangeName;
    }

    public Set<REF> getRangeRefs() {
        return refs;
    }

    public List<Coordinate> getCellLocations() {
        List<Coordinate> r = new ArrayList<>();

        refs.forEach(ref -> {
            r.add(ref.getCoordinate());
        });

        return r;
    }

    public String getRangeName() {
        return rangeName;
    }

    public void addAffectedFromThisRangeCoordinates(Coordinate coordinate) {
        cellsThatThisRangeAffects.add(coordinate);
    }

    public Set<Coordinate> getAffectingCoordinates() {
        return cellsThatThisRangeAffects;
    }

    public boolean canBeDeleted() {
        return cellsThatThisRangeAffects.isEmpty();
    }
}
