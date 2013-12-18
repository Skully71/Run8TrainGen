package traingen.track;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@XmlRootElement(name="ArrayOfTrackSection")
public class ArrayOfTrackSection {
    @XmlElement(name="TrackSection")
    private List<TrackSection> trackSections;

    private Map<Integer, TrackSection> indexedTrackSections;

    public TrackSection getTrackSection(final int trackSectionIndex) {
        if (indexedTrackSections == null) {
            indexedTrackSections = new HashMap<Integer, TrackSection>();
            for (TrackSection section : trackSections) {
                indexedTrackSections.put(section.getIndex(), section);
            }
        }
        return indexedTrackSections.get(trackSectionIndex);
    }
}
