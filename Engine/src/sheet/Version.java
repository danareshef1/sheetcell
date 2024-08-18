package sheet;

public class Version {
    private int versionNumber;
    private int cellsChanged;

    public Version() {
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
}

