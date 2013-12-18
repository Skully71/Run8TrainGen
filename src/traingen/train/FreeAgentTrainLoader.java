package traingen.train;

import javax.xml.bind.annotation.XmlElement;

/**
 *
 */
public class FreeAgentTrainLoader {
    @XmlElement
    private int trainID;

    @XmlElement
    private UnitLoaderList unitLoaderList;

    public UnitLoaderList getUnitLoaderList() {
        return unitLoaderList;
    }
}
