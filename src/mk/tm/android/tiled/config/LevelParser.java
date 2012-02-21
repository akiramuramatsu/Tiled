package mk.tm.android.tiled.config;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import org.andengine.util.StreamUtils;
import org.andengine.util.debug.Debug;
import org.andengine.util.level.LevelLoader;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 * User: Tome
 * Date: 20.2.12
 * Time: 14:30
 * To change this template use File | Settings | File Templates.
 */
class LevelParser extends DefaultHandler implements GameConstants {
    private static final String TAG_LEVEL = "level";
    private static final String TAG_CELL = "cell";
    private static final String TAG_START = "start";
    private static final String TAG_NONEXISTENT = "nonexistent";
    private static final String TAG_UNAVAILABLE = "unavailable";

    private static final String ATTRIBUTE_SIZE_X = "size_x";
    private static final String ATTRIBUTE_SIZE_Y = "size_y";

    private static final String ATTRIBUTE_CELL_X = "x";
    private static final String ATTRIBUTE_CELL_Y = "y";

    final LevelConfiguration mConfig;

    private boolean inStart;
    private boolean inUnavailable;
    private boolean inNonexistent;

    LevelParser(int pWorld, int pLevel, Context context) {
        mConfig = new LevelConfiguration();

        InputStream pInputStream = null;
        try {
            pInputStream = context.getAssets().open(String.format("level/%s_%s.lvl", pWorld, pLevel));
            final SAXParserFactory spf = SAXParserFactory.newInstance();
            final SAXParser sp = spf.newSAXParser();

            final XMLReader xr = sp.getXMLReader();

            xr.setContentHandler(this);

            xr.parse(new InputSource(new BufferedInputStream(pInputStream)));
        } catch (final SAXException se) {
            Debug.e(se);
        } catch (final ParserConfigurationException pe) {
            Debug.e(pe);
        } catch (IOException e) {
            Debug.e(LOGTAG, e);
        } finally {
            StreamUtils.close(pInputStream);
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);

        if (TAG_START.equals(localName))
            this.inStart = true;
        if (TAG_UNAVAILABLE.equals(localName))
            this.inUnavailable = true;
        if (TAG_NONEXISTENT.equals(localName))
            this.inNonexistent = true;

        if (TAG_LEVEL.equals(localName)) {
            this.mConfig.setSize(Integer.valueOf(attributes.getValue(ATTRIBUTE_SIZE_X)),
                    Integer.valueOf(attributes.getValue(ATTRIBUTE_SIZE_Y)));
        }
        if (TAG_CELL.equals(localName)) {
            final int x = Integer.valueOf(attributes.getValue(ATTRIBUTE_CELL_X));
            final int y = Integer.valueOf(attributes.getValue(ATTRIBUTE_CELL_Y));

            if (this.inStart)
                this.mConfig.setStartCell(new Point(x, y));
            if (this.inNonexistent)
                this.mConfig.addNonexistantCell(new Point(x, y));
            if (this.inUnavailable)
                this.mConfig.addUnavailableCell(new Point(x, y));
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);

        if (TAG_START.equals(localName))
            this.inStart = false;
        if (TAG_UNAVAILABLE.equals(localName))
            this.inUnavailable = false;
        if (TAG_NONEXISTENT.equals(localName))
            this.inNonexistent = false;
    }

    LevelConfiguration getConfiguration() {
        return this.mConfig;
    }
}
