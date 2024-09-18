package sheet.version;

import java.io.Serializable;

public interface Version extends Serializable {
    int getVersionNumber();

    void incrementVersion();
}
