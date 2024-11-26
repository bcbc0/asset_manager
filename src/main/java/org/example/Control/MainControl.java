package org.example.Control;

import org.example.ApplicationLogic.AssetApiService;
import org.example.ApplicationLogic.AssetFactory;
import org.example.ApplicationLogic.NewsApiService;
import org.example.ApplicationLogic.PortfolioService;
import org.example.Boundary.AssetInfoBoundary;
import org.example.Boundary.MainBoundary;
import org.example.Boundary.PortfolioBoundary;
import org.example.Boundary.PortfolioListBoundary;
import org.example.Entity.*;

import java.util.List;

public class MainControl {

    private final AssetFactory assetFactory;
    private final NewsApiService newsApiService;
    private final PortfolioService portfolioService;

    public MainControl(AssetFactory assetFactory, NewsApiService newsApiService, PortfolioService portfolioService){
        this.assetFactory = assetFactory;
        this.newsApiService = newsApiService;
        this.portfolioService = portfolioService;
    }

    public void showMainBoundary(){
        new MainBoundary(this);
    }

    public void showAssetInfoBoundary(){
        new AssetInfoBoundary(this);
    }

    public void showPortfolioListBoundary() {
        new PortfolioListBoundary(this);
    }

    public AssetDetail searchAsset(AssetType type, String symbol) {
        AssetApiService apiService = assetFactory.getApiService(type);
        return null;
    }

    public List<News> fetchNewsHeadlines(int page, int size) {
        return newsApiService.getNewsList(page, size);
    }

    public void logout() {
        System.out.println("logout");
    }

    public List<Portfolio> getPortfolioList() {
        return portfolioService.getPortfolioList();
    }

    public boolean checkDuplicatedPortfolioName(String name) {
        return portfolioService.checkDuplicate(name);
    }

    public void addPortfolio(String name) {
        portfolioService.addPortfolio(name);
    }

    public void showPortfolioBoundary(Portfolio portfolio) {
        portfolioService.setCurrentPortfolio(portfolio);
        new PortfolioBoundary(this);
    }

    public void deletePortfolio(Portfolio portfolio) {
        portfolioService.deletePortfolio(portfolio);
    }

    public Portfolio getCurrentPortfolio() {
        return portfolioService.getCurrentPortfolio();
    }

    public void addAsset(Asset newAsset) {
        portfolioService.addAsset(newAsset);
    }

    public void changeAsset(int selectedRow, Asset newAsset) {
        portfolioService.changeAsset(selectedRow, newAsset);
    }

    public void deleteAsset(int selectedRow) {
        portfolioService.deleteAsset(selectedRow);
    }

    public List<Object[]> getPortfolioDataList() {
        return portfolioService.getPortfolioDataList();
    }

    public Asset getDuplicatedAsset(AssetType assetType, String symbol){
        return portfolioService.getDuplicatedAsset( assetType,  symbol);
    }

    public void addAsset(AssetType assetType, String symbol, Double purchasePrice, Double quantity) {
        Asset asset = assetFactory.createAsset(assetType, symbol, purchasePrice, quantity);
        if(asset instanceof TradableAsset){
            double currentPrice = assetFactory.getApiService(assetType).getCurrentPrice(symbol);
            ((TradableAsset) asset).setCurrentPrice(currentPrice);
        }
        portfolioService.addAsset(asset);
    }

    public Asset getAssetByIndex(int index) {
        return portfolioService.getCurrentPortfolio().getAssetList().get(index);
    }

    public void fetchAllCurrentPrice(){
        for(Asset asset : portfolioService.getCurrentPortfolio().getAssetList()){
            if(asset instanceof TradableAsset){
                double currentPrice = assetFactory.getApiService(asset.getAssetType()).getCurrentPrice(asset.getSymbol());
                ((TradableAsset) asset).setCurrentPrice(currentPrice);
            }
        }
    }
}