package sheet.version;

public class VersionImpl implements Version {
    private int versionNumber;
    private int cellsChanged;

    public VersionImpl() {
        this.versionNumber = 1;
        this.cellsChanged = 0;
    }

    @Override
    public int getVersionNumber() {
        return versionNumber;
    }
    @Override
    public void incrementVersion() {
        versionNumber++;
    }
}

