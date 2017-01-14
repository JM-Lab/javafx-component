package kr.jm.fx.path.treetableview;

import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import kr.jm.fx.path.JMFXPath;
import kr.jm.fx.path.treeview.PathTreeViewModel;

/**
 * The Class PathListTreeTableView.
 */
public class PathListTreeTableView extends PathTreeTableView {

	@FXML
	protected TreeTableColumn<JMFXPath, Integer> numberColumn;

	private TreeItem<JMFXPath> rootTreeItem;
	private List<JMFXPath> jmfxPathList;

	/**
	 * Instantiates a new path list tree table view.
	 */
	public PathListTreeTableView() {
		super();
	}

	/**
	 * Instantiates a new path list tree table view.
	 *
	 * @param i18nResourceBundle
	 *            the i 18 n resource bundle
	 */
	public PathListTreeTableView(ResourceBundle i18nResourceBundle) {
		super(i18nResourceBundle);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see kr.jm.fx.path.treetableview.PathTreeTableView#initializeView()
	 */
	@Override
	public void initializeView() {
		super.initializeView();
		this.numberColumn.setStyle("-fx-alignment: CENTER-RIGHT;");
		this.numberColumn.setCellValueFactory(
				cellData -> Optional.of(cellData.getValue().getValue())
						.filter(jmfxPathList::contains)
						.map(jmfxPathList::indexOf).map(i -> i + 1)
						.map(SimpleObjectProperty<Integer>::new).orElse(null));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see kr.jm.fx.path.treetableview.PathTreeTableView#bindModelToView()
	 */
	@Override
	public void bindModelToView() {
		pathTreeViewModel = new PathTreeViewModel(JMFXPath
				.getInstance("PathListTreeTableView-" + System.nanoTime()));
		rootTreeItem = pathTreeViewModel.getModel();
		this.setRoot(rootTreeItem);
	}

	/**
	 * Sets the JMFX path list.
	 *
	 * @param jmfxPathList
	 *            the jmfx path list
	 * @return the path list tree table view
	 */
	public PathListTreeTableView setJMFXPathList(List<JMFXPath> jmfxPathList) {
		pathTreeViewModel.setChildrenInRoot(this.jmfxPathList = jmfxPathList);
		return this;
	}

	public List<JMFXPath> getJMFXPathList() {
		return jmfxPathList;
	}

}
