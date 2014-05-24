package net.metamug.metascrapper.strategy;

///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.metamug.web.metadata;
//
//import com.metamug.util.StorageManager;
//import com.metamug.web.metadata.WebMetaData;
//import java.awt.image.BufferedImage;
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import javax.imageio.ImageIO;
//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.pdmodel.PDDocumentInformation;
//import org.apache.pdfbox.pdmodel.PDPage;
//import org.jsoup.Connection;
//import org.jsoup.Connection.Response;
//import org.jsoup.Jsoup;
//
///**
// *
// * @author deepak
// */
//public class PDFMetaFinder {
//
//    public PDFMetaFinder(String path) throws IOException {
//        
//            Connection.Response response = Jsoup.connect(path)
//                    //.userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
//                    .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
//                    .referrer("http://www.google.com")
//                    .ignoreContentType(true)
//                    //timeout 5000 causing connection refused.
//                    .timeout(12000)
//                    //.method(Method.POST)
//                    .followRedirects(true)
//                    //.header("Content-Type","*/*;charset=UTF-8")
//                    .execute();
//
//            document = PDDocument.load(new ByteArrayInputStream(response.bodyAsBytes()));
//
//    }
//
//    public PDFMetaFinder(Response response) throws IOException {
//            document = PDDocument.load(new ByteArrayInputStream(response.bodyAsBytes()));
//    }
//
//    private String getImageURL() throws IOException {
//       
//            BufferedImage image = ((PDPage) document.getDocumentCatalog().getAllPages().get(0)).convertToImage();
//            ByteArrayOutputStream os = new ByteArrayOutputStream();
//
//            ImageIO.write(image, "png", os);
//
//            byte[] buffer = os.toByteArray();
//            InputStream is = new ByteArrayInputStream(buffer);
//            
//            return StorageManager.upload(is, buffer.length,"png");
//            
////            ObjectMetadata meta = new ObjectMetadata();
////            meta.setContentLength(buffer.length);
////            String randomFileName = RandomStringUtils.randomAlphanumeric(20);
////            s3Client.putObject(new PutObjectRequest(AWS_S3_BUCKET, "images/" + randomFileName, is, meta)
////                    .withCannedAcl(CannedAccessControlList.PublicRead));
////            String publicURL = "https://" + AWS_S3_SITE + ".amazonaws.com/" + AWS_S3_BUCKET + "/" + AWS_S3_FOLDER + "/" + randomFileName;
////            return (publicURL);
//        
//    }
//
//    public WebMetaData getMeta() throws IOException {
//        
//            PDFMetaData meta = new PDFMetaData();
//            PDDocumentInformation info = document.getDocumentInformation();
//            meta.setTitle(info.getTitle());
//            meta.setDescription("");
//            
//            meta.setAuthor(info.getAuthor());
//            meta.setPages(document.getNumberOfPages());
//            meta.setThumbnail(getImageURL());
//            meta.setType("pdf");
//            meta.setDateCreated(info.getCreationDate().getTime());
//            meta.setDomain("http://metamug.com/assets/img/symbols/pdf_symbol.png");
//            return meta;
//    }
//    
//    PDDocument document;
//}
