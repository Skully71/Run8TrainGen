package traingen;

import traingen.railvehicles.RailVehicle;
import traingen.track.ArrayOfTrackSection;
import traingen.track.TrackDatabase;
import traingen.track.TrackSection;
import traingen.train.RailVehicleStateClass;
import traingen.train.ScnLoader;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.*;
import java.util.List;

/**
 *
 */
public class RepairTrain {
    public static void main(String[] args) throws IOException, JAXBException {
        final ArrayOfTrackSection trackDatabase = TrackDatabase.load("D:/Program Files (x86)/Run 8 Studios/Run 8 Train Simulator/Content/Routes/BNSF_MojaveSub/trackDatabase.xml");

        final String fileName = "D:/Program Files (x86)/Run 8 Studios/Run 8 Train Simulator/Content/Routes/BNSF_MojaveSub/Trains/AwCrap_RecoverMyTrain - Copy.xml";
        final BufferedReader reader = new BufferedReader(new FileReader(fileName));
        final ScnLoader scnLoader = JAXB.unmarshal(reader, ScnLoader.class);

        final List<RailVehicleStateClass> consist = scnLoader.getFreeAgentTrainList().getFreeAgentTrainLoader().getUnitLoaderList().getRailVehicleStateClass();
        final RailVehicleStateClass engine = consist.get(0);

        assert !engine.getReverseDirection()[0] : "can't do reversed engines yet";
        TrackSection section = trackDatabase.getTrackSection(engine.getCurrentTrackSectionIndex()[0]);
        float sectionLength = section.getLength();
        final int nextNodeIndex = 1; // why should it be 1?

        // the start position of the train in meters vs track
        float position = 8.0f - engine.getDistanceTravelledInMeters()[0];

        for (int c = 0; c < consist.size(); c++) {
            RailVehicleStateClass state = consist.get(c);
            RailVehicle vehicle = RailVehicle.load(state.getRvXMLfilename());
            if (c > 0) {
                System.out.println("coupler dist: " + ((railVehicle(consist.get(c - 1)).getCouplerZOffset()[0] + vehicle.getCouplerZOffset()[0]) - (railVehicle(consist.get(c - 1)).getBolsterLengthMeters() + vehicle.getBolsterLengthMeters()) / 2));
                position += (railVehicle(consist.get(c - 1)).getCouplerZOffset()[0] + vehicle.getCouplerZOffset()[0]) - (railVehicle(consist.get(c - 1)).getBolsterLengthMeters() + vehicle.getBolsterLengthMeters()) / 2;
            }

            final float position1;
            final int currentTrackSectionIndex1;
            float sectionLength1 = sectionLength;
            while (position > sectionLength) {
                position -= sectionLength;
                section = trackDatabase.getTrackSection(section.getNextSectionIndex(nextNodeIndex));
                sectionLength = section.getLength();
                sectionLength1 = sectionLength;
            }
            position1 = position;
            currentTrackSectionIndex1 = section.getIndex();

            final float carLength = vehicle.getBolsterLengthMeters();
            //final float carLength = vehicle.getBolsterLengthMeters() - 12.205f;
            position += carLength;

            final float position2;
            final int currentTrackSectionIndex2;
            float sectionLength2 = sectionLength;
            while (position > sectionLength) {
                position -= sectionLength;
                section = trackDatabase.getTrackSection(section.getNextSectionIndex(nextNodeIndex));
                sectionLength = section.getLength();
                sectionLength2 = sectionLength;
            }

            position2 = position;
            currentTrackSectionIndex2 = section.getIndex();

            System.out.println(position1 + " " + position2);
            System.out.println(currentTrackSectionIndex1 + " " + currentTrackSectionIndex2);

            if (state.getReverseDirection()[0]) {
                state.setCurrentTrackSectionIndex(currentTrackSectionIndex2, currentTrackSectionIndex1);
                // I think the reverse position (len - pos) instead of just pos has to do with reversePath of a TrackSection
                state.setDistanceTravelledInMeters(sectionLength2 - position2, sectionLength1 - position1);
            } else {
                state.setCurrentTrackSectionIndex(currentTrackSectionIndex1, currentTrackSectionIndex2);
                state.setDistanceTravelledInMeters(sectionLength1 - position1, sectionLength2 - position2);
            }
        }

        // temporary delete all but a couple of cars to check if it works.
//        while (consist.size() > 2) {
//            consist.remove(consist.size() - 1);
//        }

        final Writer writer = new BufferedWriter(new FileWriter("D:/Program Files (x86)/Run 8 Studios/Run 8 Train Simulator/Content/Routes/BNSF_MojaveSub/Trains/Repaired.xml"));

        final JAXBContext context = JAXBContext.newInstance(ScnLoader.class);
        final Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(scnLoader, writer);

        writer.close();
    }

    private static RailVehicle railVehicle(final RailVehicleStateClass instance) throws IOException, JAXBException {
        return RailVehicle.load(instance.getRvXMLfilename());
    }
}
