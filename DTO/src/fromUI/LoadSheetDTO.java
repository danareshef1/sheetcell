package fromUI;

public class LoadSheetDTO {
    private static String filePath;

    public LoadSheetDTO(String filePath) {
        LoadSheetDTO.filePath = filePath;
    }

    public static String getFilePath() {
        return filePath;
    }
}
