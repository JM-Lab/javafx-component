package kr.jm.fx.template;

import kr.jm.fx.JMFXModelInterface;

/**
 * The Class AbstractJMFXModel.
 *
 * @param <M>
 *            the generic type
 */
public abstract class AbstractJMFXModel<M> implements JMFXModelInterface<M> {

	protected M model;

	/**
	 * Instantiates a new abstract JMFX model.
	 *
	 * @param model
	 *            the model
	 */
	public AbstractJMFXModel(M model) {
		this.model = model;
	}

	@Override
	public M getModel() {
		return model;
	}

}
