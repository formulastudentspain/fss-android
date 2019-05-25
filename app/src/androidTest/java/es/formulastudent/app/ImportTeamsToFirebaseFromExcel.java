package es.formulastudent.app;

import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.InputStream;
import java.util.Iterator;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ImportTeamsToFirebaseFromExcel {

    @Test
    public void importAllTeams() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        InputStream is = appContext.getResources().openRawResource(R.raw.fss_data);

        // Create a POI File System object
        POIFSFileSystem myFileSystem = new POIFSFileSystem(is);

        // Create a workbook using the File System
        HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);

        Sheet datatypeSheet = myWorkBook.getSheetAt(0);
        Iterator<Row> iterator = datatypeSheet.iterator();
    }




    @Test
    public void deleteAllTeams() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();

    }
}
