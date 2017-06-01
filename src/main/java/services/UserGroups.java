package services;

/**
 * Created by vasiliev on 6/1/2017.
 */

public enum UserGroups {
    CUSTOMER("customers"), SUPPLIER("suppliers"), ADMIN("");

    private String folderName;

    UserGroups(String folderName) {
        this.folderName = folderName;
    }

    public String getFolderName() {
        return folderName;
    }
}
