package photoOrderManager.settings;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

public class Settings implements Serializable {

    private String pathToPendrive;
    private Map<String, BigDecimal> formatToOrder;
    private String pathToOrdersDirectory;
    private int numberOfPanels;

    public Settings(){
        setDefaultSettings();
    }

    private void setDefaultSettings(){
        pathToPendrive="F:\\";
        formatToOrder=new LinkedHashMap<>();
        formatToOrder.put("9x13",BigDecimal.valueOf(0.4));
        formatToOrder.put("10x15",BigDecimal.valueOf(0.5));
        formatToOrder.put("13x18",BigDecimal.valueOf(0.6));
        formatToOrder.put("15x21",BigDecimal.valueOf(0.7));
        formatToOrder.put("21x30",BigDecimal.valueOf(0.8));
        pathToOrdersDirectory="D:\\Zamowienia";
        numberOfPanels=6;
    }

    public String getPathToPendrive() {
        return pathToPendrive;
    }

    public Map<String, BigDecimal> getFormatToOrder() {
        return formatToOrder;
    }

    public String getPathToOrdersDirectory() {
        return pathToOrdersDirectory;
    }

    public void setPathToPendrive(String pathToPendrive) {
        this.pathToPendrive = pathToPendrive;
    }

    public void setFormatToOrder(Map<String, BigDecimal> formatToOrder) {
        this.formatToOrder = formatToOrder;
    }

    public void setPathToOrdersDirectory(String pathToOrdersDirectory) {
        this.pathToOrdersDirectory = pathToOrdersDirectory;
    }

    public int getNumberOfPanels() {
        return numberOfPanels;
    }

    public void setNumberOfPanels(int numberOfPanels) {
        this.numberOfPanels = numberOfPanels;
    }
}
