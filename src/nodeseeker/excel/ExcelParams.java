package nodeseeker.excel;

/**
 * Contain parsing settings for excel file.
 *
 * @author Andrey Korneychuk on 25-Dec-21
 * @version 1.0
 */
public class ExcelParams {

	private final int noDataRows, idCellNumber, xCellNumber, yCellNumber, zCellNumber;

	public ExcelParams(int noDataRows, int idCellNumber, int xCellNumber, int yCellNumber, int zCellNumber) {
		this.noDataRows = noDataRows;
		this.idCellNumber = idCellNumber;
		this.xCellNumber = xCellNumber;
		this.yCellNumber = yCellNumber;
		this.zCellNumber = zCellNumber;
	}

	public int getNoDataRows() {
		return noDataRows;
	}

	public int getIdCellNumber() {
		return idCellNumber;
	}

	public int getXCellNumber() {
		return xCellNumber;
	}

	public int getYCellNumber() {
		return yCellNumber;
	}

	public int getZCellNumber() {
		return zCellNumber;
	}
}