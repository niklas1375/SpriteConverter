package converter.util;

public class Naming {
	public static boolean readOnly = false;

	public static String getName(int row, int col) {

		if (col % 5 == 2 && row % 5 > 0) {
			return null;
		} else if (col % 5 > 2 && row % 5 > 2) {
			return null;
		} else {
			String name = "";

			name += getPrefix(col);

			name += getType(col);

			name += getStatus(row, col);

			if (readOnly) {
				name += "_RO";
				readOnly = false;
			}
			return name;
		}
	}

	private static String getStatus(int row, int col) {
		String status = "";
		if (col % 5 != 2) {
			switch (row % 5) {
			case 0:
				status = "NORMAL";
				break;
			case 1:
				status = "NORMAL_HOVER";
				break;
			case 2:
				status = "SELECTED";
				break;
			case 3:
				status = "NORMAL_PEND";
				break;
			case 4:
				status = "SELECTED_PEND";
				break;
			}
		}
		return status;
	}

	private static String getType(int col) {
		String type = "";
		switch (col % 5) {
		case 0:
			type = "SINGLE_";
			break;
		case 1:
			type = "STACK_";
			break;
		case 2:
			type = "GHOST";
			break;
		case 3:
			type = "SINGLE_";
			readOnly = true;
			break;
		case 4:
			type = "STACK_";
			readOnly = true;
			break;
		}
		return type;
	}

	private static String getPrefix(int col) {
		String prefix = "";
		int tmpCol = Math.floorDiv(col, 5);
		switch (tmpCol) {
		case 0:
			prefix = "QG_32_";
			break;
		case 1:
			prefix = "IND_32_";
			break;
		case 2:
			prefix = "ST_32_";
			break;
		case 3:
			prefix = "PT_32_";
			break;
		}
		return prefix;
	}

}
