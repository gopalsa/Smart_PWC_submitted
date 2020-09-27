package smart.cst.pwc.action.meeting;

/**
 * Created by SanAji on 30-11-2018.
 */

public class Meeting {
    private String meetingSerial;
    private String meetingName;
    private String meetingDate;
    private String meetingNoOfParticipants;
    private String meetingKeyOutcomes;

    public Meeting(String meetingSerial, String meetingName, String meetingDate, String meetingNoOfParticipants, String meetingKeyOutcomes) {
        this.meetingSerial = meetingSerial;
        this.meetingName = meetingName;
        this.meetingDate = meetingDate;
        this.meetingNoOfParticipants = meetingNoOfParticipants;
        this.meetingKeyOutcomes = meetingKeyOutcomes;
    }

    public String getMeetingSerial() {
        return meetingSerial;
    }
    public void setMeetingSerial(String meetingSerial) {
        this.meetingSerial = meetingSerial;
    }

    public String getMeetingName() {
        return meetingName;
    }

    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }

    public String getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(String meetingDate) {
        this.meetingDate = meetingDate;
    }

    public String getMeetingNoOfParticipants() {
        return meetingNoOfParticipants;
    }

    public void setMeetingNoOfParticipants(String meetingNoOfParticipants) {
        this.meetingNoOfParticipants = meetingNoOfParticipants;
    }

    public String getMeetingKeyOutcomes() {
        return meetingKeyOutcomes;
    }

    public void setMeetingKeyOutcomes(String meetingKeyOutcomes) {
        this.meetingKeyOutcomes = meetingKeyOutcomes;
    }

    public void getMeetingKeyOutcomes(String s) {
    }
}


