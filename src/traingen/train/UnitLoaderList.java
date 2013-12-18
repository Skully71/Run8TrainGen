package traingen.train;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 *
 */
public class UnitLoaderList {
    @XmlElement(name = "RailVehicleStateClass")
    private List<RailVehicleStateClass> railVehicleStateClass;

    public List<RailVehicleStateClass> getRailVehicleStateClass() {
        return railVehicleStateClass;
    }
}
