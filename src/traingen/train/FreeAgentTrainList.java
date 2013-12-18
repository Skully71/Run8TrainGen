package traingen.train;

import javax.xml.bind.annotation.XmlElement;

/**
 *
 */
public class FreeAgentTrainList {
    // TODO: probably an array
    @XmlElement(name = "FreeAgentTrainLoader")
    private FreeAgentTrainLoader freeAgentTrainLoader;

    public FreeAgentTrainLoader getFreeAgentTrainLoader() {
        return freeAgentTrainLoader;
    }
}
