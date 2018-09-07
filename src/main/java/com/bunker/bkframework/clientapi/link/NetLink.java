package com.bunker.bkframework.clientapi.link;

import com.bunker.bkframework.clientapi.NetHandle;
import com.bunker.bkframework.clientapi.transaction.Transaction;

public abstract class NetLink<SendDataType, ReceiveDataType> implements NetHandle<SendDataType, ReceiveDataType> {
	interface OnResultListener {
		public void result(boolean result);
		public void broken();
	}

	public interface OnLinkResultListener {
		public void result(boolean result, String key, Object link);
	}

	private OnResultListener mListener;
	private String mLinkResultKey;
	private OnLinkResultListener mLinkListener;
	private boolean isHanding = false;

	void setOnResultListener(OnResultListener listener) {
		mListener = listener;
	}

	public void setOnLinkResultListener(OnLinkResultListener listener, String key) {
		mLinkResultKey = key;
		mLinkListener = listener;
	}

	protected void result(boolean result) {
		linkResult(result, mLinkResultKey);
		if (mListener != null && isHanding)
			mListener.result(result);
		isHanding = false;
	}

	protected void linkResult(boolean result, String key) {
		if (mLinkListener != null && isHanding)
			mLinkListener.result(result, key, this);		
	}

	@Override
	public void broken() {
		if (mListener != null && isHanding) {
			mListener.result(false);
			mListener.broken();
		}
		isHanding = false;
	}

	public void setMainChain() {
		isHanding = true;
	}

	public Transaction toTransaction() {
		return null;
	}

	public void setTimeout(long timeMillisec) {
		
	}
}