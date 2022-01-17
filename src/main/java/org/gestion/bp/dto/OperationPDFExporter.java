package org.gestion.bp.dto;

import java.util.List;
import com.lowagie.text.*;
import java.awt.Color;
import java.io.IOException;
import com.lowagie.text.pdf.*;
import javax.servlet.http.HttpServletResponse;

public class OperationPDFExporter {
private List<ProduitResponse> listoP;

public OperationPDFExporter(List<ProduitResponse> listoP) {
	super();
	this.listoP = listoP;
}
private void writeTableHeader(PdfPTable table) {
    PdfPCell cell = new PdfPCell();
    cell.setBackgroundColor(Color.decode("#7F8C8D"));
    cell.setPadding(6);
     
    Font font = FontFactory.getFont(FontFactory.COURIER); /**/
    font.setColor(Color.WHITE);
     
    cell.setPhrase(new Phrase("#Op", font));
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    table.addCell(cell);
    
    cell.setPhrase(new Phrase("#Prod", font));
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    table.addCell(cell);
     
    cell.setPhrase(new Phrase("Nom Operation", font));
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    table.addCell(cell);
    
    cell.setPhrase(new Phrase("Intitulé", font));
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    table.addCell(cell);  
    
    cell.setPhrase(new Phrase("Matricule", font));
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    table.addCell(cell);  
    
    
    cell.setPhrase(new Phrase("Qte", font));
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    table.addCell(cell); 
    
    cell.setPhrase(new Phrase("Date retour", font));
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    table.addCell(cell); 
    
   
    
//    cell.setPhrase(new Phrase("Date retour", font));
//    table.addCell(cell);  
}
 
private void writeTableData(PdfPTable table) {
    for (ProduitResponse user : listoP) {
        table.addCell(String.valueOf(user.getId()));
        table.addCell(String.valueOf(user.getIdP()));
        table.addCell(user.getNomOp());
        table.addCell(user.getIntitule());
        table.addCell(user.getMatricule());
        table.addCell(String.valueOf(user.getQte()));
        table.addCell(user.getDateRetour());
        table.setHorizontalAlignment(Element.ALIGN_MIDDLE);
       // table.addCell(String.valueOf(user.getDateRetour()));
    }
}
 
public void export(HttpServletResponse response) throws DocumentException, IOException {
    Document document = new Document(PageSize.A4);
    PdfWriter.getInstance(document, response.getOutputStream());
     
    document.open();
//    Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN); /* */ 
//    font.setSize(23);
//    font.setColor(Color.LIGHT_GRAY);
    
    
    Paragraph p3 = new Paragraph("crée le :"+listoP.get(0).getDateOP());
    p3.setAlignment(Paragraph.ALIGN_RIGHT);
    document.add(p3);
    p3.setSpacingAfter(4);
    
    Paragraph p1 = new Paragraph("Nom de l'ouvrier : "+listoP.get(0).getUsername());
    p1.setAlignment(Paragraph.ALIGN_RIGHT);
    document.add(p1);
    
    Paragraph p5 = new Paragraph("Gestion de stock");
    p5.setAlignment(Paragraph.ALIGN_LEFT);
    document.add(p5);
    p5.setSpacingBefore(2);
    p5.setAlignment(3);
    
    Paragraph p6 = new Paragraph("Bizerte 7000");
    p6.setAlignment(Paragraph.ALIGN_LEFT);
    document.add(p6);
    p6.setSpacingBefore(3);
    p6.setAlignment(3);
    
    Paragraph p4 = new Paragraph("Nom du responsable : "+listoP.get(0).getNomResp());
    p4.setAlignment(Paragraph.ALIGN_LEFT);
    document.add(p4);
    p4.setSpacingBefore(5);
    p4.setAlignment(3);
   
    
    Paragraph p = new Paragraph("List des operations effectuées ");
    p.setAlignment(Paragraph.ALIGN_CENTER);
    document.add(p);
  
    Paragraph p2 = new Paragraph("Signature ");
    p2.setAlignment(Paragraph.ALIGN_MIDDLE);
//    document.add(p2);
     
    PdfPTable table = new PdfPTable(7);
    table.setWidthPercentage(100f);
    table.setWidths(new float[] {1.5f, 2.1f, 4.3f, 4.5f, 3.1f, 1.5f,3.0f});
    table.setSpacingBefore(15);
     
    writeTableHeader(table);
    writeTableData(table);
     
    document.add(table);
    p2.setSpacingBefore(100);
    document.add(p2);
     
    document.close();
     
}


}
