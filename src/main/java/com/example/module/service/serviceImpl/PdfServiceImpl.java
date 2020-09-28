package com.example.module.service.serviceImpl;

import com.example.module.service.PdfService;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.TextAlignment;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.util.Map;

/**
 * Created by DIPU on 8/12/20
 */

@Log4j2
@Service
public class PdfServiceImpl implements PdfService {
    String BANGLA_TTF_PATH=new  ClassPathResource("Bangla.ttf").getPath();
    public PdfServiceImpl() throws IOException {
        PdfFontFactory.createFont(BANGLA_TTF_PATH, PdfEncodings.IDENTITY_H, true, true);
    }

    static {
        //createFormatedPdf();
        createFormatedPdf2();

        String codeOrMsg= HttpStatus.OK.name();
        System.out.println("CodeOrString:"+ codeOrMsg);

        Map<String, Object> map=null;
        //map.put("sdfsd","dfd");
       System.out.println("empty?:"+ MapUtils.isNotEmpty(map));
    }


    @Override
    public boolean generateDummyPdf()  {

        try
        {
            String dest= FileSystems.getDefault().getPath("").toAbsolutePath().toString()+"/test.pdf";
            log.info(dest);
            PdfWriter writer= new PdfWriter(dest);
            PdfDocument pdfDoc = new PdfDocument(writer);
            pdfDoc.addNewPage();

            Document document = new Document(pdfDoc);
            Paragraph paragraph1=new Paragraph("this is paragraph-1");
            Paragraph paragraph2=new Paragraph("this is paragraph-2");
            document.add(paragraph1);
            document.add(paragraph2);

            //adding areabreak
            AreaBreak areaBreak=new AreaBreak();
            document.add(areaBreak);

            Paragraph paragraph3=new Paragraph("this is paragraph-3");
            document.add(paragraph3);

            //adding list
            List list=new List();
            list.add("list1");
            list.add("list2");
            list.add("list3");

            float [] pointColumnWidths = {150F, 150F, 150F};
            Table table = new Table(pointColumnWidths);

            // Adding cells to the table
            Cell cell = new Cell(1, 3).add(new Paragraph("Table Header"));
            cell.setTextAlignment(TextAlignment.CENTER);
            cell.setPadding(5);
            cell.setBackgroundColor(new DeviceRgb(14, 221, 8));
            table.addCell(cell);

            table.addCell("column-one");
            table.addCell("Column-two");
            table.addCell("Column-three");

            for (int i = 0; i < 10; i++) {
                table.addCell("col1-" + i);
                table.addCell("col2-" + i);
                table.addCell("col3-" + i);

            }

            //adding image
            String imageFile="/home/***/Pictures/min2.png";
            ImageData image=ImageDataFactory.create(imageFile);
            Image img=new Image(image);
            Cell cell1=new Cell().add(img.setAutoScale(true));
            table.addCell(cell1);

            document.add(table);
            
            document.add(list);

            //diff. font
            Paragraph para2=new Paragraph();
            String encoding = "UTF-8";
            Text text=new Text(" আমার সোনার বাংলা ").setFont(PdfFontFactory.createFont("/home/***/Downloads/kalpurush ANSI.ttf", encoding, false));

            //text.setFont(font);
            para2.add(text);
            document.add(para2);

            document.close();

            return true;
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
        }
        return false;
    }

    private static boolean createFormatedPdf()
    {
        try
        {
            String dest= FileSystems.getDefault().getPath("").toAbsolutePath().toString()+"/test.pdf";
            log.info(dest);
            PdfWriter writer= new PdfWriter(dest);
            PdfDocument pdfDoc = new PdfDocument(writer);
            pdfDoc.addNewPage();

            Document document = new Document(pdfDoc);

            float [] pointColumnWidths = {150F, 150F};
            Table outerTable = new Table(pointColumnWidths);

            for (int i = 0; i < 2; i++) {
                outerTable.addCell("col1-" + i);
                outerTable.addCell("col2-" + i);

            }

            float [] innerTablePointColumnWidths = {150F, 150F, 150F, 150F};
            Table nestedTable = new Table(innerTablePointColumnWidths);

            nestedTable.addCell("col1");
            nestedTable.addCell("val1");

            nestedTable.addCell("col2");
            nestedTable.addCell("val2");

            nestedTable.addCell("col11");
            nestedTable.addCell("val11");

            nestedTable.addCell("col21");
            nestedTable.addCell("val21");

            nestedTable.addCell("col12");
            nestedTable.addCell("val12");

            nestedTable.addCell("col22");
            nestedTable.addCell("val22");

            outerTable.addCell("Present Address:");

            //outerTable.setBorder(Border.NO_BORDER);
            Cell cell = new Cell();
            cell.add(nestedTable);
           // cell.setBorder(Border.NO_BORDER);
            outerTable.addCell(cell);

          //  outerTable.addCell(nestedTable);



            document.add(outerTable);

            document.close();

            return true;
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
        }
        return false;
    }

    private static boolean createFormatedPdf2()
    {
        try
        {
            String dest= FileSystems.getDefault().getPath("").toAbsolutePath().toString()+"/test.pdf";
            log.info(dest);
            PdfWriter writer= new PdfWriter(dest);
            PdfDocument pdfDoc = new PdfDocument(writer);
            pdfDoc.addNewPage();

            Document document = new Document(pdfDoc);

            float [] pointColumnWidths = {5F, 5F, 5F, 5F, 5F};
            Table outerTable =new Table(pointColumnWidths);
            outerTable.setWidth(400);



             Cell cell=new Cell(1,1).add(new Paragraph("col1-"+1)).setTextAlignment(TextAlignment.CENTER);
             outerTable.addCell(cell);
             cell=new Cell(1,4).add(new Paragraph("col1-"+2)).setTextAlignment(TextAlignment.CENTER);
             outerTable.addCell(cell);
            outerTable.addCell(new Cell(1,1).add(new Paragraph("sf")).setTextAlignment(TextAlignment.CENTER));
            outerTable.addCell(new Cell(1,4).add(new Paragraph("sf")).setTextAlignment(TextAlignment.CENTER));
            outerTable.addCell(new Cell(1,1).add(new Paragraph("sf")).setTextAlignment(TextAlignment.CENTER));
            outerTable.addCell(new Cell(1,4).add(new Paragraph("sf")).setTextAlignment(TextAlignment.CENTER));

            //define how many inner row we are going to insert withing a single cell-row ie. (2,1)
            outerTable.addCell(new Cell(2,1).add(new Paragraph("parent-field")).setTextAlignment(TextAlignment.CENTER));
            outerTable.addCell(new Cell(1,1).add(new Paragraph("field")).setTextAlignment(TextAlignment.CENTER));
            outerTable.addCell(new Cell(1,1).add(new Paragraph("value")).setTextAlignment(TextAlignment.CENTER));
            outerTable.addCell(new Cell(1,1).add(new Paragraph("field")).setTextAlignment(TextAlignment.CENTER));
            outerTable.addCell(new Cell(1,1).add(new Paragraph("value")).setTextAlignment(TextAlignment.CENTER));

            outerTable.addCell(new Cell(1,1).add(new Paragraph("field")).setTextAlignment(TextAlignment.CENTER));
            outerTable.addCell(new Cell(1,1).add(new Paragraph("value")).setTextAlignment(TextAlignment.CENTER));
            outerTable.addCell(new Cell(1,1).add(new Paragraph("field")).setTextAlignment(TextAlignment.CENTER));
            outerTable.addCell(new Cell(1,1).add(new Paragraph("value")).setTextAlignment(TextAlignment.CENTER));

            outerTable.addCell(new Cell(2,1).add(new Paragraph("parent-field")).setTextAlignment(TextAlignment.CENTER));
            outerTable.addCell(new Cell(1,1).add(new Paragraph("field")).setTextAlignment(TextAlignment.CENTER));
            outerTable.addCell(new Cell(1,1).add(new Paragraph("value")).setTextAlignment(TextAlignment.CENTER));
            outerTable.addCell(new Cell(1,1).add(new Paragraph("field")).setTextAlignment(TextAlignment.CENTER));
            outerTable.addCell(new Cell(1,1).add(new Paragraph("value")).setTextAlignment(TextAlignment.CENTER));

            outerTable.addCell(new Cell(1,1).add(new Paragraph("field")).setTextAlignment(TextAlignment.CENTER));
            outerTable.addCell(new Cell(1,1).add(new Paragraph("value")).setTextAlignment(TextAlignment.CENTER));
            outerTable.addCell(new Cell(1,1).add(new Paragraph("field")).setTextAlignment(TextAlignment.CENTER));
            outerTable.addCell(new Cell(1,1).add(new Paragraph("value")).setTextAlignment(TextAlignment.CENTER));

            outerTable.addCell(new Cell(1,1).add(new Paragraph("parent-field")).setTextAlignment(TextAlignment.CENTER));
            outerTable.addCell(new Cell(1,4).add(new Paragraph("parent-field")).setTextAlignment(TextAlignment.CENTER));

            outerTable.addCell(new Cell(1,1).add(new Paragraph("parent-field")).setTextAlignment(TextAlignment.CENTER));
            outerTable.addCell(new Cell(1,4).add(new Paragraph("parent-field")).setTextAlignment(TextAlignment.CENTER));

            document.add(outerTable);
            String ssimageUrl="http://localhost:9000/bucket1/image2.png";
            byte[] bytes=getFileBytes(ssimageUrl);
           if(bytes.length>0)
           {
               ImageData imageData = ImageDataFactory.create(bytes);
               Image image= new Image(imageData);
               //image.setFixedPosition(100, -200);

               document.add(new Paragraph());
               document.add(new Paragraph("Voter Documents"));
               document.add(new Paragraph("Photo"));
               image.setMarginLeft(30F);
               image.setHeight(100F);
               image.setWidth(180F);
               document.add(image);

               Image image2=new Image(imageData);
               document.add(new Paragraph("Signature"));
               image2.setMarginLeft(30F);
               image2.setHeight(100F);
               image2.setWidth(180F);
               document.add(image2);

           }

            document.close();

            return true;
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
        }
        return false;
    }


    public static Cell createCell(String content, float borderWidth,int rowspan, int colspan, TextAlignment alignment) {
        Cell cell = new Cell(rowspan, colspan).add(new Paragraph(content))
                .setTextAlignment(alignment);
        cell.setHeight(PageSize.A4.getWidth()* 0.1F);
        return cell;
    }

    private static byte[] getFileBytes(String url)
    {
        byte[] fileContent = new byte[0];
        if(url==null || url.isEmpty())
            return fileContent;

        try {
            return IOUtils.toByteArray(new URL(url));
        } catch (IOException e) {
            log.error("File reading error:"+ e.getMessage());
        }
        return fileContent;
    }
}
