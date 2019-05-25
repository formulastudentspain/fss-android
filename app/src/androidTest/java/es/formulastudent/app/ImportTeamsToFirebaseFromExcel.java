package es.formulastudent.app;

import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.google.firebase.firestore.FirebaseFirestore;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import es.formulastudent.app.mvp.data.model.Car;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ImportTeamsToFirebaseFromExcel {

    @Ignore
    @Test
    public void importAllTeams() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        InputStream is = appContext.getResources().openRawResource(R.raw.fss_data);

        // Create a POI File System object
        POIFSFileSystem myFileSystem = new POIFSFileSystem(is);

        // Create a workbook using the File System
        HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);

        Sheet datatypeSheet = myWorkBook.getSheetAt(0);
        Iterator<Row> iterator = datatypeSheet.iterator();
        iterator.next();
        while(iterator.hasNext()){
            Row row = iterator.next();
            //For each row we get the three values
            Iterator<Cell> cellIterator = row.cellIterator();
            //Format is: Team Name - Car type - Car number
            String teamName = cellIterator.next().getStringCellValue();
            String carType = cellIterator.next().getStringCellValue();
            Double carNumber = cellIterator.next().getNumericCellValue();

            Map<String, Object> dataToSave = new HashMap<>();
            dataToSave.put("name", teamName);
            Car car = new Car();
            car.setNumber(carNumber.longValue());
            car.setType(carType);
            //Firebase accepts custom objects
            dataToSave.put("car", car);

            db.collection("TEAM").document().set(dataToSave);

            System.out.println(teamName+" - "+carType+" - "+carNumber.intValue());

        }
    }

    @Test
    public void deleteAllTeams() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();

    }
}
