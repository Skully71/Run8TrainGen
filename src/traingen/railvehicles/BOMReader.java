package traingen.railvehicles;

import java.io.FilterReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;

/**
 *
 */
public class BOMReader extends FilterReader {
    /**
     * Creates a new filtered reader.
     *
     * @param in a Reader object providing the underlying stream.
     * @throws NullPointerException if <code>in</code> is <code>null</code>
     */
    public BOMReader(final Reader in) throws IOException {
        super(new PushbackReader(in, 2));
        final char bom[] = new char[2];
        final int read  = this.in.read(bom);
        int offset = 0;

        if (read >= 1 && bom[0] == 65279) {
            offset = 1;
        }

        if (read > 0)
            ((PushbackReader) this.in).unread(bom, offset, read - offset);
    }
}
