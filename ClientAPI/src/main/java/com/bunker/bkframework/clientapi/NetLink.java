package com.bunker.bkframework.clientapi;

public abstract class NetLink implements NetHandle {
	public interface OnResultListener {
		public void result(boolean result);
	}

	private OnResultListener mListener;
	private boolean isHanding = false;

	public void setOnResultListener(OnResultListener listener) {
		mListener = listener;
	}

	protected void result(boolean result) {
		if (mListener != null && isHanding)
			mListener.result(result);
		isHanding = false;
	}

	@Override
	public void broken() {
		if (mListener != null && isHanding) {
			mListener.result(false);
		}
		isHanding = false;
	}

	public void setMainChain() {
		isHanding = true;
	}
}