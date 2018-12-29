package nec.cst.pra;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.google.gson.Gson;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import fr.arnaudguyon.xmltojsonlib.XmlToJson;
import nec.cst.pra.app.AppConfig;
import nec.cst.pra.app.AppController;
import nec.cst.pra.app.GlideApp;
import nec.cst.pra.db.DbVrp;
import nec.cst.pra.service.HttpService;
import katomaran.evao.lib.qrmodule.activity.QrScannerActivity;

/**
 * Created by vidhushi.g on 4/10/17.
 */

public class VRPRegistration extends AppCompatActivity

{
    private int CAMERA_PERMISSION_CODE = 23;
    DbVrp dbVrp;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String vrpid = "vrpidKey";
    public static final String update = "updateKey";
    String imageUri = "";
    GPSTracker gps;

    private ProgressDialog pDialog;
    private static final String TAG = VRPRegistration.class.getSimpleName();
    private NestedScrollView scroll;
    private AutoCompleteTextView email;
    private CircleImageView image;
    CustomFontEditText name;
    CustomFontEditText geotag, contact, depNo;
    AutoCompleteTextView institution;
    CustomFontEditText password;
    CustomFontEditText address;
    CustomFontEditText whatsapp;
    CustomFontEditText confirmpassword;
    CustomFontTextView submit;


//    Map<String, ArrayList<String>> institutionVillageMap = new HashMap<String, ArrayList<String>>() {{
//        put("One", new ArrayList<String>() {{"one","two","three"}});
//        put("Two", 2);
//        put("Three", 3);
//    }};


    String[] INSTITUTIONS = {


            "CHANDIGARH UNIVERSITY (Id: U-0747)"
            , "Koneru Lakshmaiah Education Foundation,Guntur (Id: U-0020)"
            , "Charotar University of Science & Technology,  Anand (Id: U-0128)"
            , "Lovely Professional University, Jullunder (Id: U-0379)"
            , "Maharaja Sayajirao University of Baroda, Vadodara (Id: U-0143)"
            , "Sher-e-Kashmir University of Agricultural Science & Technology of Kashmir, Srinagar (Id: U-0200)"
            , "Sri Venkateswara Veterinary University, Tirupathi (Id: U-0039)"
            , "Gudlavalleru Engineering College, Seshadri Rao Knowledge Village, Gudlavalleru, PIN-521356(CC-48) (Id: C-18104)"
            , "BRACT Vishwakarma Institute of Information Technology, Pune 411048. (Id: C-41497)"
            , "VNR Vignanajyothi Institute of Engineering & Technology (Id: C-19667)"
            , "Vel Tech Multi Tech Dr. Rangarajan Dr. Sakunthala Engineering College (Id: C-16490)"
            , "S.R Engineering College (Id: C-19754)"
            , "Sahyadri Institute of Technology & Management, MANGALORE (Id: C-1265)"
            , "Dr.K.V.Subba Reddy College of Engineering for Women, Kurnool (Id: C-26890)"
            , "KPR Institute of Engineering and Technology (Id: C-36999)"
            , "PES Institute of Technology & Management,  SHIVAMOGA (Id: C-1358)"
            , "Nandha Engineering College (Id: C-36958)"
            , "Acharya B M Reddy College of Pharmacy (Id: C-40354)"
            , "ADITYA COLLEGE OF ENGINEERING & TECHNOLOGY (Id: C-18060)"
            , "ANNAI FATIMA COLLEGE OF ARTS & SCIENCE (Id: C-36493)"
            , "SEACOM SKILLS UNIVERSITY (Id: U-0778)"
            , "Sri Adichunchanagiri women`s college, Cumbum (Id: C-17037)"
            , "UNIVERSITY OF SCIENCE AND TECHNOLOGY (Id: U-0639)"
            , "Vinayaka Mission`s Kirupananda Variyar Engineering College (Id: C-10214)"
            , "Sri Ramakrishna Engineering College (Id: C-37089)"
            , "Shri Jagdish Prasad Jhabarmal Tibrewala University, Jhunjhunu (Id: U-0423)"
            , "Saveetha Engineering College (Id: C-16590)"
            , "Vel Tech High Tech Dr.Rangarajan Dr.Sakunthala Engineering College (Id: C-16616)"
            , "Yashoda Technical campus, Faculty of Engineering, Wadhe (Id: C-11075)"
            , "ANNAI COLLEGE OF POLYTECHNIC KOVILACHERI. KOVILACHERI (Id: S-2727)"
            , "ADITYA INSTITUTE OF TECHNOLOGY & MANAGEMENT, K.Kothuru, Tekkali - 532201, Srikakulam District, A.P(CC-A5) (Id: C-18122)"
            , "CV Raman College of Engineering (CVRCE), Bhubaneswar (Id: C-30045)"
            , "Dr Vishwanath Karad Wrld Peace University (Id: U-0938)"
            , "National Engineering College (Id: C-27089)"
            , "Madanapalle Institute of Technology & Science, Madanapalle (Id: C-26902)"
            , "ITM UNIVERSITY, GWALIOR (Id: U-0648)"
            , "Kumaraguru college of Technology (Id: C-36926)"
            , "NSHM  College of Management and Technology, Kolkata 234 (Id: C-6217)"
            , "Pandit Deendayal Petroleum University,  Gandhi Nagar (Id: U-0147)"
            , "Ramrao Adik Institute of Technology Dr D Y  Patil Vidyanagar Sector-7 Phase-I Nerul Navi Mumbai  400 706 (Id: C-34167)"
            , "Gandhi Institute of Technological Advancements (GITA), Bhubaneswar (Id: C-30066)"
            , "Guru Nanak Dev Engineering College, Ludhiana (Id: C-10381)"
            , "Annai College of Engineering and Technology (Id: C-25040)"
            , "SWAMI VIVEKANANDA UNIVERSITY (Id: U-0661)"
            , "Tadipatri Engineering College,  Tadipatri (Id: C-26884)"
            , "St. Vincent Palloti College of Engineering and Technology, Warha Road (Id: C-18657)"
            , "Sreenidi Institute of Science &Technology (Id: C-19951)"
            , "Ganapathy Chettiar College of Engineering and Technology, Paramakudi (Id: C-26801)"
            , "DAYANANDA SAGAR ACADEMY OF TECHNOLOGY AND MANAGEMENT, BANGALORE (Id: C-45307)"
            , "DAYANANDA SAGAR UNIVERSITY (Id: U-0856)"
            , "CMR College of Engineering & Technology (Id: C-19735)"
            , "RajaRajesheshwari College of Engineering, BANGALORE (Id: C-1404)"
            , "K.R. MANGALAM UNIVERSITY, GURGAON (Id: U-0700)"
            , "Damisetty Bala Suresh Institute of Technology, Kavali (Id: C-26995)"
            , "Dr.K.V.Subba Reddy Institute of Technology, Kurnool (Id: C-26856)"
            , "G.H. Raisoni Societys, College of Engineering and Management,Pune (Id: C-41913)"
            , "GOVERNMENT POLYTECHNIC AURANGABAD (Id: S-1785)"
            , "SG College of Agriculture and Research Centre (Id: C-33320)"
            , "NRI Colelge of Pharmacy, Pothavarappadu (V), Via Nunna Agiripalli (M), PIN-821212(CC-9E) (Id: C-18121)"
            , "Godavari Institute of Engineering & Technology (GIET), NH-5, Chaitanya nagar, Velaguvanda Village, Rajanagaram(M),Rajahmundry,PIN- 533296  (CC-55) (Id: C-18029)"
            , "Dream Institute of Technology 209 (Id: C-6304)"
            , "Anurag Group of Institutions (Id: C-19747)"
            , "AGRICULTURAL DEVELOPMENT TRUSTS COLLEGE OF AGRICULTURE, BARAMATI (Id: C-50693)"
            , "B M S Institute of technology, BANGALORE (Id: C-1336)"
            , "JAYARAM POLYTECHNIC COLLEGE KARATTAMPATTY-PAGALAVADI (Id: S-2828)"
            , "Malla Reddy Institute of Engineering and Technology (Id: C-19562)"
            , "RAMA UNIVERSITY UTTAR PRADESH (Id: U-0808)"
            , "PEOPLES EMPOWERMENT GROUPS ISB&M SCHOOL OF TECHNOLOGY, NANDE VILLAGE, MULSHI (Id: C-48614)"
            , "Oxford College of Engineering, BANGALORE (Id: C-1377)"
            , "S J C Institute of Technology, CHICKBALLAPUR (Id: C-1320)"
            , "Vidya Jythi Institute of Technology (Id: C-19582)"
            , "V.S.M. College of Engineering (Id: C-24342)"
            , "V.V.V. COLLEGE FOR WOMEN (Id: C-36487)"
            , "College of Engineering ,Chengannoor (Id: C-9453)"
            , "The Kavery Engineering College (Id: C-36948)"
            , "Yashoda Technical campus Faculty of Pharmacy,  Wadhe (Id: C-10997)"
            , "SRI SRI UNIVERSITY (Id: U-0649)"
            , "NRI Institute of Technology, Pothavarappadu (V),  Agiripalli (M), Vijayawada Rural, PIN-821212(CC-KN) (Id: C-17942)"
            , "Nalla Narasimha Reddy Education Society Group of Institutions (Id: C-19726)"
            , "PRASAD V POTLURI SIDDHARTHA INSTITUTE OF TECHNOLOGY, Devabhaktuni Ramalingeswara Rao Road, Behind VR Siddhartha Engg. College, Kanuru, Vijayawada, PIN-520007(CC-50) (Id: C-18075)"
            , "Prime College of Architecture and Planning (New) (Id: C-25001)"
            , "Baba Banda Singh Engg. College, Fatehgarh Sahib (Id: C-10277)"
            , "Alagappa Chettiar College of Engg &Tech, Karaikudi (Id: C-26789)"
            , "Datta Kala Group of Institutions Faculty of Engineering, Gat No. 541-2, Pune - Solapur Highway, A-p. Swami Chincholi, Taluka Daund, District Pune (Id: C-44576)"
            , "Koti Vidya Charitable Trusts Alamuri Ratnamala Institute of Engineering & Technology At Post Sapgaon Tal Shahapur Dist Thane-421 601 (Id: C-33794)"
            , "Bankura Unnayani Institute of Engineering 105 (Id: C-6211)"
            , "Prince Shri Venkateshwara Padmavathy Engineering College (Id: C-16609)"
            , "National Institute of Technology, Tiruchirapalli (Id: U-0467)"
            , "Institute of Aeronautical Engineering (Id: C-19706)"
            , "Tagore Institute of Engineering and Technology (Id: C-37029)"
            , "Sanjeevani Education Societys College of Engineering, At. Sahajanandnagar, Post. Shingnapur, Tal. Kopargaon, Dist. Ahmednagar 423603 (Id: C-41474)"
            , "Shri Neminath Jain Brahmacharyashramas College of Engineering, Neminagar, Chandwad, Dist.Nashik 423101 (Id: C-41699)"
            , "JAWAHARLAL COLLEGE OF ENGINEERING & TECHNOLOGY, MANGALAM (Id: C-43332)"
            , "Sri Manakula Vinayagar Engineering College (Id: C-6553)"
            , "RATHINAM TECHNICAL CAMPUS, COIMBATORE (Id: C-45287)"
            , "Sai Samajik Santha Sanchalit College of Engineering, Pardarigaon, (Id: C-34400)"
            , "Shahajirao Patil Vikas Pratishthans S.B.Patil College of Engineering, Vangali, Tal. Indapur, Dist.Pune 413106 (Id: C-41982)"
            , "Jyothisamithi Institute of Technological Technology & Science (Id: C-19868)"
            , "KAMATCHI POLYTECHNIC COLLEGE POTHAVUR, TRICHY (Id: S-12774)"
            , "MORADABAD INSTITUTE OF TECHNOLOGY, MORADABAD. (Id: C-46076)"
            , "Ponnaiyah Ramajayam Institute of Science & Technology, Thanjavur (Id: U-0471)"
            , "Chennai Institute of Technology (Id: C-16576)"
            , "GOLDEN POLYTECHNIC COLLEGE PALLAKKADU VILLAGE, SALEM (Id: S-12763)"
            , "Chaudhary Attarsingh Yadav Memorial Education Trusts, Siddhant College of Engineering, Pune (Id: C-41286)"
            , "BIT Extension Center,Patna (Id: C-8339)"
            , "Ahir College, Rewari. (Id: C-28311)"
            , "Dr. Ashok Gujar Technical Institute`s Dr.Daulatrao Aher College of Engineering, Banavadi, KARAD, Tal. Karad (Id: C-11008)"
            , "GOVERNMENT POLYTECHNIC, KASHIPUR (Id: S-12178)"
            , "Heera College of Engineering and Technology, Panavoor, Nedumangad (Id: C-43639)"
            , "RAMCO INSTITUTE OF TECHNOLOGY (Id: C-49144)"
            , "P. R. Pote (Patil) Education & Welfare Trust`s Group of Institutions`s College of Engineering & Management, Amravati. (Id: C-43031)"
            , "JYOTHY INSTITUTE OF TECHNOLOGY, BANGALORE (Id: C-45308)"
            , "Srinivasa Institute of Engineering & Technology, NH-214, Amalapuram to Kakinada Road, Cheyyuru (V), Katrenikona (M),     PIN-533222  (CC-6N) (Id: C-18062)"
            , "Sharad Institute of Technology College of Engineering, Gat (Id: C-11110)"
            , "Santhiram Engineering College, Nandyal (Id: C-26952)"
            , "Trinity Institute of Technology & Research, Hathai Kheda Kokta, Raisen Road, Bhopal (Id: C-36185)"
            , "UNIVERSITY OF ENGINEERING AND MANAGEMENT, KOLKATA (Id: U-0823)"
            , "Indian Institute of Information Technology, Design & Manufacturing, Kancheepuram (Id: U-0455)"
            , "ROHINI COLLEGE OF ENGINEERING AND TECHNOLOGY (Id: C-48273)"
            , "PARUL INSTITUTE OF ENGG. AND TECH., LIMDA, VAGHODIA 037 (Id: C-131)"
            , "PSIT COLLEGE OF HIGHER EDUCATION, BHAUTI (Id: C-54316)"
            , "Bapurao Deshmukh Engineering College, Sewagram (Id: C-18935)"
            , "NEHRU COLLEGE OF ENGINEERING AND RESEARCH CENTRE, PAMBADY (Id: C-8241)"
            , "Thiagarajar College of Engineering, Madurai (Id: C-26794)"
            , "Atria Institute of Technology, BANGALORE (Id: C-1264)"
            , "Pandiyan Saraswathi Yadav Engineering College, Arasanoor, Sivagangai (Id: C-26775)"
            , "TKM College of Engineering , Kollam (Id: C-43683)"
            , "Scope College of Engineering, NH-12, Hoshangabad Road, Village Bhaironpur, Misrod Bhopal.-462026 (Id: C-36055)"
            , "Sengunthar Engineering College (Id: C-36966)"
            , "Shri Vishnu Engineering College for Women, Vishnupur, Kovvada Village, Bhimavaram, PIN-534202(CC-B0) (Id: C-17962)"
            , "Rajasthan College of Engineering for Women,Jaipur (Id: C-25274)"
            , "Padamshri. Dr.Vitthalrao Vikhe Patil Foundations Padamshri. Dr.Vitthalrao Vikhe Patil College of Engineering, Vilad Ghat,  Ahmednagar 414111 (Id: C-41294)"
            , "S.R.M. Institute of Science & Technology, Chennai (Id: U-0473)"
            , "Narasimha Reddy Engineering College (Id: C-27180)"
            , "Moodalkatte Institute of Technology, Moodlekatti, UDUPI (Id: C-1387)"
            , "Model Engineering College,Thrikkakkara (Id: C-44109)"
            , "Camellia  Institute of Technology 230 (Id: C-6151)"
            , "GOVERNMENT POLYTECHNIC KARWAR (Id: S-1265)"
            , "Dr. B.C. Roy Engineering College, Durgapur 120 (Id: C-6176)"
            , "Gandhi Institute of Engineering and Technology (GIET), Gunupur (Id: C-30092)"
            , "Arya College of Engg. & Information Technology, Jaipur (Id: C-25117)"
            , "PERI Institute of Technology (Id: C-16482)"
            , "Progressive Educational Societys Modern College of Engineering,Shivajinagar, Pune 5 (Id: C-41767)"
            , "K.L.N. College of Engineering Pottapalayam, Sivagangai Dist (Id: C-26785)"
            , "Shri Labhubhai Trivedi Institute of Engineering And Technology, Rajkot 089 (Id: C-162)"
            , "Swarnandhra College of Engineering & Technology, Narasapur. PIN -534275  (CC-A2) (Id: C-18045)"
            , "CVR College of Engineering (Id: C-19607)"
            , "Adhi College of Engineering and Technology (Id: C-16554)"
            , "Maharashtra Academy of Engineering and Educational Research, Pune MIT  Women Engineering College, Kothrud, Pune 38 (Id: C-41380)"
            , "Sri Chandrasekharendra Saraswathi Vishwa Mahavidyalaya, Kancheepuram (Id: U-0477)"
            , "Kanad Institute of Engineering and Management 252 (Id: C-6261)"
            , "Kathir College of Engineering (Id: C-36988)"
            , "Lal Bahadur Shastri Institute of Management (Id: C-32873)"
            , "ABES ENGINEERING COLLEGE, GHAZIABAD (Id: C-46112)"
            , "DR D Y PATIL EDUCATIONAL ENTERPRISES CHARITABLE TRUSTS DR D Y PATIL GROUP OF INSTITUTIONS DR D Y PATIL SCHOOL OF ENGINEERING, PUNE (Id: C-46648)"
            , "DINKARRAO K. SHINDE SMARAK TRUST DR. A.D. SHINDE INSTITUTE OF TECHNOLOGY GADHINGLAJ (Id: S-1740)"
            , "Guru Nanak Institute of Technology (Id: C-19531)"
            , "Dr Ambedkar Institute of Management Studies Research, Deekshabhoomi (Id: C-18898)"
            , "SSR POLYTECHNIC COLLEGE (Id: S-3052)"
            , "Shanmugha Arts, Science, Technology & Reserch Academy (SASTRA), Thanjavur (Id: U-0476)"
            , "Acropolis Institute of Technology & Research, Village Raukhedi, Khasra No. 144/3, Indore Bypass Road, Indore-453771 (Id: C-36237)"
            , "Babasaheb Naik college of Engineering, Pusad (Id: C-43064)"
            , "Prasad Institute of Technology & Science, Vidya Nagar, Jaggayyapet, PIN-521175(CC-FF) (Id: C-18117)"
            , "RAJA BALWANT SINGH ENGINEERING TECHNICAL CAMPUS, AGRA (Id: C-46018)"
            , "NATIONAL INSTITUTE OF FOOD TECHNOLOGY, ENTERPRENURSHIP & MANAGEMENT (NIFTEM) (Id: S-8898)"
            , "Sophitorium Engineering College (SEC), Khurda (Id: C-30111)"
            , "Rustamji Institute of Technology, Border Security Force (BSF) Academy, Tekanpur, Gwalior 474005 (Id: C-36033)"
            , "PSG College of Technology (Id: C-37013)"
            , "KSR INSTITUTE FOR ENGINEERING AND TECHNOLOGY, NAMAKKAL (Id: C-45295)"
            , "ManakulaVinayagar Institute of Technology (Id: C-6499)"
            , "Mahatma Gandhi Institute of Technology (Id: C-19793)"
            , "Maharishi University of Information Technology Id(U-0952)"
            , "M.G.M.`S College of Engineering, Ari Port Raod, Nanded (Id: C-7623)"
            , "HINDUSTAN COLLEGE OF SCIENCE AND TECHNOLOGY, FARAH, MATHURA (Id: C-46281)"
            , "Aditya College of Engineering, ADB Road, Surampalem, PIN-533437(CC-MH) (Id: C-17955)"
            , "Annamacharya Institute of Technology & Sciences,(Autonomous)  New Bowenpally, Rajampet (Id: C-26924)"
            , "Aditya Engineering College, Aditya Nagar, ADB Raod, Surampalem, PIN-533437(CC-A9) (Id: C-17952)"
            , "College of Engg., Guindy Campus (Id: C-25072)"
            , "MADAN MOHAN MALAVIYA UNIVERSITY OF TECHNOLOGY, (Id: U-0739)"
            , "MEERUT INSTITUTE OF ENGINEERING AND TECHNOLOGY, MEERUT (Id: C-46414)"
            , "Smt. Kamala & Vekappa  M Agadi College of Engg. & Technology, Lakshmeshwara (Id: C-1324)"
            , "Thirumalai Engineering College (Id: C-16513)"
            , "Vaagdevi College of Engineering (Id: C-19948)"
            , "Vinayaka Mission`s College of Pharmacy (Id: C-10216)"
            , "Vinayaka Mission`s College of Nursing (Id: C-10226)"
            , "Shivalik College of Engg. (Id: C-21299)"
            , "Sant Longowal Institute of Engg. & Tech., Longowal (Id: U-0384)"
            , "MALABAR POLYTECHNIC CAMPUS (Id: S-1519)"
            , "K.K WAGH EDUCATION SOCIETY`S K. K. WAGH POLYTECHNIC NASHIK (Id: S-1879)"
            , "JSS College for  Women,Saraswathipuram  Mysore (Id: C-17505)"
            , "K S R College of Engineering (Id: C-36924)"
            , "Karpagam College of Engineering (Id: C-37044)"
            , "Mahatma Gandhi Missions Institute of Management Plot No1 Sector 18 Kamothe Navi Mumbai 410 209 (Id: C-33939)"
            , "MADHAV UNIVERSITY (Id: U-0698)"
            , "Roorkee College of Engineering (Id: C-21318)"
            , "DAV Institute of Engineering and Technology, Jalandhar (Id: C-10384)"
            , "G.L.A University, Mathura (Id: U-0513)"
            , "Christian College of Engineering and Technology, Oddanchatram, Palani (Id: C-26780)"
            , "Sree Vidyanikethan  Engineering College,  Tirupathi, (Id: C-26929)"
            , "Shridhar University,  Pilani (Id: U-0424)"
            , "Vidya Vikas Institute of Technology (Id: C-19676)"
            , "VIDYA COLLEGE OF ENGINEERING, MEERUT (Id: C-46188)"
            , "Kamaraj College of Engineering and Technology (Id: C-27075)"
            , "VISHWAKARMA UNIVERSITY (Id: U-0888)"
            , "VISHWATMAK JANGLI MAHARAJ TRUST VISHWATMAK OM GURUDEV COLLEGE OF ENGINEERING, THANE (Id: C-45231)"
            , "SUDHARSHANA POLYTECHNIC COLLEGE KALIPATTI (Id: S-3064)"
            , "Aditya College of Engineering, Madanapalle (Id: C-26971)"
            , "Aurora`s Technological and Management Academy (Id: C-19852)"
            , "Anil Neerukonda Institute Of Technology & Sciences (Id: C-24171)"
            , "G.H.PATEL COLLEGE OF ENGG. & TECH. , V. V. NAGAR 011 (Id: C-94)"
            , "CMR Technical Campus (Id: C-19705)"
            , "GOVERNMENT POLYTECHNIC, G.T.B.GARH (Id: S-2446)"
            , "Gokhale Edcuation Societys College of Engineering, Vidyanagar, Nashik 422005 (Id: C-41921)"
            , "PRANVEER SINGH INSTITUTE OF TECHNOLOGY, KANPUR (Id: C-46844)"
            , "Pankaj Laddhad Institute of Technology and  Management Studies, Buldana (Id: C-42983)"
            , "Maharaja Institute of Tech., MYSORE (Id: C-1278)"
            , "Maharaja Surajmal Institute (Id: C-32847)"
            , "GOVERNMENT POLYTECHNIC, SRINAGAR (Id: S-12183)"
            , "M.I.E.T. Engineering College (Id: C-25046)"
            , "K N G D MODI ENGIEERING COLLEGE, MODINAGAR (Id: C-46987)"
            , "Integral University, Lucknow (Id: U-0519)"
            , "Pataldhamal Wadhwani B.Pharmacy College, Yavatmal. (Id: C-43180)"
            , "Rajadhani Institute of Engineering and Technology, Alamcode, Attingal (Id: C-43630)"
            , "MEDIA COMPUTER EDUCATION GANJBASODA (Id: C-31569)"
            , "P.S.V.College of Engineering and Technology (Id: C-37061)"
            , "Global Research  Institute of  Management. & Technology (Id: C-10617)"
            , "Girija Devi Polytechnic College (Id: S-16143)"
            , "Annamacharya Institute of Technology & Sciences (Id: C-19623)"
            , "Apeejay  Stya University, Sohna (Id: U-0156)"
            , "Acharya Institute of Technology, BANGALORE (Id: C-1319)"
            , "SRINIVASA RAMANUJAN INSTITUTE OF TECHNOLOGY (Id: C-26984)"
            , "Vel Tech (Id: C-16534)"
            , "Gurukul Institute of Engineering & Technology,Kota (Id: C-25271)"
            , "Anurag College of Engineering (Id: C-19509)"
            , "Thakur Shivkumar Singh Memorial Engineering College, Navall Nagar (Ziri), Tehsil & District Burhanpur-450223 (Id: C-36278)"
            , "Sagar Institute of Research &Technology Excellence, Ayodhya Bypass Road, Bhopal -462041 (Id: C-35957)"
            , "Adarsh Institute of Technology & Research Centre, Post ? Khambale (Bha.),  Near MIDC,Tasgaon Road, VITA,  Tal. Khanapur Dist. Sangli  (Id: C-11007)"
            , "B.L.PATIL POLYTECHNIC (Id: S-1680)"
            , "Global Institute of Engineering & Technology (Id: C-19722)"
            , "D. Y. Patil Pratishthans D.Y. Patil College of Engineering ,Pune (Id: C-41513)"
            , "PRESIDENCY UNIVERSITY , BENGALURU (Id: U-0876)"
            , "Pune Institute of Computer Technology, Dhankawadi, Pune 411043 (Id: C-42072)"
            , "Karpagam Institute of Technology (Id: C-37035)"
            , "Laxmipati Institute of Science & Technology (List), Opp SOS, Village Ahjehara Road, Khajuri Kalan, Bhopal (Id: C-36231)"
            , "Manjara Charitable Trusts Rajiv Gandhi Institute of Technology Serve No.161 CTS No 1976 1 48 Juhu Versova Link Road Behind HDFC Bank Versova Andheri (West) Mumbai 400061 (Id: C-33792)"
            , "GOVERNMENT POLYTECHNIC JAMMU (Id: S-1135)"
            , "New Prince Shri Bhavani College of Engineering and Technology (Id: C-16470)"
            , "VISHWAKARMA GOVERNMENT ENGINEERING COLLEGE, CHANDKHEDA,GANDHINAGAR 017  (Id: C-382)"
            , "KISAN VIDYA PRASARAK SANSTHA`S MAHARANI AHILYABAI HOLKAR COLLEGE OF PHARMACY BORADI (Id: S-10759)"
            , "Joginpally B.R Engineering College (Id: C-19540)"
            , "Ganga Institute of Technology & Management (Id: C-28433)"
            , "BABA MASTNATH UNIVERSITY (Id: U-0666)"
            , "Bangalore Institute of Technology, BANGALORE-560 004 (Id: C-1340)"
            , "Al-Ameen College of Pharmacy (Id: C-40298)"
            , "ANNAMALAI POLYTECHNIC COLLEGE CHETTINAD (Id: S-2733)"
            , "SRI SATISH CHANDRA PANDEY MEMORIAL HOSPITAL, GONDA (Id: S-14152)"
            , "SRI RAMACHANDRA POLYTECHNIC COLLEGE, DHARMANEETHI (Id: S-14885)"
            , "Yagyavalkya Institute of Technology,Jaipur (Id: C-25078)"
            , "K.Ramakrishnan College of Engineering (Id: C-24983)"
            , "Velalar College of Engineering and Technology (Id: C-37094)"
            , "S J B Institute of Technology, BANGALORE (Id: C-1271)"
            , "SHREE SHANKAR NARAYAN EDUCATION TRUSTPRAVIN PATIL COLLEGE OF DIPLOMA ENGG. & TECHNOLOGY THANE (Id: S-2136)"
            , "A G PATIL INSTITUTE OF TECHNOLOGY  18 2A2  PRATANAGER SOREGON ROAD (Id: C-15759)"
            , "GANESHI LAL BAJAJ INSTITUTE OF TECHNOLOGY AND MANAGEMENT, GREATER NOIDA (Id: C-46239)"
            , "ITM VOCATIONAL UNIVERSITY, VADODARA (Id: U-0714)"
            , "INDUS INSTITUTE OF TECHNOLOGY AND MANAGEMENT, KANPUR (Id: C-46829)"
            , "MAHARAJA AGRASEN UNIVERSITY (Id: U-0671)"
            , "NSHM Knowledge Campus Durgapur Group of Institutions 273 (Id: C-6143)"
            , "Panipat Institute of Engg. & Technology (Id: C-10575)"
            , "Professional Group of Institutions (Id: C-36940)"
            , "MEA ENGINEERING COLLEGE, CHEMMANIYODE (Id: C-8079)"
            , "KONGHU VELALAR POLYTECNIC COLLEGE PATTAKARNPALAYAM (Id: S-2843)"
            , "JANATA SHIKSHAN PRASARAK MANDAL`S DR.N.P.HIRANI POLYTECHNIC PUSAD (Id: S-1864)"
            , "Kalyani Charitable Trusts Late G.N.Sapkal College of Engineering , Sapkal Knowledge Hub,Kalyani Hills, Anjaneri Vadholi, Tryambakeshwar, Dist.Nashik 422212 (Id: C-42196)"
            , "GOVERNMENT COLLEGE OF ENGINEERING AND RESEARCH ,AVASARI (Id: C-55912)"
            , "GOVERNMENT POLYTECHNIC, NARENDRA NAGAR (Id: S-12181)"
            , "GURUVANDAN MAHILA KALYANKARI BAHUUDDESHIYA MANDAL`S GURU SAI POLYTECHNIC COLLEGE MAUZA DATALA CHANDRAPUR (Id: S-1828)"
            , "Ashokrao Mane Group of Institutions Faculty of Engineering, Wathar Tarfe Vadgaon (Id: C-11010)"
            , "AMBALIKA INSTITUTE OF MANAGEMENT AND TECHNOLOGY, LUCKNOW (Id: C-46875)"
            , "ALLENHOUSE INSTITUTE OF TECHNOLOGY, KANPUR (Id: C-46813)"
            , "Bharati Vidyapeeths College of Engineering Sector-7 C.B.D.Belpada Navi Mumbai  400 614 (Id: C-33566)"
            , "BMS SCHOOL OF ARCHITECTURE, YELAHANKA (Id: C-47256)"
            , "S.C.M.S. School of Engineering Technology, Vidyanagar, Palisserry, Karukutty P.O., Ernakulam- 683 582 (Id: C-11658)"
            , "VISHVESWARYA INSTITUTE OF TECHNOLOGY, DADRI,GREATER NOIDA PHASE -II,G B NAGAR (Id: C-46257)"
            , "Vighnaharta Trust s Shivajirao S Jondhale College of Engineering &Technology Asangaon Tal Shahapur  Dist Thane (Id: C-33937)"
            , "Shaheed Rajguru College of Applied Sciences for Women (Id: C-6426)"
            , "SVR Engineering College, Nandyal (Id: C-26873)"
            , "GOVERNMENT GIRLS POLYTECHNIC LUCKNOW (Id: S-3200)"
            , "Chamelidevi Group of Institutions, Gram Umrikheda, Near Toll Naka, Khandwa Road, Indore 452020 (Id: C-36096)"
            , "DR A D SHINDE COLLEGE OF ENGINEERING (Id: C-50898)"
            , "Amro College of Hotel Management, B.Sc. H.S.Rajur Bahula, CIDCO, Nashik 422010 (Id: C-42133)"
            , "GOVERNMENT POLYTECHNIC COLLEGE KANIYALAMPATTI, KARUR (Id: S-12766)"
            , "Biyani Institute of Science & Management (Id: C-25205)"
            , "Baddi University of Emerging Sciences and Technology, Baddi (Makhnumajra), Solan (Id: U-0177)"
            , "Ansal University (Id: U-0667)"
            , "M.G.M`s Jawaharlal Nehru College of Engineering, N-6, Cidco. (Id: C-34503)"
            , "MAHARAJA RANJIT SINGH STATE TECHNICAL UNIVERSITY (Id: U-0811)"
            , "JNTUA College of Engineering, Anantapur (Id: C-26928)"
            , "Laxmi Institute of Technology, Valsad 086 (Id: C-308)"
            , "IMS ENGINEERING COLLEGE, GHAZIABAD (Id: C-46125)"
            , "Rajarambapu College of Pharmacy, KASEGAON, (Id: C-11067)"
            , "Amjad Ali Khan College of Business Administration (Id: C-26080)"
            , "Anna University of Technology, Coimbatore (Id: C-54584)"
            , "Vidya Vardhaka College of Engineering, MYSORE (Id: C-1273)"
            , "LOYOLA POLYTECHNIC PULLUVENDLA (Id: S-591)"
            , "Jhulelal Institute of Technology, Lonara (Id: C-18650)"
            , "ABHINAV EDUCATION SOCIETY COLLEGE OF ENGINEERING AND TECHNOLOGY(DIPLOMA) KHANDALA (Id: S-10795)"
            , "Dhanekula Institute of Engineering & Technology, Ganguru, Penamalluru Mandal, PIN-521139(CC-8T) (Id: C-17937)"
            , "Swami Vivekananda Subharti University, Meerut (Id: U-0543)"
            , "Sagar Institute of Research & Technology,     Opp. Sagar Estates, Ayodhya Nagar, By Pass Road, Bhopal-462041 (Id: C-36202)"
            , "BAJAJ INSTITUTE OF TECHNOLOGY (Id: C-58531)"
            , "Noida International University (Id: U-0533)"
            , "Malla Reddy Engineering College (Id: C-19830)"
            , "Dr. B. R. Ambedkar University, Agra (Id: U-0509)"
            , "Pramukhswami Medical College, Karamsad (Id: C-1215)"
            , "Saintgits College of Engineering, Kottukulam Hills, Pathamuttam P.O., Kottayam 686532 (Id: C-11572)"
            , "Sudhakarrao Naik Institute of Pharmacy, Pusad. (Id: C-43084)"
            , "HEMNALINI MAMORIAL COLLEGE OF ENGINEERING 343 (Id: C-49705)"
            , "UNIVERSITY OF ENGINEERING AND MANAGEMENT (Id: U-0756)"
            , "National Institute of Technology Delhi (Id: U-0622)"
            , "Mar Baselios Christian College of Engineering and Technology, Peerumedu - 685 531 (Id: C-11767)"
            , "KAZIRANGA UNIVERSITY (Id: U-0682)"
            , "JAI AMBE INSTITUTE ON INFORMATION TECHNOLOGY (Id: C-55146)"
            , "K.S.R.M. College of Engineering (Id: C-26868)"
            , "Chettinad College of Engineering & Technology (Id: C-37006)"
            , "GALGOTIA S COLLEGE OF ENGINEERING AND TECHNOLOGY, GREATER NOIDA (Id: C-46229)"
            , "Chandigarh College of  Engineering & Technology (Degree Wing), Sector-26 (Id: C-29359)"
            , "GURU GOBIND SINGH FOUNDATIONS GURU GOBIND SINGH COLLEGE OF ENGINEERING, & RESEARCH CENTRE, KHALSA EDUCATIONAL COMPLEX, GURU GOBIND SINGH MARG (Id: C-48617)"
            , "150027-TMES`S J.T.MAHAJAN COLLEGE OF ENGINEERING, FAIZPUR. (Id: C-9012)"
            , "HMR Institute of Technology & Management (Id: C-32846)"
            , "WOMEN`S POLYTECHNIC COLLEGE (Id: S-1533)"
            , "Seacom Engineering College 206 (Id: C-6298)"
            , "Jaya Prakash Narayan College of Engineering (Id: C-19643)"
            , "JHUNJHUNWALA P G COLLEGE FACULTY OF ENGINEERING AND TECHNOLOGY DWARIKAPURI FAIZABAD (Id: S-15173)"
            , "Viswajyothi College of Engineering and Technology, Vazhakulam P.O., Muvattupuzha , Ernakulam- 686 670 (Id: C-11623)"
            , "SCPM Ayurvedic Medical College & Hospital"
            , "Vaishno College Of Engg. Thapkour, Nurpur, Kangra (Id: C-11353)"
            , "CLG PHARMACY COLLEGE (Id: C-51868)"
            , "UKF College of Engineering and Technology, Parippally, Kollam (Id: C-43765)"
            , "GOVERNMENT RESIDENTIAL WOMEN`S POLYTECHNIC YAVATMAL (Id: S-1818)"
            , "ANJUMAN-ISLAM`S ABDUL RAZZAK KALSEKAR POLYTECHNICPANVELNAVI MUMBAI (Id: S-1671)"
            , "Jaypee Institute of Information Technology, Noida (Id: U-0522)"
            , "Jaywant Shikshan Prasarak Mandal`s Rajshree Shahu College of Engineering, S.No. 80, Pune Bombay Bypass highway, Thathwade, Pune 411 033 (Id: C-41614)"
            , "Indian Institute of Technology, Palakkad (Id: U-0878)"
            , "B.R. POLYTECHNIC COLLEGE, SIKAR (Id: S-2544)"
            , "SRI RAMMURTI SMARAK COLLEGE OF ENGG AND TECH , UNNAO (Id: C-47028)"
            , "SHAKUMBHARI INSTITUTE OF HIGHER EDUCATION, ROORKEE (Id: S-3438)"
            , "SHUBHAM ACADEMY SHUJALPUR (Id: C-31380)"
            , "B I T SINDRI (Id: C-48594)"
            , "Faculty of Engg., Tech. & Research (FETR), ISROLI (AFWA), Bardoli 084 (Id: C-20)"
            , "INSTITUTE OF MANAGEMENT STUDIES, GHAZIABAD (Id: C-46749)"
            , "M.L.V Textile & Engineering College,Bhilwara (Id: C-25151)"
            , "Indira Gandhi Krishi Vishwavidyalaya, Raipur (Id: U-0087)"
            , "Modern Institute of Technology & Research Centre,Alwar (Id: C-25248)"
            , "Geeta Engg. College (Id: C-10842)"
            , "CLG Institute of Engg. & Technology,Sumerpur,Pali (Id: C-25210)"
            , "DR VIRENDRA SWARUP MEMORIAL TRUST GROUP OF INSTITUTIONS, UNNAO (Id: C-47021)"
            , "DR. V. R. GODHANIA COLLEGE OF ENGINEERING AND TECHNOLOGY (Id: C-58070)"
            , "Columbia Institute of Pharmacy Raipur (Id: C-16643)"
            , "GOVERNMENT POLYTECHNIC KOLHAPUR (Id: S-1798)"
            , "GOVERNMENT COLLEGE OF ENGINEERING, BODINAYAKKANUR (Id: C-51677)"
            , "GOPAL EDUCATION SOCEITY`S SHRI MUKUNDRAO PANNASE POLYTECHNIC MAUZA MONDHA (Id: S-1775)"
            , "B.H.GARDI COLLEGE OF ENGG. & TECHNOLOGY , RAJKOT 004 (Id: C-91)"
            , "Chaitanya College of Pharmacy Education & Research, Hanamkonda (Id: C-27431)"
            , "100007-SSBT`S COLLEGE OF ENGINEERING & TECHNOLOGY, BAMBHORI. (Id: C-8977)"
            , "P S G POLYTECHNIC COLLEGE PEELAMEDU (Id: S-2907)"
            , "INSTITUTE OF PROFF.  EXCELLENCE & MGMT.   A-13/1, SOUTH OF G.T. ROAD, INDUSTRIAL AREA, DELHI-HAPUR BYPASS, NATIONAL HIGHWAY-24, GHAZIABAD, Ph. No- 741189,  721633 (Id: C-28651)"
            , "I.T.S ENGINEERING COLLEGE, GREATER NOIDA (Id: C-46247)"
            , "VITS Group of Institutions Faculty of Engineering  School of Management,Sontyam, PIN-531173(CC-NU) (Id: C-17883)"
            , "Asian Institute Of Rural Technology (Id: S-16699)"
            , "School of Engineering and Technology, Soldha (Id: C-51014)"
            , "INDUS UNIVERSITY (Id: U-0663)"
            , "Maharshi Karve stree Shikshan Sanstha`s Dr.Bhanuben Nanavati College of Artchitecture for Women, Karve Nagar, Pune (Id: C-41983)"
            , "Pavai College of Technology (Id: C-36967)"
            , "Arasu Engineering College (Id: C-24993)"
            , "BACKWARD CLASS YOUTH RELIEF COMMITTEES NAGPUR POLYTECHNIC NAGPUR. (Id: S-1686)"
            , "Bharath Niketan Engineering College, Theni (Id: C-26806)"
            , "Thapar Institute of Engineering and Technology, Patiala (Id: U-0385)"
            , "Gateway Institute of Engineering and Technology (Id: C-19311)"
            , "Rajagiri School of Engineering and Technology, Rajagiri Valley P.O, Kakkanad,  Kochi- 682 039 (Id: C-11554)"
            , "VARDHAMAN EDUCATION & WELFARE SOCIETYAHINSA POLYTECHNIC POST. DONDAICHA DHULE (Id: S-2222)"
            , "Shri Tapi Brahmcharyashram sabha college of Diploma Engg,Surat. 647 (Id: C-23)"
            , "KNK COLLEGE OF HORTICULTURE, MANDSAUR (Id: C-45272)"
            , "LUCKNOW POLYTECHNIC (Id: S-3307)"
            , "All India Shivaji Memorial Societys College of Engineering for women, Pune (Id: C-41410)"
            , "Angel College of Engineering and Technology (Id: C-36981)"
            , "Institute of Technology and Management, 60 Chakrata Road,Dehradun (Id: C-24560)"
            , "MBS SCHOOL OF PLANNING AND ARCHITECTURE, WEST DELHI (Id: C-46311)"
            , "ABHINAV EDUCATION SOCIETY`S COLLEGE OF PHARMACY NARHE TAL HAVELI PUNE (Id: S-10813)"
            , "Gayatri Vidya Parishad College of Engineering (Autonomous), 530048(CC-13) (Id: C-17913)"
            , "DR. D.Y.PATIL PRATISHTHAN`S DR. D. Y. PATIL INSTITUTE OF PHARMACY AKURDI PUNE (Id: S-1748)"
            , "LAXMI INSTITUTE OF ARCHITECTURE, SARIGAM-355 (Id: C-49980)"
            , "Manav Institute of Technology & Management (Id: C-27604)"
            , "Vidya Vihar Institute of Technology (Id: C-29596)"
            , "Baba Isher Singh Institute of Technology VPO-Gagra (Id: C-10282)"
            , "Kalpataru Institute of Technology, TIPTUR (Id: C-1390)"
            , "Muffakham Jah College of Engineering & Technology (Id: C-25464)"
            , "CMJ University(Id:U-0942)"
            , "DR. BHIMRAO AMBEDKAR ENGINEERING COLLEGE OF INFORMATION TECHNOLOGY,BANDA (Id: C-49267)"
            , "RAJ POLYTECHNIC, BABATPUR,VARANASI(Id:S-17609)"
            , "IMT Pharmacy College, Puri (Id: C-30129)"
            , "Birla Institute of Technology & Sciences, Pilani (Id: U-0391)"
            , "LLYOD INSTITUTE OF MANAGEMENT AND TECHNOLOGY, GREATER NOIDA (Id: C-46238)"
            , "Lakshay College of Hotel Management (Id: C-10526)"
            , "Vi Institute of Technology (Id: C-16568)"
            , "Sat kabir Institute of Technology (Id: C-28207)"
            , "SCHOOL OF DIPLOMA ENGINEERING (Id: S-16097)"
            , "Dev Bhoomi Institute of Technology (Id: C-21289)"
            , "V.R.S. College of Engineering and Technology (Id: C-16516)"
            , "Rattan Institute of Technology & Management (Id: C-28434)"
            , "MVSR Engineering College (Id: C-25615)"
            , "200004-SSVPS`S BAPUSAHEB SHIVAJIRAO DEORE COLLEGE OF ENGINEERING, DHULE. (Id: C-8874)"
            , "AJAY KUMAR GARG ENGINEERING COLLEGE, GHAZIABAD (Id: C-46109)"
            , "Gyan Ganga Institute of Technology Sciences  (Id: C-53649)"
            , "GOVERNMENT POLYTECHNIC, KANDA (Id: S-12198)"
            , "XAVIER INSTITUTE OF POLYTECHNIC AND TECHNOLOGY (Id: S-1176)"
            , "RUDRAPUR INSTITUTE OF TECHNOLOGY, BHAGWANPUR, DANPUR RUDRAPUR (Id: S-14564)"
            , "Nannapaneni Venkat Rao (NVR) College of Engineering & Technology, NVR Nagar, Ithanagar, Tenali, PIN-522501(CC-7T) (Id: C-17892)"
            , "GOVERMENT POLYTECHNIC KURSI  FATEHPUR BARABANKI (Id: S-15221)"
            , "GOVERNMENT POLYTECHNIC LUCKNOW (Id: S-3231)"
            , "GOVERNMENT POLYTECHNIC, JAKHNIDHAR (Id: S-14504)"
            , "Guru  Jambheshwar University of Science and Technology, Hissar (Id: U-0162)"
            , "GALGOTIAS UNIVERSITY (Id: U-0643)"
            , "Abhinav Education Society`s Institute of Management & Business Administration, Akole, Dhamangaon  Awari Road, Akole, Dist.Ahmednagar 422601. (Id: C-44574)"
            , "Annai Vailankanni College of Engineering (Id: C-27088)"
            , "Tirumala Engineering College, Jonnalagadda(Post), Narasaraopet, PIN-522601(CC-NE) (Id: C-18162)"
            , "SCHOOL OF MANAGEMENT SCIENCES, LUCKNOW (Id: C-49465)"
            , "G M Institute of Technology, DAVANGERE (Id: C-1374)"
            , "P.P. Savani University (Id: U-0873)"
            , "P.M. College of Engineering (Id: C-19315)"
            , "National Institute of Technology, Raipur (Id: U-0092)"
            , "Baba Ghulam Shah Badshah University,  Jammu (Id: U-0191)"
            , "Swami Vivekananda Institute of Science and Technology 241 (Id: C-6280)"
            , "Sri Indu Institute of Engineering and Technology (Id: C-19692)"
            , "Technology Education & Research Institute (Id: C-10535)"
            , "Bhadrak Institute of Engineering and Technology, (BIET), Bhadrak (Id: C-30169)"
            , "Eswar College of Engineering, Kesanupalli(V),  Narasaraopet , PIN-522 601(CC-JE) (Id: C-18149)"
            , "Delhi Technological University (Id: U-0098)"
            , "GOVRNMENT POLYTECHNIC COLLEGE PUNALUR (Id: S-1497)"
            , "GOVERNMENT POLYTECHNIC, HINDOLAKHAL (Id: S-12208)"
            , "GOVERNMENT POLYTECHNIC, KANDIKHAL (Id: S-14834)"
            , "Mauli Group of Institution`s College of Engineering &  Technology, Shegaon, (Id: C-43183)"
            , "Rajiv Gandhi Institute of Technology, Velloor P.O.,Pampady. Kottayam - 689 501 (Id: C-11734)"
            , "GYAN GANGA COLLEGE OF TECHNOLOGY, JABALPUR (Id: C-53648)"
            , "Mar Baselios College of Engineering and Technology, Nalanchira, Thiruvananthapuram (Id: C-43611)"
            , "Maamallan Institute of Technology (Id: C-16483)"
            , "INSTITUTE OF TECHNOLOGY, GOPESHWAR (Id: C-50743)"
            , "Govt. Engineering College, RAMANAGARA (Id: C-1328)"
            , "ANJUMAN INSTITUTE OF TECHNOLOGY AND MANAGEMENT (Id: C-51535)"
            , "Malnad College of Engineering, HASSAN (Id: C-1300)"
            , "Veterinary College, Shimoga (Id: C-30191)"
            , "INDIAN INSTITUTE OF INFORMATION TECHNOLOGY, CHITTOOR (Id: U-0760)"
            , "ST.Thomas Institute of Science and Technology, Mar Chrystom Nagar, Kattaikonam P.O., Thiruvananthapuram (Id: C-43651)"
            , "Sagar Institute of Research Technology & Science (SIRTS), Near ISRO, Ayodhya Nagar, By Pass Road, Bhopal - 462041 (Id: C-36302)"
            , "Hindustan Institute of Technology and Science, Kancheepuram (Id: U-0454)"
            , "K V PATEL COLLEGE OF AGRICULTURE, SHAHADA (Id: C-50812)"
            , "Padmashri Dr. D.Y.Patil Institute of Pharmaceutical Science and Research, Pimpri, Pune 411018 (Id: C-41223)"
            , "Sir M Visvesvaraya Institute of Technology, BANGALORE (Id: C-1327)"
            , "ROYAL POLYTECHNIC (Id: S-1148)"
            , "G S S S Institute of Engineering & Technology for Women, MYSORE (Id: C-1341)"
            , "DR AMBEDKAR INSTITUTE OF TECHNOLOGY FOR HANDICAPPED , KANPUR (Id: C-46823)"
            , "Ch. Devi Lal University, Sirsa (Id: U-0160)"
            , "A.B.M.S.) Akhil Bharatiya Maratha Shikshan Parishad`s College of Engineering & Research  Address: S. No. 103, Shahu College Campus, Parvati, Taluka: Pune (corporation Area) District: Pune - 411009  (Id: C-41484)"
            , "Regent Education and Research Foundation Group of Institutions 263 (Id: C-6300)"
            , "Indira Institute of Engineering and Technology (Id: C-16582)"
            , "Islamic University of Science & Technology, Pulwama (Id: U-0194)"
            , "Sankalchand Patel University, Visnagar (Id: U-0821)"
            , "S.V.P.M. College of Engineering, Malegaon bk., Tal. Baramati, Dist Pune 413115 (Id: C-41453)"
            , "Rural Engineering College, BHALKI (Id: C-1261)"
            , "Annamacharya College of Pharmacy, Rajampet (Id: C-26843)"
            , "Mother Theresa Institute of Engineering & Technology,  Palamaner (Id: C-26938)"
            , "Sankalchand Patel College of Engineering, Visnagar (Id: C-381)"
            , "SNS College of Engineering (Id: C-37036)"
            , "JIMS ENGINEERING MANAGEMENT AND TECHINICAL CAMPUS (Id: C-52689)"
            , "Haldia Institute of Technology 103 (Id: C-6238)"
            , "Chhattisgarh Engineering College Borsi, Dhanora Road (Id: C-15653)"
            , "Bharati Vidyapeeth`s College of Engineering (Id: C-32863)"
            , "BABA BANDA SINGH BAHADUR POLYTECHNIC COLLEGE, FATEHGARH SAHEB (Id: S-2403)"
            , "PT. RAM ADHAR J. TIWARI COLLEGE OF POLYTECHNIC (Id: S-3346)"
            , "N.S.S. POLYTECHNIC COLLEGE PANDALAM (Id: S-1521)"
            , "Sengunthar College of Engineering (Id: C-36945)"
            , "VEDAVYASA INSTITUTE OF TECHNOLOGY, PONNEMPADAM (Id: C-8196)"
            , "AYUSH COLLEGE OF POLYTECHNIC, MEDUKA PENDRA ROAD (Id: C-50481)"
            , "College of Pharmacy Hanuman Nagar, Malkapur. (Id: C-42873)"
            , "Sri Ramakrishna Institute of Technology (Id: C-37032)"
            , "Jawaharlal Nehru Govt.Engineering College, Mandi (Id: C-53483)"
            , "Jay Shriram Group of Institutions (Id: C-36950)"
            , "ROSHANBI SHAMANJI COLLEGE OF AGRICULTURE, NESARI (Id: C-50687)"
            , "Janardan Rai Nagar Rajasthan Vidyapeeth,  Udaipur (Id: U-0402)"
            , "Karamvir Dadasaheb Kannamwar Engineering College, Nandanvan (Id: C-18630)"
            , "Indira Gandhi National Open University (Id: U-0104)"
            , "KTSP Mandal`s Hutatma Rajguru Mahavidyalaya Arts, Science and Commerce, Rajgurunagar, Pune 410505 (Id: C-41707)"
            , "Kristhu Jayanthi College, K. Narayanapura, Kottanoor Post, K.R. Puram, Bangalore-77 (Id: C-20653)"
            , "Nalanda College of Engineering (Id: C-43565)"
            , "Meenakshri Academy of Higher Education and Research, Chennai (Id: U-0465)"
            , "North Maharashtra University, Jalgaon (Id: U-0320)"
            , "Nesamony Memorial Christian College, Marthandam - 629 165, Kanyakumari Dist. (Id: C-41154)"
            , "Raja Narendra Lal Khan Women`s College (Id: C-19059)"
            , "REGENT INSTITUTE OF SCIENCE AND TECHNOLOGY NORTH 24 PARGANAS (Id: S-10945)"
            , "Annapoorana Engineering College (Id: C-36971)"
            , "Gyanmanjari Institute of Technology (Id: C-51523)"
            , "Government Degree College for Men (Autonomous), Anantapur (Id: C-30916)"
            , "Datta Meghe Institute of Medical Sciences, Nagpur (Id: U-0295)"
            , "GANGADHAR MEHER UNIVERSITY (Id: U-0852)"
            , "Sri Dharmasthala Manjunatheshwara College, Ujire-574240, Belthangady (Id: C-16801)"
            , "Sri Krishna Arts and Science College (Id: C-41126)"
            , "Shrimant Babasaheb Deshmukh Mahavidyalaya, Atpadi (Id: C-11004)"
            , "St.Josephs College of Commerce,  # 5031, Museum Road, Banglore 25. (Autonomous) (Id: C-21115)"
            , "St.Mary`s College, North Beach Road, Tuticorin - 628 001 (Id: C-41151)"
            , "SHREE RAMKRISHNA INSTITUTE OF SCIENCE & TECHNOLOGY SOUTH 24 PARGANAS (Id: S-10953)"
            , "Sangamner Nagarpalika Arts, D.J.Malpani Commerce and B.N.Sarda Science College, Sangamner, Dist.Ahmednagar (Id: C-42178)"
            , "Savitribai Phule Pune University (Id: U-0323)"
            , "Vellalar College for Women (Id: C-41068)"
            , "THIAGARAJAR COLLEGE (Id: C-36513)"
            , "TUFUNGANJ MAHAVIDYALAYA, COOCHBEHAR (Id: C-45330)"
            , "University of Jammu, Jammu Tawi (Id: U-0195)"
            , "Virudhunagar Hindu Nadars` Senthikumara Nadar College (Id: C-36502)"
            , "WOMENS CHRISTIAN COLLEGE (Id: C-43946)"
            , "Sadakathullah Appa College, Rahmath Nagar, Palayamkottai, 627 011 (Id: C-41191)"
            , "Holy Cross College, Tiruchirappalli - 620 002. (Id: C-35787)"
            , "MADRAS CHRISTIAN COLLEGE (Id: C-43965)"
            , "KAMESHWAR NARAYAN SINGH GOVERNMENT POLYTECHNIC, SAMASTIPUR (Id: S-16203)"
            , "Vivekanandha College of Arts & Science (W) (Id: C-9488)"
            , "VIMALA COLLEGE, THRISSUR (Id: C-8008)"
            , "Thevanai Ammal College for Women,(Autonomous),Chennai-Trichy Trunk Road,605 401 (Id: C-36433)"
            , "Shri Chhatrapati Shivaji College of Arts, Science  & Commerce College, Omerga (Id: C-34650)"
            , "SHRI SAKTHIKAILASSH WOMEN`S COLLEGE SALEM (Id: C-9532)"
            , "St. Aloysous College, Sadar, Jabalpur (Id: C-33354)"
            , "St. Andrews P.G. College, Gorakhpur (Id: C-14234)"
            , "St. Josephs Arts & Science College, PB 27094, Lalbhag Rd,Bangalore-27  (Autonomous) (Id: C-20872)"
            , "St. Teresa`s College, Ernakulam - 682 011 (Id: C-11582)"
            , "Smt. Radhadevi Goenka Mahila Mahavidyalaya, Akola. (Id: C-42998)"
            , "Shri Vile Parle Kelvani Mandals Mithibai College of Arts Chauhan Institute of Science and Amrutben Jivanlal College of Commerce and Economics Vile Parle West Mumbai 400056 (Id: C-33803)"
            , "Sri Krishna Adithya College of Arts and Science (Id: C-52639)"
            , "D.Y. Patil Educational Society , Kolhapur (Id: U-0294)"
            , "Gopinath Mahavidylala, Devali, Salamatpur, Ghazipur (Id: C-16100)"
            , "Government National College, Sirsa (Id: C-22444)"
            , "Avinashilingam Institute for Home Science & Higher Education for Women, Coimbatore (Id: U-0444)"
            , "Alliance University. Bangalore (Id: U-0213)"
            , "Ahmednagar Jilha Maratha Vidya Prasarak Samajs New Arts,Commerce & Science College,Parner,Dist Ahmednagar  414302 (Id: C-42184)"
            , "Bhairab Ganguly College (Id: C-43363)"
            , "Bharathiar University, Coimbatore (Id: U-0447)"
            , "CHRIST COLLEGE, IRINJALAKKUDA (Id: C-8213)"
            , "BRAINWARE UNIVERSITY (Id: U-0846)"
            , "BIT Extension Center Deoghar (Id: C-8344)"
            , "RAJARSHI RANANJAY SINH INSTITUTE OF MANAGEMENT AND TECHNOLOGY, SULTANPUR (Id: C-47018)"
            , "Phulsingh Naik Mahavidyalaya, Pusad. (Id: C-42901)"
            , "Panskura Banamali College (Id: C-19060)"
            , "PSG College of Arts and Science (Id: C-41124)"
            , "NATIONAL P.G. COLLEGE (Id: C-12749)"
            , "Nehru Arts and Science College (Id: C-41133)"
            , "Nirmala College, Muvattupuzha - 686 661 (Id: C-11839)"
            , "P.R.Govt.Degree College, Kakinada (Id: C-24020)"
            , "P K R Arts College for Women (Id: C-41039)"
            , "MG College, Thiruvananthapuram (Id: C-43760)"
            , "marudhar Kesari Jain College for Women,Marudhar Nagar,Chinnakallupalli,Vaniyambadi-635751 (Id: C-36362)"
            , "Murgaon Education Societys College of Arts & Commerce Zuarinagar (Id: C-30864)"
            , "Mount Carmel College,58, Palace Rd, Bangalore-52 (Autonomous) (Id: C-20774)"
            , "Lal Bahadur Shastri College of Arts, Science and Commerce,  17, Malhar Peth, Satara (Id: C-11116)"
            , "Mahishadal Raj College (Id: C-19045)"
            , "Mahatma Education Societys  Pillais College of Arts Commerce & Science Dr. K.M.Vasudevan Pillas Campus  Plot No  10 Sector 16  Podi No  2 New Panvel  Navi Mumbai  410206 (Id: C-33961)"
            , "Mahatma Gandhi Arts Commerce Science, Armori (Id: C-18355)"
            , "K.G. College of Arts and Science (Id: C-41125)"
            , "Jawaharlal Institute of Post Graduate Medical Education & Research, Puducherry (Id: U-0368)"
            , "Mysore University, Mysore (Id: U-0235)"
            , "GOVERNMENT POLYTECHNIC JALGAON (Id: S-1793)"
            , "GOVERNMENT WOMAN POLYTECHNIC COLLEGE, JAIPUR (Id: S-2597)"
            , "CH.RAM SINGH MAHAVIDYALAYA NAHAL AUTROLI ALIGARH (Id: C-15390)"
            , "GOVERMENT GIRLS POLYTECHNIC, CHARKHARI (Id: S-16226)"
            , "Bhagat Phool Singh  Mahila University, Khanpur Kalan, Sonipat (Id: U-0157)"
            , "A.D.M. College for Women, Nagapattinam - 611 001. (Id: C-35805)"
            , "Vishwa Bharti College, Sikar (Id: C-38999)"
            , "JAGAN NATH UNIVERSITY, JHAJJAR (Id: C-53480)"
            , "Indira Gandhi National Tribal University, Amarkantak (Id: U-0290)"
            , "IslamiahWomen`SCollege,!0,Bye-Pass Road,NewTown,Vaniyambadi-635752 (Id: C-36430)"
            , "M.S.P. Mandal`s Arts,Science & Commerce College, Kille Dharur. (Id: C-34646)"
            , "Mahatma Gandhi Mission`s Medical College,Aurangabad (Id: C-44885)"
            , "Ly. Khalsa College  Jalandhar (Id: C-27849)"
            , "Krantiveer Vasantrao Narayanrao Naik Shikshan Prasarak Sansthas Arts & Commerce College, Dindori, Dist.Nashik (Id: C-41216)"
            , "Mugberia Gangadhar Mahavidyalaya (Id: C-19112)"
            , "MARY MATHA COLLEGE, MADURAI (Id: C-45945)"
            , "NS Patel Arts College, Anand (Id: C-1151)"
            , "RAJAPALAYAM RAJU`S COLLEGE (Id: C-36542)"
            , "Ch.S.D. St. THERESAS (A) COLLEGE FOR WOMEN (Id: C-23939)"
            , "C.M.S College, Kottayam - 686 001 (Id: C-11771)"
            , "CHEVALIER T THOMAS ELIZABETH COLLEGE FOR WOMEN (Id: C-44011)"
            , "Bharat College of Education, Babain (Id: C-10584)"
            , "BANSAL INSTITUTE OF ENGINEERING & TECHNOLOGY,LUCKNOW (Id: C-49307)"
            , "Acharya Instiute Graduate Studies(of Journalism), No.80-90, Soladevanahalli, Chikkabanavara Post, Bangalore -90 (Id: C-20716)"
            , "AL HILAL MISSION TEACHERS TRAINING INSTITUTE (Id: C-48709)"
            , "Annai College of Arts & Science, Kumbakonam-612 503. (Id: C-35839)"
            , "Govt. Benazeer College Golghar Chaoraha, Shahjahanabad, Bhopal (Id: C-35156)"
            , "Goutami Institute of Technology & Management for Women, Proddatur (Id: C-27006)"
            , "Guru Nanak Girls College, Santpura (Id: C-12031)"
            , "Gurukul Kangri Vishwavidyalaya, Haridwar (Id: U-0556)"
            , "Dempo Charities Trusts S.S. Dempo College of Commerce & Economics  Altinho (Id: C-30828)"
            , "Dolhpin (PG) College of Life Sciences, Chuni, Fatehgarh Sahib (Id: C-22189)"
            , "Shri. Guru Tegh Bahadur Khalsa College, Anandpur Sahib (Id: C-22277)"
            , "Shri Ambabai Talim Sanstha, Sanjay Bhokare Group of Institutes, Faculty of Engineering,  Tilak Nagar, Miraj-Sangli Road, Miraj ? 416 414, Dist-Sangli. (Id: C-49105)"
            , "Veerashaiva College, Bellary (Id: C-8575)"
            , "Yashwantrao Chavan Arts Commerce College, Lakhandoor (Id: C-18804)"
            , "St. Paul`s College, Kalamasserry - 683 503 (Id: C-11796)"
            , "NAIPUNYA INSTITUTE OF MANAGEMENT AND INFORMATION TECHNOLOGY, KORATTY (Id: C-43314)"
            , "GOVT POLYTECHNIC (MINING INSTITUTE) BHAGA DHANBAD (Id: S-1156)"
            , "Vidya Prasarak Mandals K G Joshi College of Arts & N G Bedekar College of  Commerce  Chendani Bunder Road  Thane  400 601 (Id: C-33995)"
            , "Sarah Tucker College, Perumalpuram, Palayamkottai - 627 011 (Id: C-41180)"
            , "Sant Gadge Baba Amravati University, Amravati (Id: U-0324)"
            , "S.R.NAIDU MEMORIAL COLLEGE (Id: C-36494)"
            , "Sibsagar Commerce College P.O. Sibsagar-- 785640 (Id: C-8443)"
            , "Sri Rachapudi Naga Bhusana Degree College (Id: C-30234)"
            , "St. Anthony`s College (Id: C-16310)"
            , "St. Aloysius College, Mangalore-575003 (Id: C-16897)"
            , "STELLA MARIS COLLEGE FOR WOMEN (Id: C-43931)"
            , "Egra Sarada Sashi Bhusan College (Id: C-19107)"
            , "DRBCCC HINDU COLLEGE (Id: C-43903)"
            , "Fr. Muller Medical College (Id: C-40208)"
            , "G.G.D.S.D.College, Sector-32 (Id: C-29440)"
            , "Diphu Govt College (Id: C-26410)"
            , "Gujarat University, Ahmedabad (Id: U-0136)"
            , "Govt. Degree College Pattan, Kashmir (Id: C-21443)"
            , "Govt. D. B. P.G. Girls College kalibadi Chowk (Id: C-21733)"
            , "Government maharaja (Auto) PG college chhatarpur (Id: C-53535)"
            , "AYYA NADAR JANAKI AMMAL COLLEGE (Id: C-36579)"
            , "Atma Ram Sanatan Dharam College (Id: C-22462)"
            , "Baruipur College (Id: C-11969)"
            , "Bajali College (Id: C-17107)"
            , "B.P.H.E.Society`s Ahmednagar College, Ahmednagar 414001. (Id: C-41242)"
            , "CHAUDHARY RANBIR SINGH UNIVERSITY, JIND (Id: U-0738)"
            , "CHELLAMMAL WOMENS COLLEGE (Id: C-43980)"
            , "CHAS COLLEGE, CHAS, BOKARO (Id: C-44403)"
            , "Bishop Heber College, Tiruchirappalli - 620 017. (Id: C-35825)"
            , "Biyani Girls College, Jaipur (Id: C-38706)"
            , "Rajmata Sindhiya Govt. Girls College CHHINDWARA (Id: C-57170)"
            , "R.N.A.R. College (Id: C-8763)"
            , "RAMA DEVI WOMEN`S UNIVERSITY, BHUBANESWAR (Id: U-0838)"
            , "Rashtriya Charitable Trust Aurangabad Rashtriya College of Arts, Commerce & Science College (Id: C-34663)"
            , "Pune Zilha Shikshan Mandals Annasaheb Magar Mahavidyalaya Arts, Science & Commerce, Hadapsar, Pune 28 (Id: C-41752)"
            , "NSHM Knowledge Campus, Kolkata  Group of Institutions 277 (Id: C-6269)"
            , "NSS COLLEGE, MANJERI (Id: C-8207)"
            , "Odisha State Open University(U-0932)"
            , "Neminath Jain Brahmacharya Aashram Trusts Karmaveer K.H.Abad Arts & Shri.M.G.Lodha Commerce, Chandwad, Dist.Nashik 423101 (Id: C-42199)"
            , "MGA INSTITUTE OF POLYTECHNIC (Id: S-16504)"
            , "Modern Education Societys Ness Wadia College of Commerce, P.V.K.Joag Path, Pune 411004 (Id: C-41463)"
            , "Muthayammal College of Arts & Science (Id: C-9490)"
            , "Kirori Mal College (Id: C-6385)"
            , "Krantiveer Vasantrao Narayanrao Naik Education Societys Arts, Commerce  & Science College, Canada Corner, Sharanpur Road, Nashik 422002 (Id: C-41248)"
            , "Kongu Arts and Science College (Id: C-41104)"
            , "Kishinchand Chellaram College Dinshaw  Wachha Road  Churchgate Mumbai  400 020 (Id: C-33753)"
            , "LOYOLA COLLEGE (Id: C-44006)"
            , "Jamal Mohamed College, Tiruchirappalli - 620 020. (Id: C-35832)"
            , "K.T.H.M.Arts, Science and Commerce College and Research Centre, Shivajinagar, Nashik 422002 (Id: C-41276)"
            , "GOVERNMENT GIRLS POLYTECHNIC BAREILLY (Id: S-3198)"
            , "Government College, Jind (Id: C-10780)"
            , "F.A. Ahmed College (Id: C-17139)"
            , "Arts & Comm. College, NAGTHANE (Id: C-11212)"
            , "Bharat Shikshan Sanstha`s College of Arts, Makni. (Id: C-34386)"
            , "D.G. COLLEGE, (Id: C-12428)"
            , "Chatrapati Sahuji Maharaj Kanpur University, Kanpur (Id: U-0505)"
            , "Central University of Karnataka, Gulbarga (Id: U-0216)"
            , "N.S.S College, Rajakumari, Kulapparachal P.O. 685 619 (Id: C-11688)"
            , "Modern Education Societys Ness Wadia College of Commerce, P.V.K.Joag Path, Pune 411004 (Id: C-41463)"
            , "MATA SUNDARI UNIVERSITY GIRLS COLLEGE MANSA (Id: C-49546)"
            , "Maharshi Dayanand University, Rohtak (Id: U-0167)"
            , "Padmabhushan Vasantdada Patil, Arts & Science College, Patoda. (Id: C-34370)"
            , "Ponda Education Society Shri Ravi S Naik College of Arts & Science (Id: C-30860)"
            , "Principal  L N Welingkar Institute of Management Development and Research L. N. Road, Opp. Matunga Gymkhana, Matunga (Central) Mumbai 400 019 (Id: C-34129)"
            , "Prof Ramkrishna More Arts,Commerce & Science College, Akurdi, Pune 44 (Id: C-41446)"
            , "Raja Shripatrao Bhagawantrao Mahavidyalaya,  AUNDH (Id: C-11140)"
            , "Sengamala Thayaar Educational Trust Women`s College, Mannargudi - 614 001. (Id: C-35824)"
            , "Sri Nehru Maha Vidyalaya College of Arts and Science (Id: C-41040)"
            , "Jeevandip Shikshanik Sansthas Arts Commerce and Science  Goveli  Poi  Dist Thane (Id: C-34121)"
            , "Jeyaraj Annapackiyam College for Women, Periyakulam (Id: C-17047)"
            , "J.T.S.S.P Mandals Shri Shiv Chhatrapati College of Arts, Science & Commerce, Bodkenagar, Junnar Dist. Pune 410 502 (Id: C-42032)"
            , "Jagadamba Mahvidyalaya , Achalpur City (Id: C-42838)"
            , "M. E. S College, Marampally, North Vazhakulam, Aluva- 683 107 (Id: C-11732)"
            , "M.S. Bidwe College of Engineering, Latur (Id: C-7593)"
            , "Madurai Sivakasi Nadar Pioneer Meenaksmi Women college, Poovanthi (Id: C-28534)"
            , "Mar Athanasius College, Kothamangalam- 686 666 (Id: C-11815)"
            , "Presidency College, No.33-2C, 33-2D, Kempapuara Hebbal, Bangalore-24 (Id: C-20686)"
            , "Parwati Mahila Degree College, Doharighat, Mau (Id: C-15981)"
            , "Rani Bhagyavati Devi Mahila Mahavidyalaya (Id: C-13481)"
            , "RR Bawa DAV. College for Girls Batala (Id: C-27862)"
            , "Rosary College of Commerce & Arts  Navelim (Id: C-30852)"
            , "Raghunath Jew College, Deuliisahi,Cuttack (Id: C-39765)"
            , "Pune Zilha Shikshan Prasarak Mandals Baburaoji Gholap Mahavidyalaya Arts, Science & Commerce,Sangavi, Pune 27 (Id: C-41703)"
            , "BUNDELKHAND INSTITUTE OF ENGINEERING AND TECHNOLOGY, JHANSI (Id: C-46805)"
            , "Calicut University, Thenhipalem, Malapuram (Id: U-0251)"
            , "CARMEL COLLEGE, MALA (Id: C-7994)"
            , "Balagarh B.K. Mahavidyalaya (Id: C-44697)"
            , "Balasaheb Desai College, PATAN (Id: C-10994)"
            , "Bhawabhuti Mahavidyalaya, Amgaon (Id: C-18394)"
            , "Bhawanipur Anchalik College (Id: C-17089)"
            , "Asannagar Madan Mohan Tarkalankar College (Id: C-7063)"
            , "Apex College, Makrana (Id: C-13102)"
            , "Anand Charitable Trust`s Anandrao Alias Babaji Dhonde Arts,Commerce & Science College, Kada. (Id: C-34333)"
            , "Alphonsa College, Pala - 686 574 (Id: C-11593)"
            , "Aishwarya College, A-9, Hudco Quarters, Kamala Nehru Nagar, Jodhpur (Id: C-37183)"
            , "ABES INSTITUTE OF TECHNOLOGY, GHAZIABAD (Id: C-46141)"
            , "200005-VWS`S ARTS,COMMERCE&SCIENCE COLLEGE, DHULE. (Id: C-8867)"
            , "240036-KVPS`S SMT.P.D.M.ARTS, SMT.H.D.COMMERCE & S.M.A.SCIENCE COLLEGE, SHIRPUR. (Id: C-9024)"
            , "140022-MGTSM`S ARTS,SCIENCE & COMMERCE COLLEGE, CHOPDA. (Id: C-8975)"
            , "Government College, Koraput (Id: C-39360)"
            , "Govt. Degree College Boys Baramullah, Kashmir (Id: C-21409)"
            , "Govt. College for Women, Thiruvananthapuram (Id: C-43705)"
            , "GOVT. VICTORIA COLLEGE, PALAKKAD (Id: C-8209)"
            , "Govt.College of Arts Science & Commerce  Sanquelim (Id: C-30847)"
            , "Govt. Degree College for Women, Guntur (Id: C-32670)"
            , "HHMSPB NSS College for Women, Neeramankara, Thiruvananthapuram (Id: C-43645)"
            , "Hindu College, Sonepat. (Id: C-28123)"
            , "Dayanand Commerce College, Latur (Id: C-7449)"
            , "Dev Samaj College for Women, Firozpur (Id: C-31037)"
            , "Devamatha College, Kuravilangad-686633 (Id: C-11579)"
            , "G.C. for Women, M.Garh. (Id: C-28414)"
            , "Dr. B.R. Ambedkar Satabarshiki Mahavidyalaya (Id: C-43341)"
            , "Dr.Babasaheb Ambedkar College of Arts  Sience and Commerce  Mahad Dist Raigad (Id: C-34043)"
            , "G.Venkataswamy Naidu College, Kovilpatti - 628 502, Thoothukudi Dist. (Id: C-41187)"
            , "State Institute of Hotel Management and Catering Technology and Applied Nutrition, New Tehri (Id: C-52450)"
            , "Swami Vivekanand Govt. Degree College Ghurmarwin, Distt Bilaspur (Id: C-11404)"
            , "St. Xavier`s College for Women, Aluva - 683 101 (Id: C-11756)"
            , "St. Claret College, MES Road, 5th Cross, 1st Main, Sharadambangar, Bangalore-13 (Id: C-20977)"
            , "St. Thomas College, Arunapuram P.O., Pala-686 574 (Id: C-11591)"
            , "ST. THOMAS COLLEGE, THRISSUR (Id: C-8169)"
            , "Sri Ramakrishna College of Arts and Science for Women (Id: C-41059)"
            , "ST  JOSEPH COLLEGE, DARJEELING (Id: C-45349)"
            , "Sibsagar Girls College P.O.Sibsagar-785640 (Id: C-8380)"
            , "Siddharth Arts, Commerce & Science College,Jafrabad. (Id: C-34479)"
            , "Smt. Vatsalabai Naik Women College, Pusad. (Id: C-43080)"
            , "Sree Sankara College, Kalady 683 574 (Id: C-11702)"
            , "S.B.K. COLLEGE (Id: C-36553)"
            , "Sabour College, Sabour (Id: C-17641)"
            , "Shri Asaramji Bhandwaldar Arts, Science & Commerce College (Id: C-34615)"
            , "SHRI U P ARTS & SMT MGP SCI & SHRI VLS COMM COLLEGE (Id: C-6642)"
            , "Shri Shivaji College, Vasmat Road, Parbhani (Id: C-7371)"
            , "SETH BADRI PRASAD SMRATI MAHAVIDYALAYA, KONCH (Id: C-49945)"
            , "VINAYAKA MISSIONs RESEARCH FOUNDATION, SALEM (Id: U-0492)"
            , "Vivekananda College (Id: C-43346)"
            , "T.K.M.College of Arts and Science, Kollam (Id: C-43738)"
            , "U.V.K. College, Karama (Id: C-29630)"
            , "St James College of pharmaceutical Sciences (Id: C-50870)"
            , "R.B. Attal Arts, Science & Commerce College, Georai. (Id: C-34591)"
            , "V.S.M.College (Id: C-24390)"
            , "Mahatma Education Societys Pillai HOC College of Arts, Science and Commerce (Id: C-33633)"
            , "Maharaja Ranjit Singh College of Prof. Science, Khandwa Road, Indore (Id: C-29856)"
            , "K.M. Government  College, Narwana (Id: C-10608)"
            , "Koshys Institute of  Management Studies, No.31/1, Hennur-Bagalur Road, Kadusonnappana Halli, Bangalore -562 149 (Id: C-20942)"
            , "Bangabasi Evening College (Id: C-11890)"
            , "Chikitsak Samuhas Sir Sitaram and Lady Shantabai  Patkar College of Arts and Science and V R Varde College of Commerce and Economics S V Road Goregaon West Mumbai  400 062 (Id: C-33734)"
            , "APJ College of Fine Arts Jalandhar (Id: C-27900)"
            , "Government College, Suratgarh (Id: C-40759)"
            , "Govt. Degree College Kargil (Id: C-21423)"
            , "Vaish Arya Kanya M. B.Garh(Jhajjar) (Id: C-28165)"
            , "VIVEKANANDA DEGREE & PG COLLEGE, JAGTIAL ROAD, KARIMNAGAR (Id: C-21492)"
            , "VIVEKANANDA INSTITUTE OF TECHNOLOGY (EAST) (Id: C-25226)"
            , "Vyasa Nagar College,Jajpur Road, Jajpur (Id: C-39740)"
            , "Vivek College of Education (Id: C-13440)"
            , "Vivekanand Institute of Technology,Jaipur (Id: C-25237)"
            , "Shikshan Vikas Mandals S H  Kelkar College of Arts  Science and Commerce  Devgad Dist Sindhudurg  416 613 (Id: C-34108)"
            , "Shri Ratanlal Kanwarlal Patni Girls` College (Id: C-53433)"
            , "Shri Vasntro Naik Mahavidyalaya Dharani (Id: C-42854)"
            , "SHRI SATHYA SAI MEDICAL COLLEGE AND RESEARCH INSTITUTE (Id: C-47765)"
            , "S.B.M.S. College (Id: C-17350)"
            , "SCMS School of Technology & Management, Muttom, Aluva (Id: C-11633)"
            , "Siddheswar College (Id: C-21354)"
            , "St.Xavier`s College, Thumba, Thiruvananthapuram (Id: C-43653)"
            , "Swami Vivekananda Institute of Modern Science 264 (Id: C-6214)"
            , "Takshila College, RAchana Nagar, Bhopal (Id: C-35286)"
            , "TEACHERS ACADEMY GROUP OF INSTITUTIONS (Id: C-57358)"
            , "Digboi Mohila Mahavidyalaya P.O. Digboi-786171 (Id: C-8469)"
            , "DR. A.H. RIZVI DEGREE COLLEGE, KARARI, (Id: C-12455)"
            , "D416 Govt First Grade Degree College Manvi (Id: C-9319)"
            , "CSSR & SRRM Degree College, Kamalapuram (Id: C-30225)"
            , "COLLEGE OF ENGINEERING AND RURAL TECHNOLOGY, MEERUT (Id: C-46415)"
            , "Hindu Kanya College Kapurthala (Id: C-27998)"
            , "Guru Gobind Singh College, Sanghera (Id: C-22174)"
            , "Gujarat National Law University, Gandhinagar (Id: U-0134)"
            , "GREAT LAKES INSTITUTE OF MANAGEMENT (Id: S-327)"
            , "GOvt. Degree College, Reasi (Id: C-22934)"
            , "Govt. College, HODEL (Id: C-28314)"
            , "Govt. College, Nahan, Distt Sirmour (Id: C-11522)"
            , "GOVERNMENT COLLEGE FOR WOMEN SAFIDON (Id: C-49385)"
            , "ABHINAV PRAGYA MAHAVIDYALAYA, HARDUARPUR, (Id: C-12381)"
            , "AMAL COLLEGE OF ADVANCED STUDIES, MYLADI (Id: C-8040)"
            , "Amolak Jain Shikshan Prasarak Mandal`s, College of Arts,Science & Commerce, Kada. (Id: C-34612)"
            , "B.T.I. of Excellence Sagar (Id: C-19188)"
            , "B.E. Society Daund`s Lt. K. G. Kataria College of Arts & Science, Address: Ambedkar Chawk, Siddhtech Road, Daund, Taluka: Daund, District: Pune (Id: C-41827)"
            , "CHAUDHARY BANSI LAL UNIVERSITY (Id: U-0788)"
            , "Chaman Lal Mahavidhyalaya (Id:C-58847)"
            , "Chandra Shekhar Azad Degree College (Id: C-52645)"
            , "City College of Education, Bhopal (Id: C-35255)"
            , "PERIYAR UNIVERSITY COLLEGE OF ARTS AND SCIENCE, IDAPPADI (Id: C-47502)"
            , "Pune Zilha Shikshan Mandals Late Shankarrao Bhelke Arts, Science & Commerce College, Nasarapur, Tal. Bhor, Dist. Pune 412213 (Id: C-41339)"
            , "Pt. Deen Dayal Upadhyay Rajkiya Mahila Mahavidyalaya, Sewapuri, Varanasi (Id: C-13673)"
            , "Matoshree Shantabai Gote Arts, Commerce & Science College, Washim. (Id: C-43109)"
            , "Mary Matha Arts & Science College, P.O. Vemom, Mananthavady, Wayanad (Id: C-43785)"
            , "MES KEVEEYAM COLLEGE, VALANCHERI (Id: C-8037)"
            , "N.D.M.V.P.Samajs Arts,Commerce & Science College, Nandgaon,Nashik 423106 (Id: C-42014)"
            , "Naipunya School of Management, Near Manorama Jn., Cherthala (Id: C-43659)"
            , "New Horizon College, Kasturinagar, NGEF Layout, Bangalore-43 (Id: C-20739)"
            , "Nambol L. Sanoi College (Id: C-9398)"
            , "PACHAIYAPPAS COLLEGE FOR MEN (Id: C-44005)"
            , "Mahila Mahavidyalaya, Gadchiroli (Id: C-18661)"
            , "Manoharbhai Patel Arts Commerce Science College, Sakoli (Id: C-18749)"
            , "Late. Shankarrao Gutte Gramin Arts, Science & Commerce College, Dharmpuri. (Id: C-34287)"
            , "Kiranlata Singh Mahavidyalay Kaundar Asothar Fatehpur (Id: C-57818)"
            , "J. M. Shah Arts & Commerce College (Id: C-500)"
            , "J.H.N.S. College P.O.Jahinji-785683 (Id: C-8436)"
            , "ISBM University (Id: U-0895)"
            , "I.P.E.M. LAW COLLEGE A-13/1, SOUTH OF G.T. ROAD, INDUSTRIAL AREA, DELHI-HAPUR BYPASS, NATIONAL HIGHWAY-24, GHAZIABAD, Ph. No- 741189,  721633 (Id: C-28562)"
            , "Government Engineering College Chaibasa  (Id: C-54194)"
            , "DON BOSCO COLLEGE, SULTHAN BATHERY (Id: C-8110)"
            , "Ahmednagar Jilha Maratha Vidya Prasarak Samajs New Arts,Commerce & Science College, Shevgaon, Dist. Ahmednagar 414502 (Id: C-41346)"
            , "100006-KRES`S ADV.SITARAM (BABANBHAU) A.BAHETI ARTS,COMMERCE&SCIENCE COLLEGE, JALGAON (Id: C-9025)"
            , "BLDEA`s SP College of Commerce, Bijapur (Id: C-44849)"
            , "Kaswan Kanya College, Tibbi (Id: C-40746)"
            , "KLESs  P.C.Jabin Science College, Hubli  (Autonomous) (Id: C-35573)"
            , "KNM Govt. Arts and Science College, Kanjiramkulam (Id: C-43737)"
            , "M.E.S College, Erumeli 686 509 (Id: C-11705)"
            , "Institute of Information Technology & Management (Id: C-32837)"
            , "Institute of Prof. Education Research,  Bhojpur Road, Bhopal (Id: C-35116)"
            , "M.V.N.J.S & R.V.R Degree College (Id: C-23886)"
            , "MSP Mandal`s Yeshwantrao Chavan Arts & Commerce College, Ambajogai. (Id: C-34421)"
            , "Prabhakar Patil Education Societys College of Arts  Science & Commerce  at Veshvi  Taluka  Alibag  Dist  Raigad. (Id: C-34134)"
            , "Prathima Institute of Medical Sciences, Karimnagar (Id: C-30564)"
            , "Patherkandi College (Id: C-26418)"
            , "Niranjan Government Women`s College, Aska (Id: C-39334)"
            , "SUKLA DEVI ACADEMY FOR B ED (Id: C-50779)"
            , "Vinayaka Missions Sikkim University, Tadong (Id: U-0433)"
            , "Sri  Adichunchanagiri  First Grade College ,Chennarayapatna (Id: C-17456)"
            , "RAI UNIVERSITY (Id: U-0790)"
            , "BASANTIKA INSTITUTE OF ENGINEERING & TECHNOLOGY BIRBHUM (Id: S-10875)"
            , "GIDC DEGREE ENGINEERING COLLEGE, NAVSARI (Id: C-50737)"
            , "Dr Ambedkar Social work, Borgaon Naka (Id: C-18710)"
            , "Acharya Degree College, D.No. 18-144/26, Dwarakapet Road, Opp. Fire Station, W.No. 18, Narsampet (Id: C-27234)"
            , "Rungta College of Science and Technology G. E. Road, Ganjpara, Durg  (Id: C-21664)"
            , "Nutan Mahavidyalaya, Selu (Id: C-7386)"
            , "Yeshwantrao Chavan Science College, Mangrulpir. (Id: C-43008)"
            , "Shetkari Shikshan Prasarak Mandal`s Arts, Science & Commerce College, Dhanora (Id: C-34657)"
            , "Rajeev Gandhi Memorial GNM Nursing College Sinnar (Id: S-16046)"
            , "RAMKRISHNA COLLEGE OF EDUCATION (Id: C-58132)"
            , "JNP COLLEGE OF EDUCATION (Id: C-58143)"
            , "ARYAN EDUCATION AND RESEARCH INSTITUTE (Id: C-56800)"

    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_registration);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        gps = new GPSTracker(VRPRegistration.this);

        dbVrp = new DbVrp(this);
        //dbProfile = new DbProfile(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>" + getResources().getString(R.string.app_name) + "</font>"));
        scroll = (NestedScrollView) findViewById(R.id.scroll);
        image = (CircleImageView) findViewById(R.id.imageview);
        geotag = (CustomFontEditText) findViewById(R.id.geotag);
        depNo = (CustomFontEditText) findViewById(R.id.depNo);
        contact = (CustomFontEditText) findViewById(R.id.contact);
        institution = (AutoCompleteTextView) findViewById(R.id.institution);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, INSTITUTIONS);
        //Getting the instance of AutoCompleteTextView
        institution.setThreshold(1);//will start working from first character
        institution.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        institution.setTextColor(Color.RED);


        ImageView georefresh = (ImageView) findViewById(R.id.refresh);
        email = (AutoCompleteTextView) findViewById(R.id.gmail);
        submit = (CustomFontTextView) findViewById(R.id.r_submittxt);
        password = (CustomFontEditText) findViewById(R.id.password);
        whatsapp = (CustomFontEditText) findViewById(R.id.whatspp);
        name = (CustomFontEditText) findViewById(R.id.name);
        address = (CustomFontEditText) findViewById(R.id.address);
        confirmpassword = (CustomFontEditText) findViewById(R.id.confirmpassword);

        georefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check if GPS enabled
                gps = new GPSTracker(VRPRegistration.this);
                if (gps.canGetLocation()) {

                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();

                    geotag.setText(latitude + "," + longitude);
                    // \n is for new line
                    //       Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                } else {
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }
            }
        });
        // check if GPS enabled
        if (gps.canGetLocation()) {
            gps = new GPSTracker(VRPRegistration.this);
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            geotag.setText(latitude + "," + longitude);
            // \n is for new line
            //       Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickSetup setup = new PickSetup();
                PickImageDialog.build(setup).setOnPickResult(new IPickResult() {
                    @Override
                    public void onPickResult(PickResult pickResult) {
                        GlideApp.with(VRPRegistration.this).load(pickResult.getUri())
                                .centerCrop()
                                .dontAnimate()
                                .thumbnail(0.5f)
                                .placeholder(R.drawable.profile)
                                .into(image);
                        imageUri = pickResult.getUri().toString();
                    }
                })
                        .show(VRPRegistration.this);
            }
        });


        if (sharedpreferences.contains(update)) {
            try {
                Vrp vrp = new Gson()
                        .fromJson(ConvertUtils.sample(dbVrp.getDataByvrpid(sharedpreferences.getString(vrpid, "")).get(1)), Vrp.class);
                imageUri = vrp.getImage();
                geotag.setText(vrp.getGeotag());
                email.setText(vrp.getGmail());
                contact.setText(vrp.contact);
                name.setText(vrp.name);
                institution.setText(vrp.institution);
                address.setText(vrp.address);
                depNo.setText(vrp.depNo);
                whatsapp.setText(vrp.whatsapp);
                password.setText(dbVrp.getDataByvrpid(sharedpreferences.getString(vrpid, "")).get(2));
                confirmpassword.setText(dbVrp.getDataByvrpid(sharedpreferences.getString(vrpid, "")).get(2));
                GlideApp.with(VRPRegistration.this).load(imageUri)
                        .centerCrop()
                        .dontAnimate()
                        .thumbnail(0.5f)
                        .placeholder(R.drawable.profile)
                        .into(image);
                submit.setText("SUBMIT");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!(contact.getText().toString() != null && contact.getText().toString().length() == 10)) {
                    name.setError("Enter valid number");
                } else if (name.getText().toString() == null && name.getText().toString().length() <= 0) {
                    name.setError("Enter valid name");
                } else if (!password.getText().toString().trim().equals(confirmpassword.getText().toString().trim())) {
                    Toast.makeText(getApplicationContext(), "Password dosn't match", Toast.LENGTH_SHORT).show();
                } else if (email.getText().toString().length() <= 0
                        ) {
                    Toast.makeText(getApplicationContext(), "Enter all fields", Toast.LENGTH_SHORT).show();
                } else {
                    String vrpidnew;
                    Vrp farmerdata = new Vrp();
                    farmerdata.setData(sharedpreferences.getString(vrpid, ""), imageUri
                            , name.getText().toString()
                            , geotag.getText().toString()
                            , address.getText().toString()
                            , contact.getText().toString()
                            , whatsapp.getText().toString()
                            , institution.getText().toString()
                            , email.getText().toString()
                            , depNo.getText().toString(),
                            password.getText().toString()
                    );
                    if (sharedpreferences.contains(update)) {
                        if (checkInternetConnection()) {
                            registerUser(farmerdata, true);
                        } else {
                            Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        farmerdata.setId(contact.getText().toString() + "vrp_" + String.valueOf(System.currentTimeMillis()));
                        if (checkInternetConnection()) {
                            registerUser(farmerdata
                                    , false);
                            gps = new GPSTracker(VRPRegistration.this);
                        } else {
                            Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_SHORT).show();
                        }

                    }
                }

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_logout, menu);
        if (!sharedpreferences.contains(update)) {
            MenuItem item = menu.findItem(R.id.logout);
            item.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                SharedPreferences.Editor editor1 = sharedpreferences.edit();
                editor1.remove(update);
                editor1.commit();
                finish();
                return true;
            case R.id.logout:
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.remove(vrpid);
                editor.remove(update);
                editor.commit();
                Intent io = new Intent(VRPRegistration.this, MainActivity.class);
                io.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                io.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(io);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == QrScannerActivity.QR_REQUEST_CODE) {

        }
    }

    private boolean isReadStorageAllowed() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;
        return false;
    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startActivityForResult(
                        new Intent(VRPRegistration.this, QrScannerActivity.class),
                        QrScannerActivity.QR_REQUEST_CODE);
            } else {
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }


    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     */
    private void registerUser(final Vrp mdata, final boolean mUpdate) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";
        String url = AppConfig.URL_CREATE_NEW_VRP;
        if (mUpdate) {
            pDialog.setMessage("Updating ...");
        } else {
            pDialog.setMessage("Registering ...");
        }
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        if (mUpdate) {
                            dbVrp.updatedataByvrpid(sharedpreferences.getString(vrpid, ""),
                                    new Gson().toJson(mdata));
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.remove(update);
                            editor.commit();
                            Intent io = new Intent(VRPRegistration.this, ProfileActivity.class);
                            startActivity(io);
                            finish();
                        } else {
                            dbVrp.addData(mdata.getStudentid(), new Gson().toJson(mdata));
                            dbVrp.updatePassByvrpid(mdata.getStudentid(), mdata.password);
                            Toast.makeText(getApplicationContext(), "Student successfully registered." +
                                    " Try login now!", Toast.LENGTH_LONG).show();
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString(vrpid, mdata.studentid);
                            editor.commit();
                        }
                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("message");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                if (mUpdate) {
                    params.put("id", mdata.getStudentid());
                }
                params.put("vrpid", mdata.getStudentid());
                params.put("data", new Gson().toJson(mdata));
                params.put("password", mdata.password);
                params.put("username", mdata.name);
                params.put("mobile", mdata.contact);
                params.put("institution", mdata.institution);
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


    private boolean checkInternetConnection() {
        ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() && conMgr.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }
    }


}
