package example.persistence;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class BaseEntity<T> implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private 	String id;
	protected String remarks;	// 备注
	protected String createBy;	// 创建者
	protected Date createDate;	// 创建日期
	protected String updateBy;	// 更新者
	protected Date updateDate;	// 更新日期
	protected String delFlag; 	// 删除标记（0：正常；1：删除；2：审核）
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
	public void preInsert() {
		this.id = uuid();
		this.updateDate = new Date();
		this.createDate = new Date();
		this.createBy = "system";
		this.updateBy = "system";
	}
	
	public void preUpdate() {
		this.updateDate = new Date();
		this.updateBy = "systems";
	}
	
	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
