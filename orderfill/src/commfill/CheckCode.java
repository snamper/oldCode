package commfill;


public class CheckCode  extends Common{
	
	/*
	 * 返回验证码图片ID列表
	 */
	public String getCheckCodeID(int nPage, int nMaxCount){
		String sql = "select * from CoCheckCode where (fCode = '') or (fCode is null) order by fCreateTime desc";
		return queryData(nPage, sql, nMaxCount);	
	}

	/*
	 * 输入验证码值
	 */
	public int inputCheckCode(String sID, String sCode){
		String sql = "update CoCheckCode set fCode = '" + sCode + "', fEndTime = GETDATE() where fid = '" + sID + "'";
		return dc.execute(sql);	
	}

}
