package ena;

import java.util.List;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

// TableModel to work with Lists
public class ChannelTableModelList extends AbstractTableModel implements TableModel {
	private List<Channel> list;

	public ChannelTableModelList(List<Channel> list) {
		this.list = list;
	}

	// returns number of columns
	public int getColumnCount() {
		return 2;
	}

	// returns number of rows
	public int getRowCount() {
		return list.size();
	}

	// return element at selected row and column
	public Object getValueAt(int row, int column) {
		Channel channel = list.get(row);
		switch (column) {
		case 0:
			return channel.getChannelNumber();
		case 1:
			return channel.getChannelName();
		}
		return null;
	}

	// returns columnname
	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return "Channel";
		case 1:
			return "Name";
		}
		return null;
	}
}