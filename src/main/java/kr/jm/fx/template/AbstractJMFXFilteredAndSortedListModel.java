package kr.jm.fx.template;

import static kr.jm.utils.helper.JMPredicate.peek;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;

public abstract class AbstractJMFXFilteredAndSortedListModel<B>
		extends AbstractJMFXModel<SortedList<B>> {

	public AbstractJMFXFilteredAndSortedListModel() {
		super(new SortedList<>(FXCollections.emptyObservableList()));
	}

	public void setModel(SortedList<B> sortedList) {
		this.model = sortedList;
	}

	public void bindComparator(
			ObservableValue<? extends Comparator<? super B>> observable) {
		this.model.comparatorProperty().bind(observable);
	}

	public void setSortedModelAndBind(ObservableList<B> list,
			ObservableValue<Comparator<B>> toBindValue) {
		Optional.ofNullable(list).map(ObservableList::sorted)
				.filter(peek(sortedList -> sortedList.comparatorProperty()
						.bind(toBindValue)))
				.ifPresent(this::setModel);
	}

	public void setFilteredAndSortedModelAndBind(ObservableList<B> list,
			Predicate<B> filter, ObservableValue<Comparator<B>> toBindValue) {
		Optional.ofNullable(list).map(l -> l.filtered(filter))
				.ifPresent(filteredList -> setSortedModelAndBind(filteredList,
						toBindValue));
	}

}
