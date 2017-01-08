package kr.jm.fx.template;

import java.util.Comparator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public abstract class AbstractJMFXListModel<B>
		extends AbstractJMFXModel<ObservableList<B>> {

	public AbstractJMFXListModel() {
		super(FXCollections.observableArrayList());
	}

	public void addRow(B row) {
		this.model.add(row);
	}

	public void clear() {
		this.model.clear();
	}

	public void addAllRows(List<B> rowList) {
		this.model.addAll(rowList);
	}

	public void changeAllRows(List<B> rowList) {
		clear();
		addAllRows(rowList);
	}

	public void removeRow(B row) {
		this.model.remove(row);
	}

	public void remove(int index) {
		this.model.remove(index);
	}

	public void remove(int from, int to) {
		this.model.remove(from, to);
	}

	public void sort(Comparator<? super B> comparator) {
		this.model.sort(comparator);
	}

}
