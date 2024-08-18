package menu.menuItems.selectionOne;

import files.jaxb.schema.generated.STLSheet;
import files.loader.XMLFileLoader;
import menu.MenuItem;
import menu.MenuItemListener;
import org.w3c.dom.Document;
import sheet.SheetImpl;

public class LoadFile implements MenuItemListener {
    private STLSheet sheet;

    public LoadFile(MenuItem menuItem) {
        menuItem.addItemSelectedListener(this);
    }

    @Override
    public void reportItemSelectedFromMenu() {
        loadFile();
    }

    public void loadFile() {
        sheet = XMLFileLoader.loadXmlFile("/Users/danareshef/IdeaProjects/testXmlForSeetcell1/basic.xml");
    }
}
