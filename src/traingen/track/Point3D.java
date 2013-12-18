package traingen.track;

import javax.xml.bind.annotation.XmlElement;

public class Point3D {
    @XmlElement(name="X")
    float x;

    @XmlElement(name="Y")
    float y;

    @XmlElement(name="Z")
    float z;
}
