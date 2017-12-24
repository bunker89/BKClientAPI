package com.bunker.bkframework.clientapi.transaction;

import java.util.LinkedList;
import java.util.List;

import com.bunker.bkframework.clientapi.BKClientException;

public class TransactionManager {

	private boolean mIsTransactioning = false;
	private List<Transaction> mTransactionQueue;

	public void addTransaction(Transaction transaction) {
		mTransactionQueue.add(transaction);
	}

	public void clearTransaction() {
		mTransactionQueue.clear();
	}

	public void removeTransaction(Transaction transaction) {
		mTransactionQueue.remove(transaction);
	}

	public void startTransaction() throws BKClientException {
		if (mIsTransactioning) {
			throw new BKClientException("transaction is already started!");
		}
		mIsTransactioning = true;
		mTransactionQueue = new LinkedList<>();
	}

	public void endTransaction() throws BKClientException {
		if (!mIsTransactioning) {
			throw new BKClientException("transaction not using");
		}
		mIsTransactioning = false;
	}

	public boolean isTransactioning() {
		return mIsTransactioning;
	}
}
