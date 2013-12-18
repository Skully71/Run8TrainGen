package traingen.train;

import traingen.track.ArrayOfTrackSection;
import traingen.track.Point3D;
import traingen.track.TileXZ;

import javax.xml.bind.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 *
 */
@XmlRootElement(name="ScnLoader")
public class ScnLoader {
    @XmlElement(name="freeAgentTrainList")
    private FreeAgentTrainList freeAgentTrainList;

    @XmlElement(name = "cameraXYZ")
    private Point3D cameraXYZ;

    @XmlElement(name = "camRotationXYZ")
    private Point3D camRotationXYZ;

    @XmlElement(name = "camTileXZ")
    private TileXZ camTileXZ;

    @XmlElement
    private int hours;

    @XmlElement
    private int minutes;

    @XmlElement
    private int seconds;

    public static void main(String[] args) throws FileNotFoundException, JAXBException {
        final String fileName = "D:/Program Files (x86)/Run 8 Studios/Run 8 Train Simulator/Content/Routes/BNSF_MojaveSub/Trains/AwCrap_RecoverMyTrain - Copy.xml";
        final BufferedReader reader = new BufferedReader(new FileReader(fileName));
        final ScnLoader scnLoader = JAXB.unmarshal(reader, ScnLoader.class);

        final JAXBContext context = JAXBContext.newInstance(ScnLoader.class);
        final Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(scnLoader, System.out);
    }

    public FreeAgentTrainList getFreeAgentTrainList() {
        return freeAgentTrainList;
    }
}
