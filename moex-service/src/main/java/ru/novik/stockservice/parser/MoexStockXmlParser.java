package ru.novik.stockservice.parser;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import ru.novik.stockservice.dto.SecurityDto;
import ru.novik.stockservice.model.Stock;
import ru.novik.stockservice.exception.StockParsingException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class MoexStockXmlParser implements Parser {

    @Override
    public Stock parse(String stockAsString) {
        Stock stock = new Stock();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        dbf.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
        try {
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            try (StringReader reader = new StringReader(stockAsString)) {
                Document document = db.parse(new InputSource(reader));
                document.getDocumentElement().normalize();
                NodeList list = document.getElementsByTagName("data");
                for (int i = 0; i < list.getLength(); i++) {
                    Node node = list.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;
                        String dataId = element.getAttribute("id");
                        if (dataId.equals("securities")) {
                            NodeList rowList = element.getElementsByTagName("row");
                            for (int j = 0; j < rowList.getLength(); j++) {
                                Node rowNode = rowList.item(j);
                                Element rowElement = (Element) rowNode;
                                String secId = rowElement.getAttribute("SECID");
                                String boardId = rowElement.getAttribute("BOARDID");
                                String shortName = rowElement.getAttribute("SHORTNAME");
                                String isin = rowElement.getAttribute("ISIN");

                                setSecurities(stock, secId, boardId, shortName, isin);
                            }
                        }
                        if (dataId.equals("marketdata")) {
                            NodeList rowList = element.getElementsByTagName("row");
                            for (int j = 0; j < rowList.getLength(); j++) {
                                Node rowNode = rowList.item(j);
                                Element rowElement = (Element) rowNode;
                                Double openPrice = Double.parseDouble(rowElement.getAttribute("OPEN"));
                                Double lowPrice = Double.parseDouble(rowElement.getAttribute("LOW"));
                                Double highPrice = Double.parseDouble(rowElement.getAttribute("HIGH"));
                                Double lastPrice = Double.parseDouble(rowElement.getAttribute("LAST"));
                                Integer qty = Integer.parseInt(rowElement.getAttribute("QTY"));

                                setMarketData(stock, openPrice, lowPrice, highPrice, lastPrice, qty);
                            }
                        }
                    }
                }
            }

        } catch (Exception ex) {
            throw new StockParsingException(String.format("XML %s parsing exception.", stockAsString), ex);
        }
        return stock;
    }

    @Override
    public List<SecurityDto> stocksNamesParse(String allStocksAsString) {
        List<SecurityDto> securities = new ArrayList<>();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        dbf.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
        try {
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            try (StringReader reader = new StringReader(allStocksAsString)) {
                Document document = db.parse(new InputSource(reader));
                document.getDocumentElement().normalize();
                NodeList list = document.getElementsByTagName("data");
                for (int i = 0; i < list.getLength(); i++) {
                    Node node = list.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;
                        String dataId = element.getAttribute("id");
                        if (dataId.equals("securities")) {
                            NodeList rowList = element.getElementsByTagName("row");
                            for (int j = 0; j < rowList.getLength(); j++) {
                                Node rowNode = rowList.item(j);
                                Element rowElement = (Element) rowNode;
                                String secId = rowElement.getAttribute("SECID");
                                String shortName = rowElement.getAttribute("SHORTNAME");
                                SecurityDto securityDto = SecurityDto.builder()
                                        .secId(secId)
                                        .shortName(shortName)
                                        .build();

                                securities.add(securityDto);
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            throw new StockParsingException(String.format("XML %s parsing exception.", allStocksAsString), ex);
        }
        return securities;
    }


    private Stock setSecurities(Stock stock,
                                String secId,
                                String boardId,
                                String shortName,
                                String isin) {
        stock.setSecId(secId);
        stock.setBoardId(boardId);
        stock.setShortName(shortName);
        stock.setIsin(isin);
        return stock;
    }

    private Stock setMarketData(Stock stock,
                                Double openPrice,
                                Double lowPrice,
                                Double highPrice,
                                Double lastPrice,
                                Integer qty) {
        stock.setOpenPrice(openPrice);
        stock.setLowPrice(lowPrice);
        stock.setHighPrice(highPrice);
        stock.setLastPrice(lastPrice);
        stock.setQty(qty);
        return stock;
    }
}

