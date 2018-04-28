package com.hui10.app.model.acquirer;
/**
 * @ClassName: SettlementInfo.java
 * @Description:
 * @author zhangll
 * @date 2017年11月8日 下午1:24:56
 */
public class SettlementInfo {
	/**商户号	 **/
	private String merc_id;
	/**随机字符串，不长于32位	 **/
	private String salt;
	/**签名	 **/
	private String req_time;
	/**请求发起日期时间	 **/
	private String signature;
	/**参与分润方ID	 **/
	private String profit_participant_id;
	/**参与分润方名称	 **/
	private String profit_participant_name;
	/**商户联系人	 **/
	private String profit_participant_contact;
	/**商户联系电话	 **/
	private String profit_participant_phone;
	/**商户财务联系人	 **/
	private String profit_participant_financial_contact;
	/**	商户财务联系电话 **/
	private String profit_participant_financial_phone;
	/**	商户开户行总行名称 **/
	private String merc_bank_head_name;
	/**	商户开户行总行联行号 **/
	private String merc_bank_head_code;
	/**	商户开户行具体支行名称 **/
	private String merc_bank_branch_name;
	/**	商户开户行具体支行联行号 **/
	private String merc_bank_branch_code;
	/**	商户在银行账户开户类型 **/
	private String merc_account_open_type;
	/**	商户在银行的账户名称 **/
	private String merc_account_name;
	/**	商户银行账户号 **/
	private String merc_account_no;
	/**	单笔结算费率 **/
	private String single_fee_rate;
	/**	单笔手续费的上限额 **/
	private  String single_fee_upper_limit;
	/**	单笔手续费的下限额 **/
	private String single_fee_lower_limit;
	/**分润金额每月结算日期**/
	private String profit_settle_date;
	/**分润金额每月结算起始金额**/
	private String profit_floor_amount;
	/**通道手续费每月结算日期**/
	private String chl_fee_settle_date;
	/**chl_fee_floor_amount**/
	private String chl_fee_floor_amount;
	/**
	 * @return the merc_id
	 */
	public String getMerc_id() {
		return merc_id;
	}
	/**
	 * @param merc_id the merc_id to set
	 */
	public void setMerc_id(String merc_id) {
		this.merc_id = merc_id;
	}
	/**
	 * @return the salt
	 */
	public String getSalt() {
		return salt;
	}
	/**
	 * @param salt the salt to set
	 */
	public void setSalt(String salt) {
		this.salt = salt;
	}
	/**
	 * @return the req_time
	 */
	public String getReq_time() {
		return req_time;
	}
	/**
	 * @param req_time the req_time to set
	 */
	public void setReq_time(String req_time) {
		this.req_time = req_time;
	}
	/**
	 * @return the signature
	 */
	public String getSignature() {
		return signature;
	}
	/**
	 * @param signature the signature to set
	 */
	public void setSignature(String signature) {
		this.signature = signature;
	}
	/**
	 * @return the profit_participant_id
	 */
	public String getProfit_participant_id() {
		return profit_participant_id;
	}
	/**
	 * @param profit_participant_id the profit_participant_id to set
	 */
	public void setProfit_participant_id(String profit_participant_id) {
		this.profit_participant_id = profit_participant_id;
	}
	/**
	 * @return the profit_participant_name
	 */
	public String getProfit_participant_name() {
		return profit_participant_name;
	}
	/**
	 * @param profit_participant_name the profit_participant_name to set
	 */
	public void setProfit_participant_name(String profit_participant_name) {
		this.profit_participant_name = profit_participant_name;
	}
	/**
	 * @return the profit_participant_contact
	 */
	public String getProfit_participant_contact() {
		return profit_participant_contact;
	}
	/**
	 * @param profit_participant_contact the profit_participant_contact to set
	 */
	public void setProfit_participant_contact(String profit_participant_contact) {
		this.profit_participant_contact = profit_participant_contact;
	}
	/**
	 * @return the profit_participant_phone
	 */
	public String getProfit_participant_phone() {
		return profit_participant_phone;
	}
	/**
	 * @param profit_participant_phone the profit_participant_phone to set
	 */
	public void setProfit_participant_phone(String profit_participant_phone) {
		this.profit_participant_phone = profit_participant_phone;
	}
	/**
	 * @return the profit_participant_financial_contact
	 */
	public String getProfit_participant_financial_contact() {
		return profit_participant_financial_contact;
	}
	/**
	 * @param profit_participant_financial_contact the profit_participant_financial_contact to set
	 */
	public void setProfit_participant_financial_contact(String profit_participant_financial_contact) {
		this.profit_participant_financial_contact = profit_participant_financial_contact;
	}
	/**
	 * @return the profit_participant_financial_phone
	 */
	public String getProfit_participant_financial_phone() {
		return profit_participant_financial_phone;
	}
	/**
	 * @param profit_participant_financial_phone the profit_participant_financial_phone to set
	 */
	public void setProfit_participant_financial_phone(String profit_participant_financial_phone) {
		this.profit_participant_financial_phone = profit_participant_financial_phone;
	}
	/**
	 * @return the merc_bank_head_name
	 */
	public String getMerc_bank_head_name() {
		return merc_bank_head_name;
	}
	/**
	 * @param merc_bank_head_name the merc_bank_head_name to set
	 */
	public void setMerc_bank_head_name(String merc_bank_head_name) {
		this.merc_bank_head_name = merc_bank_head_name;
	}
	/**
	 * @return the merc_bank_head_code
	 */
	public String getMerc_bank_head_code() {
		return merc_bank_head_code;
	}
	/**
	 * @param merc_bank_head_code the merc_bank_head_code to set
	 */
	public void setMerc_bank_head_code(String merc_bank_head_code) {
		this.merc_bank_head_code = merc_bank_head_code;
	}
	/**
	 * @return the merc_bank_branch_name
	 */
	public String getMerc_bank_branch_name() {
		return merc_bank_branch_name;
	}
	/**
	 * @param merc_bank_branch_name the merc_bank_branch_name to set
	 */
	public void setMerc_bank_branch_name(String merc_bank_branch_name) {
		this.merc_bank_branch_name = merc_bank_branch_name;
	}
	/**
	 * @return the merc_bank_branch_code
	 */
	public String getMerc_bank_branch_code() {
		return merc_bank_branch_code;
	}
	/**
	 * @param merc_bank_branch_code the merc_bank_branch_code to set
	 */
	public void setMerc_bank_branch_code(String merc_bank_branch_code) {
		this.merc_bank_branch_code = merc_bank_branch_code;
	}
	/**
	 * @return the merc_account_open_type
	 */
	public String getMerc_account_open_type() {
		return merc_account_open_type;
	}
	/**
	 * @param merc_account_open_type the merc_account_open_type to set
	 */
	public void setMerc_account_open_type(String merc_account_open_type) {
		this.merc_account_open_type = merc_account_open_type;
	}
	/**
	 * @return the merc_account_name
	 */
	public String getMerc_account_name() {
		return merc_account_name;
	}
	/**
	 * @param merc_account_name the merc_account_name to set
	 */
	public void setMerc_account_name(String merc_account_name) {
		this.merc_account_name = merc_account_name;
	}
	/**
	 * @return the merc_account_no
	 */
	public String getMerc_account_no() {
		return merc_account_no;
	}
	/**
	 * @param merc_account_no the merc_account_no to set
	 */
	public void setMerc_account_no(String merc_account_no) {
		this.merc_account_no = merc_account_no;
	}
	/**
	 * @return the single_fee_rate
	 */
	public String getSingle_fee_rate() {
		return single_fee_rate;
	}
	/**
	 * @param single_fee_rate the single_fee_rate to set
	 */
	public void setSingle_fee_rate(String single_fee_rate) {
		this.single_fee_rate = single_fee_rate;
	}
	/**
	 * @return the single_fee_upper_limit
	 */
	public String getSingle_fee_upper_limit() {
		return single_fee_upper_limit;
	}
	/**
	 * @param single_fee_upper_limit the single_fee_upper_limit to set
	 */
	public void setSingle_fee_upper_limit(String single_fee_upper_limit) {
		this.single_fee_upper_limit = single_fee_upper_limit;
	}
	/**
	 * @return the single_fee_lower_limit
	 */
	public String getSingle_fee_lower_limit() {
		return single_fee_lower_limit;
	}
	/**
	 * @param single_fee_lower_limit the single_fee_lower_limit to set
	 */
	public void setSingle_fee_lower_limit(String single_fee_lower_limit) {
		this.single_fee_lower_limit = single_fee_lower_limit;
	}
	/**
	 * @return the profit_settle_date
	 */
	public String getProfit_settle_date() {
		return profit_settle_date;
	}
	/**
	 * @param profit_settle_date the profit_settle_date to set
	 */
	public void setProfit_settle_date(String profit_settle_date) {
		this.profit_settle_date = profit_settle_date;
	}
	/**
	 * @return the profit_floor_amount
	 */
	public String getProfit_floor_amount() {
		return profit_floor_amount;
	}
	/**
	 * @param profit_floor_amount the profit_floor_amount to set
	 */
	public void setProfit_floor_amount(String profit_floor_amount) {
		this.profit_floor_amount = profit_floor_amount;
	}
	/**
	 * @return the chl_fee_settle_date
	 */
	public String getChl_fee_settle_date() {
		return chl_fee_settle_date;
	}
	/**
	 * @param chl_fee_settle_date the chl_fee_settle_date to set
	 */
	public void setChl_fee_settle_date(String chl_fee_settle_date) {
		this.chl_fee_settle_date = chl_fee_settle_date;
	}
	/**
	 * @return the chl_fee_floor_amount
	 */
	public String getChl_fee_floor_amount() {
		return chl_fee_floor_amount;
	}
	/**
	 * @param chl_fee_floor_amount the chl_fee_floor_amount to set
	 */
	public void setChl_fee_floor_amount(String chl_fee_floor_amount) {
		this.chl_fee_floor_amount = chl_fee_floor_amount;
	}
	
	
}
