// default package
// Generated Jul 14, 2016 11:10:17 PM by Hibernate Tools 5.1.0.Alpha1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Applicationuserdetailshistory generated by hbm2java
 */
@Entity
@Table(name = "APPLICATIONUSERDETAILSHISTORY", catalog = "netgrains")
public class Applicationuserdetailshistory implements java.io.Serializable {

	private Long applicationuserdetailshistoryid;
	private Long applicationuserdetailsid;
	private String contactname;
	private Long representorganisation;
	private String telephonenumber;
	private String addressone;
	private String addresstwo;
	private String city;
	private String stateprovinceregion;
	private String postcode;
	private Long country;
	private String emailaddress;
	private Long createdbyid;
	private Long updatedbyid;
	private Date datecreated;
	private Date dateupdated;

	public Applicationuserdetailshistory() {
	}

	public Applicationuserdetailshistory(Long applicationuserdetailsid, String contactname, Long representorganisation,
			String telephonenumber, String addressone, String addresstwo, String city, String stateprovinceregion,
			String postcode, Long country, String emailaddress, Long createdbyid, Long updatedbyid, Date datecreated,
			Date dateupdated) {
		this.applicationuserdetailsid = applicationuserdetailsid;
		this.contactname = contactname;
		this.representorganisation = representorganisation;
		this.telephonenumber = telephonenumber;
		this.addressone = addressone;
		this.addresstwo = addresstwo;
		this.city = city;
		this.stateprovinceregion = stateprovinceregion;
		this.postcode = postcode;
		this.country = country;
		this.emailaddress = emailaddress;
		this.createdbyid = createdbyid;
		this.updatedbyid = updatedbyid;
		this.datecreated = datecreated;
		this.dateupdated = dateupdated;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "APPLICATIONUSERDETAILSHISTORYID", unique = true, nullable = false)
	public Long getApplicationuserdetailshistoryid() {
		return this.applicationuserdetailshistoryid;
	}

	public void setApplicationuserdetailshistoryid(Long applicationuserdetailshistoryid) {
		this.applicationuserdetailshistoryid = applicationuserdetailshistoryid;
	}

	@Column(name = "APPLICATIONUSERDETAILSID")
	public Long getApplicationuserdetailsid() {
		return this.applicationuserdetailsid;
	}

	public void setApplicationuserdetailsid(Long applicationuserdetailsid) {
		this.applicationuserdetailsid = applicationuserdetailsid;
	}

	@Column(name = "CONTACTNAME", length = 50)
	public String getContactname() {
		return this.contactname;
	}

	public void setContactname(String contactname) {
		this.contactname = contactname;
	}

	@Column(name = "REPRESENTORGANISATION")
	public Long getRepresentorganisation() {
		return this.representorganisation;
	}

	public void setRepresentorganisation(Long representorganisation) {
		this.representorganisation = representorganisation;
	}

	@Column(name = "TELEPHONENUMBER", length = 20)
	public String getTelephonenumber() {
		return this.telephonenumber;
	}

	public void setTelephonenumber(String telephonenumber) {
		this.telephonenumber = telephonenumber;
	}

	@Column(name = "ADDRESSONE", length = 50)
	public String getAddressone() {
		return this.addressone;
	}

	public void setAddressone(String addressone) {
		this.addressone = addressone;
	}

	@Column(name = "ADDRESSTWO", length = 50)
	public String getAddresstwo() {
		return this.addresstwo;
	}

	public void setAddresstwo(String addresstwo) {
		this.addresstwo = addresstwo;
	}

	@Column(name = "CITY", length = 50)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "STATEPROVINCEREGION", length = 50)
	public String getStateprovinceregion() {
		return this.stateprovinceregion;
	}

	public void setStateprovinceregion(String stateprovinceregion) {
		this.stateprovinceregion = stateprovinceregion;
	}

	@Column(name = "POSTCODE", length = 10)
	public String getPostcode() {
		return this.postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	@Column(name = "COUNTRY")
	public Long getCountry() {
		return this.country;
	}

	public void setCountry(Long country) {
		this.country = country;
	}

	@Column(name = "EMAILADDRESS")
	public String getEmailaddress() {
		return this.emailaddress;
	}

	public void setEmailaddress(String emailaddress) {
		this.emailaddress = emailaddress;
	}

	@Column(name = "CREATEDBYID")
	public Long getCreatedbyid() {
		return this.createdbyid;
	}

	public void setCreatedbyid(Long createdbyid) {
		this.createdbyid = createdbyid;
	}

	@Column(name = "UPDATEDBYID")
	public Long getUpdatedbyid() {
		return this.updatedbyid;
	}

	public void setUpdatedbyid(Long updatedbyid) {
		this.updatedbyid = updatedbyid;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATECREATED", length = 19)
	public Date getDatecreated() {
		return this.datecreated;
	}

	public void setDatecreated(Date datecreated) {
		this.datecreated = datecreated;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATEUPDATED", length = 19)
	public Date getDateupdated() {
		return this.dateupdated;
	}

	public void setDateupdated(Date dateupdated) {
		this.dateupdated = dateupdated;
	}

}