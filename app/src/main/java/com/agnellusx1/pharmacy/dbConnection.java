package com.agnellusx1.pharmacy;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.transform.Result;

import static java.lang.Integer.parseInt;

public class dbConnection
{

    private static  final String dbUser = "DietApp";
    private static final String dbPass ="BvhApp@123";
    private static final String dbMain = "DietApp";
    private static final String ip = "192.168.5.19:49429;";

    @SuppressLint("NewApi")
    public static Connection CONN()
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Connection conn = null;
        String connectionUrl;
        try
        {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connectionUrl = "jdbc:jtds:sqlserver://"+ ip + ";databaseName=" + dbMain + ";";

            //"jdbc:jtds:sqlserver://192.168.5.19\\apexsql:1433;databaseName="+dbMain+";";
            conn = DriverManager.getConnection(connectionUrl,dbUser,dbPass);//, dbUser, dbPass);
        }
        catch (SQLException se)
        {
            Log.e("ERROR", se.getMessage());
        }
        catch (ClassNotFoundException cnfe)
        {
            Log.e("ERROR", cnfe.getMessage());
        }
        catch (Exception e)
        {
            Log.e("ERROR", e.getMessage());
        }
        return conn;
    }
    public static String getUserName(String UserCode) {
        if(UserCode==null){return UserCode;}else {
            Connection conn = CONN();
            String query = "SELECT PersonFullName from LoginTable where UserCode = '" + UserCode + "'";
            String UserName = null;
            Statement stmt;
            try {
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    UserName = rs.getString("PersonFullName");
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
            return  UserName;}
    }
    public static HashMap<String,String> getDiet(String IPCaseNumber){
        HashMap<String,String> DietResult = new HashMap<>();
        Connection conn = CONN();
        String query = "SELECT top 1 * from Diet_Status where  PatientIPCaseNumber = '"+IPCaseNumber+"' order by  DietRqstNumber DESC";
        //CONVERT(Date,RqstDate) =CONVERT(Date,getDate())

        Statement stmt;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next())
            {
                DietResult.put("DietRqstNumber",Integer.toString(rs.getInt("DietRqstNumber")));
                DietResult.put("PatientIPCaseNumber",rs.getString("PatientIPCaseNumber"));
                DietResult.put("BasicDiet",rs.getString("BasicDiet"));
                DietResult.put("ProvisionalDiagnosis",rs.getString("ProvisionalDiagnosis"));
                DietResult.put("SgaRemark",rs.getString("SgaRemark"));
                DietResult.put("DietRemark",rs.getString("DietRemark"));
                DietResult.put("DD",rs.getString("DD"));
                DietResult.put("SRD",rs.getString("SRD"));
                DietResult.put("SaltFree",rs.getString("SaltFree"));
                DietResult.put("RD",rs.getString("RD"));
                DietResult.put("HPD",rs.getString("HPD"));
                DietResult.put("LPD",rs.getString("LPD"));
                DietResult.put("BLAND",rs.getString("BLAND"));
                DietResult.put("FatFree",rs.getString("FatFree"));
                DietResult.put("ColdDiet",rs.getString("ColdDiet"));
                DietResult.put("UserCode",rs.getString("UserCode"));
                DietResult.put("RqstDate",rs.getString("RqstDate"));
                DietResult.put("NutritionScreening",rs.getString("nutrition_screening"));
                DietResult.put("SpecialCareNutrition",rs.getString("special_nutrition_care"));
                DietResult.put("DiseaseSpecificCare",rs.getString("disease_nutrition_EN"));
                DietResult.put("DiseaseSpecificCare1",rs.getString("disease_nutrition_pmt"));
                DietResult.put("nurseRemark",rs.getString("nurseRemark"));


            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return DietResult;
    }
    public static HashMap<String,String> getMeal(String IPCaseNumber, String DietRqstNumber){
        HashMap<String,String> MealResult = new HashMap<>();
        Connection conn = CONN();
        //  DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");

        String query;
        Statement stmt;
        try {
            stmt = conn.createStatement();
            query = "SELECT top 1 * from meal_status where PatientIPCaseNumber = '"+IPCaseNumber+"' and Convert(Date,RqstDate) = Convert(Date,getDate())" ;
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                MealResult.put("Breakfast",rs.getString("Meal1"));
                MealResult.put("TenAM",rs.getString("Meal2"));
                MealResult.put("Lunch",rs.getString("Meal3"));
                MealResult.put("TwoPM",rs.getString("Meal4"));
                MealResult.put("TeaTime",rs.getString("Meal5"));
                MealResult.put("SixPM",rs.getString("Meal6"));
                MealResult.put("Dinner",rs.getString("Meal7"));
                MealResult.put("Bed",rs.getString("Meal8"));
                MealResult.put("Last",rs.getString("Meal9"));
                MealResult.put("Breakfast_q",rs.getString("Meal1_Q"));
                MealResult.put("TenAM_q",rs.getString("Meal2_Q"));
                MealResult.put("Lunch_q",rs.getString("Meal3_Q"));
                MealResult.put("TwoPM_q",rs.getString("Meal4_Q"));
                MealResult.put("TeaTime_q",rs.getString("Meal5_Q"));
                MealResult.put("SixPM_q",rs.getString("Meal6_Q"));
                MealResult.put("Dinner_q",rs.getString("Meal7_Q"));
                MealResult.put("Bed_q",rs.getString("Meal8_Q"));
                MealResult.put("Last_q",rs.getString("Meal9_Q"));
                MealResult.put("swBreakfast",rs.getString("isMeal1Done"));
                MealResult.put("swTenAM",rs.getString("isMeal2Done"));
                MealResult.put("swLunch",rs.getString("isMeal3Done"));
                MealResult.put("swTwoPM",rs.getString("isMeal4Done"));
                MealResult.put("swTeaTime",rs.getString("isMeal5Done"));
                MealResult.put("swSixPM",rs.getString("isMeal6Done"));
                MealResult.put("swDinner",rs.getString("isMeal7Done"));
                MealResult.put("swBed",rs.getString("isMeal8Done"));
                MealResult.put("swLast",rs.getString("isMeal9Done"));
                MealResult.put("mUserCode",rs.getString("UserCode"));
                MealResult.put("RqstDate",rs.getString("RqstDate"));
                MealResult.put("Breakfast_t",rs.getString("Meal1_t"));
                MealResult.put("TenAM_t",rs.getString("Meal2_t"));
                MealResult.put("Lunch_t",rs.getString("Meal3_t"));
                MealResult.put("TwoPM_t",rs.getString("Meal4_t"));
                MealResult.put("TeaTime_t",rs.getString("Meal5_t"));
                MealResult.put("SixPM_t",rs.getString("Meal6_t"));
                MealResult.put("Dinner_t",rs.getString("Meal7_t"));
                MealResult.put("Bed_t",rs.getString("Meal8_t"));
                MealResult.put("Last_t",rs.getString("Meal9_t"));

                MealResult.put("Meal1deliverytime",rs.getString("deliverytimeMeal1"));
                MealResult.put("Meal2deliverytime",rs.getString("deliverytimeMeal2"));
                MealResult.put("Meal3deliverytime",rs.getString("deliverytimeMeal3"));
                MealResult.put("Meal4deliverytime",rs.getString("deliverytimeMeal4"));
                MealResult.put("Meal5deliverytime",rs.getString("deliverytimeMeal5"));
                MealResult.put("Meal6deliverytime",rs.getString("deliverytimeMeal6"));
                MealResult.put("Meal7deliverytime",rs.getString("deliverytimeMeal7"));
                MealResult.put("Meal8deliverytime",rs.getString("deliverytimeMeal8"));
                MealResult.put("Meal9deliverytime",rs.getString("deliverytimeMeal9"));

                //add all similarly SHIVAM

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return MealResult;
    }
    public static String getDietRequestCode(){

        Connection conn = CONN();
        String query = "select max(DietRqstNumber) from Diet_Status";
        String DietRequestCode = null;
        int DietCode;
        Statement stmt;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next())
            {
                DietRequestCode = rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return DietRequestCode;
    }

    public static boolean  updateDiet(String DietRqstNumber, String PatientIPCaseNumber, String ProvisionalDiagno,String Editsgaremark, String BasicDiet, int DD, int SRD, int SaltFree, int RD, int HPD, int LPD, int BLAND, int FatFree, int ColdDiet, String AdditionalRemark,String nurseRemark, String UserCode){

        try {
            Connection conn = CONN();
            String query="";
            Statement stmt;
            // int flag =1;
            stmt = conn.createStatement();
            // if(DietRqstNumber!=null) {
            query = "select DietRqstNumber from Diet_Status where CONVERT(Date,RqstDate) =CONVERT(Date,getDate()) and isActive=1 and PatientIPCaseNumber = '"+PatientIPCaseNumber+"'";

            ResultSet rs = stmt.executeQuery(query);
            Log.i("Dietq",query);
            if (rs.next()){
                //    flag=0;
                // if (Integer.toString(rs.getInt("DietRqstNumber")).equals(DietRqstNumber)){
                query = "update Diet_Status SET isDone = 0, ProvisionalDiagnosis ='" + ProvisionalDiagno + "',SgaRemark ='" + Editsgaremark + "', BasicDiet = '" + BasicDiet + "', DD = '" + DD + "', SRD = '" + SRD + "', SaltFree ='" + SaltFree + "', RD ='" + RD + "', HPD='" + HPD + "',LPD = '" + LPD + "', BLAND = '" + BLAND + "', FatFree = '" + FatFree + "', ColdDiet ='" + ColdDiet + "' ,DietRemark ='" + AdditionalRemark + "',nurseRemark ='" + nurseRemark + "', UserCode = '" + UserCode + "' where CONVERT(Date,RqstDate) = CONVERT(Date,getDate()) and isActive= 1 and DietRqstNumber = " + Integer.parseInt(DietRqstNumber);
                // }else {
                // query = "INSERT INTO Diet_Status (PatientIPCaseNumber,isDone,isActive,RqstDate,ProvisionalDiagnosis,SgaRemark,BasicDiet,DD,SRD,SaltFree,RD,HPD,LPD,BLAND,FatFree,ColdDiet,DietRemark,UserCode)  values('"+PatientIPCaseNumber+"',0,1,getDate(),'"+ProvisionalDiagno+"','"+Editsgaremark+"', '"+BasicDiet+"',  '"+DD+"', '"+SRD+"', '"+SaltFree+"', '"+RD+"', '"+HPD+"', '"+LPD+"', '"+BLAND+"', '"+FatFree+"','"+ColdDiet+"' ,'"+AdditionalRemark+"','"+UserCode+"')";
                // }
                //}

            }else
                // if(flag==1){
                query = "INSERT INTO Diet_Status (PatientIPCaseNumber,isDone,isActive,RqstDate,ProvisionalDiagnosis,SgaRemark,BasicDiet,DD,SRD,SaltFree,RD,HPD,LPD,BLAND,FatFree,ColdDiet,DietRemark,nurseRemark,UserCode)  values('"+PatientIPCaseNumber+"',0,1,getDate(),'"+ProvisionalDiagno+"','"+Editsgaremark+"', '"+BasicDiet+"',  '"+DD+"', '"+SRD+"', '"+SaltFree+"', '"+RD+"', '"+HPD+"', '"+LPD+"', '"+BLAND+"', '"+FatFree+"','"+ColdDiet+"' ,'"+AdditionalRemark+"','" + nurseRemark + "','"+UserCode+"')";
            //  }
            Log.i("Dietq1:",query);

            int success;
            success = stmt.executeUpdate(query);
            if(success > 0)
                return true;
            else
                return false;

        } catch (SQLException e) {
            e.printStackTrace();
        }catch (NullPointerException e){
            Log.e("error",e.toString());
        }

        return true;
    }
    public static boolean updateMeal(String DietRqstNumber, String PatientIPCaseNumber, HashMap<String,String>mPayload,String UserCode){

        String meal,quantity,details,mealTime;
        meal = mPayload.get("meal");
        quantity = mPayload.get("quantity");
        details = mPayload.get("details");
        mealTime=mPayload.get("mealTime");


        HashMap<String,String> oldData = getMeal(PatientIPCaseNumber,DietRqstNumber);
        int bitBreakfast = 0,bitTenAM = 0,bitLunch =0,bitTwoPM =0,bitTeaTime =0,bitSixPM = 0,bitDinner=0,bitBedTime =0;


        Connection conn = CONN();
        String query = "select DietRqstNumber,"+meal+" from meal_status where DietRqstNumber = '"+DietRqstNumber+"'";
        Statement stmt;
        try {
            stmt = conn.createStatement();
            ResultSet rs= stmt.executeQuery(query);
            query = "INSERT INTO meal_status (DietRqstNumber,PatientIPCaseNumber,RqstDate,"+meal+","+meal+"_Q, "+meal+"_t, UserCode,MealCount) values('"+DietRqstNumber+"','"+PatientIPCaseNumber+"',getDate(),'"+details+"', '"+quantity+"','"+mealTime+"','"+UserCode+"',1)";
            Log.i("query",query );

            while (rs.next()){
                if (rs.getString("DietRqstNumber").equals(DietRqstNumber)){
                    if (rs.getString(meal) != null) {
                        query = "UPDATE meal_status SET  RqstDate = getDate(), " + meal + " = '" + details + "'," + meal + "_Q='" + quantity +"', "+ meal + "_t='" + mealTime +  "',is" + meal + "Done =0 ,UserCode='" + UserCode + "',MealCount=(Case When isnull(meal1_q,'0')='0' Then 0  else 1 End +Case When isnull(meal2_q,'0')='0' Then 0  else 1 End +Case When isnull(meal3_q,'0')='0' Then 0  else 1 End +Case When isnull(meal4_q,'0')='0' Then 0  else 1 End +Case When isnull(meal5_q,'0')='0' Then 0  else 1 End +Case When isnull(meal6_q,'0')='0' Then 0  else 1 End  +Case When isnull(meal7_q,'0')='0' Then 0  else 1 End +Case When isnull(meal8_q,'0')='0' Then 0  else 1 End +Case When isnull(meal9_q,'0')='0' Then 0  else 1 End ) where  DietRqstNumber = '" + DietRqstNumber + "'";
                    }else{
                        query = "UPDATE meal_status SET  RqstDate = getDate(), " + meal + " = '" + details + "'," + meal + "_Q='" + quantity + "', "+ meal + "_t='" + mealTime +"',is" + meal + "Done =0 ,UserCode='" + UserCode + "',MealCount=(Case When isnull(meal1_q,'0')='0' Then 0  else 1 End +Case When isnull(meal2_q,'0')='0' Then 0  else 1 End +Case When isnull(meal3_q,'0')='0' Then 0  else 1 End +Case When isnull(meal4_q,'0')='0' Then 0  else 1 End +Case When isnull(meal5_q,'0')='0' Then 0  else 1 End +Case When isnull(meal6_q,'0')='0' Then 0  else 1 End  +Case When isnull(meal7_q,'0')='0' Then 0  else 1 End +Case When isnull(meal8_q,'0')='0' Then 0  else 1 End +Case When isnull(meal9_q,'0')='0' Then 0  else 1 End ) where  DietRqstNumber = '" + DietRqstNumber + "'";
                    }
                }
            }
            Log.i("query",query );
            System.out.println(query);

            int success;
            success=stmt.executeUpdate(query);
            if(success > 0) {
                query="update meal_status set mealcount=(Case When isnull(meal1_q,'0')='0' Then 0  else 1 End +Case When isnull(meal2_q,'0')='0' Then 0  else 1 End +Case When isnull(meal3_q,'0')='0' Then 0  else 1 End +Case When isnull(meal4_q,'0')='0' Then 0  else 1 End +Case When isnull(meal5_q,'0')='0' Then 0  else 1 End +Case When isnull(meal6_q,'0')='0' Then 0  else 1 End  +Case When isnull(meal7_q,'0')='0' Then 0  else 1 End +Case When isnull(meal8_q,'0')='0' Then 0  else 1 End +Case When isnull(meal9_q,'0')='0' Then 0  else 1 End )where DietRqstNumber ="+Integer.parseInt(DietRqstNumber);
                stmt.execute(query);
                query="Update Diet_Status set isDone=0 where DietRqstNumber ="+Integer.parseInt(DietRqstNumber);
                stmt.execute(query);
                return true;
            }
            else
                return false;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        return true;
    }
    public static List<String> getQuery(String query, boolean param){
        Connection conn = CONN();
        Statement stmt;
        List<String> SpinnerResult = new ArrayList<>();
        if(param)
            SpinnerResult.add("Select a diet");
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next())
            {
                SpinnerResult.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return SpinnerResult;
    }
    public static String ExportDiet(){
        Connection conn = CONN();
        Statement stmt;
        String query;
        List<String> PatientIPCaseNumbers= new ArrayList<>();
        String Result = "";
        query = "Select a.PatientIPCaseNumber from Diet_Status a LEFT JOIN meal_status ON a.DietRqstNumber = convert(int,meal_status.DietRqstNumber) where CONVERT(Date,a.RqstDate)=CONVERT(Date,getDate())";
        PatientIPCaseNumbers=getQuery(query,false);

        for (String PatientIPCaseNumber:PatientIPCaseNumbers) {

            HashMap<String,String> temp = dbConnection.getDiet(PatientIPCaseNumber);
            String tempdiet = " ";
            if(temp.get("ProvisionalDiagnosis")!=null)
                tempdiet = "ProvisionalDiagno="+temp.get("ProvisionalDiagnosis");
            if(temp.get("SgaRemark")!=null)
                tempdiet += " Sgaremark="+temp.get("SgaRemark");
            if(temp.get("DietRemark")!=null)
                tempdiet += " Remark = "+temp.get("DietRemark");
            if(temp.get("DD")=="1")
                tempdiet+=" DD ";
            if(temp.get("SRD")=="1")
                tempdiet +=" SRD ";
            if(temp.get("SaltFree")=="1")
                tempdiet+=" SaltFree ";
            if(temp.get("RD")=="1")
                tempdiet +=" RD ";
            if(temp.get("HPD")=="1")
                tempdiet+=" HPD ";
            if(temp.get("LPD")=="1")
                tempdiet +=" LPD ";
            if(temp.get("BLAND")=="1")
                tempdiet+=" BLAND ";
            if(temp.get(" FatFree")=="1")
                tempdiet +=" FatFree ";
            if(temp.get("ColdDiet")=="1")
                tempdiet+=" ColdDiet ";
            if(temp.get("BasicDiet")!=null)
                tempdiet +="Basic Diet="+temp.get("BasicDiet");
            String specialnutrition=temp.get("SpecialCareNutrition")==null?"N":temp.get("SpecialCareNutrition");
            String diseaseSpecific=temp.get("DiseaseSpecificCare")==null?"N":temp.get("DiseaseSpecificCare");
            String nutritionCare=temp.get("NutritionScreening")==null?"N":temp.get("NutritionScreening");
            String diseaseSpecificpmt=temp.get("DiseaseSpecificCare1")==null?"N":temp.get("DiseaseSpecificCare1");
            Log.i("temp",tempdiet);
            String DietRqstNumber = temp.get("DietRqstNumber");
            query ="Select BedCode from vw_BedPersonDetail where PatientIPCaseNumber='"+PatientIPCaseNumber+"'";
            List bednum=getQuery(query,false);
            String bednum1 =(bednum.size()>0)?bednum.get(0).toString():"";

            query = "Select PatientName from vw_BedPersonDetail where PatientIPCaseNumber='"+PatientIPCaseNumber+"'";

            List pname = getQuery(query,false);
            String pname1 =(bednum.size()>0)?pname.get(0).toString():"";


            Result += DietRqstNumber+","+bednum1 +","+ pname1+","+tempdiet+","+nutritionCare+","+specialnutrition+","+diseaseSpecific+","+diseaseSpecificpmt+",";

            temp=dbConnection.getMeal(PatientIPCaseNumber,DietRqstNumber);
            String meal = " ";
            if(temp.get("Breakfast")!=null)
                meal=temp.get("Breakfast_t")+","+temp.get("Breakfast_q")+" " +temp.get("Breakfast")+","+(Integer.parseInt(temp.get("swBreakfast"))==1?"Y":"N")+","+temp.get("Meal1deliverytime")+",";//add same like these below SHIVAM
            else
                meal+=",,,,";
            if(temp.get("TenAM")!=null)
                meal+=temp.get("TenAM_t")+","+temp.get("TenAM_q")+" " +temp.get("TenAM")+","+(Integer.parseInt(temp.get("swTenAM"))==1?"Y":"N")+","+temp.get("Meal2deliverytime")+",";
            else
                meal+=",,,,";
            if(temp.get("Lunch")!=null)
                meal+=temp.get("Lunch_t")+","+temp.get("Lunch_q")+ " " +temp.get("Lunch")+","+(Integer.parseInt(temp.get("swLunch"))==1?"Y":"N")+","+temp.get("Meal3deliverytime")+",";
            else
                meal+=",,,,";
            if(temp.get("TwoPM")!=null)
                meal+=temp.get("TwoPM_t")+","+temp.get("TwoPM_q")+" " +temp.get("TwoPM")+","+(Integer.parseInt(temp.get("swTwoPM"))==1?"Y":"N")+","+temp.get("Meal4deliverytime")+",";
            else
                meal+=",,,,";
            if(temp.get("TeaTime")!=null)
                meal+=temp.get("TeaTime_t")+","+temp.get("TeaTime_q")+" " +temp.get("TeaTime")+","+(Integer.parseInt(temp.get("swTeaTime"))==1?"Y":"N")+","+temp.get("Meal5deliverytime")+",";
            else
                meal+=",,,,";
            if(temp.get("SixPM")!=null)
                meal+=temp.get("SixPM_t")+","+temp.get("SixPM_q")+" " +temp.get("SixPM")+","+(Integer.parseInt(temp.get("swSixPM"))==1?"Y":"N")+","+temp.get("Meal6deliverytime")+",";
            else
                meal+=",,,,";
            if(temp.get("Dinner")!=null)
                meal+=temp.get("Dinner_t")+","+temp.get("Dinner_q")+" " +temp.get("Dinner")+","+(Integer.parseInt(temp.get("swDinner"))==1?"Y":"N")+","+temp.get("Meal7deliverytime")+",";
            else
                meal+=",,,,";
            if(temp.get("Bed")!=null)
                meal+=temp.get("Bed_t")+","+temp.get("Bed_q")+" " +temp.get("Bed")+","+(Integer.parseInt(temp.get("swBed"))==1?"Y":"N")+","+temp.get("Meal8deliverytime")+",";
            else
                meal+=",,,,";
            if(temp.get("Last")!=null)
                meal+=temp.get("Last_t")+","+temp.get("Last_q")+" " +temp.get("Last")+","+(Integer.parseInt(temp.get("swLast"))==1?"Y":"N")+","+temp.get("Meal9deliverytime")+",";
            else
                meal+=",,,,";

            Result+=meal+"\n";
            Log.i("meal",meal);

            Log.i("Result",Result);

        }
        return Result;
    }
    public static HashMap<String,String> getEachMeal(String IPCaseNumber, String meal, Date date){
        String query = "Select top 1  "+meal+", "+meal+"_q,is"+meal+"Done,RqstDate, UserCode from meal_status where PatientIPCaseNumber='"+IPCaseNumber+"' and Convert(Date,RqstDate)='"+date+"'";
        Log.i("eachmq:",query);

        HashMap<String,String> temp = new HashMap<>();

        try {
            Connection conn = dbConnection.CONN();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()){
                temp.put(meal,rs.getString(meal));
                temp.put(meal+"_q",rs.getString(meal+"_q"));
                temp.put("sw"+meal,rs.getString("is"+meal+"Done"));
                temp.put("RqstDate",rs.getString("RqstDate"));
                temp.put("mUserCode",rs.getString("UserCode"));

            }
        } catch (SQLException e) {
            Log.i("eachmq:",query);

            e.printStackTrace();
        }
        return temp;
    }

    public static HashMap<String,String> getUserDetails(String PatientIPCNumber) {

        Connection conn = CONN();
        String query = "SELECT PatientName,BedCode FROM vw_BedPersonDetail where PatientIpCaseNumber='"+PatientIPCNumber+"'";
        HashMap<String,String> patientDetails=new HashMap<>();
        Statement stmt;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                patientDetails.put("PatientName", rs.getString("PatientName"));
                patientDetails.put("BedCode",  rs.getString("BedCode"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return  patientDetails;
    }

    public static boolean updateSpecialCareDetails(String PatientIPCaseNumber,String columnName,String columnValue){

        Connection conn = CONN();
        String  query="Update Diet_Status set "+ columnName +" = ? where Convert(Date,RqstDate) = Convert(Date,getDate()) and PatientIPCaseNumber=?" ;
        PreparedStatement stmt;
        try {
            stmt = conn.prepareStatement(query);
            stmt.setString(1,columnValue);
            stmt.setString(2,PatientIPCaseNumber);
            Log.i("query",query );
            int success=stmt.executeUpdate();
            if(success > 0) {

                return true;
            }
            else
                return false;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        return false;
    }

    public static boolean clearMeal1(String DietRqstNumber){

        Connection conn = CONN();
        String query = "update meal_status set Meal1=null, Meal1_q=null, Meal1_t=null, deliverytimeMeal1=null where DietRqstNumber='"+DietRqstNumber+"'";
        PreparedStatement stmt;
        try {
            stmt = conn.prepareStatement(query);
            Log.i("query = ",query );
            int success1=stmt.executeUpdate();
            if(success1 > 0) {
                query="update meal_status set mealcount=(Case When isnull(meal1_q,'0')='0' Then 0  else 1 End +Case When isnull(meal2_q,'0')='0' Then 0  else 1 End +Case When isnull(meal3_q,'0')='0' Then 0  else 1 End +Case When isnull(meal4_q,'0')='0' Then 0  else 1 End +Case When isnull(meal5_q,'0')='0' Then 0  else 1 End +Case When isnull(meal6_q,'0')='0' Then 0  else 1 End  +Case When isnull(meal7_q,'0')='0' Then 0  else 1 End +Case When isnull(meal8_q,'0')='0' Then 0  else 1 End +Case When isnull(meal9_q,'0')='0' Then 0  else 1 End )where DietRqstNumber ="+Integer.parseInt(DietRqstNumber);
                stmt = conn.prepareStatement(query);
                int success91=stmt.executeUpdate();

                return true;
            }
            else
                return false;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        return false;
    }

    public static boolean clearMeal2(String DietRqstNumber){

        Connection conn = CONN();
        String query = "update meal_status set Meal2=null, Meal2_q=null, Meal2_t=null, deliverytimeMeal2=null where DietRqstNumber='"+DietRqstNumber+"'";
        PreparedStatement stmt;
        try {
            stmt = conn.prepareStatement(query);
            Log.i("query = ",query );
            int success2=stmt.executeUpdate();
            if(success2 > 0) {
                query="update meal_status set mealcount=(Case When isnull(meal1_q,'0')='0' Then 0  else 1 End +Case When isnull(meal2_q,'0')='0' Then 0  else 1 End +Case When isnull(meal3_q,'0')='0' Then 0  else 1 End +Case When isnull(meal4_q,'0')='0' Then 0  else 1 End +Case When isnull(meal5_q,'0')='0' Then 0  else 1 End +Case When isnull(meal6_q,'0')='0' Then 0  else 1 End  +Case When isnull(meal7_q,'0')='0' Then 0  else 1 End +Case When isnull(meal8_q,'0')='0' Then 0  else 1 End +Case When isnull(meal9_q,'0')='0' Then 0  else 1 End )where DietRqstNumber ="+Integer.parseInt(DietRqstNumber);
                stmt = conn.prepareStatement(query);
                int success91=stmt.executeUpdate();
                return true;
            }
            else
                return false;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return false;
    }

    public static boolean clearMeal3(String DietRqstNumber){

        Connection conn = CONN();
        String query = "update meal_status set Meal3=null, Meal3_q=null, Meal3_t=null, deliverytimeMeal3=null where DietRqstNumber='"+DietRqstNumber+"'";
        PreparedStatement stmt;
        try {
            stmt = conn.prepareStatement(query);
            Log.i("query = ",query );
            int success3=stmt.executeUpdate();
            if(success3 > 0) {
                query="update meal_status set mealcount=(Case When isnull(meal1_q,'0')='0' Then 0  else 1 End +Case When isnull(meal2_q,'0')='0' Then 0  else 1 End +Case When isnull(meal3_q,'0')='0' Then 0  else 1 End +Case When isnull(meal4_q,'0')='0' Then 0  else 1 End +Case When isnull(meal5_q,'0')='0' Then 0  else 1 End +Case When isnull(meal6_q,'0')='0' Then 0  else 1 End  +Case When isnull(meal7_q,'0')='0' Then 0  else 1 End +Case When isnull(meal8_q,'0')='0' Then 0  else 1 End +Case When isnull(meal9_q,'0')='0' Then 0  else 1 End )where DietRqstNumber ="+Integer.parseInt(DietRqstNumber);
                stmt = conn.prepareStatement(query);
                int success91=stmt.executeUpdate();

                return true;
            }
            else
                return false;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return false;
    }




    public static boolean clearMeal4(String DietRqstNumber){

        Connection conn = CONN();
        String query = "update meal_status set Meal4=null, Meal4_q=null, Meal4_t=null, deliverytimeMeal4=null where DietRqstNumber='"+DietRqstNumber+"'";
        PreparedStatement stmt;
        try {
            stmt = conn.prepareStatement(query);
            Log.i("query = ",query );
            int success4=stmt.executeUpdate();
            if(success4 > 0) {
                query="update meal_status set mealcount=(Case When isnull(meal1_q,'0')='0' Then 0  else 1 End +Case When isnull(meal2_q,'0')='0' Then 0  else 1 End +Case When isnull(meal3_q,'0')='0' Then 0  else 1 End +Case When isnull(meal4_q,'0')='0' Then 0  else 1 End +Case When isnull(meal5_q,'0')='0' Then 0  else 1 End +Case When isnull(meal6_q,'0')='0' Then 0  else 1 End  +Case When isnull(meal7_q,'0')='0' Then 0  else 1 End +Case When isnull(meal8_q,'0')='0' Then 0  else 1 End +Case When isnull(meal9_q,'0')='0' Then 0  else 1 End )where DietRqstNumber ="+Integer.parseInt(DietRqstNumber);
                stmt = conn.prepareStatement(query);
                int success91=stmt.executeUpdate();
                return true;
            }
            else
                return false;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return false;
    }


    public static boolean clearMeal5(String DietRqstNumber){

        Connection conn = CONN();
        String query = "update meal_status set Meal5=null, Meal5_q=null, Meal5_t=null, deliverytimeMeal5=null where DietRqstNumber='"+DietRqstNumber+"'";
        PreparedStatement stmt;
        try {
            stmt = conn.prepareStatement(query);
            Log.i("query = ",query );
            int success5=stmt.executeUpdate();
            if(success5 > 0) {
                query="update meal_status set mealcount=(Case When isnull(meal1_q,'0')='0' Then 0  else 1 End +Case When isnull(meal2_q,'0')='0' Then 0  else 1 End +Case When isnull(meal3_q,'0')='0' Then 0  else 1 End +Case When isnull(meal4_q,'0')='0' Then 0  else 1 End +Case When isnull(meal5_q,'0')='0' Then 0  else 1 End +Case When isnull(meal6_q,'0')='0' Then 0  else 1 End  +Case When isnull(meal7_q,'0')='0' Then 0  else 1 End +Case When isnull(meal8_q,'0')='0' Then 0  else 1 End +Case When isnull(meal9_q,'0')='0' Then 0  else 1 End )where DietRqstNumber ="+Integer.parseInt(DietRqstNumber);
                stmt.execute(query);  stmt = conn.prepareStatement(query);
                int success91=stmt.executeUpdate();
                return true;
            }
            else
                return false;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return false;
    }


    public static boolean clearMeal6(String DietRqstNumber){

        Connection conn = CONN();
        String query = "update meal_status set Meal6=null, Meal6_q=null, Meal6_t=null, deliverytimeMeal6=null where DietRqstNumber='"+DietRqstNumber+"'";
        PreparedStatement stmt;
        try {
            stmt = conn.prepareStatement(query);
            Log.i("query = ",query );
            int success6=stmt.executeUpdate();
            if(success6 > 0) {
                query="update meal_status set mealcount=(Case When isnull(meal1_q,'0')='0' Then 0  else 1 End +Case When isnull(meal2_q,'0')='0' Then 0  else 1 End +Case When isnull(meal3_q,'0')='0' Then 0  else 1 End +Case When isnull(meal4_q,'0')='0' Then 0  else 1 End +Case When isnull(meal5_q,'0')='0' Then 0  else 1 End +Case When isnull(meal6_q,'0')='0' Then 0  else 1 End  +Case When isnull(meal7_q,'0')='0' Then 0  else 1 End +Case When isnull(meal8_q,'0')='0' Then 0  else 1 End +Case When isnull(meal9_q,'0')='0' Then 0  else 1 End )where DietRqstNumber ="+Integer.parseInt(DietRqstNumber);
                stmt = conn.prepareStatement(query);
                int success91=stmt.executeUpdate();
                return true;
            }
            else
                return false;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return false;
    }


    public static boolean clearMeal7(String DietRqstNumber){

        Connection conn = CONN();
        String query = "update meal_status set Meal7=null, Meal7_q=null, Meal7_t=null, deliverytimeMeal7=null where DietRqstNumber='"+DietRqstNumber+"'";
        PreparedStatement stmt;
        try {
            stmt = conn.prepareStatement(query);
            Log.i("query = ",query );
            int success7=stmt.executeUpdate();
            if(success7 > 0) {
                query="update meal_status set mealcount=(Case When isnull(meal1_q,'0')='0' Then 0  else 1 End +Case When isnull(meal2_q,'0')='0' Then 0  else 1 End +Case When isnull(meal3_q,'0')='0' Then 0  else 1 End +Case When isnull(meal4_q,'0')='0' Then 0  else 1 End +Case When isnull(meal5_q,'0')='0' Then 0  else 1 End +Case When isnull(meal6_q,'0')='0' Then 0  else 1 End  +Case When isnull(meal7_q,'0')='0' Then 0  else 1 End +Case When isnull(meal8_q,'0')='0' Then 0  else 1 End +Case When isnull(meal9_q,'0')='0' Then 0  else 1 End )where DietRqstNumber ="+Integer.parseInt(DietRqstNumber);
                stmt = conn.prepareStatement(query);
                int success91=stmt.executeUpdate();
            }
            else
                return false;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return false;
    }


    public static boolean clearMeal8(String DietRqstNumber){

        Connection conn = CONN();
        String query = "update meal_status set Meal8=null, Meal8_q=null, Meal8_t=null, deliverytimeMeal8=null where DietRqstNumber='"+DietRqstNumber+"'";
        PreparedStatement stmt;
        try {
            stmt = conn.prepareStatement(query);
            Log.i("query = ",query );
            int success8=stmt.executeUpdate();
            if(success8 > 0) {
                query="update meal_status set mealcount=(Case When isnull(meal1_q,'0')='0' Then 0  else 1 End +Case When isnull(meal2_q,'0')='0' Then 0  else 1 End +Case When isnull(meal3_q,'0')='0' Then 0  else 1 End +Case When isnull(meal4_q,'0')='0' Then 0  else 1 End +Case When isnull(meal5_q,'0')='0' Then 0  else 1 End +Case When isnull(meal6_q,'0')='0' Then 0  else 1 End  +Case When isnull(meal7_q,'0')='0' Then 0  else 1 End +Case When isnull(meal8_q,'0')='0' Then 0  else 1 End +Case When isnull(meal9_q,'0')='0' Then 0  else 1 End )where DietRqstNumber ="+Integer.parseInt(DietRqstNumber);
                stmt = conn.prepareStatement(query);
                int success91=stmt.executeUpdate();
            }
            else
                return false;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return false;
    }


    public static boolean clearMeal9(String DietRqstNumber){

        Connection conn = CONN();
        String query = "update meal_status set Meal9=null, Meal9_q=null, Meal9_t=null, deliverytimeMeal9=null where DietRqstNumber='"+DietRqstNumber+"'";
        PreparedStatement stmt;
        try {
            stmt = conn.prepareStatement(query);
            Log.i("query = ",query );
            int success9=stmt.executeUpdate();
            if(success9 > 0) {

                query="update meal_status set mealcount=(Case When isnull(meal1_q,'0')='0' Then 0  else 1 End +Case When isnull(meal2_q,'0')='0' Then 0  else 1 End +Case When isnull(meal3_q,'0')='0' Then 0  else 1 End +Case When isnull(meal4_q,'0')='0' Then 0  else 1 End +Case When isnull(meal5_q,'0')='0' Then 0  else 1 End +Case When isnull(meal6_q,'0')='0' Then 0  else 1 End  +Case When isnull(meal7_q,'0')='0' Then 0  else 1 End +Case When isnull(meal8_q,'0')='0' Then 0  else 1 End +Case When isnull(meal9_q,'0')='0' Then 0  else 1 End )where DietRqstNumber ="+Integer.parseInt(DietRqstNumber);
                stmt = conn.prepareStatement(query);
                int success91=stmt.executeUpdate();

                return true;
            }
            else
                return false;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return false;
    }
}