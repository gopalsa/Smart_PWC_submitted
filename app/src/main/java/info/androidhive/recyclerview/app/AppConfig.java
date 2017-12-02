package info.androidhive.recyclerview.app;

import java.util.HashMap;
import java.util.Map;

import info.androidhive.recyclerview.R;

public class AppConfig {

    public static String iplocalfarmer = "192.168.43.233/coconut/farmer";
    public static String iplocalpro = "192.168.43.233/coconut/product";
    public static String iplocalplot = "192.168.43.233/coconut/plot";
    public static String iplocalpop = "192.168.43.233/coconut/pop";
    public static String iplocalanimal = "192.168.43.233/coconut/animal";
    public static String iplocalservice = "192.168.43.233/coconut/service";
    public static String iplocalprofile = "192.168.43.233/coconut/profile";
    public static String iplocaltree = "192.168.43.233/coconut/tree";

    public static String ipcloud = "coconutfpo.smartfpo.com/pra";

    public static String ipsms = "192.168.43.233/android_sms/msg91";

    // Server user login url
    public static String URL_REGISTER = "http://" + ipcloud + "/create_vrp.php";
    public static String URL_LOGIN = "http://" + ipcloud + "/get_vrp_login.php";
    public static String URL_UPDATE = "http://" + ipcloud + "/update_vrp.php";
    public static String URL_FARMER_DETAIL = "http://" + ipcloud + "/get_vrp_details.php";

    public static String URL_ALL_PROFILE = "http://" + ipcloud + "/get_all_profile.php";
    public static String URL_PROFILE_CREATE = "http://" + ipcloud + "/create_profile.php";
    public static String URL_PROFILE_UPDATE = "http://" + ipcloud + "/update_profile.php";

    // server URL configuration
    public static final String URL_REQUEST_SMS = "http://" + ipcloud + "/request_sms.php";
    public static final String URL_VERIFY_OTP = "http://" + ipcloud + "/verify_otp.php";

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
}
