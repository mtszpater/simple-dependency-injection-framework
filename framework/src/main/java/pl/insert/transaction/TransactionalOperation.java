package pl.insert.transaction;

import java.sql.SQLException;

public interface TransactionalOperation<T> {
	T run() throws SQLException;
}
