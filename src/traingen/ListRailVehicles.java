package traingen;

import java.io.File;

public class ListRailVehicles {
    public static void main(final String[] args) {
        final File file = new File("D:/Program Files (x86)/Run 8 Studios/Run 8 Train Simulator/Content/RailVehicles");
        for (String name : file.list()) {
            if (name.startsWith("R8_Boxcar_50ft_PlateF_") ||
                    name.startsWith("R8_C14Hopper_") ||
                    name.startsWith("R8_Pig_") ||
                    name.startsWith("R8_Reefer_PCF_57_"))
                System.out.print("\"" + name + "\", ");
        }
    }
}
