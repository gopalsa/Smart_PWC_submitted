package smart.cst.pwc.fgd;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by jmpriyanka on 22-12-2018.
 */

public class MainFGD implements Serializable {

    String id;
    String name;
    String tdate;
    String location;
    String grpinagri;
    String landsize;
    String landowner;
    String lastyrcultivate;
    String irrigationfacility;
    String irrigationsrc;
    String crppattern;
    String kharif;
    String rabi;
    String summer;
    String farminputs;
    String buyseeds;
    String avgdistance;
    String srcirrigation;
    String irregationrent;
    String extenservice;
    String getinfo;
    String guidance;
    String lastyrtraining;
    String soiltest;
    String soiltestnum;
    String soiltestfreq;
    String weatherinfo;
    String weatherinfosrc;
    String loan;
    String avgborwpseas;
    String avgborwpyr;
    String pepborwnum;
    String srcborw;
    String loanrepaid;
    String cropcprice;
    String delaysale;
    String fruits;
    String cereals;
    String vegetables;
    String sellvegetable;
    String sellplace;
    String diflocation;
    String vegecost;
    String payment;
    String marketpract;
    String colpeoplepract;
    String yesreason;
    String colmarket;
    String familysize;
    String vlagepopulation;
    String numhouseinfarm;
    String totcultivatearea;
    String agrionlyincome;
    String otherincome;
    String livestock;
    String edulevel;
    String agriproduce;
    String producergrp;
    String member;
    ArrayList<Peopleattend> peopleattends;
    ArrayList<Cropwisecultivate> cropwisecultivates;
    ArrayList<Cultivatecost> cultivatecosts;
    ArrayList<Avgwage> avgwages;
    ArrayList<Priceincrease> priceincreases;
    ArrayList<Storage> storages;
    ArrayList<Avgrate> avgrates;

    public MainFGD(String name, String tdate, String location, String grpinagri, String landsize, String landowner, String lastyrcultivate, String irrigationfacility, String irrigationsrc, String crppattern, String kharif, String rabi, String summer, String farminputs, String buyseeds, String avgdistance, String srcirrigation, String irregationrent, String extenservice, String getinfo, String guidance, String lastyrtraining, String soiltest, String soiltestnum, String soiltestfreq, String weatherinfo, String weatherinfosrc, String loan, String avgborwpseas, String avgborwpyr, String pepborwnum, String srcborw, String loanrepaid, String cropcprice, String delaysale, String fruits, String cereals, String vegetables, String sellvegetable, String sellplace, String diflocation, String vegecost, String payment, String marketpract, String colpeoplepract, String yesreason, String colmarket, String familysize, String vlagepopulation, String numhouseinfarm, String totcultivatearea, String agrionlyincome, String otherincome, String livestock, String edulevel, String agriproduce, String producergrp, String member, ArrayList<Peopleattend> peopleattends, ArrayList<Cropwisecultivate> cropwisecultivates, ArrayList<Cultivatecost> cultivatecosts, ArrayList<Avgwage> avgwages, ArrayList<Priceincrease> priceincreases, ArrayList<Storage> storages, ArrayList<Avgrate> avgrates) {
        this.name = name;
        this.tdate = tdate;
        this.location = location;
        this.grpinagri = grpinagri;
        this.landsize = landsize;
        this.landowner = landowner;
        this.lastyrcultivate = lastyrcultivate;
        this.irrigationfacility = irrigationfacility;
        this.irrigationsrc = irrigationsrc;
        this.crppattern = crppattern;
        this.kharif = kharif;
        this.rabi = rabi;
        this.summer = summer;
        this.farminputs = farminputs;
        this.buyseeds = buyseeds;
        this.avgdistance = avgdistance;
        this.srcirrigation = srcirrigation;
        this.irregationrent = irregationrent;
        this.extenservice = extenservice;
        this.getinfo = getinfo;
        this.guidance = guidance;
        this.lastyrtraining = lastyrtraining;
        this.soiltest = soiltest;
        this.soiltestnum = soiltestnum;
        this.soiltestfreq = soiltestfreq;
        this.weatherinfo = weatherinfo;
        this.weatherinfosrc = weatherinfosrc;
        this.loan = loan;
        this.avgborwpseas = avgborwpseas;
        this.avgborwpyr = avgborwpyr;
        this.pepborwnum = pepborwnum;
        this.srcborw = srcborw;
        this.loanrepaid = loanrepaid;
        this.cropcprice = cropcprice;
        this.delaysale = delaysale;
        this.fruits = fruits;
        this.cereals = cereals;
        this.vegetables = vegetables;
        this.sellvegetable = sellvegetable;
        this.sellplace = sellplace;
        this.diflocation = diflocation;
        this.vegecost = vegecost;
        this.payment = payment;
        this.marketpract = marketpract;
        this.colpeoplepract = colpeoplepract;
        this.yesreason = yesreason;
        this.colmarket = colmarket;
        this.familysize = familysize;
        this.vlagepopulation = vlagepopulation;
        this.numhouseinfarm = numhouseinfarm;
        this.totcultivatearea = totcultivatearea;
        this.agrionlyincome = agrionlyincome;
        this.otherincome = otherincome;
        this.livestock = livestock;
        this.edulevel = edulevel;
        this.agriproduce = agriproduce;
        this.producergrp = producergrp;
        this.member = member;
        this.peopleattends = peopleattends;
        this.cropwisecultivates = cropwisecultivates;
        this.cultivatecosts = cultivatecosts;
        this.avgwages = avgwages;
        this.priceincreases = priceincreases;
        this.storages = storages;
        this.avgrates = avgrates;
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

    public String getTdate() {
        return tdate;
    }

    public void setTdate(String tdate) {
        this.tdate = tdate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getGrpinagri() {
        return grpinagri;
    }

    public void setGrpinagri(String grpinagri) {
        this.grpinagri = grpinagri;
    }

    public String getLandsize() {
        return landsize;
    }

    public void setLandsize(String landsize) {
        this.landsize = landsize;
    }

    public String getLandowner() {
        return landowner;
    }

    public void setLandowner(String landowner) {
        this.landowner = landowner;
    }

    public String getLastyrcultivate() {
        return lastyrcultivate;
    }

    public void setLastyrcultivate(String lastyrcultivate) {
        this.lastyrcultivate = lastyrcultivate;
    }

    public String getIrrigationfacility() {
        return irrigationfacility;
    }

    public void setIrrigationfacility(String irrigationfacility) {
        this.irrigationfacility = irrigationfacility;
    }

    public String getIrrigationsrc() {
        return irrigationsrc;
    }

    public void setIrrigationsrc(String irrigationsrc) {
        this.irrigationsrc = irrigationsrc;
    }

    public String getCrppattern() {
        return crppattern;
    }

    public void setCrppattern(String crppattern) {
        this.crppattern = crppattern;
    }

    public String getKharif() {
        return kharif;
    }

    public void setKharif(String kharif) {
        this.kharif = kharif;
    }

    public String getRabi() {
        return rabi;
    }

    public void setRabi(String rabi) {
        this.rabi = rabi;
    }

    public String getSummer() {
        return summer;
    }

    public void setSummer(String summer) {
        this.summer = summer;
    }

    public String getFarminputs() {
        return farminputs;
    }

    public void setFarminputs(String farminputs) {
        this.farminputs = farminputs;
    }

    public String getBuyseeds() {
        return buyseeds;
    }

    public void setBuyseeds(String buyseeds) {
        this.buyseeds = buyseeds;
    }

    public String getAvgdistance() {
        return avgdistance;
    }

    public void setAvgdistance(String avgdistance) {
        this.avgdistance = avgdistance;
    }

    public String getSrcirrigation() {
        return srcirrigation;
    }

    public void setSrcirrigation(String srcirrigation) {
        this.srcirrigation = srcirrigation;
    }

    public String getIrregationrent() {
        return irregationrent;
    }

    public void setIrregationrent(String irregationrent) {
        this.irregationrent = irregationrent;
    }

    public String getExtenservice() {
        return extenservice;
    }

    public void setExtenservice(String extenservice) {
        this.extenservice = extenservice;
    }

    public String getGetinfo() {
        return getinfo;
    }

    public void setGetinfo(String getinfo) {
        this.getinfo = getinfo;
    }

    public String getGuidance() {
        return guidance;
    }

    public void setGuidance(String guidance) {
        this.guidance = guidance;
    }

    public String getLastyrtraining() {
        return lastyrtraining;
    }

    public void setLastyrtraining(String lastyrtraining) {
        this.lastyrtraining = lastyrtraining;
    }

    public String getSoiltest() {
        return soiltest;
    }

    public void setSoiltest(String soiltest) {
        this.soiltest = soiltest;
    }

    public String getSoiltestnum() {
        return soiltestnum;
    }

    public void setSoiltestnum(String soiltestnum) {
        this.soiltestnum = soiltestnum;
    }

    public String getSoiltestfreq() {
        return soiltestfreq;
    }

    public void setSoiltestfreq(String soiltestfreq) {
        this.soiltestfreq = soiltestfreq;
    }

    public String getWeatherinfo() {
        return weatherinfo;
    }

    public void setWeatherinfo(String weatherinfo) {
        this.weatherinfo = weatherinfo;
    }

    public String getWeatherinfosrc() {
        return weatherinfosrc;
    }

    public void setWeatherinfosrc(String weatherinfosrc) {
        this.weatherinfosrc = weatherinfosrc;
    }

    public String getLoan() {
        return loan;
    }

    public void setLoan(String loan) {
        this.loan = loan;
    }

    public String getAvgborwpseas() {
        return avgborwpseas;
    }

    public void setAvgborwpseas(String avgborwpseas) {
        this.avgborwpseas = avgborwpseas;
    }

    public String getAvgborwpyr() {
        return avgborwpyr;
    }

    public void setAvgborwpyr(String avgborwpyr) {
        this.avgborwpyr = avgborwpyr;
    }

    public String getPepborwnum() {
        return pepborwnum;
    }

    public void setPepborwnum(String pepborwnum) {
        this.pepborwnum = pepborwnum;
    }

    public String getSrcborw() {
        return srcborw;
    }

    public void setSrcborw(String srcborw) {
        this.srcborw = srcborw;
    }

    public String getLoanrepaid() {
        return loanrepaid;
    }

    public void setLoanrepaid(String loanrepaid) {
        this.loanrepaid = loanrepaid;
    }

    public String getCropcprice() {
        return cropcprice;
    }

    public void setCropcprice(String cropcprice) {
        this.cropcprice = cropcprice;
    }

    public String getDelaysale() {
        return delaysale;
    }

    public void setDelaysale(String delaysale) {
        this.delaysale = delaysale;
    }

    public String getFruits() {
        return fruits;
    }

    public void setFruits(String fruits) {
        this.fruits = fruits;
    }

    public String getCereals() {
        return cereals;
    }

    public void setCereals(String cereals) {
        this.cereals = cereals;
    }

    public String getVegetables() {
        return vegetables;
    }

    public void setVegetables(String vegetables) {
        this.vegetables = vegetables;
    }

    public String getSellvegetable() {
        return sellvegetable;
    }

    public void setSellvegetable(String sellvegetable) {
        this.sellvegetable = sellvegetable;
    }

    public String getSellplace() {
        return sellplace;
    }

    public void setSellplace(String sellplace) {
        this.sellplace = sellplace;
    }

    public String getDiflocation() {
        return diflocation;
    }

    public void setDiflocation(String diflocation) {
        this.diflocation = diflocation;
    }

    public String getVegecost() {
        return vegecost;
    }

    public void setVegecost(String vegecost) {
        this.vegecost = vegecost;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getMarketpract() {
        return marketpract;
    }

    public void setMarketpract(String marketpract) {
        this.marketpract = marketpract;
    }

    public String getColpeoplepract() {
        return colpeoplepract;
    }

    public void setColpeoplepract(String colpeoplepract) {
        this.colpeoplepract = colpeoplepract;
    }

    public String getYesreason() {
        return yesreason;
    }

    public void setYesreason(String yesreason) {
        this.yesreason = yesreason;
    }

    public String getColmarket() {
        return colmarket;
    }

    public void setColmarket(String colmarket) {
        this.colmarket = colmarket;
    }

    public String getFamilysize() {
        return familysize;
    }

    public void setFamilysize(String familysize) {
        this.familysize = familysize;
    }

    public String getVlagepopulation() {
        return vlagepopulation;
    }

    public void setVlagepopulation(String vlagepopulation) {
        this.vlagepopulation = vlagepopulation;
    }

    public String getNumhouseinfarm() {
        return numhouseinfarm;
    }

    public void setNumhouseinfarm(String numhouseinfarm) {
        this.numhouseinfarm = numhouseinfarm;
    }

    public String getTotcultivatearea() {
        return totcultivatearea;
    }

    public void setTotcultivatearea(String totcultivatearea) {
        this.totcultivatearea = totcultivatearea;
    }

    public String getAgrionlyincome() {
        return agrionlyincome;
    }

    public void setAgrionlyincome(String agrionlyincome) {
        this.agrionlyincome = agrionlyincome;
    }

    public String getOtherincome() {
        return otherincome;
    }

    public void setOtherincome(String otherincome) {
        this.otherincome = otherincome;
    }

    public String getLivestock() {
        return livestock;
    }

    public void setLivestock(String livestock) {
        this.livestock = livestock;
    }

    public String getEdulevel() {
        return edulevel;
    }

    public void setEdulevel(String edulevel) {
        this.edulevel = edulevel;
    }

    public String getAgriproduce() {
        return agriproduce;
    }

    public void setAgriproduce(String agriproduce) {
        this.agriproduce = agriproduce;
    }

    public String getProducergrp() {
        return producergrp;
    }

    public void setProducergrp(String producergrp) {
        this.producergrp = producergrp;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public ArrayList<Peopleattend> getPeopleattends() {
        return peopleattends;
    }

    public void setPeopleattends(ArrayList<Peopleattend> peopleattends) {
        this.peopleattends = peopleattends;
    }

    public ArrayList<Cropwisecultivate> getCropwisecultivates() {
        return cropwisecultivates;
    }

    public void setCropwisecultivates(ArrayList<Cropwisecultivate> cropwisecultivates) {
        this.cropwisecultivates = cropwisecultivates;
    }

    public ArrayList<Cultivatecost> getCultivatecosts() {
        return cultivatecosts;
    }

    public void setCultivatecosts(ArrayList<Cultivatecost> cultivatecosts) {
        this.cultivatecosts = cultivatecosts;
    }

    public ArrayList<Avgwage> getAvgwages() {
        return avgwages;
    }

    public void setAvgwages(ArrayList<Avgwage> avgwages) {
        this.avgwages = avgwages;
    }

    public ArrayList<Priceincrease> getPriceincreases() {
        return priceincreases;
    }

    public void setPriceincreases(ArrayList<Priceincrease> priceincreases) {
        this.priceincreases = priceincreases;
    }

    public ArrayList<Storage> getStorages() {
        return storages;
    }

    public void setStorages(ArrayList<Storage> storages) {
        this.storages = storages;
    }

    public ArrayList<Avgrate> getAvgrates() {
        return avgrates;
    }

    public void setAvgrates(ArrayList<Avgrate> avgrates) {
        this.avgrates = avgrates;
    }
}
