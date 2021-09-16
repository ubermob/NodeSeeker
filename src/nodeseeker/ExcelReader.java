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

public class ExcelReader {

	protected static int noDataRows;
	protected static int idCellNumber;
	protected static int xCellNumber;
	protected static int yCellNumber;
	protected static int zCellNumber;

	public static List<Node> parse(String path) throws IOException {
		List<Node> list = new ArrayList<>();
		Workbook workbook = WorkbookFactory.create(new FileInputStream(path));
		Iterator<Sheet> iterator = workbook.iterator();
		while (iterator.hasNext()) {
			Sheet iteratorSheet = iterator.next();
			Iterator<Row> iteratorRow = iteratorSheet.iterator();
			while (iteratorRow.hasNext()) {
				Row row = iteratorRow.next();
				if (row.getRowNum() < noDataRows) {
					continue;
				}
				list.add(new Node(
						(int) row.getCell(idCellNumber).getNumericCellValue(),
						row.getCell(xCellNumber).getNumericCellValue(),
						row.getCell(yCellNumber).getNumericCellValue(),
						row.getCell(zCellNumber).getNumericCellValue()
				));
			}
		}
		return list;
	}
}