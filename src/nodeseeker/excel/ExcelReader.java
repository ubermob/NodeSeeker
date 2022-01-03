package nodeseeker.excel;

import nodeseeker.NodeSeekerProperties;
import nodeseeker.node.Node;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Convert nodes coordinates from .xls file to list of nodes.
 *
 * @author Andrey Korneychuk on 17-Sep-21
 * @version 1.1
 */
public class ExcelReader {

	/**
	 * Parse .xls file.
	 *
	 * @param path        string with path to .xls file
	 * @param excelParams {@link ExcelParams} object with parsing settings
	 * @param multiplier  decimal view of node coordinates accuracy
	 * @param properties  {@link NodeSeekerProperties} loaded properties
	 * @return list with nodes
	 * @throws IOException if an I/O error occurs
	 */
	public static List<Node> parse(String path, ExcelParams excelParams
			, int multiplier, NodeSeekerProperties properties) throws IOException {
		List<Node> list = new ArrayList<>();
		Workbook workbook = WorkbookFactory.create(new FileInputStream(path));
		Iterator<Sheet> iterator = workbook.iterator();
		Sheet iteratorSheet;
		Iterator<Row> iteratorRow;
		Row row;
		while (iterator.hasNext()) {
			iteratorSheet = iterator.next();
			iteratorRow = iteratorSheet.iterator();
			while (iteratorRow.hasNext()) {
				row = iteratorRow.next();
				if (row.getRowNum() < excelParams.getNoDataRows()) {
					continue;
				}
				list.add(new Node(
						(int) row.getCell(excelParams.getIdCellNumber()).getNumericCellValue(),
						row.getCell(excelParams.getXCellNumber()).getNumericCellValue(),
						row.getCell(excelParams.getYCellNumber()).getNumericCellValue(),
						row.getCell(excelParams.getZCellNumber()).getNumericCellValue(),
						multiplier,
						properties
				));
			}
		}
		return list;
	}
}