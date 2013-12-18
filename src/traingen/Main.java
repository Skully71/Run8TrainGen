package traingen;

import com.sun.xml.internal.txw2.output.IndentingXMLStreamWriter;
import traingen.railvehicles.RailVehicle;
import traingen.track.ArrayOfTrackSection;
import traingen.track.TrackDatabase;
import traingen.track.TrackSection;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {
    private static final Random RANDOM = new Random();

    private static String[] MANIFEST_RAIL_VEHICLE_XML = { "R8_Boxcar_50ft_PlateF_AOK01.xml", "R8_Boxcar_50ft_PlateF_BN01.xml", "R8_Boxcar_50ft_PlateF_BNSF01.xml", "R8_Boxcar_50ft_PlateF_BNSF02.xml", "R8_Boxcar_50ft_PlateF_CAT01.xml", "R8_Boxcar_50ft_PlateF_CEFX01.xml", "R8_Boxcar_50ft_PlateF_CN01.xml", "R8_Boxcar_50ft_PlateF_CNW01.xml", "R8_Boxcar_50ft_PlateF_CR01.xml", "R8_Boxcar_50ft_PlateF_CSX01.xml", "R8_Boxcar_50ft_PlateF_IB01.xml", "R8_Boxcar_50ft_PlateF_NOKL01.xml", "R8_Boxcar_50ft_PlateF_R801.xml", "R8_Boxcar_50ft_PlateF_SP01.xml", "R8_Boxcar_50ft_PlateF_TTX01.xml", "R8_Boxcar_50ft_PlateF_WC01.xml", "R8_C14Hopper_AGP01.xml", "R8_C14Hopper_ASHX01.xml", "R8_C14Hopper_BN01.xml", "R8_C14Hopper_BNSF01.xml", "R8_C14Hopper_BNSF02.xml", "R8_C14Hopper_CAGX01.xml", "R8_C14Hopper_CARG01.xml", "R8_C14Hopper_CPR02.xml", "R8_C14Hopper_CSX01.xml", "R8_C14Hopper_DME01.xml", "R8_C14Hopper_GAT01.xml", "R8_C14Hopper_POTASH01.xml", "R8_C14Hopper_Run8_01.xml", "R8_C14Hopper_SOY01.xml", "R8_C14Hopper_UP01.xml", "R8_C14Hopper_UP02.xml", "R8_Pig_RTTX01.xml", "R8_Pig_RTTX02.xml", "R8_Pig_RTTX03.xml", "R8_Pig_RTTX04.xml", "R8_Pig_RTTX05.xml", "R8_Pig_RTTX06.xml", "R8_Pig_RTTX07.xml", "R8_Pig_RTTX08.xml", "R8_Pig_RTTX09.xml", "R8_Reefer_PCF_57_SFRC.xml", "R8_Reefer_PCF_57_SPFE.xml", "R8_Reefer_PCF_57_UPFE.xml" };
    //private static String[] DESTINATIONS = { "BAK", "BEL", "BOR", "GAL", "KCK", "LUB", "SJVRKern", "STO", "TPL", "TUL" };
    private static String[] DESTINATIONS = { "BAK", "BEL", "FRS", "GAL", "KCK", "LUB", "RBB", "RIC", "SJVRKern", "STO", "TPL", "TUL" };

    public static void main(String[] args) throws IOException, JAXBException, XMLStreamException {
        //final float trainLength = 1200 + RANDOM.nextInt(1200);
        final float trainLength = 600 + RANDOM.nextInt(1200);
        float currentTrainLength = 0;

        System.out.println("Train length " + trainLength);

        final List<RailVehicleInstance> vehicles = new ArrayList<RailVehicleInstance>();

        float totalWeight = 0.0f;
        int trainSectionLength = RANDOM.nextInt(10);
        int currentTrainSectionLength = 0;
        int destination = RANDOM.nextInt(DESTINATIONS.length);
        int railVehicleXml = RANDOM.nextInt(MANIFEST_RAIL_VEHICLE_XML.length);
        while (currentTrainLength < trainLength) {
            final RailVehicle vehicle = RailVehicle.load(MANIFEST_RAIL_VEHICLE_XML[railVehicleXml]);
            final RailVehicleInstance instance = new RailVehicleInstance(vehicle, DESTINATIONS[destination], true);
            final float weight = RANDOM.nextFloat() * vehicle.getMaxLoadWeightUSTons();
            instance.setLoadWeightUSTons(weight);
            totalWeight += weight + vehicle.getEmptyLoadWeightUSTons();
            vehicles.add(instance);
            float instanceLength = vehicle.getCouplerZOffset()[0] / 2 + vehicle.getBolsterLengthMeters();
            //System.out.println(instanceLength);
            // TODO: look at the previous vehicle
            currentTrainLength += instanceLength;
            currentTrainSectionLength++;
            if (currentTrainSectionLength >= trainSectionLength) {
                trainSectionLength = RANDOM.nextInt(10);
                currentTrainSectionLength = 0;
                destination = RANDOM.nextInt(DESTINATIONS.length);
                railVehicleXml = RANDOM.nextInt(MANIFEST_RAIL_VEHICLE_XML.length);
            }
        }
        System.out.println(vehicles.size() + " " + totalWeight);

        final ArrayOfTrackSection trackDatabase = TrackDatabase.load("D:/Program Files (x86)/Run 8 Studios/Run 8 Train Simulator/Content/Routes/BarstowYermo/trackDatabase.xml");

        final XMLOutputFactory factory = XMLOutputFactory.newFactory();
        final String fileName = "D:/Program Files (x86)/Run 8 Studios/Run 8 Train Simulator/Content/Routes/BarstowYermo/Trains/Generated Train.xml";
        final XMLStreamWriter writer = new IndentingXMLStreamWriter(factory.createXMLStreamWriter(new BufferedWriter(new FileWriter(fileName))));
        writer.writeStartDocument();
        writer.writeStartElement("ScnLoader");
        writer.writeNamespace("xsd", "http://www.w3.org/2001/XMLSchema");
        writer.writeNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
        writer.writeStartElement("freeAgentTrainList");
        writer.writeStartElement("FreeAgentTrainLoader");
        writer.writeStartElement("trainID");
        writer.writeCharacters("0");
        writer.writeEndElement(); // trainID
        writer.writeStartElement("unitLoaderList");

        // the start position of the train in meters vs track
        float position = 15f;
        final int startNodeIndex = 0; // for now we can only place on node 0 track

        // the section where we start placing the train
        TrackSection section = trackDatabase.getTrackSection(2);

        float sectionLength = section.getLength();

        final int nextNodeIndex = 1; // why should it be 1?

        for (int c = 0; c < 2; c++) {
            writer.writeStartElement("RailVehicleStateClass");
            writer.writeStartElement("rvXMLfilename");
            writer.writeCharacters("Run8_ES44DC.xml");
            writer.writeEndElement(); // rvXMLfilename
            writer.writeStartElement("unitType");
            writer.writeCharacters("US_DieselEngine");
            writer.writeEndElement(); // unitType
            writer.writeStartElement("currentRoutePrefix");
            writeInt(writer, 150);
            writeInt(writer, 150);
            writer.writeEndElement(); // currentRoutePrefix

            final float position1;
            final int currentTrackSectionIndex1;
            while (position > sectionLength) {
                position -= sectionLength;
                section = trackDatabase.getTrackSection(section.getNextSectionIndex(nextNodeIndex));
                sectionLength = section.getLength();
            }
            position1 = position;
            currentTrackSectionIndex1 = section.getIndex();

            position += 14.108f; // bolsterLengthMeters?

            final float position2;
            final int currentTrackSectionIndex2;
            while (position > sectionLength) {
                position -= sectionLength;
                section = trackDatabase.getTrackSection(section.getNextSectionIndex(nextNodeIndex));
                sectionLength = section.getLength();
            }

            position2 = position;
            currentTrackSectionIndex2 = section.getIndex();

            if (c == 0) {
                writer.writeStartElement("currentTrackSectionIndex");
                writeInt(writer, currentTrackSectionIndex1);
                writeInt(writer, currentTrackSectionIndex2);
                writer.writeEndElement(); // currentTrackSectionIndex
                writer.writeStartElement("startNodeIndex");
                writeInt(writer, 0);
                writeInt(writer, 0);
                writer.writeEndElement(); // startNodeIndex
                writer.writeStartElement("distanceTravelledInMeters");
                writeFloat(writer, position1);
                writeFloat(writer, position2);
                writer.writeEndElement(); // distanceTravelledInMeters

                writer.writeStartElement("reverseDirection");
                write(writer, "boolean", "true");
                write(writer, "boolean", "true");
                writer.writeEndElement(); // reverseDirection
            } else {
                writer.writeStartElement("currentTrackSectionIndex");
                writeInt(writer, currentTrackSectionIndex2);
                writeInt(writer, currentTrackSectionIndex1);
                writer.writeEndElement(); // currentTrackSectionIndex
                writer.writeStartElement("startNodeIndex");
                writeInt(writer, 0);
                writeInt(writer, 0);
                writer.writeEndElement(); // startNodeIndex
                writer.writeStartElement("distanceTravelledInMeters");
                writeFloat(writer, position2);
                writeFloat(writer, position1);
                writer.writeEndElement(); // distanceTravelledInMeters

                writer.writeStartElement("reverseDirection");
                write(writer, "boolean", "false");
                write(writer, "boolean", "false");
                writer.writeEndElement(); // reverseDirection
            }

            writer.writeStartElement("loadWeightUSTons");
            writer.writeCharacters("17.875");
            writer.writeEndElement(); // loadWeightUSTons
            writer.writeStartElement("destinationTag");
            writer.writeCharacters("M-SBDBAR");
            writer.writeEndElement(); // destinationTag
            writer.writeStartElement("unitNumber");
            writer.writeCharacters("9999");
            writer.writeEndElement(); // unitNumber
            writer.writeEndElement(); // RailVehicleStateClass

            // assuming cars are symmetrical: (couplerZOffset[0] + couplerZOffset[1]) - (bolsterLengthMeters[0] + bolsterLengthMeters[1]) / 2
            if (c == 0)
                position += 8.232f;
            else {
                // no longer needed, moved to the next section TODO cleanup
                //position += 6.551f;
            }
        }

        for (int c = 0; c < vehicles.size(); c++) {
            final RailVehicleInstance instance = vehicles.get(c);
            final RailVehicle vehicle = instance.getRailVehicle();
            if (c == 0) {
                // 11.17 = ES44DC coupler offset
                position += (11.17f + vehicle.getCouplerZOffset()[0]) - (14.108f + vehicle.getBolsterLengthMeters()) / 2;
            } else {
                position += (vehicles.get(c - 1).getRailVehicle().getCouplerZOffset()[0] + vehicle.getCouplerZOffset()[0]) - (vehicles.get(c - 1).getRailVehicle().getBolsterLengthMeters() + vehicle.getBolsterLengthMeters()) / 2;
            }
            writer.writeStartElement("RailVehicleStateClass");
            writer.writeStartElement("rvXMLfilename");
            //writer.writeCharacters("R8_Boxcar_50ft_PlateF_CSX01.xml");
            writer.writeCharacters(vehicle.getRvXMLfilename());
            writer.writeEndElement(); // rvXMLfilename
            writer.writeStartElement("unitType");
            writer.writeCharacters("US_Freightcar");
            writer.writeEndElement(); // unitType
            writer.writeStartElement("currentRoutePrefix");
            writeInt(writer, 150);
            writeInt(writer, 150);
            writer.writeEndElement(); // currentRoutePrefix

            final float position1;
            final int currentTrackSectionIndex1;
            while (position > sectionLength) {
                position -= sectionLength;
                section = trackDatabase.getTrackSection(section.getNextSectionIndex(nextNodeIndex));
                sectionLength = section.getLength();
            }
            position1 = position;
            currentTrackSectionIndex1 = section.getIndex();

            //final float carLength = 11.75f;
            final float carLength = vehicle.getBolsterLengthMeters();
            position += carLength;

            final float position2;
            final int currentTrackSectionIndex2;
            while (position > sectionLength) {
                position -= sectionLength;
                section = trackDatabase.getTrackSection(section.getNextSectionIndex(nextNodeIndex));
                sectionLength = section.getLength();
            }

            position2 = position;
            currentTrackSectionIndex2 = section.getIndex();

            writer.writeStartElement("currentTrackSectionIndex");
            writeInt(writer, currentTrackSectionIndex1);
            writeInt(writer, currentTrackSectionIndex2);
            writer.writeEndElement(); // currentTrackSectionIndex
            writer.writeStartElement("startNodeIndex");
            writeInt(writer, 0);
            writeInt(writer, 0);
            writer.writeEndElement(); // startNodeIndex
            writer.writeStartElement("distanceTravelledInMeters");
            writeFloat(writer, position1);
            writeFloat(writer, position2);
            writer.writeEndElement(); // distanceTravelledInMeters

            writer.writeStartElement("reverseDirection");
            final boolean reverseDirection = true;
            write(writer, "boolean", Boolean.toString(reverseDirection));
            write(writer, "boolean", Boolean.toString(reverseDirection));
            writer.writeEndElement(); // reverseDirection
            writer.writeStartElement("loadWeightUSTons");
            //writer.writeCharacters("0");
            writer.writeCharacters(Float.toString(instance.getLoadWeightUSTons()));
            writer.writeEndElement(); // loadWeightUSTons
            writer.writeStartElement("destinationTag");
            //writer.writeCharacters("Generated");
            writer.writeCharacters(instance.getDestinationTag());
            writer.writeEndElement(); // destinationTag
            writer.writeStartElement("unitNumber");
            writer.writeCharacters("999999");
            writer.writeEndElement(); // unitNumber
            writer.writeEndElement(); // RailVehicleStateClass

//            position += 4.87f; // no clue, should be space between axles with previous unit or something
        }

        writer.writeEndElement(); // unitLoaderList
        writer.writeEndElement(); // FreeAgentTrainLoader
        writer.writeEndElement(); // freeAgentTrainList
        writer.writeEmptyElement("flagList");
        writer.writeStartElement("cameraXYZ");
        write(writer, "X", "295.426117");
        write(writer, "Y", "681.5876");
        write(writer, "Z", "-548.2413");
        writer.writeEndElement(); // cameraXYZ
        writer.writeStartElement("camRotationXYZ");
        write(writer, "X", "0.01399994");
        write(writer, "Y", "1.64800191");
        write(writer, "Z", "0");
        writer.writeEndElement(); // cameraXYZ
        writer.writeStartElement("camTileXZ");
        write(writer, "x", "201");
        write(writer, "z", "-15");
        writer.writeEndElement(); // camTileXZ
        writer.writeEndElement(); // ScnLoader
        writer.writeEndDocument();
        writer.flush();
        writer.close();
    }

    private static void write(final XMLStreamWriter writer, final String tag, final String data) throws XMLStreamException {
        writer.writeStartElement(tag);
        writer.writeCharacters(data);
        writer.writeEndElement();
    }

    private static void writeFloat(final XMLStreamWriter writer, final float value) throws XMLStreamException {
        write(writer, "float", Float.toString(value));
    }

    private static final void writeInt(final XMLStreamWriter writer, final int value) throws XMLStreamException {
        write(writer, "int", Integer.toString(value));
    }
}
