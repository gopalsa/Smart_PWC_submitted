package smart.cst.pwc.app;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class HeaderFooterPageEvent extends PdfPageEventHelper {
    Image image;
    Image bu;
    private static Font subFont = new Font(Font.FontFamily.HELVETICA, 4,
            Font.NORMAL);

    public HeaderFooterPageEvent(Image image1, Image bu) {
        image = image1;
        this.bu=bu;
    }

    /**
     * The template with the total number of pages.
     */


    public void onStartPage(PdfWriter writer, Document document) {
        Rectangle rect = writer.getBoxSize("art");

//        ColumnText.showTextAligned(writer.getDirectContent(),
//                Element.ALIGN_CENTER, new Phrase("Bharathiar university"), rect.getRight(), rect.getTop(), 0);
        // image.setAbsolutePosition(rect.getRight(),rect.getTop());

    }

    public void onEndPage(PdfWriter writer, Document document) {
        Rectangle rect = writer.getBoxSize("art");
        ColumnText.showTextAligned(writer.getDirectContent(),
                Element.ALIGN_CENTER, new Phrase("Bharathiar University Department of Social Work Digital education and green initiative to go paperless powered by climatesmarttech.com", subFont), rect.getLeft(), rect.getBottom(), 0);
        bu.setAbsolutePosition(rect.getLeft() - 135, rect.getBottom()-2);
        bu.scaleToFit(10, 10);
        bu.setAlignment(Element.ALIGN_CENTER);
        try {
            writer.getDirectContent().addImage(bu);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        image.setAbsolutePosition(rect.getLeft() + 125, rect.getBottom()-3);
        image.scaleToFit(10, 10);
        image.setAlignment(Element.ALIGN_CENTER);
        try {
            writer.getDirectContent().addImage(image);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

//        ColumnText.showTextAligned(writer.getDirectContent(),
//                Element.ALIGN_CENTER, new Phrase("Page " + String.valueOf(writer.getPageNumber())),
//                rect.getRight(), rect.getBottom(), 0);
    }

}