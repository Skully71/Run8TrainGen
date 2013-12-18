package traingen.track;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class TrackDatabase {
    public static void main(String[] args) throws Exception {
        final String fileName = "D:/Program Files (x86)/Run 8 Studios/Run 8 Train Simulator/Content/Routes/BarstowYermo/trackDatabase.xml";
        final BufferedReader reader = new BufferedReader(new FileReader(fileName));
        final ArrayOfTrackSection array = JAXB.unmarshal(reader, ArrayOfTrackSection.class);

        System.out.println((float) array.getTrackSection(12).getLength());

        /*
        final JAXBContext context = JAXBContext.newInstance(ArrayOfTrackSection.class);
        final Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(array, System.out);
        */
    }

    public static ArrayOfTrackSection load(final String fileName) throws IOException {
        final BufferedReader reader = new BufferedReader(new FileReader(fileName));
        try {
            return JAXB.unmarshal(reader, ArrayOfTrackSection.class);
        } finally {
            reader.close();
        }
    }
}
