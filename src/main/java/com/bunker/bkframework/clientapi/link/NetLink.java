package com.bunker.bkframework.clientapi.link;

import com.bunker.bkframework.clientapi.NetHandle;
import com.bunker.bkframework.clientapi.transaction.Transaction;

public abstract class NetLink<SendDataType, ReceiveDataType> implements NetHandle<SendDataType, ReceiveDataType> {
	interface OnResultListener {
		public void result(boolean result);
	}

	public interface OnLinkResultListener {
		public void result(boolean result, String key, Object link);
	}

	private OnResultListener mListener;
	private String mLinkResultKey;
	private OnLinkResultListener mLinkListener;
	private boolean isHanding = false;

	public void setOnResultListener(OnResultListener listener) {
		mListener = listener;
	}

	public void setOnLinkResultListener(OnLinkResultListener listener, String key) {
		mLinkResultKey = key;
		mLinkListener = listener;
	}

	protected void result(boolean result) {
		if (mLinkListener != null && isHanding)
			mLinkListener.result(result, mLinkResultKey, this);
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

	public Transaction toTransaction() {
		return null;
	}

	public void setTimeout(long timeMillisec) {
		
	}
}