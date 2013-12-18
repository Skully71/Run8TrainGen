package traingen.track;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

/**
 * A section of track consisting of multiple TrackNodes.
 */
public class TrackSection {
    @XmlElement
    private Node node;

    @XmlElement
    private int indexer;

    @XmlElementWrapper
    @XmlElement(name="int")
    private int[] nextSectionIndex;

    @XmlElement
    private float arcLengthMeters;

    public int getIndex() {
        return indexer;
    }

    public float getLength() {
        // TODO: are all nodes the same length? for now assume that node 1 is a mirror of node 0
        System.out.println("arcLengthMeters " + arcLengthMeters + " " + node.getTrackNode(0).getLength());
        return node.getTrackNode(0).getLength();
        //return Math.max(node.getTrackNode(0).getLength(), arcLengthMeters);
        //return arcLengthMeters;
    }

    public int getNextSectionIndex(final int nodeIndex) {
        return nextSectionIndex[nodeIndex];
    }

    public TrackNode getTrackNode(final int nodeIndex) {
        return node.getTrackNode(nodeIndex);
    }
}
