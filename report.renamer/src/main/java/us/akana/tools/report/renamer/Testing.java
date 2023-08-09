package us.akana.tools.report.renamer;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.LoadLibs;

public class Testing {

	public static void main(String[] args) {
		File PDF = new File("Z:\\Report renaming tool\\Testing data\\IE006 BacaDlo Ay Azhi Comm School\\04 Report\\Draft\\IE006_N34-02_AB900601_Space Analysis Sub-Sit Report_BacaDlo Ay Azhi.pdf");
		File tmpFolder = LoadLibs.extractTessResources("win32-x86-64");
		System.setProperty("java.library.path", tmpFolder.getPath());
		Tesseract tesseract = new Tesseract();
		tesseract.setLanguage("eng");
		try {
			Path dataDirectory = Paths.get(ClassLoader.getSystemResource("data").toURI());
			tesseract.setDatapath(dataDirectory.toString());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		PDDocument activePDF;
		try {
			activePDF = PDDocument.load(PDF);
			PDFRenderer renderedDoc = new PDFRenderer(activePDF);
			String ret = tesseract.doOCR(renderedDoc.renderImage(0));
			System.out.println(ret);
		} catch (IOException | TesseractException e) {
			e.printStackTrace();
		}
	}
}