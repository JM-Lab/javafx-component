package kr.jm.fx.template;

import kr.jm.fx.JMFXModelInterface;

public abstract class AbstractJMFXModel<M> implements JMFXModelInterface<M> {

	protected M model;

	public AbstractJMFXModel(M model) {
		this.model = model;
	}

	@Override
	public M getModel() {
		return model;
	}

}
