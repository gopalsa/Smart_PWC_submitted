package smart.cst.pwc.survey;

import java.io.Serializable;
import java.util.ArrayList;

import smart.cst.pwc.electricity.Electricity;
import smart.cst.pwc.needs.Need;


public class Survey implements Serializable {

    String position;
    String id;
    String name;
    String gramPanchayat;
    String wards;
    String block;
    String district;
    String state;
    String constituency;
    String distanceDistrictHQ;
    String villageArea;
    String arableLandAgricultureArea;
    String forestArea;
    String housingAbadiArea;
    String areaUnderWaterBodies;
    String commonLandsArea;
    String wasteLand;
    String waterTable;
    String geoTag;
    ArrayList<Need> needs;
    String distanceHighway;
    String villageConnectedPaccaRoad;
    String roadLength;
    String yearOfConstruction;
    String schemeConstructed;
    String presentStatus;
    String lengthOfKachha;
    String lengthOfPakkka;
    String modeOfTransport;
    String frequencyOfAvailable;
    String typeOfForest;
    String communityForest;
    String governmentForest;
    String mainForestTrees;
    String energySpecies1;
    String energyArea1;
    String energySpecies2;
    String energyArea2;
    String energySpecies3;
    String energyArea3;
    ArrayList<Electricity> electricityArrayList;

    public Survey(String name, String gramPanchayat, String wards, String block, String district, String state, String constituency, String distanceDistrictHQ, String villageArea, String arableLandAgricultureArea, String forestArea, String housingAbadiArea, String areaUnderWaterBodies, String commonLandsArea, String wasteLand, String waterTable, String geoTag, ArrayList<Need> needs, String distanceHighway, String villageConnectedPaccaRoad, String roadLength, String yearOfConstruction, String schemeConstructed, String presentStatus, String lengthOfKachha, String lengthOfPakkka, String modeOfTransport, String frequencyOfAvailable, String typeOfForest, String communityForest, String governmentForest, String mainForestTrees, String energySpecies1, String energyArea1, String energySpecies2, String energyArea2, String energySpecies3, String energyArea3, ArrayList<Electricity> electricityArrayList) {
        this.name = name;
        this.gramPanchayat = gramPanchayat;
        this.wards = wards;
        this.block = block;
        this.district = district;
        this.state = state;
        this.constituency = constituency;
        this.distanceDistrictHQ = distanceDistrictHQ;
        this.villageArea = villageArea;
        this.arableLandAgricultureArea = arableLandAgricultureArea;
        this.forestArea = forestArea;
        this.housingAbadiArea = housingAbadiArea;
        this.areaUnderWaterBodies = areaUnderWaterBodies;
        this.commonLandsArea = commonLandsArea;
        this.wasteLand = wasteLand;
        this.waterTable = waterTable;
        this.geoTag = geoTag;
        this.needs = needs;
        this.distanceHighway = distanceHighway;
        this.villageConnectedPaccaRoad = villageConnectedPaccaRoad;
        this.roadLength = roadLength;
        this.yearOfConstruction = yearOfConstruction;
        this.schemeConstructed = schemeConstructed;
        this.presentStatus = presentStatus;
        this.lengthOfKachha = lengthOfKachha;
        this.lengthOfPakkka = lengthOfPakkka;
        this.modeOfTransport = modeOfTransport;
        this.frequencyOfAvailable = frequencyOfAvailable;
        this.typeOfForest = typeOfForest;
        this.communityForest = communityForest;
        this.governmentForest = governmentForest;
        this.mainForestTrees = mainForestTrees;
        this.energySpecies1 = energySpecies1;
        this.energyArea1 = energyArea1;
        this.energySpecies2 = energySpecies2;
        this.energyArea2 = energyArea2;
        this.energySpecies3 = energySpecies3;
        this.energyArea3 = energyArea3;
        this.electricityArrayList = electricityArrayList;
    }

    public Survey(String position, String id, String name, String gramPanchayat, String wards, String block, String district, String state, String constituency, String distanceDistrictHQ, String villageArea, String arableLandAgricultureArea, String forestArea, String housingAbadiArea, String areaUnderWaterBodies, String commonLandsArea, String wasteLand, String waterTable, String geoTag, ArrayList<Need> needs, String distanceHighway, String villageConnectedPaccaRoad, String roadLength, String yearOfConstruction, String schemeConstructed, String presentStatus, String lengthOfKachha, String lengthOfPakkka, String modeOfTransport, String frequencyOfAvailable, String typeOfForest, String communityForest, String governmentForest, String mainForestTrees, String energySpecies1, String energyArea1, String energySpecies2, String energyArea2, String energySpecies3, String energyArea3, ArrayList<Electricity> electricityArrayList) {
        this.position = position;
        this.id = id;
        this.name = name;
        this.gramPanchayat = gramPanchayat;
        this.wards = wards;
        this.block = block;
        this.district = district;
        this.state = state;
        this.constituency = constituency;
        this.distanceDistrictHQ = distanceDistrictHQ;
        this.villageArea = villageArea;
        this.arableLandAgricultureArea = arableLandAgricultureArea;
        this.forestArea = forestArea;
        this.housingAbadiArea = housingAbadiArea;
        this.areaUnderWaterBodies = areaUnderWaterBodies;
        this.commonLandsArea = commonLandsArea;
        this.wasteLand = wasteLand;
        this.waterTable = waterTable;
        this.geoTag = geoTag;
        this.needs = needs;
        this.distanceHighway = distanceHighway;
        this.villageConnectedPaccaRoad = villageConnectedPaccaRoad;
        this.roadLength = roadLength;
        this.yearOfConstruction = yearOfConstruction;
        this.schemeConstructed = schemeConstructed;
        this.presentStatus = presentStatus;
        this.lengthOfKachha = lengthOfKachha;
        this.lengthOfPakkka = lengthOfPakkka;
        this.modeOfTransport = modeOfTransport;
        this.frequencyOfAvailable = frequencyOfAvailable;
        this.typeOfForest = typeOfForest;
        this.communityForest = communityForest;
        this.governmentForest = governmentForest;
        this.mainForestTrees = mainForestTrees;
        this.energySpecies1 = energySpecies1;
        this.energyArea1 = energyArea1;
        this.energySpecies2 = energySpecies2;
        this.energyArea2 = energyArea2;
        this.energySpecies3 = energySpecies3;
        this.energyArea3 = energyArea3;
        this.electricityArrayList = electricityArrayList;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGramPanchayat() {
        return gramPanchayat;
    }

    public void setGramPanchayat(String gramPanchayat) {
        this.gramPanchayat = gramPanchayat;
    }

    public String getWards() {
        return wards;
    }

    public void setWards(String wards) {
        this.wards = wards;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getConstituency() {
        return constituency;
    }

    public void setConstituency(String constituency) {
        this.constituency = constituency;
    }

    public String getDistanceDistrictHQ() {
        return distanceDistrictHQ;
    }

    public void setDistanceDistrictHQ(String distanceDistrictHQ) {
        this.distanceDistrictHQ = distanceDistrictHQ;
    }

    public String getVillageArea() {
        return villageArea;
    }

    public void setVillageArea(String villageArea) {
        this.villageArea = villageArea;
    }

    public String getArableLandAgricultureArea() {
        return arableLandAgricultureArea;
    }

    public void setArableLandAgricultureArea(String arableLandAgricultureArea) {
        this.arableLandAgricultureArea = arableLandAgricultureArea;
    }

    public String getForestArea() {
        return forestArea;
    }

    public void setForestArea(String forestArea) {
        this.forestArea = forestArea;
    }

    public String getHousingAbadiArea() {
        return housingAbadiArea;
    }

    public void setHousingAbadiArea(String housingAbadiArea) {
        this.housingAbadiArea = housingAbadiArea;
    }

    public String getAreaUnderWaterBodies() {
        return areaUnderWaterBodies;
    }

    public void setAreaUnderWaterBodies(String areaUnderWaterBodies) {
        this.areaUnderWaterBodies = areaUnderWaterBodies;
    }

    public String getCommonLandsArea() {
        return commonLandsArea;
    }

    public void setCommonLandsArea(String commonLandsArea) {
        this.commonLandsArea = commonLandsArea;
    }

    public String getWasteLand() {
        return wasteLand;
    }

    public void setWasteLand(String wasteLand) {
        this.wasteLand = wasteLand;
    }

    public String getWaterTable() {
        return waterTable;
    }

    public void setWaterTable(String waterTable) {
        this.waterTable = waterTable;
    }

    public String getGeoTag() {
        return geoTag;
    }

    public void setGeoTag(String geoTag) {
        this.geoTag = geoTag;
    }

    public ArrayList<Need> getNeeds() {
        return needs;
    }

    public void setNeeds(ArrayList<Need> needs) {
        this.needs = needs;
    }

    public String getDistanceHighway() {
        return distanceHighway;
    }

    public void setDistanceHighway(String distanceHighway) {
        this.distanceHighway = distanceHighway;
    }

    public String getVillageConnectedPaccaRoad() {
        return villageConnectedPaccaRoad;
    }

    public void setVillageConnectedPaccaRoad(String villageConnectedPaccaRoad) {
        this.villageConnectedPaccaRoad = villageConnectedPaccaRoad;
    }

    public String getRoadLength() {
        return roadLength;
    }

    public void setRoadLength(String roadLength) {
        this.roadLength = roadLength;
    }

    public String getYearOfConstruction() {
        return yearOfConstruction;
    }

    public void setYearOfConstruction(String yearOfConstruction) {
        this.yearOfConstruction = yearOfConstruction;
    }

    public String getSchemeConstructed() {
        return schemeConstructed;
    }

    public void setSchemeConstructed(String schemeConstructed) {
        this.schemeConstructed = schemeConstructed;
    }

    public String getPresentStatus() {
        return presentStatus;
    }

    public void setPresentStatus(String presentStatus) {
        this.presentStatus = presentStatus;
    }

    public String getLengthOfKachha() {
        return lengthOfKachha;
    }

    public void setLengthOfKachha(String lengthOfKachha) {
        this.lengthOfKachha = lengthOfKachha;
    }

    public String getLengthOfPakkka() {
        return lengthOfPakkka;
    }

    public void setLengthOfPakkka(String lengthOfPakkka) {
        this.lengthOfPakkka = lengthOfPakkka;
    }

    public String getModeOfTransport() {
        return modeOfTransport;
    }

    public void setModeOfTransport(String modeOfTransport) {
        this.modeOfTransport = modeOfTransport;
    }

    public String getFrequencyOfAvailable() {
        return frequencyOfAvailable;
    }

    public void setFrequencyOfAvailable(String frequencyOfAvailable) {
        this.frequencyOfAvailable = frequencyOfAvailable;
    }

    public String getTypeOfForest() {
        return typeOfForest;
    }

    public void setTypeOfForest(String typeOfForest) {
        this.typeOfForest = typeOfForest;
    }

    public String getCommunityForest() {
        return communityForest;
    }

    public void setCommunityForest(String communityForest) {
        this.communityForest = communityForest;
    }

    public String getGovernmentForest() {
        return governmentForest;
    }

    public void setGovernmentForest(String governmentForest) {
        this.governmentForest = governmentForest;
    }

    public String getMainForestTrees() {
        return mainForestTrees;
    }

    public void setMainForestTrees(String mainForestTrees) {
        this.mainForestTrees = mainForestTrees;
    }

    public String getEnergySpecies1() {
        return energySpecies1;
    }

    public void setEnergySpecies1(String energySpecies1) {
        this.energySpecies1 = energySpecies1;
    }

    public String getEnergyArea1() {
        return energyArea1;
    }

    public void setEnergyArea1(String energyArea1) {
        this.energyArea1 = energyArea1;
    }

    public String getEnergySpecies2() {
        return energySpecies2;
    }

    public void setEnergySpecies2(String energySpecies2) {
        this.energySpecies2 = energySpecies2;
    }

    public String getEnergyArea2() {
        return energyArea2;
    }

    public void setEnergyArea2(String energyArea2) {
        this.energyArea2 = energyArea2;
    }

    public String getEnergySpecies3() {
        return energySpecies3;
    }

    public void setEnergySpecies3(String energySpecies3) {
        this.energySpecies3 = energySpecies3;
    }

    public String getEnergyArea3() {
        return energyArea3;
    }

    public void setEnergyArea3(String energyArea3) {
        this.energyArea3 = energyArea3;
    }

    public ArrayList<Electricity> getElectricityArrayList() {
        return electricityArrayList;
    }

    public void setElectricityArrayList(ArrayList<Electricity> electricityArrayList) {
        this.electricityArrayList = electricityArrayList;
    }
}
