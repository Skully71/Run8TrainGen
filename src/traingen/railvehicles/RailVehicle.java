package traingen.railvehicles;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.transform.stream.StreamSource;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@XmlRootElement(name="RVLoader")
public class RailVehicle {
    private static final JAXBContext CONTEXT = createContext();
    private static final Unmarshaller UNMARSHALLER = createUnmarshaller();

    private static final Map<String, RailVehicle> RAIL_VEHICLE_MAP = new HashMap<String, RailVehicle>();

    @XmlElement
    private String rvXMLfilename;

    @XmlElement
    private float bolsterLengthMeters;

    @XmlElementWrapper
    @XmlElement(name="float")
    private float[] couplerZOffset;

    @XmlElement
    private float emptyWeightUSTons;

    @XmlElement
    private float maxGrossWeightUSTons;

    private static JAXBContext createContext() {
        try {
            return JAXBContext.newInstance(RailVehicle.class);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    private static Unmarshaller createUnmarshaller() {
        try {
            return CONTEXT.createUnmarshaller();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    public float getBolsterLengthMeters() {
        return bolsterLengthMeters;
    }


    public float[] getCouplerZOffset() {
        return couplerZOffset;
    }

    public float getEmptyLoadWeightUSTons() {
        return emptyWeightUSTons;
    }

    public float getMaxLoadWeightUSTons() {
        return maxGrossWeightUSTons - emptyWeightUSTons;
    }

    public String getRvXMLfilename() {
        return rvXMLfilename;
    }

    public static RailVehicle load(final String xml) throws IOException, JAXBException {
        RailVehicle vehicle = RAIL_VEHICLE_MAP.get(xml);
        if (vehicle != null)
            return vehicle;
        final String rvXMLFileName = "D:/Program Files (x86)/Run 8 Studios/Run 8 Train Simulator/Content/RailVehicles/" + xml;
        final BufferedReader reader = new BufferedReader(new BOMReader(new FileReader(rvXMLFileName)));
        try {
            final JAXBElement<RailVehicle> element = UNMARSHALLER.unmarshal(new StreamSource(reader), RailVehicle.class);
            vehicle = element.getValue();
            RAIL_VEHICLE_MAP.put(xml, vehicle);
            return vehicle;
        } finally {
            reader.close();
        }
    }

    public static void main(final String[] args) throws JAXBException, FileNotFoundException {
        final String fileName = "D:/Program Files (x86)/Run 8 Studios/Run 8 Train Simulator/Content/RailVehicles";
        // R8_AutoRack_Bi_CN01.xml
        final JAXBContext context = JAXBContext.newInstance(RailVehicle.class);
        final Unmarshaller unmarshaller = context.createUnmarshaller();
        final BufferedReader reader = new BufferedReader(new FileReader(fileName + "/" + "R8_AutoRack_Bi_CN01.xml"));
        final JAXBElement<RailVehicle> element = unmarshaller.unmarshal(new StreamSource(reader), RailVehicle.class);
        final RailVehicle railVehicle = element.getValue();

        final Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(railVehicle, System.out);
    }
}
