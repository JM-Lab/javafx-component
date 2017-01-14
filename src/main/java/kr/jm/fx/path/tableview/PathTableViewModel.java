package kr.jm.fx.path.tableview;

import javafx.collections.transformation.SortedList;
import kr.jm.fx.path.JMFXPath;
import kr.jm.fx.template.AbstractJMFXFilteredAndSortedListModel;

/**
 * The Class PathTableViewModel.
 */
public class PathTableViewModel
		extends AbstractJMFXFilteredAndSortedListModel<JMFXPath> {

	@Override
	public void setModel(SortedList<JMFXPath> sortedList) {
		this.model = sortedList;
	}

}
