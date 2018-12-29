package nec.cst.pra.village;

import java.util.ArrayList;

public class VillageMeeting {
    String id;
    String villageGeo;
    String reference;
    ArrayList<ImportantPerson> importantPeoples;
    String date;
    String time;
    String pmalecount;
    String pfemalecount;
    String fmalecount;
    String ffemalecount;
    String agenda;
    String consent;
    ArrayList<Base> bases;

    public VillageMeeting(String id, String villageGeo, String reference, ArrayList<ImportantPerson> importantPeoples, String date, String time, String pmalecount, String pfemalecount, String fmalecount, String ffemalecount, String agenda, String consent, ArrayList<Base> bases) {
        this.id = id;
        this.villageGeo = villageGeo;
        this.reference = reference;
        this.importantPeoples = importantPeoples;
        this.date = date;
        this.time = time;
        this.pmalecount = pmalecount;
        this.pfemalecount = pfemalecount;
        this.fmalecount = fmalecount;
        this.ffemalecount = ffemalecount;
        this.agenda = agenda;
        this.consent = consent;
        this.bases = bases;
    }

    public VillageMeeting(String villageGeo, String reference, ArrayList<ImportantPerson> importantPeoples, String date, String time, String pmalecount, String pfemalecount, String fmalecount, String ffemalecount, String agenda, String consent, ArrayList<Base> bases) {
        this.villageGeo = villageGeo;
        this.reference = reference;
        this.importantPeoples = importantPeoples;
        this.date = date;
        this.time = time;
        this.pmalecount = pmalecount;
        this.pfemalecount = pfemalecount;
        this.fmalecount = fmalecount;
        this.ffemalecount = ffemalecount;
        this.agenda = agenda;
        this.consent = consent;
        this.bases = bases;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVillageGeo() {
        return villageGeo;
    }

    public void setVillageGeo(String villageGeo) {
        this.villageGeo = villageGeo;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public ArrayList<ImportantPerson> getImportantPeoples() {
        return importantPeoples;
    }

    public void setImportantPeoples(ArrayList<ImportantPerson> importantPeoples) {
        this.importantPeoples = importantPeoples;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPmalecount() {
        return pmalecount;
    }

    public void setPmalecount(String pmalecount) {
        this.pmalecount = pmalecount;
    }

    public String getPfemalecount() {
        return pfemalecount;
    }

    public void setPfemalecount(String pfemalecount) {
        this.pfemalecount = pfemalecount;
    }

    public String getFmalecount() {
        return fmalecount;
    }

    public void setFmalecount(String fmalecount) {
        this.fmalecount = fmalecount;
    }

    public String getFfemalecount() {
        return ffemalecount;
    }

    public void setFfemalecount(String ffemalecount) {
        this.ffemalecount = ffemalecount;
    }

    public String getAgenda() {
        return agenda;
    }

    public void setAgenda(String agenda) {
        this.agenda = agenda;
    }

    public String getConsent() {
        return consent;
    }

    public void setConsent(String consent) {
        this.consent = consent;
    }

    public ArrayList<Base> getBases() {
        return bases;
    }

    public void setBases(ArrayList<Base> bases) {
        this.bases = bases;
    }
}
