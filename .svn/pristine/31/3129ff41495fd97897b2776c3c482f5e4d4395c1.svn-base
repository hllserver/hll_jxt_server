package ssm.entity.common;

import java.io.Serializable;
import java.util.List;
/**
 * 增、删、改 的记录
 * @author liaoyun
 * 2016-3-13
 * @param <T>
 */
public class RecordsO <T> implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2210481180097753730L;
	private List<T> insertedRecords;
	private List<T> updatedRecords;
	private List<T> deletedRecords;
	public RecordsO() {
		super();
	}
	public List<T> getInsertedRecords() {
		return insertedRecords;
	}
	public void setInsertedRecords(List<T> insertedRecords) {
		this.insertedRecords = insertedRecords;
	}
	public List<T> getUpdatedRecords() {
		return updatedRecords;
	}
	public void setUpdatedRecords(List<T> updatedRecords) {
		this.updatedRecords = updatedRecords;
	}
	public List<T> getDeletedRecords() {
		return deletedRecords;
	}
	public void setDeletedRecords(List<T> deletedRecords) {
		this.deletedRecords = deletedRecords;
	}
	
}
