package traingen.train;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

/**
 *
 */
@XmlType(
        propOrder = {"rvXMLfilename", "unitType", "currentRoutePrefix", "currentTrackSectionIndex", "startNodeIndex", "distanceTravelledInMeters",
                    "reverseDirection", "loadWeightUSTons", "destinationTag", "unitNumber" })
public class RailVehicleStateClass {
    @XmlElement
    private String rvXMLfilename;

    @XmlElement
    private String unitType;

    @XmlElementWrapper
    @XmlElement(name="int")
    private int[] currentRoutePrefix;

    private int[] currentTrackSectionIndex;

    @XmlElementWrapper
    @XmlElement(name="int")
    private int[] startNodeIndex;

    private float[] distanceTravelledInMeters;

    @XmlElementWrapper
    @XmlElement(name="boolean")
    private boolean[] reverseDirection;

    @XmlElement
    private float loadWeightUSTons;

    @XmlElement
    private String destinationTag;

    @XmlElement
    // TODO: or int?
    private String unitNumber;

    @XmlElementWrapper
    @XmlElement(name="int")
    public int[] getCurrentTrackSectionIndex() {
        return currentTrackSectionIndex;
    }

    public boolean[] getReverseDirection() {
        return reverseDirection;
    }

    @XmlElementWrapper
    @XmlElement(name="float")
    public float[] getDistanceTravelledInMeters() {
        return distanceTravelledInMeters;
    }

    public String getRvXMLfilename() {
        return rvXMLfilename;
    }

    public void setDistanceTravelledInMeters(final float... distanceTravelledInMeters) {
        this.distanceTravelledInMeters = distanceTravelledInMeters;
    }

    public void setCurrentTrackSectionIndex(final int... currentTrackSectionIndex) {
        this.currentTrackSectionIndex = currentTrackSectionIndex;
    }
}
