package traingen.track;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * No clue what this is.
 */
public class Node {
    @XmlElement(name="TrackNode")
    private List<TrackNode> trackNodes;

    TrackNode getTrackNode(final int nodeIndex) {
        return trackNodes.get(nodeIndex);
    }
}
