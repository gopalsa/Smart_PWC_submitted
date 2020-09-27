package smart.cst.pwc.app;

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

import smart.cst.pwc.R;
import smart.cst.pwc.household.Mainbean;
import smart.cst.pwc.household.SurveyItem;
import smart.cst.pwc.survey.PrintSurveyItem;
import smart.cst.pwc.survey.Survey;

public class AppConfig {

    public static String packageName = ".smart.cst.pwc";

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
    public static String URL_GET_All_NEC_SURVEY = "http://coconutfpo.smartfpo.com/AMBank/get_all_uba.php";
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
            paragraph.add(new Paragraph(""));
        }
    }

    public static ArrayList<SurveyItem> loadHouseQuestions(Context context) {
        ArrayList<SurveyItem> surveyItems = new ArrayList<>();

        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.name), "name", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.gender), "gender", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.age), "age", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.household), "household", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.contactNo), "contactNo", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.idCardType), "idCardType", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.idCardNo), "idCardNo", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.householdId), "householdId", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.nameOfhead), "nameOfhead", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.houseGender), "houseGender", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.category), "category", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.povertyStatus), "povertyStatus", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.ownHouse), "ownHouse", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.typeOfHouse), "typeOfHouse", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.toilet), "toilet", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.drainage), "drainage", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.wasteSystem), "wasteSystem", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.compostPit), "compostPit", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.biogasPlant), "biogasPlant", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.annualInncom), "annualInncom", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.migrateWork), "migrateWork", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.pmJan), "pmJan", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.pmUjjwala), "pmUjjwala", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.pmAwas), "pmAwas", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.sukanya), "sukanya", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.mudra), "mudra", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.pmJivan), "pmJivan", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.pmSuraksha), "pmSuraksha", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.atal), "atal", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.fasal), "fasal", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.kaushal), "kaushal", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.krishi), "krishi", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.janAushadi), "janAushadi", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.swachh), "swachh", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.soilHealth), "soilHealth", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.ladli), "ladli", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.janani), "janani", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.kisan), "kisan", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.pipedWater), "pipedWater", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.communityWater), "communityWater", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.handPump), "handPump", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.openWell), "openWell", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.modeWaterStorage), "modeWaterStorage", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.anyOtherSource), "anyOtherSource", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.electHousehold), "electHousehold", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.electDay), "electDay", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.electLight), "electLight", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.electCooking), "electCooking", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.electCullah), "electCullah", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.cultivableArea), "cultivableArea", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.irrigatedArea), "irrigatedArea", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.unIrrigated), "unIrrigated", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.barran), "barran", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.unCultArea), "unCultArea", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.chemicalFertilisers), "chemicalFertilisers", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.chemicalInsecticides), "chemicalInsecticides", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.weedicide), "weedicide", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.manures), "manures", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.irrigation), "irrigation", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.irrigationSystem), "irrigationSystem", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.cows), "cows", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.Buffalo), "Buffalo", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.goatsSheep), "goatsSheep", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.calves), "calves", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.bullocks), "bullocks", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.poultryDucks), "poultryDucks", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.liveStockOthers), "liveStockOthers", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.shelterforLivestock), "shelterforLivestock", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.averageMilk), "averageMilk", false));
        surveyItems.add(new SurveyItem(context.getResources().getString(R.string.animalWaste), "animalWaste", false));

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
                table1.addCell(createTextCell(printSurveyItems[i].getSubtitle().replaceAll("\n", ""),
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

    public static void addToolReport(Document document, String strings) throws Exception {

        PdfPTable table1 = new PdfPTable(1);
        table1.setWidthPercentage(100);
        table1.setWidths(new int[]{1});
        table1.addCell(createTextCell(strings,
                subFont));
        table1.setKeepTogether(true);
        document.add(table1);
    }

}
