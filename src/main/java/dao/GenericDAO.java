package dao;

import java.util.List;

import mapper.RowMapper;

public interface GenericDAO<T> { //GenericDAO<ID, T> 
//	T findById(ID id);
//	List<T> all();
//	void save(T entity);


	<T> List<T> query(String sql, RowMapper<T> rowMapper, Object... parameters);

	Integer insert(String sql, Object... parameters);

	int update(String sql, Object... parameters);

}