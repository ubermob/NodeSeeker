package nodeseeker;

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
 * @version 1.0
 */
public class ExcelReader {

	/**
	 * Parse .xls file.
	 *
	 * @param path string with path to .xls file
	 * @return list with nodes
	 * @throws IOException
	 */
	public static List<Node> parse(String path) throws IOException {
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
				if (row.getRowNum() < NodeSeeker.noDataRows) {
					continue;
				}
				list.add(new Node(
						(int) row.getCell(NodeSeeker.idCellNumber).getNumericCellValue(),
						row.getCell(NodeSeeker.xCellNumber).getNumericCellValue(),
						row.getCell(NodeSeeker.yCellNumber).getNumericCellValue(),
						row.getCell(NodeSeeker.zCellNumber).getNumericCellValue()
				));
			}
		}
		return list;
	}
}