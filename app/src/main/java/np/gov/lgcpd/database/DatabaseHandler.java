package np.gov.lgcpd.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import np.gov.lgcpd.AdaptersAndModel.SMDetails;

/**
 * Created by asis on 12/2/16.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private String TABLE_NAME_SM = "tbl_sm";
    private String TABLE_NAME_LSP = "tbl_lsp";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context,name,factory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_QUESTIONS_TABLE = "CREATE TABLE " + TABLE_NAME_SM + "("
                + "id" + " INTEGER PRIMARY KEY,"
                +"id_remote" + TEXT_TYPE + COMMA_SEP
                + "name" + TEXT_TYPE + COMMA_SEP
                + "email" + TEXT_TYPE + COMMA_SEP
                + "img_name" + TEXT_TYPE + COMMA_SEP
                + "phone" + TEXT_TYPE + COMMA_SEP
                + "address"+ TEXT_TYPE + COMMA_SEP
                + "lsp_id"+ TEXT_TYPE + COMMA_SEP
                + "hired"+ TEXT_TYPE + COMMA_SEP
                + "vdc"+ TEXT_TYPE + COMMA_SEP
                + "sex"+ TEXT_TYPE + COMMA_SEP
                + "dalit"+ TEXT_TYPE + COMMA_SEP
                + "janajati"+ TEXT_TYPE + COMMA_SEP
                + "dag"+ TEXT_TYPE + COMMA_SEP
                + "education"+ TEXT_TYPE + COMMA_SEP
                + "work_experience"+ TEXT_TYPE + COMMA_SEP
                + "belong_to"+ TEXT_TYPE + COMMA_SEP
                + "tranning"+ TEXT_TYPE + COMMA_SEP
                + "entry_date"+ TEXT_TYPE + COMMA_SEP
                + "last_modified_date"+ TEXT_TYPE + COMMA_SEP
                + "remarks"+ TEXT_TYPE + COMMA_SEP
                + "district_id"+ TEXT_TYPE + COMMA_SEP
                + "type"+ TEXT_TYPE
                + ")";

        db.execSQL(CREATE_QUESTIONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_SM);

        // Create tables again
        onCreate(db);
    }

    public void addSM(SMDetails sm){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put("id_remote", sm.getId());
        values.put("name",sm.getName());
        values.put("email",sm.getEmail());
        values.put("img_name",sm.getImg_name());
        values.put("phone",sm.getPhone());
        values.put("address",sm.getAddress());
        values.put("lsp_id",sm.getLsp_id());
        values.put("hired",sm.getHired());
        values.put( "vdc",sm.getVdc());
        values.put("sex",sm.getSex());
        values.put( "dalit",sm.getDalit());
        values.put( "janajati",sm.getJanajati());
        values.put( "dag",sm.getDag());
        values.put( "education",sm.getEducation());
        values.put( "work_experience",sm.getWork_experience());
        values.put( "belong_to",sm.getBelong_to());
        values.put( "tranning",sm.getTraining());
        values.put( "entry_date",sm.getEntry_date());
        values.put("last_modified_date",sm.getLast_date_modify());
        values.put("remarks",sm.getRemarks());
        values.put("district_id",sm.getDistrict_id());
        values.put("type",sm.getType());

        db.insert(TABLE_NAME_SM, null, values);
        db.close();
    }

    public SMDetails getSM(String id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME_SM,
                new String[] {   "id",
                        "id_remote",
                         "name" ,
                         "email" ,
                         "img_name",
                         "phone" ,
                        "address",
                         "lsp_id",
                         "hired",
                         "vdc",
                         "sex",
                         "dalit",
                         "janajati",
                         "dag",
                         "education",
                         "work_experience",
                         "belong_to",
                         "tranning",
                         "entry_date",
                         "last_modified_date",
                         "remarks",
                         "district_id",
                         "type"
                },
                "id_remote" + "=?",
                new String[] { String.valueOf(id) },
                null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        SMDetails d = new SMDetails(
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),
                cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8),
                cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(12),
                cursor.getString(13), cursor.getString(14), cursor.getString(15), cursor.getString(16),
                cursor.getString(17), cursor.getString(18), cursor.getString(19), cursor.getString(20),
                cursor.getString(21), cursor.getString(22)
                );
        return d;
    }

    public List<SMDetails> getAllSM(){
        List<SMDetails> list = new ArrayList<SMDetails>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME_SM ;
        //+ "Where subject =" + subject;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                SMDetails d = new SMDetails();

                d.setId(cursor.getString(1));
                d.setName(cursor.getString(2));
                d.setEmail(cursor.getString(3));
                d.setImg_name(cursor.getString(4));
                d.setPhone(cursor.getString(5));
                d.setAddress(cursor.getString(6));
                d.setLsp_id(cursor.getString(7));
                d.setHired(cursor.getString(8));
                d.setVdc(cursor.getString(9));
                d.setSex(cursor.getString(10));
                d.setDalit(cursor.getString(11));
                d.setJanajati(cursor.getString(12));
                d.setDag(cursor.getString(13));
                d.setEducation(cursor.getString(14));
                d.setWork_experience(cursor.getString(15));
                d.setBelong_to(cursor.getString(16));
                d.setTraining(cursor.getString(17));
                d.setEntry_date(cursor.getString(18));
                d.setLast_date_modify(cursor.getString(19));
                d.setRemarks(cursor.getString(20));
                d.setDistrict_id(cursor.getString(21));
                d.setType(cursor.getString(22));

                // Adding contact to list
                list.add(d);
            } while (cursor.moveToNext());
        }

        db.close();
        cursor.close();

        return list;
    }
}
