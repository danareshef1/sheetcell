package sheet;

import java.io.Serializable;

public interface Sheet extends SheetReadActions, SheetUpdateActions, Serializable {
    int getCountChangedCells();
}
