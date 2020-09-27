package smart.cst.pwc.household;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by arthi on 12/3/2018.
 */

public class Mainbean implements Serializable {
    public String studentid;
    public String id;
    public String geoTag;
    public String village;
    public String wardNo;
    public String district;
    public String gramPanchayat;
    public String block;
    public String state;
    public String name;
    public String gender;
    public String age;
    public String household;
    public String contactNo;
    public String idCardType;
    public String idCardNo;
    public String householdId;
    public String nameOfhead;
    public String houseGender;
    public String category;
    public String povertyStatus;
    public String ownHouse;
    public String typeOfHouse;
    public String toilet;
    public String drainage;
    public String wasteSystem;
    public String compostPit;
    public String biogasPlant;
    public String annualInncom;
    public String pmJan;
    public String pmUjjwala;
    public String pmAwas;
    public String sukanya;
    public String mudra;
    public String pmJivan;
    public String pmSuraksha;
    public String atal;
    public String fasal;
    public String kaushal;
    public String krishi;
    public String janAushadi;
    public String swachh;
    public String soilHealth;
    public String ladli;
    public String janani;
    public String kisan;
    public String pipedWater;
    public String communityWater;
    public String handPump;
    public String openWell;
    public String modeWaterStorage;
    public String anyOtherSource;
    public String electHousehold;
    public String electDay;
    public String electLight;
    public String electCooking;
    public String electCullah;
    public String cultivableArea;
    public String irrigatedArea;
    public String unIrrigated;
    public String barran;
    public String unCultArea;
    public String chemicalFertilisers;
    public String chemicalInsecticides;
    public String weedicide;
    public String manures;
    public String irrigation;
    public String irrigationSystem;
    public String cows;
    public String Buffalo;
    public String goatsSheep;
    public String calves;
    public String bullocks;
    public String poultryDucks;
    public String liveStockOthers;
    public String shelterforLivestock;
    public String averageMilk;
    public String animalWaste;
    public String sign;
    public String survey;
    ArrayList<FamilyInfo> familyInfos;
    ArrayList<EnergyPower> energyPowers;
    ArrayList<AgriProduce> agriProduces;
    ArrayList<MajorPrb> majorPrbs;

    public Mainbean(String geoTag, String village, String wardNo, String district, String gramPanchayat, String block, String state, String name, String gender, String age, String household, String contactNo, String idCardType, String idCardNo, String householdId, String nameOfhead, String houseGender, String category, String povertyStatus, String ownHouse, String typeOfHouse, String toilet, String drainage, String wasteSystem, String compostPit, String biogasPlant, String annualInncom, String pmJan, String pmUjjwala, String pmAwas, String sukanya, String mudra, String pmJivan, String pmSuraksha, String atal, String fasal, String kaushal, String krishi, String janAushadi, String swachh, String soilHealth, String ladli, String janani, String kisan, String pipedWater, String communityWater, String handPump, String openWell, String modeWaterStorage, String anyOtherSource, String electHousehold, String electDay, String electLight, String electCooking, String electCullah, String cultivableArea, String irrigatedArea, String unIrrigated, String barran, String unCultArea, String chemicalFertilisers, String chemicalInsecticides, String weedicide, String manures, String irrigation, String irrigationSystem, String cows, String buffalo, String goatsSheep, String calves, String bullocks, String poultryDucks, String liveStockOthers, String shelterforLivestock, String averageMilk, String animalWaste, String sign, String survey, ArrayList<FamilyInfo> familyInfos, ArrayList<EnergyPower> energyPowers, ArrayList<AgriProduce> agriProduces, ArrayList<MajorPrb> majorPrbs) {
        this.geoTag = geoTag;
        this.village = village;
        this.wardNo = wardNo;
        this.district = district;
        this.gramPanchayat = gramPanchayat;
        this.block = block;
        this.state = state;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.household = household;
        this.contactNo = contactNo;
        this.idCardType = idCardType;
        this.idCardNo = idCardNo;
        this.householdId = householdId;
        this.nameOfhead = nameOfhead;
        this.houseGender = houseGender;
        this.category = category;
        this.povertyStatus = povertyStatus;
        this.ownHouse = ownHouse;
        this.typeOfHouse = typeOfHouse;
        this.toilet = toilet;
        this.drainage = drainage;
        this.wasteSystem = wasteSystem;
        this.compostPit = compostPit;
        this.biogasPlant = biogasPlant;
        this.annualInncom = annualInncom;
        this.pmJan = pmJan;
        this.pmUjjwala = pmUjjwala;
        this.pmAwas = pmAwas;
        this.sukanya = sukanya;
        this.mudra = mudra;
        this.pmJivan = pmJivan;
        this.pmSuraksha = pmSuraksha;
        this.atal = atal;
        this.fasal = fasal;
        this.kaushal = kaushal;
        this.krishi = krishi;
        this.janAushadi = janAushadi;
        this.swachh = swachh;
        this.soilHealth = soilHealth;
        this.ladli = ladli;
        this.janani = janani;
        this.kisan = kisan;
        this.pipedWater = pipedWater;
        this.communityWater = communityWater;
        this.handPump = handPump;
        this.openWell = openWell;
        this.modeWaterStorage = modeWaterStorage;
        this.anyOtherSource = anyOtherSource;
        this.electHousehold = electHousehold;
        this.electDay = electDay;
        this.electLight = electLight;
        this.electCooking = electCooking;
        this.electCullah = electCullah;
        this.cultivableArea = cultivableArea;
        this.irrigatedArea = irrigatedArea;
        this.unIrrigated = unIrrigated;
        this.barran = barran;
        this.unCultArea = unCultArea;
        this.chemicalFertilisers = chemicalFertilisers;
        this.chemicalInsecticides = chemicalInsecticides;
        this.weedicide = weedicide;
        this.manures = manures;
        this.irrigation = irrigation;
        this.irrigationSystem = irrigationSystem;
        this.cows = cows;
        Buffalo = buffalo;
        this.goatsSheep = goatsSheep;
        this.calves = calves;
        this.bullocks = bullocks;
        this.poultryDucks = poultryDucks;
        this.liveStockOthers = liveStockOthers;
        this.shelterforLivestock = shelterforLivestock;
        this.averageMilk = averageMilk;
        this.animalWaste = animalWaste;
        this.sign = sign;
        this.survey = survey;
        this.familyInfos = familyInfos;
        this.energyPowers = energyPowers;
        this.agriProduces = agriProduces;
        this.majorPrbs = majorPrbs;
    }

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGeoTag() {
        return geoTag;
    }

    public void setGeoTag(String geoTag) {
        this.geoTag = geoTag;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getWardNo() {
        return wardNo;
    }

    public void setWardNo(String wardNo) {
        this.wardNo = wardNo;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getGramPanchayat() {
        return gramPanchayat;
    }

    public void setGramPanchayat(String gramPanchayat) {
        this.gramPanchayat = gramPanchayat;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHousehold() {
        return household;
    }

    public void setHousehold(String household) {
        this.household = household;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getIdCardType() {
        return idCardType;
    }

    public void setIdCardType(String idCardType) {
        this.idCardType = idCardType;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getHouseholdId() {
        return householdId;
    }

    public void setHouseholdId(String householdId) {
        this.householdId = householdId;
    }

    public String getNameOfhead() {
        return nameOfhead;
    }

    public void setNameOfhead(String nameOfhead) {
        this.nameOfhead = nameOfhead;
    }

    public String getHouseGender() {
        return houseGender;
    }

    public void setHouseGender(String houseGender) {
        this.houseGender = houseGender;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPovertyStatus() {
        return povertyStatus;
    }

    public void setPovertyStatus(String povertyStatus) {
        this.povertyStatus = povertyStatus;
    }

    public String getOwnHouse() {
        return ownHouse;
    }

    public void setOwnHouse(String ownHouse) {
        this.ownHouse = ownHouse;
    }

    public String getTypeOfHouse() {
        return typeOfHouse;
    }

    public void setTypeOfHouse(String typeOfHouse) {
        this.typeOfHouse = typeOfHouse;
    }

    public String getToilet() {
        return toilet;
    }

    public void setToilet(String toilet) {
        this.toilet = toilet;
    }

    public String getDrainage() {
        return drainage;
    }

    public void setDrainage(String drainage) {
        this.drainage = drainage;
    }

    public String getWasteSystem() {
        return wasteSystem;
    }

    public void setWasteSystem(String wasteSystem) {
        this.wasteSystem = wasteSystem;
    }

    public String getCompostPit() {
        return compostPit;
    }

    public void setCompostPit(String compostPit) {
        this.compostPit = compostPit;
    }

    public String getBiogasPlant() {
        return biogasPlant;
    }

    public void setBiogasPlant(String biogasPlant) {
        this.biogasPlant = biogasPlant;
    }

    public String getAnnualInncom() {
        return annualInncom;
    }

    public void setAnnualInncom(String annualInncom) {
        this.annualInncom = annualInncom;
    }

    public String getPmJan() {
        return pmJan;
    }

    public void setPmJan(String pmJan) {
        this.pmJan = pmJan;
    }

    public String getPmUjjwala() {
        return pmUjjwala;
    }

    public void setPmUjjwala(String pmUjjwala) {
        this.pmUjjwala = pmUjjwala;
    }

    public String getPmAwas() {
        return pmAwas;
    }

    public void setPmAwas(String pmAwas) {
        this.pmAwas = pmAwas;
    }

    public String getSukanya() {
        return sukanya;
    }

    public void setSukanya(String sukanya) {
        this.sukanya = sukanya;
    }

    public String getMudra() {
        return mudra;
    }

    public void setMudra(String mudra) {
        this.mudra = mudra;
    }

    public String getPmJivan() {
        return pmJivan;
    }

    public void setPmJivan(String pmJivan) {
        this.pmJivan = pmJivan;
    }

    public String getPmSuraksha() {
        return pmSuraksha;
    }

    public void setPmSuraksha(String pmSuraksha) {
        this.pmSuraksha = pmSuraksha;
    }

    public String getAtal() {
        return atal;
    }

    public void setAtal(String atal) {
        this.atal = atal;
    }

    public String getFasal() {
        return fasal;
    }

    public void setFasal(String fasal) {
        this.fasal = fasal;
    }

    public String getKaushal() {
        return kaushal;
    }

    public void setKaushal(String kaushal) {
        this.kaushal = kaushal;
    }

    public String getKrishi() {
        return krishi;
    }

    public void setKrishi(String krishi) {
        this.krishi = krishi;
    }

    public String getJanAushadi() {
        return janAushadi;
    }

    public void setJanAushadi(String janAushadi) {
        this.janAushadi = janAushadi;
    }

    public String getSwachh() {
        return swachh;
    }

    public void setSwachh(String swachh) {
        this.swachh = swachh;
    }

    public String getSoilHealth() {
        return soilHealth;
    }

    public void setSoilHealth(String soilHealth) {
        this.soilHealth = soilHealth;
    }

    public String getLadli() {
        return ladli;
    }

    public void setLadli(String ladli) {
        this.ladli = ladli;
    }

    public String getJanani() {
        return janani;
    }

    public void setJanani(String janani) {
        this.janani = janani;
    }

    public String getKisan() {
        return kisan;
    }

    public void setKisan(String kisan) {
        this.kisan = kisan;
    }

    public String getPipedWater() {
        return pipedWater;
    }

    public void setPipedWater(String pipedWater) {
        this.pipedWater = pipedWater;
    }

    public String getCommunityWater() {
        return communityWater;
    }

    public void setCommunityWater(String communityWater) {
        this.communityWater = communityWater;
    }

    public String getHandPump() {
        return handPump;
    }

    public void setHandPump(String handPump) {
        this.handPump = handPump;
    }

    public String getOpenWell() {
        return openWell;
    }

    public void setOpenWell(String openWell) {
        this.openWell = openWell;
    }

    public String getModeWaterStorage() {
        return modeWaterStorage;
    }

    public void setModeWaterStorage(String modeWaterStorage) {
        this.modeWaterStorage = modeWaterStorage;
    }

    public String getAnyOtherSource() {
        return anyOtherSource;
    }

    public void setAnyOtherSource(String anyOtherSource) {
        this.anyOtherSource = anyOtherSource;
    }

    public String getElectHousehold() {
        return electHousehold;
    }

    public void setElectHousehold(String electHousehold) {
        this.electHousehold = electHousehold;
    }

    public String getElectDay() {
        return electDay;
    }

    public void setElectDay(String electDay) {
        this.electDay = electDay;
    }

    public String getElectLight() {
        return electLight;
    }

    public void setElectLight(String electLight) {
        this.electLight = electLight;
    }

    public String getElectCooking() {
        return electCooking;
    }

    public void setElectCooking(String electCooking) {
        this.electCooking = electCooking;
    }

    public String getElectCullah() {
        return electCullah;
    }

    public void setElectCullah(String electCullah) {
        this.electCullah = electCullah;
    }

    public String getCultivableArea() {
        return cultivableArea;
    }

    public void setCultivableArea(String cultivableArea) {
        this.cultivableArea = cultivableArea;
    }

    public String getIrrigatedArea() {
        return irrigatedArea;
    }

    public void setIrrigatedArea(String irrigatedArea) {
        this.irrigatedArea = irrigatedArea;
    }

    public String getUnIrrigated() {
        return unIrrigated;
    }

    public void setUnIrrigated(String unIrrigated) {
        this.unIrrigated = unIrrigated;
    }

    public String getBarran() {
        return barran;
    }

    public void setBarran(String barran) {
        this.barran = barran;
    }

    public String getUnCultArea() {
        return unCultArea;
    }

    public void setUnCultArea(String unCultArea) {
        this.unCultArea = unCultArea;
    }

    public String getChemicalFertilisers() {
        return chemicalFertilisers;
    }

    public void setChemicalFertilisers(String chemicalFertilisers) {
        this.chemicalFertilisers = chemicalFertilisers;
    }

    public String getChemicalInsecticides() {
        return chemicalInsecticides;
    }

    public void setChemicalInsecticides(String chemicalInsecticides) {
        this.chemicalInsecticides = chemicalInsecticides;
    }

    public String getWeedicide() {
        return weedicide;
    }

    public void setWeedicide(String weedicide) {
        this.weedicide = weedicide;
    }

    public String getManures() {
        return manures;
    }

    public void setManures(String manures) {
        this.manures = manures;
    }

    public String getIrrigation() {
        return irrigation;
    }

    public void setIrrigation(String irrigation) {
        this.irrigation = irrigation;
    }

    public String getIrrigationSystem() {
        return irrigationSystem;
    }

    public void setIrrigationSystem(String irrigationSystem) {
        this.irrigationSystem = irrigationSystem;
    }

    public String getCows() {
        return cows;
    }

    public void setCows(String cows) {
        this.cows = cows;
    }

    public String getBuffalo() {
        return Buffalo;
    }

    public void setBuffalo(String buffalo) {
        Buffalo = buffalo;
    }

    public String getGoatsSheep() {
        return goatsSheep;
    }

    public void setGoatsSheep(String goatsSheep) {
        this.goatsSheep = goatsSheep;
    }

    public String getCalves() {
        return calves;
    }

    public void setCalves(String calves) {
        this.calves = calves;
    }

    public String getBullocks() {
        return bullocks;
    }

    public void setBullocks(String bullocks) {
        this.bullocks = bullocks;
    }

    public String getPoultryDucks() {
        return poultryDucks;
    }

    public void setPoultryDucks(String poultryDucks) {
        this.poultryDucks = poultryDucks;
    }

    public String getLiveStockOthers() {
        return liveStockOthers;
    }

    public void setLiveStockOthers(String liveStockOthers) {
        this.liveStockOthers = liveStockOthers;
    }

    public String getShelterforLivestock() {
        return shelterforLivestock;
    }

    public void setShelterforLivestock(String shelterforLivestock) {
        this.shelterforLivestock = shelterforLivestock;
    }

    public String getAverageMilk() {
        return averageMilk;
    }

    public void setAverageMilk(String averageMilk) {
        this.averageMilk = averageMilk;
    }

    public String getAnimalWaste() {
        return animalWaste;
    }

    public void setAnimalWaste(String animalWaste) {
        this.animalWaste = animalWaste;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSurvey() {
        return survey;
    }

    public void setSurvey(String survey) {
        this.survey = survey;
    }

    public ArrayList<FamilyInfo> getFamilyInfos() {
        return familyInfos;
    }

    public void setFamilyInfos(ArrayList<FamilyInfo> familyInfos) {
        this.familyInfos = familyInfos;
    }

    public ArrayList<EnergyPower> getEnergyPowers() {
        return energyPowers;
    }

    public void setEnergyPowers(ArrayList<EnergyPower> energyPowers) {
        this.energyPowers = energyPowers;
    }

    public ArrayList<AgriProduce> getAgriProduces() {
        return agriProduces;
    }

    public void setAgriProduces(ArrayList<AgriProduce> agriProduces) {
        this.agriProduces = agriProduces;
    }

    public ArrayList<MajorPrb> getMajorPrbs() {
        return majorPrbs;
    }

    public void setMajorPrbs(ArrayList<MajorPrb> majorPrbs) {
        this.majorPrbs = majorPrbs;
    }
}
