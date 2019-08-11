package dev.pater.transaction;

import java.sql.SQLException;

public interface TransactionalOperation<T> {
	T run() throws SQLException;
}
