package np.gov.lgcpd.database;

import android.content.ContentValues;
import android.content.Context;


//import android.database.Cursor;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;
import net.sqlcipher.Cursor;

//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import np.gov.lgcpd.AdaptersAndModel.LSPDetail;
import np.gov.lgcpd.AdaptersAndModel.SM;
import np.gov.lgcpd.AdaptersAndModel.SMDetails;
import np.gov.lgcpd.Helper.Constants;

/**
 * Created by asis on 12/2/16.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private String TABLE_NAME_SM = "tbl_sm";
    private String TABLE_NAME_LSP = "tbl_lsp";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    private Context context;

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context,name,factory,version);
       this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        SQLiteDatabase.loadLibs(context);

        String CREATE_SM_TABLE = "CREATE TABLE " + TABLE_NAME_SM + "("
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

        String CREATE_LSP_TABLE = "CREATE TABLE " + TABLE_NAME_LSP + "("
                + "id" + " INTEGER PRIMARY KEY,"
                +"id_remote" + TEXT_TYPE + COMMA_SEP
                +"name" + TEXT_TYPE + COMMA_SEP
                +"address" + TEXT_TYPE + COMMA_SEP
                +"office_phone" + TEXT_TYPE + COMMA_SEP
                +"contact_person" + TEXT_TYPE + COMMA_SEP
                +"chairman" + TEXT_TYPE + COMMA_SEP
                +"contact_email" + TEXT_TYPE + COMMA_SEP
                +"contact_phone" + TEXT_TYPE + COMMA_SEP
                +"contact_mobile" + TEXT_TYPE + COMMA_SEP
                +"chairman_mobile" + TEXT_TYPE + COMMA_SEP
                +"chairman_email" +TEXT_TYPE + COMMA_SEP
                +"remark" + TEXT_TYPE
                + ")";

        db.execSQL(CREATE_SM_TABLE);
        db.execSQL(CREATE_LSP_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        SQLiteDatabase.loadLibs(context);
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_SM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_LSP);

        // Create tables again
        onCreate(db);
    }

    public boolean addSM(SMDetails sm){

        SQLiteDatabase.loadLibs(context);

        SQLiteDatabase db = this.getWritableDatabase(Constants.DATABASE_PASSWORD);
        ContentValues values = new ContentValues();

        String Query = "Select * from " + TABLE_NAME_SM + " where " + "id_remote" + " = " + sm.getId();
        Cursor cursor = db.rawQuery(Query, null);

        if(cursor.getCount() <= 0){

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

            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    public boolean deleteSM(String id){

        SQLiteDatabase.loadLibs(context);

        SQLiteDatabase db = this.getWritableDatabase(Constants.DATABASE_PASSWORD);

        return db.delete(TABLE_NAME_SM, "id_remote" + "=" + id, null) > 0;

    }

    public boolean addLSP(LSPDetail lsp){

        SQLiteDatabase.loadLibs(context);

        SQLiteDatabase db = this.getWritableDatabase(Constants.DATABASE_PASSWORD);
        ContentValues values = new ContentValues();

        String Query = "Select * from " + TABLE_NAME_LSP + " where " + "id_remote" + " = " + lsp.getId();
        Cursor cursor = db.rawQuery(Query, null);

        if(cursor.getCount() <= 0){

            values.put("id_remote", lsp.getId());
            values.put("name", lsp.getName());
            values.put("address",lsp.getAddress());
            values.put("office_phone",lsp.getOfficePhone());
            values.put("contact_person",lsp.getContactPerson());
            values.put("chairman",lsp.getChairman());
            values.put("contact_email",lsp.getChairmanEmail());
            values.put("contact_phone",lsp.getContactPhone());
            values.put("contact_mobile",lsp.getContactMobile());
            values.put("chairman_mobile",lsp.getChairmanMobile());
            values.put("chairman_email",lsp.getChairmanEmail());
            values.put("remark",lsp.getRemark());

            db.insert(TABLE_NAME_LSP, null, values);
            db.close();

            cursor.close();
            return true;
        }
        cursor.close();
        return false;

    }

    public boolean deleteLSP(String id){

        SQLiteDatabase.loadLibs(context);

        SQLiteDatabase db = this.getWritableDatabase(Constants.DATABASE_PASSWORD);

        return db.delete(TABLE_NAME_LSP, "id_remote" + "=" + id, null) > 0;

    }

    public SMDetails getSM(String id){

        SQLiteDatabase.loadLibs(context);

        SQLiteDatabase db = this.getReadableDatabase(Constants.DATABASE_PASSWORD);

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

    public LSPDetail getLSP(String id){

        SQLiteDatabase.loadLibs(context);

        SQLiteDatabase db = this.getReadableDatabase(Constants.DATABASE_PASSWORD);

        Cursor cursor = db.query(TABLE_NAME_LSP,
                new String[] {   "id",
                        "id_remote",
                        "name",
                        "address",
                        "office_phone",
                        "contact_person",
                        "chairman",
                        "contact_email",
                        "contact_phone",
                        "contact_mobile",
                        "chairman_mobile",
                        "chairman_email",
                        "remark"
                },
                "id_remote" + "=?",
                new String[] { String.valueOf(id) },
                null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        LSPDetail d = new LSPDetail(
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),
                cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8),
                cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(12)
        );
        return d;
    }

    public List<SM> getAllSM(){

        SQLiteDatabase.loadLibs(context);

        List<SM> list = new ArrayList<SM>();
        // Select All Query
        String selectQuery = "SELECT  id_remote,name,phone,address,vdc FROM " + TABLE_NAME_SM ;
        //+ "Where subject =" + subject;

        SQLiteDatabase db = this.getReadableDatabase(Constants.DATABASE_PASSWORD);
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                SM d = new SM();

                d.setId(cursor.getString(0));
                d.setName(cursor.getString(1));
                d.setPhone(cursor.getString(2));
                d.setAddress(cursor.getString(3));
                d.setVdc_ward(cursor.getString(4));

                // Adding contact to list
                list.add(d);
            } while (cursor.moveToNext());
        }

        db.close();
        cursor.close();

        return list;
    }

    public List<SM> getAllLSP(){

        SQLiteDatabase.loadLibs(context);

        List<SM> list = new ArrayList<SM>();
        // Select All Query
        String selectQuery = "SELECT  id_remote,name,office_phone,address,contact_email FROM " + TABLE_NAME_LSP ;
        //+ "Where subject =" + subject;

        SQLiteDatabase db = this.getReadableDatabase(Constants.DATABASE_PASSWORD);
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                SM d = new SM();

                d.setId(cursor.getString(0));
                d.setName(cursor.getString(1));
                d.setPhone(cursor.getString(2));
                d.setAddress(cursor.getString(3));
                d.setVdc_ward(cursor.getString(4));

                // Adding contact to list
                list.add(d);
            } while (cursor.moveToNext());
        }

        db.close();
        cursor.close();

        return list;
    }
}
