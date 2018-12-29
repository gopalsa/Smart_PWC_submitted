package nec.cst.pra.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nec.cst.pra.R;
import nec.cst.pra.household.Mainbean;
import nec.cst.pra.household.SurveyItem;
import nec.cst.pra.survey.PrintSurveyItem;
import nec.cst.pra.survey.Survey;

public class AppConfig {

    private static Font catFont = new Font(Font.FontFamily.HELVETICA, 14,
            Font.BOLD);

    private static Font responseFont = new Font(Font.FontFamily.HELVETICA, 10,
            Font.BOLD);
    private static Font subFont = new Font(Font.FontFamily.HELVETICA, 8,
            Font.NORMAL);

    public static String iplocalfarmer = "192.168.43.233/coconut/farmer";
    public static String iplocalpro = "192.168.43.233/coconut/product";
    public static String iplocalplot = "192.168.43.233/coconut/plot";
    public static String iplocalpop = "192.168.43.233/coconut/pop";
    public static String iplocalanimal = "192.168.43.233/coconut/animal";
    public static String iplocalservice = "192.168.43.233/coconut/service";
    public static String iplocalprofile = "192.168.43.233/coconut/profile";
    public static String iplocaltree = "192.168.43.233/coconut/tree";

    public static String ipcloud = "coconutfpo.smartfpo.com/pra";
    public static String ipcloudUBA = "coconutfpo.smartfpo.com/breedcontest";
    public static String URL_UPLOAD_IMAGES = "http://" + ipcloud + "/fileUpload.php";
    public static String imagePath = "http://coconutfpo.smartfpo.com/pra/uploads/";

    public static String ipsms = "192.168.43.233/android_sms/msg91";

    // Server user login url
    public static String URL_REGISTER = "http://" + ipcloud + "/create_vrp.php";
    public static String URL_LOGIN = "http://" + ipcloud + "/get_vrp_login.php";
    public static String URL_UPDATE = "http://" + ipcloud + "/update_vrp.php";
    public static String URL_CREATE_NEW_VRP = "http://" + ipcloud + "/create_new_vrp.php";
    public static String URL_FARMER_DETAIL = "http://" + ipcloud + "/get_vrp_details.php";

    public static String URL_ALL_PROFILE = "http://" + ipcloud + "/get_all_profile.php";
    public static String URL_PROFILE_CREATE = "http://" + ipcloud + "/create_profile.php";
    public static String URL_PROFILE_UPDATE = "http://" + ipcloud + "/update_profile.php";

    public static String URL_CREATE_SURVEY = "http://" + ipcloud + "/data/create_survey.php";
    public static String URL_CREATE_OFFLINE_SURVEY = "http://" + ipcloud + "/data/create_offline_survey.php";
    public static String URL_GET_All_SURVEY = "http://" + ipcloudUBA + "/survey/get_all_uba_report.php";
    public static String URL_GET_USER_SURVEY = "http://" + ipcloudUBA + "/data/get_user_survey_report.php";

    // server URL configuration
    public static final String URL_REQUEST_SMS = "http://" + ipcloud + "/request_sms.php";
    public static final String URL_VERIFY_OTP = "http://" + ipcloud + "/verify_otp.php";
    public static String URL_CREATE_SURVEYANS = "http://" + ipcloudUBA + "/survey/create_uba_ans.php";
    public static String URL_GET_All_SURVEYANS = "http://" + ipcloudUBA + "/survey/get_all_uba_ans.php";

    // SMS provider identification
    // It should match with your SMS gateway origin
    // You can use  MSGIND, TESTER and ALERTS as sender ID
    // If you want custom sender Id, approve MSG91 to get one
    public static final String SMS_ORIGIN = "CSFARM";

    // special character to prefix the otp. Make sure this character appears only once in the sms
    public static final String OTP_DELIMITER = ":";


    private static final Map<String, Integer> attendances = new HashMap<String, Integer>() {{

        put("Informal Exposure Walk", R.string.InformalExposureWalk);

        put("Historical Timeline", R.string.HistoricalTimeline);

        put("Gender Analysis", R.string.GenderAnalysis);

        put("Village Resource Mapping (Village, water and soil maps)", R.string.VillageMapping);

        put("Transect Walk", R.string.VillageTransectWalk);

        put("Seasonal Analysis", R.string.SeasonalAnalysis);

        put("Watershed Resources Analysis (forest, agriculture and other resources)", R.string.ForestResourcesAnalysis);

        put("Livelihood Analysis", R.string.LivelihoodAnalysis);

        put("Institutional Analysis", R.string.InstitutionalAnalysis);

        put("S.W.O.T. Analysis", R.string.SWOTAnalysis);

        put("Wealth Ranking", R.string.WealthRanking);
    }};

    public static final Integer getProcess(String name) {
        return attendances.get(name);
    }

    public static final String YOUTUBE_API_KEY = "AIzaSyCG25IwSEZcJuF5Te7kko9XawkHaEJ48Ws";


    public static void addMetaData(Document document) {
        document.addTitle("My first PDF");
        document.addSubject("Using iText");
        document.addKeywords("Java, PDF, iText");
        document.addAuthor("Lars Vogel");
        document.addCreator("Lars Vogel");
    }

    public static void addContent(Document document, Mainbean survey, Context context) throws Exception {

        ArrayList<SurveyItem> surveyItems = AppConfig.loadHouseQuestions(context);
        PdfPTable table1 = new PdfPTable(1);
        table1.setWidthPercentage(100);
        table1.setWidths(new int[]{1});
        for (int i = 0; i < surveyItems.size(); i++) {

            SurveyItem surveyItem = surveyItems.get(i);
            String value = "Not Available";
            try {
                Class<?> clazz = survey.getClass();
                Field field = clazz.getField(surveyItem.getField()); //Note, this can throw an exception if the field doesn't exist.
                value = (String) field.get(survey);
                value = value.replaceAll(("\n"), "");
                if (value.length() <= 0) {
                    value = "Not Available";
                }
            } catch (Exception e) {
                value = "Not Available";
            }
            table1.addCell(createTextCell(surveyItem.getQuestion(),
                    catFont));
            table1.addCell(createTextCell(value,
                    new Font(Font.FontFamily.HELVETICA, 14,
                            Font.NORMAL)));

        }
        document.add(table1);

    }


    public static void addContent(Document document, Survey survey, Context context) throws Exception {

        ArrayList<SurveyItem> surveyItems = AppConfig.loadVillageQuestions(context);
        PdfPTable table1 = new PdfPTable(1);
        table1.setWidthPercentage(100);
        table1.setWidths(new int[]{1});
        for (int i = 0; i < surveyItems.size(); i++) {

            SurveyItem surveyItem = surveyItems.get(i);
            String value = "Not Available";
            try {
                Class<?> clazz = survey.getClass();
                Field field = clazz.getField(surveyItem.getField()); //Note, this can throw an exception if the field doesn't exist.
                value = (String) field.get(survey);
                value = value.replaceAll(("\n"), "");
                if (value.length() <= 0) {
                    value = "Not Available";
                }
            } catch (Exception e) {
                value = "Not Available";
            }
            table1.addCell(createTextCell(surveyItem.getQuestion(),
                    catFont));
            table1.addCell(createTextCell(value,
                    new Font(Font.FontFamily.HELVETICA, 14,
                            Font.NORMAL)));

        }
        document.add(table1);

    }


    public static PdfPCell createImageCell(Image path) throws DocumentException, IOException {
        PdfPCell cell = new PdfPCell(path, true);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setFixedHeight(400);
        return cell;
    }

    public static PdfPCell createTextCell(String text, Font font) throws DocumentException, IOException {
        PdfPCell cell = new PdfPCell();
        Paragraph p = new Paragraph(text, font);
        p.setAlignment(Element.ALIGN_LEFT);
        cell.addElement(p);
        cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    public static ArrayList<SurveyItem> loadHouseQuestions(Context context) {
        ArrayList<SurveyItem> surveyItems = new ArrayList<>();

        surveyItems.add(new SurveyItem("student", "studentid", false));
        surveyItems.add(new SurveyItem("village", "village", false));
        surveyItems.add(new SurveyItem("Ward No", "wardNo", false));
        surveyItems.add(new SurveyItem("District", "district", false));
        surveyItems.add(new SurveyItem("Gram Panchayat", "gramPanchayat", false));
        surveyItems.add(new SurveyItem("Block", "block", false));
        surveyItems.add(new SurveyItem("State", "state", false));
        surveyItems.add(new SurveyItem("Name", "name", false));
        surveyItems.add(new SurveyItem("Gender", "gender", false));
        surveyItems.add(new SurveyItem("Age", "age", false));
        surveyItems.add(new SurveyItem("Household", "household", false));
        surveyItems.add(new SurveyItem("Contact No", "contactNo", false));
        surveyItems.add(new SurveyItem("Id Card Type", "idCardType", false));
        surveyItems.add(new SurveyItem("Id Card No", "idCardNo", false));
        surveyItems.add(new SurveyItem("Household Id", "householdId", false));
        surveyItems.add(new SurveyItem("Name Of head", "nameOfhead", false));
        surveyItems.add(new SurveyItem("House Gender", "houseGender", false));
        surveyItems.add(new SurveyItem("Category", "category", false));
        surveyItems.add(new SurveyItem("Poverty Status", "povertyStatus", false));
        surveyItems.add(new SurveyItem("Own House", "ownHouse", false));
        surveyItems.add(new SurveyItem("Type Of House", "typeOfHouse", false));
        surveyItems.add(new SurveyItem("Toilet", "toilet", false));
        surveyItems.add(new SurveyItem("Drainage", "drainage", false));
        surveyItems.add(new SurveyItem("Waste System", "wasteSystem", false));
        surveyItems.add(new SurveyItem("CompostPit", "compostPit", false));
        surveyItems.add(new SurveyItem("Biogas Plant", "biogasPlant", false));
        surveyItems.add(new SurveyItem("Annual InCome", "annualInncom", false));
        surveyItems.add(new SurveyItem("Migrate Work", "migrateWork", false));
        surveyItems.add(new SurveyItem("Family Migrat", "familyMigrat", false));
        surveyItems.add(new SurveyItem("Migrate days", "migratedays", false));
        surveyItems.add(new SurveyItem("Migrate Place", "migratePlace", false));
        surveyItems.add(new SurveyItem("Elect Household", "electHousehold", false));
        surveyItems.add(new SurveyItem("Elect Day", "electDay", false));
        surveyItems.add(new SurveyItem("Elect Light", "electLight", false));
        surveyItems.add(new SurveyItem("Elect Cooking", "electCooking", false));
        surveyItems.add(new SurveyItem("Elect Cullah", "electCullah", false));
        surveyItems.add(new SurveyItem("Total", "total", false));
        surveyItems.add(new SurveyItem("Cultivable Area", "cultivableArea", false));
        surveyItems.add(new SurveyItem("Irrigated Area", "irrigatedArea", false));
        surveyItems.add(new SurveyItem("Un Irrigated", "unIrrigated", false));
        surveyItems.add(new SurveyItem("Barran", "barran", false));
        surveyItems.add(new SurveyItem("UnCultivate Area", "unCultArea", false));
        surveyItems.add(new SurveyItem("Irrigation", "irrigation", false));
        surveyItems.add(new SurveyItem("IrrSystem", "irrSystem", false));
        surveyItems.add(new SurveyItem("Sign", "sign", false));
        surveyItems.add(new SurveyItem("Survey", "survey", false));
        return surveyItems;

    }

    public static ArrayList<SurveyItem> loadVillageQuestions(Context context) {
        ArrayList<SurveyItem> surveyItems = new ArrayList<>();

        surveyItems.add(new SurveyItem("Name", "name", true));
        surveyItems.add(new SurveyItem("Gram Panchayat", "gramPanchayat", true));
        surveyItems.add(new SurveyItem("Wards", "wards", true));
        surveyItems.add(new SurveyItem("Block", "block", true));
        surveyItems.add(new SurveyItem("District", "district", true));
        surveyItems.add(new SurveyItem("State", "state", true));
        surveyItems.add(new SurveyItem("Constituency", "constituency", true));
        surveyItems.add(new SurveyItem("Distance District HQ", "distanceDistrictHQ", true));
        surveyItems.add(new SurveyItem("Village Area", "villageArea", true));
        surveyItems.add(new SurveyItem("Arable Land Agriculture Area", "arableLandAgricultureArea", true));
        surveyItems.add(new SurveyItem("Forest Area", "forestArea", true));
        surveyItems.add(new SurveyItem("Housing AbadiArea", "housingAbadiArea", true));
        surveyItems.add(new SurveyItem("Area Under Water Bodies", "areaUnderWaterBodies", true));
        surveyItems.add(new SurveyItem("CommonLands Area", "commonLandsArea", true));
        surveyItems.add(new SurveyItem("Waste Land", "wasteLand", true));
        surveyItems.add(new SurveyItem("Water Table", "waterTable", true));
        surveyItems.add(new SurveyItem("GeoTag", "geoTag", true));
        surveyItems.add(new SurveyItem("Distance Highway", "distanceHighway", true));
        surveyItems.add(new SurveyItem("Village Connected PaccaRoad", "villageConnectedPaccaRoad", true));
        surveyItems.add(new SurveyItem("Road Length", "roadLength", true));
        surveyItems.add(new SurveyItem("Year Of Construction", "yearOfConstruction", true));
        surveyItems.add(new SurveyItem("Scheme Constructed", "schemeConstructed", true));
        surveyItems.add(new SurveyItem("Present Status", "presentStatus", true));
        surveyItems.add(new SurveyItem("Length Of Kachha", "lengthOfKachha", true));
        surveyItems.add(new SurveyItem("Length Of Pakkka", "lengthOfPakkka", true));
        surveyItems.add(new SurveyItem("Mode Of Transport", "modeOfTransport", true));
        surveyItems.add(new SurveyItem("Frequency Of Available", "frequencyOfAvailable", true));
        surveyItems.add(new SurveyItem("Type Of Forest", "typeOfForest", true));
        surveyItems.add(new SurveyItem("Community Forest", "communityForest", true));
        surveyItems.add(new SurveyItem("Government Forest", "governmentForest", true));
        surveyItems.add(new SurveyItem("Main Forest Trees", "mainForestTrees", true));
        surveyItems.add(new SurveyItem("Energy Species 1", "energySpecies1", true));
        surveyItems.add(new SurveyItem("Energy Area 1", "energyArea1", true));
        surveyItems.add(new SurveyItem("Energy Species 2", "energySpecies2", true));
        surveyItems.add(new SurveyItem("Energy Area 2", "energyArea2", true));
        surveyItems.add(new SurveyItem("Energy Species 3", "energySpecies3", true));
        surveyItems.add(new SurveyItem("Energy Area 3", "energyArea3", true));
        return surveyItems;

    }



    public static Bitmap getBitmapFromView(View view, int totalHeight, int totalWidth) {

        Bitmap returnedBitmap = Bitmap.createBitmap(totalWidth, totalHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return returnedBitmap;
    }



    public static void addContent(Document document, PrintSurveyItem[] printSurveyItems) throws Exception {


        for (int i = 0; i < printSurveyItems.length; i++) {
            if (printSurveyItems[i] != null && printSurveyItems[i].getBitmap() != null) {


                if (i != 0) {
                    document.newPage();
                }
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                printSurveyItems[i].getBitmap().compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                Image convertBmp = Image.getInstance(byteArray);
                convertBmp.setAlignment(Element.ALIGN_CENTER);


                PdfPTable table1 = new PdfPTable(1);
                table1.setWidthPercentage(100);
                table1.setWidths(new int[]{1});
                table1.addCell(createTextCell(printSurveyItems[i].getQuestion(),
                        catFont));
                table1.addCell(createImageCell(convertBmp));
                table1.addCell(createTextCell(printSurveyItems[i].getSubtitle().replaceAll("\n", " "),
                        responseFont));

                table1.addCell(createTextCell("Relevance",
                        catFont));
                if (printSurveyItems[i].getRelevance().length() > 0) {
                    table1.addCell(createTextCell(printSurveyItems[i].getRelevance(),
                            subFont));
                } else {
                    table1.addCell(createTextCell("Relevance appear here",
                            subFont));
                }
                table1.addCell(createTextCell("Inference",
                        catFont));

                if (printSurveyItems[i].getInference().length() > 0) {
                    table1.addCell(createTextCell(printSurveyItems[i].getInference(),
                            subFont));
                } else {
                    table1.addCell(createTextCell("Inference appear here",
                            subFont));
                }

                table1.setKeepTogether(true);

                document.add(table1);


            }

        }


    }

}
