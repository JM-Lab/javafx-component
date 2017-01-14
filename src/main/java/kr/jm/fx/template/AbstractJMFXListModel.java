package kr.jm.fx.template;

import java.util.Comparator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The Class AbstractJMFXListModel.
 *
 * @param <B>
 *            the generic type
 */
public abstract class AbstractJMFXListModel<B>
		extends AbstractJMFXModel<ObservableList<B>> {

	/**
	 * Instantiates a new abstract JMFX list model.
	 */
	public AbstractJMFXListModel() {
		super(FXCollections.observableArrayList());
	}

	/**
	 * Adds the row.
	 *
	 * @param row
	 *            the row
	 */
	public void addRow(B row) {
		this.model.add(row);
	}

	/**
	 * Clear.
	 */
	public void clear() {
		this.model.clear();
	}

	/**
	 * Adds the all rows.
	 *
	 * @param rowList
	 *            the row list
	 */
	public void addAllRows(List<B> rowList) {
		this.model.addAll(rowList);
	}

	/**
	 * Change all rows.
	 *
	 * @param rowList
	 *            the row list
	 */
	public void changeAllRows(List<B> rowList) {
		clear();
		addAllRows(rowList);
	}

	/**
	 * Removes the row.
	 *
	 * @param row
	 *            the row
	 */
	public void removeRow(B row) {
		this.model.remove(row);
	}

	/**
	 * Removes the.
	 *
	 * @param index
	 *            the index
	 */
	public void remove(int index) {
		this.model.remove(index);
	}

	/**
	 * Removes the.
	 *
	 * @param from
	 *            the from
	 * @param to
	 *            the to
	 */
	public void remove(int from, int to) {
		this.model.remove(from, to);
	}

	/**
	 * Sort.
	 *
	 * @param comparator
	 *            the comparator
	 */
	public void sort(Comparator<? super B> comparator) {
		this.model.sort(comparator);
	}

}
