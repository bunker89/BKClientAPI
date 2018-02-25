package com.bunker.bkframework.clientapi.link;

import com.bunker.bkframework.clientapi.NetHandle;
import com.bunker.bkframework.clientapi.transaction.Transaction;

public abstract class NetLink<SendDataType, ReceiveDataType> implements NetHandle<SendDataType, ReceiveDataType> {
	public interface OnResultListener {
		public void result(boolean result);
	}

	public interface OnLinkResultListener<SendDataType, ReceiveDataType> {
		public void result(String key, NetLink<SendDataType, ReceiveDataType> link);
	}

	private OnResultListener mListener;
	private String mLinkResultKey;
	private OnLinkResultListener<SendDataType, ReceiveDataType> mLinkListener;
	private boolean isHanding = false;

	public void setOnResultListener(OnResultListener listener) {
		mListener = listener;
	}

	public void estOnLinkResultListener(OnLinkResultListener<SendDataType, ReceiveDataType> listener, String key) {
		mLinkResultKey = key;
		mLinkListener = listener;
	}

	protected void result(boolean result) {
		if (mListener != null && isHanding)
			mListener.result(result);
		if (mLinkListener != null && isHanding)
			mLinkListener.result(mLinkResultKey, this);
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
}