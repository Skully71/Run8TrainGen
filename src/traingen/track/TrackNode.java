package traingen.track;

import javax.xml.bind.annotation.XmlElement;

public class TrackNode {
    @XmlElement
    private TileXZ tileXZ;

    @XmlElement
    private PositionXYZ positionXYZ;

    @XmlElement
    private TangentDegXYZ tangentDegXYZ;

    @XmlElement
    private EndPositionXYZ endPositionXYZ;

    // ?
    @XmlElement
    private int nodeIndex;

    // ?
    @XmlElement
    private int trackNodeListIndex;

    @XmlElement
    private boolean isReversePath;

    @XmlElement
    private float arcLengthMeters;

    @XmlElement
    private int numSegments;

    // not sure whether we truly need to read this one
    @XmlElement
    private int belongsToTrackIndex;

    // TODO: for now, probably needs a name change
    public float getAngle() {
        return tangentDegXYZ.x;
    }

    float getLength() {
        System.out.println("numSegments " + numSegments);
//        return (float) Math.sqrt(Math.pow(positionXYZ.x - endPositionXYZ.x, 2) + Math.pow(positionXYZ.y - endPositionXYZ.y, 2) + Math.pow(positionXYZ.z - endPositionXYZ.z, 2));
        return arcLengthMeters;
    }
}
