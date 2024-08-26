package sheet.version;

import java.io.Serializable;

public class VersionImpl implements Version {
    private int versionNumber;
    private int cellsChanged;

    public VersionImpl() {
        this.versionNumber = 1;
        this.cellsChanged = 0;
    }

    public int getVersionNumber() {
        return versionNumber;
    }

    public int getCellsChanged() {
        return cellsChanged;
    }

    public void incrementVersion() {
        versionNumber++;
    }

    public void incrementCellChanged() {
        cellsChanged++;
    }

    public void setVersionNumber(int versionNumber){
        this.versionNumber = versionNumber;
    }
}

