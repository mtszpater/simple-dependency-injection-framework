package pl.insert.transaction;

import java.sql.SQLException;

public interface TransactionTemplate {

	<T> T execute(TransactionalOperation<T> action) throws SQLException;

}