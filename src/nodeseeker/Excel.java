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

public class Excel {
	public static List<Node> parse(String path) throws IOException {
		List<Node> result = new ArrayList<>();

		Workbook workbook  = WorkbookFactory.create(new FileInputStream(path));
		Iterator<Sheet> iterator = workbook.iterator();

		while (iterator.hasNext()) {
			Sheet sheet = iterator.next();
			Iterator<Row> iteratorRow = sheet.iterator();
			while (iteratorRow.hasNext()) {
				Row row = iteratorRow.next();
				if (row.getRowNum() < 7)
					continue;
				result.add(new Node(
						(int) row.getCell(0).getNumericCellValue(),
						row.getCell(1).getNumericCellValue(),
						row.getCell(2).getNumericCellValue(),
						row.getCell(3).getNumericCellValue()
				));
			}
		}

		return result;
	}
}