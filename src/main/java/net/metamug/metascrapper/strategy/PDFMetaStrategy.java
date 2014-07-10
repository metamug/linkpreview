//package net.metamug.metascrapper.strategy;
//
///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//import java.awt.image.BufferedImage;
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import javax.imageio.ImageIO;
//import net.metamug.metascrapper.entity.PDFMetaData;
//import net.metamug.metascrapper.entity.WebMetaData;
//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.pdmodel.PDDocumentInformation;
//import org.apache.pdfbox.pdmodel.PDPage;
//import org.jsoup.Connection.Response;
//
///**
// *
// * @author deepak
// */
//public class PDFMetaStrategy {
//
//    public PDFMetaStrategy(String path) throws IOException {
//        document = PDDocument.load(new ByteArrayInputStream(getBytes(path)));
//    }
//
//    public PDFMetaStrategy(Response response) throws IOException {
//        document = PDDocument.load(new ByteArrayInputStream(response.bodyAsBytes()));
//    }
//
//    private String getImageURL() throws IOException {
//
//        BufferedImage image = ((PDPage) document.getDocumentCatalog().getAllPages().get(0)).convertToImage();
//        ByteArrayOutputStream os = new ByteArrayOutputStream();
//
//        ImageIO.write(image, "png", os);
//
//        byte[] buffer = os.toByteArray();
//        InputStream is = new ByteArrayInputStream(buffer);
//        return null;
////        return StorageManager.upload(is, buffer.length, "png");
//    }
//
//    public WebMetaData getMeta() throws IOException {
//
//        PDFMetaData meta = new PDFMetaData();
//        PDDocumentInformation info = document.getDocumentInformation();
//        meta.setTitle(info.getTitle());
//        meta.setDescription("");
//
//        meta.setAuthor(info.getAuthor());
//        meta.setPages(document.getNumberOfPages());
//        meta.setPicture(getImageURL());
//        meta.setType("pdf");
//        meta.setDateCreated(info.getCreationDate().getTime());
//        meta.setDomain("http://metamug.com/assets/img/symbols/pdf_symbol.png");
//        return meta;
//    }
//
//    PDDocument document;
//}
